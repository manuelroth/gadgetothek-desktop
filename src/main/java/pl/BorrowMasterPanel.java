package pl;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableRowSorter;

import bl.Library;

public class BorrowMasterPanel extends JPanel {
	private JTextField searchTextField;
	private JTable customerTable = new JTable();
	private CustomerTableModel customerTableModel;
    private TableRowSorter<CustomerTableModel> sorter;

	public BorrowMasterPanel(Library library) {
		customerTableModel = new CustomerTableModel(library);
		setBounds(100, 100, 450, 300);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));
		initTable();
		
		JPanel searchPanel = new JPanel();
		add(searchPanel, BorderLayout.NORTH);
		searchPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		searchTextField = new JTextField();
		searchTextField.setToolTipText("Suchen...");
		searchTextField.setText("Suchen...");
		searchTextField.setColumns(10);
		searchPanel.add(searchTextField);
		add(customerTable, BorderLayout.WEST);
	}
	
	private void initTable(){
		customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		customerTable.setModel(customerTableModel);
		
		sorter = new TableRowSorter<CustomerTableModel>(customerTableModel);
		customerTable.setRowSorter(sorter);
	}
}
