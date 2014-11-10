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
import javax.swing.table.TableRowSorter;

import bl.Customer;
import bl.Library;

public class UserMasterPanel extends JPanel {
	private CustomerTableModel customerTableModel;
    private TableRowSorter<CustomerTableModel> sorter;
    private JTextField searchTextField;
    private JTable customerTable = new JTable();
	private BorrowMasterPanel masterPanel;
	private Library library;

	public UserMasterPanel(Library library, BorrowMasterPanel masterPanel) {
		this.library = library;
		this.masterPanel = masterPanel;
		
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
		
		customerTable.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent arg0) {
				
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				JTable table = (JTable)arg0.getComponent();
				Customer customer = library.getCustomers().get(table.getSelectedRow());		
				
				
				masterPanel.setCurrentUserPanel(new UserDetailPanel(library, customer));
			}
		});
		
		sorter = new TableRowSorter<CustomerTableModel>(customerTableModel);
		customerTable.setRowSorter(sorter);
	}
}
