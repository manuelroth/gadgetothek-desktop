package pl;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import bl.Customer;
import bl.Gadget;
import bl.Library;
import bl.Loan;
import bl.Reservation;

import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import javax.swing.BoxLayout;
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.Box;

import pl.validation.ErrorUserTextField;
import pl.validation.TextFieldGadgetIdValidator;


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
    private JButton newBorrowButton;
    private JButton newReservationButton;
    private JLabel borrowValidationLabel;
    private JLabel reservationValidationLabel;
	private JLabel reservationLabel;
	private Customer customer;
	private Library library;
	private JLabel borrowLabel;
	private JPanel reservationTablePanel;
	private JPanel borrowTablePanel;
    
	public UserDetailPanel(Library library){
		Customer defaultCustomer = library.getCustomers().get(0);
		this.customer = defaultCustomer;
		this.library = library;

		initComponents(library);
		initReservationTable(library, defaultCustomer);
		initBorrowTable(library, defaultCustomer);
		setCustomer(defaultCustomer);
		warnIfOverdueBorrowedGadgets(library);
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
		
		setBorder(BorderFactory.createTitledBorder(customer.getName()));
		
		reservationLabel.setText("Reservationen ( " + library.getReservatonFor(customer, true).size() + " von 3)");
		borrowLabel.setText("Ausleihen ( " + library.getLoansFor(customer, true).size() + " von 3)");
		borrowValidationLabel.setText("");
		reservationValidationLabel.setText("");
		
		borrowTableModel.setCustomer(customer);
		reservationTableModel.setCustomer(customer);
		
		invalidate();
	}

	private void warnIfOverdueBorrowedGadgets(Library library) {
		for(Loan loan :library.getLoansFor(customer, true)){
			if(loan.isOverdue()){
				reservationValidationLabel.setText("Keine Reservation moeglich: Ueberfaellige vorhanden");
				newReservationButton.setEnabled(false);
				borrowValidationLabel.setText("Keine Ausleihe moeglich: Ueberfaellige vorhanden");
				newBorrowButton.setEnabled(false);
			}
		}
	}

	private void initComponents(Library library) {
		setLayout(new GridLayout(0, 1, 0, 0));
		
		reservationTablePanel = new JPanel();
		add(reservationTablePanel);
		reservationTablePanel.setLayout(new BorderLayout(0, 0));
		
		reservationLabel = new JLabel();
		reservationLabel.setVerticalAlignment(SwingConstants.TOP);
		reservationLabel.setHorizontalAlignment(SwingConstants.LEFT);
		reservationTablePanel.add(reservationLabel, BorderLayout.NORTH);
	
		JPanel newReservationPanel = new JPanel();
		add(newReservationPanel);
		newReservationPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel labelPanel = new JPanel();
		newReservationPanel.add(labelPanel);
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));
		
		JLabel newReservationLabel = new JLabel("Neue Reservation");
		labelPanel.add(newReservationLabel);
		
		JPanel textFieldPanel = new JPanel();
		newReservationPanel.add(textFieldPanel);
		textFieldPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel reservationIdLabel = new JLabel("Id");
		textFieldPanel.add(reservationIdLabel, BorderLayout.WEST);
		
		KeyListener reservationChecker =  new CheckReservationKeyListener();
		newReservationTextField = new ErrorUserTextField(new TextFieldGadgetIdValidator());
		newReservationTextField.addKeyListener(reservationChecker);
		textFieldPanel.add(newReservationTextField, BorderLayout.CENTER);
		newReservationTextField.setColumns(10);
		
		JPanel validationPanel = new JPanel();
		newReservationPanel.add(validationPanel);
		validationPanel.setLayout(new BorderLayout(0, 0));
		
		reservationValidationLabel = new JLabel();
		validationPanel.add(reservationValidationLabel, BorderLayout.CENTER);
		
		newReservationButton = new JButton("Reservation");
		newReservationButton.setEnabled(false);
		textFieldPanel.add(newReservationButton, BorderLayout.EAST);
		newReservationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reserveGadget();
			}
		});
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		validationPanel.add(horizontalStrut, BorderLayout.WEST);
		
		borrowTablePanel = new JPanel();
		add(borrowTablePanel);
		initBorrowTable(library, this.customer);
		borrowTablePanel.setLayout(new BorderLayout(0, 0));
		
		borrowLabel = new JLabel();
		borrowTablePanel.add(borrowLabel, BorderLayout.NORTH);
		
		JPanel newBorrowPanel = new JPanel();
		add(newBorrowPanel);
		newBorrowPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel labelPanel1 = new JPanel();
		newBorrowPanel.add(labelPanel1);
		labelPanel1.setLayout(new BoxLayout(labelPanel1, BoxLayout.X_AXIS));
		
		JLabel newBorrowLabel = new JLabel("Neue Ausleihe");
		labelPanel1.add(newBorrowLabel);
		
		JPanel textFieldPanel1 = new JPanel();
		newBorrowPanel.add(textFieldPanel1);
		textFieldPanel1.setLayout(new BorderLayout(0, 0));
		
		JLabel borrowIdLabel = new JLabel("Id");
		textFieldPanel1.add(borrowIdLabel, BorderLayout.WEST);
		
		KeyListener borrowChecker =  new CheckBorrowKeyListener();
		newBorrowTextField = new ErrorUserTextField(new TextFieldGadgetIdValidator());
		newBorrowTextField.addKeyListener(borrowChecker);
		textFieldPanel1.add(newBorrowTextField, BorderLayout.CENTER);
		newBorrowTextField.setColumns(10);
		
		newBorrowButton = new JButton("Ausleihen");
		newBorrowButton.setEnabled(false);
		textFieldPanel1.add(newBorrowButton, BorderLayout.EAST);
		newBorrowButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				borrowGadget();
			}

			
		});
		
		JPanel validationPanel1 = new JPanel();
		newBorrowPanel.add(validationPanel1);
		validationPanel1.setLayout(new BorderLayout(0, 0));
		
		borrowValidationLabel = new JLabel();
		borrowValidationLabel.setVerticalAlignment(SwingConstants.TOP);
		borrowValidationLabel.setHorizontalAlignment(SwingConstants.LEFT);
		validationPanel1.add(borrowValidationLabel);
	}
	
	private void reserveGadget() {
		List<Reservation> reservedItems = library.getReservatonFor(customer, true);
		Gadget gadget = library.getGadget(newReservationTextField.getText());
		if (gadget == null) {
			newBorrowButton.setToolTipText("Please check the gadgetId(consists of 19 numbers)");
			reservationValidationLabel.setText("Gadget with id: " + newReservationTextField.getText() + " not available");
		} else if (reservedItems.size() >= 3) {
			reservationValidationLabel.setText("You can not reserve more than three gadgets");
		}else {
			library.addReservation(gadget, customer);
			setCustomer(customer);
		}
	}
	
	private void borrowGadget() {
		borrowValidationLabel.setText("");
		Gadget gadget = library.getGadget(newBorrowTextField.getText());
		
		boolean hasOverdue = false;
		List<Loan> loanedItems = library.getLoansFor(customer, true);
		for (Loan loan : loanedItems) {
			if (loan.isOverdue()) {
				hasOverdue = true;
			}
		}
		
		if (gadget == null) {
			newBorrowButton.setToolTipText("Please check the gadgetId(consists of 19 numbers)");
			borrowValidationLabel.setText("Gadget with id: " + newBorrowTextField.getText() + " not available");
		} else if (hasOverdue) {
			borrowValidationLabel.setText("Please return overdue gadgets, before borrowing a new gadget");
		} else if (loanedItems.size() >= 3) {
			borrowValidationLabel.setText("You can not borrow more than three gadgets");
		} else if (!library.getLoansFor(gadget, true).isEmpty()) {
			String customerId = loanedItems.get(0).getCustomerId();
			borrowValidationLabel.setText("Is already lent to " + library.getCustomer(customerId).getName());
		} else {
			library.addLoan(gadget, customer);
			setCustomer(customer);
		}
	}
	
	private void initReservationTable(Library library, Customer customer){
		reservationTableModel = new ReservationTableModel(library, customer);
		reservationTable = new JTable();
		reservationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		reservationTable.setModel(reservationTableModel);
		reservationSorter = new TableRowSorter<ReservationTableModel>(reservationTableModel);
		reservationTable.setRowSorter(reservationSorter);
		TableCellRenderer buttonRenderer = new JTableButtonRenderer();
		reservationTable.getColumn("Ausleihen").setCellRenderer(buttonRenderer);
		reservationTable.getColumn("Loeschen").setCellRenderer(buttonRenderer);
		reservationTable.setAutoCreateColumnsFromModel(false);
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
		
		JScrollPane reservationScrollPane = new JScrollPane(reservationTable);
		reservationTablePanel.add(reservationScrollPane);
	}
	
	private void initBorrowTable(Library library, Customer customer){
		borrowTable = new JTable();
		borrowTableModel = new BorrowTableModel(library, customer);
		borrowSorter = new TableRowSorter<BorrowTableModel>(borrowTableModel);
		borrowTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		borrowTable.setModel(borrowTableModel);
		borrowTable.setRowSorter(borrowSorter);	
		borrowTable.setAutoCreateColumnsFromModel(false);
		TableCellRenderer buttonRenderer = new JTableButtonRenderer();
		borrowTable.getColumn("Ruecknahme").setCellRenderer(buttonRenderer);
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
		
		JScrollPane borrowScrollPane = new JScrollPane(borrowTable);
		borrowTablePanel.add(borrowScrollPane);
	}

	private static class JTableButtonRenderer implements TableCellRenderer {        
        @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JButton button = (JButton)value;
            return button;  
        }
    }
	
	private class CheckBorrowKeyListener extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent arg0) {
			if (newBorrowTextField.isValid()) {
				newBorrowButton.setToolTipText("Ausleihen");
				newBorrowButton.setEnabled(true);
			} else {
				newBorrowButton
						.setToolTipText("Please check the gadgetId(consists of 19 numbers)");
				newBorrowButton.setEnabled(false);
			}
		}
	}
	
	private class CheckReservationKeyListener extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent arg0) {
			if (newReservationTextField.isValid()) {
				newReservationButton.setToolTipText("Reservation");
				newReservationButton.setEnabled(true);
			} else {
				newReservationButton
						.setToolTipText("Please check the gadgetId(consists of 19 numbers)");
				newReservationButton.setEnabled(false);
			}
		}
	}
}
