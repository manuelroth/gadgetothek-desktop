package pl;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import bl.Customer;
import bl.Gadget;
import bl.Library;
import bl.Loan;
import bl.Reservation;

import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import javax.swing.BoxLayout;
import javax.swing.CellRendererPane;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.GridLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;

public class UserDetailPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private JTable reservationTable;
    private TableRowSorter<ReservationTableModel> reservationSorter;
    private ReservationTableModel reservationTableModel;
    private JTextField newReservationTextField;
    private JTable borrowTable;
    private TableRowSorter<BorrowTableModel> borrowSorter;
    private BorrowTableModel borrowTableModel;
    private JTextField newBorrowTextField;
    private Customer customer;
	
	public UserDetailPanel(Library library){	
		customer = library.getCustomers().get(0);
		TitledBorder title = BorderFactory.createTitledBorder(customer.getName());
		setBorder(title);
		setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel ReservationTablePanel = new JPanel();
		add(ReservationTablePanel);
		ReservationTablePanel.setLayout(new BoxLayout(ReservationTablePanel, BoxLayout.Y_AXIS));
		
		JLabel reservationLabel = new JLabel("Reservationen (1 von n)");
		reservationLabel.setVerticalAlignment(SwingConstants.TOP);
		reservationLabel.setHorizontalAlignment(SwingConstants.LEFT);
		ReservationTablePanel.add(reservationLabel);
		
		initReservationTable(library);
		
		JScrollPane reservationScrollPane = new JScrollPane();
		reservationScrollPane.setViewportView(reservationTable);
		ReservationTablePanel.add(reservationScrollPane);
		
		JPanel NewReservationPanel = new JPanel();
		add(NewReservationPanel);
		NewReservationPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel newReservationLabel = new JLabel("Neue Reservation");
		NewReservationPanel.add(newReservationLabel, BorderLayout.NORTH);
		
		JLabel reservationIdLabel = new JLabel("Id");
		NewReservationPanel.add(reservationIdLabel, BorderLayout.WEST);
		
		newReservationTextField = new JTextField();
		NewReservationPanel.add(newReservationTextField);
		newReservationTextField.setColumns(10);
		
		JButton newReservationButton = new JButton("Reservation");
		NewReservationPanel.add(newReservationButton, BorderLayout.EAST);
		newReservationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Gadget gadget = library.getGadget(newReservationTextField.getText());
				library.addReservation(gadget, customer);
			}
		});
		
		JPanel panel = new JPanel();
		NewReservationPanel.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel reservationValidationLabel = new JLabel();
		panel.add(reservationValidationLabel, BorderLayout.CENTER);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut, BorderLayout.WEST);
		
		JPanel BorrowTablePanel = new JPanel();
		add(BorrowTablePanel);
		BorrowTablePanel.setLayout(new BoxLayout(BorrowTablePanel, BoxLayout.Y_AXIS));
		initBorrowTable(library);
		
		JLabel borrowLabel = new JLabel("Ausleihen (1 von n)");
		BorrowTablePanel.add(borrowLabel);
		
		JScrollPane borrowScrollPane = new JScrollPane();
		borrowScrollPane.setViewportView(borrowTable);
		BorrowTablePanel.add(borrowScrollPane);
		
		JPanel NewBorrowPanel = new JPanel();
		add(NewBorrowPanel);
		NewBorrowPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel newBorrowLabel = new JLabel("Neue Ausleihe");
		NewBorrowPanel.add(newBorrowLabel, BorderLayout.NORTH);
		
		JLabel borrowIdLabel = new JLabel("id");
		NewBorrowPanel.add(borrowIdLabel, BorderLayout.WEST);
		
		newBorrowTextField = new JTextField();
		NewBorrowPanel.add(newBorrowTextField, BorderLayout.CENTER);
		newBorrowTextField.setColumns(10);
		
		JButton newBorrowButton = new JButton("Ausleihen");
		NewBorrowPanel.add(newBorrowButton, BorderLayout.EAST);
		newBorrowButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Gadget gadget = library.getGadget(newBorrowTextField.getText());
				library.addLoan(gadget, customer);
			}
		});
		
		JPanel panel_1 = new JPanel();
		NewBorrowPanel.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JLabel borrowValidationLabel = new JLabel();
		borrowValidationLabel.setVerticalAlignment(SwingConstants.TOP);
		borrowValidationLabel.setHorizontalAlignment(SwingConstants.LEFT);
		panel_1.add(borrowValidationLabel);
		
		for(Loan loan :library.getLoansFor(customer, true)){
			if(loan.isOverdue()){
				reservationValidationLabel.setText("Keine Reservation möglich: Überfällige vorhanden");
				newReservationButton.setEnabled(false);
				newBorrowTextField.setText("Keine Ausleihe möglich: Überfällige vorhanden");
				newBorrowButton.setEnabled(false);
			}
		}
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		panel_1.add(horizontalStrut_1, BorderLayout.WEST);
	}
	
	void initReservationTable(Library library){
		reservationTableModel = new ReservationTableModel(library);
		reservationTable = new JTable();
		reservationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		reservationTable.setModel(reservationTableModel);
		reservationSorter = new TableRowSorter<ReservationTableModel>(reservationTableModel);
		reservationTable.setRowSorter(reservationSorter);
		TableCellRenderer buttonRenderer = new JTableButtonRenderer();
		reservationTable.getColumn("Ausleihen").setCellRenderer(buttonRenderer);
		reservationTable.getColumn("Löschen").setCellRenderer(buttonRenderer);
		reservationTable.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int column = reservationTable.getColumnModel().getColumnIndexAtX(e.getX());
				int row = e.getY()/reservationTable.getRowHeight();
				if (row < reservationTable.getRowCount() && row >= 0 && column < reservationTable.getColumnCount() && column >= 0) {
	                Object value = reservationTable.getValueAt(row, column);
	                if (value instanceof JButton) {
	                    ((JButton)value).doClick();
	                }
	            }
			}
		});
	}
	
	void initBorrowTable(Library library){
		borrowTable = new JTable();
		borrowTableModel = new BorrowTableModel(library);
		borrowSorter = new TableRowSorter<BorrowTableModel>(borrowTableModel);
		borrowTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		borrowTable.setModel(borrowTableModel);
		borrowTable.setRowSorter(borrowSorter);	
		TableCellRenderer buttonRenderer = new JTableButtonRenderer();
		borrowTable.getColumn("Rücknahme").setCellRenderer(buttonRenderer);
		borrowTable.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int column = borrowTable.getColumnModel().getColumnIndexAtX(e.getX());
				int row = e.getY()/borrowTable.getRowHeight();
				if (row < borrowTable.getRowCount() && row >= 0 && column < borrowTable.getColumnCount() && column >= 0) {
	                Object value = borrowTable.getValueAt(row, column);
	                if (value instanceof JButton) {
	                    ((JButton)value).doClick();
	                }
	            }
			}
		});
	}
	
	private static class JTableButtonRenderer implements TableCellRenderer {        
        @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JButton button = (JButton)value;
            return button;  
        }
    }
}
