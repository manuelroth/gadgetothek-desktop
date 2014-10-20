package pl;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JList;
import javax.swing.JButton;

import bl.Gadget;
import bl.Library;
import dl.GadgetTableModel;
import dl.LocalLibrary;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GadgetMasterFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField searchTextField;
    private JTable gadgetsTable = new JTable();
    private TableRowSorter<GadgetTableModel> sorter;
	private Library library = new Library(new LocalLibrary());
	private GadgetTableModel gadgetTableModel = new GadgetTableModel(library);

	/**
	 * Create the frame.
	 */
	public GadgetMasterFrame() {
		setTitle("Gadgets Biblio");
		setMinimumSize(new Dimension(540, 340));
		setSize(new Dimension(540, 340));
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel gadgetsTab = new JPanel();
		tabbedPane.addTab("Gadgets", null, gadgetsTab, null);
		gadgetsTab.setLayout(new BorderLayout(0, 0));
		
		JPanel gadgetsPanel = new JPanel();
		gadgetsTab.add(gadgetsPanel, BorderLayout.NORTH);
		gadgetsPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(gadgetsTable);
		gadgetsTab.add(scrollPane, BorderLayout.CENTER);
		initTable();
		scrollPane.setViewportView(gadgetsTable);
		searchTextField = new JTextField();
		searchTextField.setText("Suchen...");
		searchTextField.setToolTipText("Suchen...");
		gadgetsPanel.add(searchTextField);
		searchTextField.setColumns(10);
		
		JPanel buttonPanel = new JPanel();
		gadgetsPanel.add(buttonPanel);
		buttonPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton newGadgetButton = new JButton("Gadget erfassen");
		buttonPanel.add(newGadgetButton);
		newGadgetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GadgetDetailFrame window = new GadgetDetailFrame(new Gadget(), library, true);
			}
		});
		
		JButton editGadgetButton = new JButton("Gadget editieren");
		buttonPanel.add(editGadgetButton);
		editGadgetButton.setEnabled(false);
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
				GadgetDetailFrame window = new GadgetDetailFrame(gadget, library, false);
			}
		});
		
		JPanel ausleihenTab = new JPanel();
		tabbedPane.addTab("Ausleihen & Rückgabe", null, ausleihenTab, null);
	}
	
	private void initTable(){
		gadgetsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		gadgetsTable.setModel(gadgetTableModel);
		
		sorter = new TableRowSorter<GadgetTableModel>(gadgetTableModel);
		gadgetsTable.setRowSorter(sorter);
	}
}
