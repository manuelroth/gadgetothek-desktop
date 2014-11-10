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

import bl.Gadget;
import bl.Library;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GadgetMasterPanel extends JPanel {
	private JTextField searchTextField;
    private JTable gadgetsTable = new JTable();
    private TableRowSorter<GadgetTableModel> sorter;
	private GadgetTableModel gadgetTableModel;
	GadgetDetailFrame gadgetDetailFrame = null;

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
		
		searchTextField.addFocusListener(new FocusListener()
		{
			@Override
			public void focusGained(FocusEvent e) {
				searchTextField.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {				
			}			
		});
		searchTextField.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
			}

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

			@Override
			public void keyTyped(KeyEvent arg0) {
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
				if (gadgetDetailFrame == null)
					gadgetDetailFrame = new GadgetDetailFrame(new Gadget(), library, true);
				else
				{
					gadgetDetailFrame = new GadgetDetailFrame(new Gadget(), library, true);
					//gadgetDetailFrame.setFocus(true);
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
						int row = gadgetsTable.getSelectedRow();
						Gadget gadget = (Gadget) gadgetTableModel.getGadget(row);
						if (gadgetDetailFrame == null)
							gadgetDetailFrame = new GadgetDetailFrame(gadget, library, false);
						else
						{
							gadgetDetailFrame = new GadgetDetailFrame(gadget, library, false);
							//gadgetDetailFrame.setFocus(true);
						}
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
