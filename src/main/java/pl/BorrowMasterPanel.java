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
	private CustomerTableModel customerTableModel;
    private TableRowSorter<CustomerTableModel> sorter;

	public BorrowMasterPanel(Library library) {
		setBounds(100, 100, 450, 300);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel = new JPanel();
		add(panel);
		
		JPanel userDetailPanel = new UserDetailPanel(library);
		add(userDetailPanel);
		
	}
}
