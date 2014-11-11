package pl;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowFilter.ComparisonType;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;

import bl.Customer;
import bl.Library;

public class UserMasterPanel extends JPanel {
	private CustomerTableModel customerTableModel;
    private TableRowSorter<CustomerTableModel> sorter;
    private JTextField searchTextField;
    private JTable customerTable = new JTable();
	private UserDetailPanel userDetailPanel;
	private Library library;

	public UserMasterPanel(Library library, JPanel userDetailPanel) {
		this.library = library;
		this.userDetailPanel = (UserDetailPanel)userDetailPanel;
		
		customerTableModel = new CustomerTableModel(library);
		setBounds(100, 100, 450, 300);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		initTable();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new GridLayout(1, 1, 0, 0));
			
		
		searchTextField = new JTextField();
 		searchTextField.setToolTipText("Suchen...");
 		searchTextField.setText("Suchen...");
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
 					sorter = (TableRowSorter<CustomerTableModel>) customerTable.getRowSorter();
 					sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchTextField.getText(), 0, 1)); 
 			    }
 				else
 					sorter.setRowFilter(null);
 			}
 			@Override
 			public void keyTyped(KeyEvent arg0) {
 			}
 		});
 		searchPanel.add(searchTextField);
 		add(searchPanel);
 		
 		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(customerTable);
		add(scrollPane);		
 				
	}
	
	private void initTable(){
		customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		customerTable.setModel(customerTableModel);
		
		customerTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int customerIndex = customerTable.convertRowIndexToModel(customerTable.getSelectedRow());
				Customer customer = library.getCustomers().get(customerIndex);
				System.out.println("Selected customer " + customerIndex + ":" + customer.getName());
				userDetailPanel.setCustomer(customer);
				
			}
		});
		
		sorter = new TableRowSorter<CustomerTableModel>(customerTableModel);
		customerTable.setRowSorter(sorter);
	}
}
