package pl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowFilter.ComparisonType;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import javax.swing.JButton;

import bl.Customer;
import bl.Gadget;
import bl.Library;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class GadgetMasterPanel extends JPanel {
	private JTextField searchTextField;
    private JTable gadgetsTable = new JTable();
    private TableRowSorter<GadgetTableModel> sorter;
	private GadgetTableModel gadgetTableModel;
	private GadgetDetailFrame createGadgetDetailFrame = null;
	private Map<Gadget, GadgetDetailFrame> editGadgetDetailFrames = new HashMap<Gadget, GadgetDetailFrame>();

	/**
	 * Create the frame.
	 */
	public GadgetMasterPanel(Library library) {
		gadgetTableModel = new GadgetTableModel(library);

		setMinimumSize(new Dimension(540, 340));
		setSize(new Dimension(540, 340));
		setBounds(100, 100, 450, 300);
		initTable();
		
		JPanel gadgetsPanel = new JPanel();
		gadgetsPanel.setLayout(new GridLayout(1, 0, 0, 0));
		searchTextField = new JTextField();
		searchTextField.setText("Suchen...");
		searchTextField.setToolTipText("Suchen...");
		gadgetsPanel.add(searchTextField);
		searchTextField.setColumns(10);
		
		searchTextField.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusGained(FocusEvent e) {
				searchTextField.setText("");
			}
			
		});
		searchTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if (searchTextField.getText().length() != 0)
				{
					sorter = (TableRowSorter<GadgetTableModel>) gadgetsTable.getRowSorter();
					try  
			    	{  
						Integer searchParameter = Integer.parseInt(searchTextField.getText());			         
						sorter.setRowFilter(RowFilter.numberFilter(ComparisonType.EQUAL, searchParameter, 0, 3));
			    	} 
					catch(NumberFormatException nfe)  
			    	{  					
						sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchTextField.getText(), 1, 2, 4)); 
			    	}  
				}
				else
					sorter.setRowFilter(null);
			}
		});
		setLayout(new BorderLayout(0, 0));
		
		
		JPanel buttonPanel = new JPanel();
		gadgetsPanel.add(buttonPanel);
		add(gadgetsPanel, BorderLayout.NORTH);
		buttonPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton newGadgetButton = new JButton("Gadget erfassen");
		buttonPanel.add(newGadgetButton);
		newGadgetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (createGadgetDetailFrame == null)
					createGadgetDetailFrame = new GadgetDetailFrame(new Gadget(), library, true);
				else
				{
					createGadgetDetailFrame.setFocus(true);
				}
			}
		});
		
		JButton editGadgetButton = new JButton("Gadget editieren");
		buttonPanel.add(editGadgetButton);
		editGadgetButton.setEnabled(false);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(gadgetsTable);
		add(scrollPane);
		gadgetsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				editGadgetButton.setEnabled(true);
			}
		});
		
				editGadgetButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int row = gadgetsTable.convertRowIndexToModel(gadgetsTable.getSelectedRow());
						Gadget gadget = (Gadget) gadgetTableModel.getGadget(row);
						if (editGadgetDetailFrames.containsKey(gadget))
							editGadgetDetailFrames.get(gadget).setFocus(true);
						else
							editGadgetDetailFrames.put(gadget, new GadgetDetailFrame(gadget, library, false));						
					}
				});
	}
	
	private void initTable(){
		gadgetsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		gadgetsTable.setModel(gadgetTableModel);
		sorter = new TableRowSorter<GadgetTableModel>(gadgetTableModel);
		gadgetsTable.setRowSorter(sorter);	
	}
}
