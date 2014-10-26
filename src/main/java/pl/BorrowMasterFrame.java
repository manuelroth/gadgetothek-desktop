package pl;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import dl.GadgetTableModel;

public class BorrowMasterFrame extends JFrame {

	private JPanel contentPane;
	private JTextField searchTextField;
	private JTable customerTable = new JTable();

	public BorrowMasterFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel searchPanel = new JPanel();
		contentPane.add(searchPanel, BorderLayout.NORTH);
		searchPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		searchTextField = new JTextField();
		searchTextField.setToolTipText("Suchen...");
		searchTextField.setText("Suchen...");
		searchTextField.setColumns(10);
		searchPanel.add(searchTextField);
		
		customerTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"KundenId", "Name", "Reservationen", "Ausleihen", "\u00DCberf\u00E4llige"
			}
		));
		contentPane.add(customerTable, BorderLayout.WEST);
	}
}
