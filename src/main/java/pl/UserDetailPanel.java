package pl;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import bl.Customer;
import bl.Gadget;
import bl.Library;
import bl.Loan;

import javax.swing.border.TitledBorder;
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
    private Customer customer;
	
	public UserDetailPanel(Library library){	
		customer = library.getCustomers().get(0);
		KeyListener borrowChecker =  new CheckBorrowKeyListener();
		KeyListener reservationChecker =  new CheckReservationKeyListener();

		TitledBorder title = BorderFactory.createTitledBorder(customer.getName());
		setBorder(title);
		setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel reservationTablePanel = new JPanel();
		add(reservationTablePanel);
		reservationTablePanel.setLayout(new BorderLayout(0, 0));
		
		JLabel reservationLabel = new JLabel();
		reservationLabel.setText("Reservationen ( "+library.getReservatonFor(customer, true).size()+" von 3)");
		reservationLabel.setVerticalAlignment(SwingConstants.TOP);
		reservationLabel.setHorizontalAlignment(SwingConstants.LEFT);
		reservationTablePanel.add(reservationLabel, BorderLayout.NORTH);
		
		initReservationTable(library);
		
		JScrollPane reservationScrollPane = new JScrollPane();
		reservationScrollPane.setViewportView(reservationTable);
		reservationTablePanel.add(reservationScrollPane);
		
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
				Gadget gadget = library.getGadget(newReservationTextField.getText());
				if(gadget == null){
					newBorrowButton.setToolTipText("Please check the gadgetId(consists of 19 numbers)");
					reservationValidationLabel.setText("Gadget with id: "+newReservationTextField.getText()+" not available");
				}else{
					library.addReservation(gadget, customer);
				}
			}
		});
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		validationPanel.add(horizontalStrut, BorderLayout.WEST);
		
		JPanel borrowTablePanel = new JPanel();
		add(borrowTablePanel);
		initBorrowTable(library);
		borrowTablePanel.setLayout(new BorderLayout(0, 0));
		
		JLabel borrowLabel = new JLabel("Ausleihen ( "+library.getLoansFor(customer, true).size()+" von 3)");
		borrowTablePanel.add(borrowLabel, BorderLayout.NORTH);
		
		JScrollPane borrowScrollPane = new JScrollPane();
		borrowScrollPane.setViewportView(borrowTable);
		borrowTablePanel.add(borrowScrollPane);
		
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
		
		newBorrowTextField = new ErrorUserTextField(new TextFieldGadgetIdValidator());
		newBorrowTextField.addKeyListener(borrowChecker);
		textFieldPanel1.add(newBorrowTextField, BorderLayout.CENTER);
		newBorrowTextField.setColumns(10);
		
		newBorrowButton = new JButton("Ausleihen");
		newBorrowButton.setEnabled(false);
		textFieldPanel1.add(newBorrowButton, BorderLayout.EAST);
		
		JPanel validationPanel1 = new JPanel();
		newBorrowPanel.add(validationPanel1);
		validationPanel1.setLayout(new BorderLayout(0, 0));
		
		borrowValidationLabel = new JLabel();
		borrowValidationLabel.setVerticalAlignment(SwingConstants.TOP);
		borrowValidationLabel.setHorizontalAlignment(SwingConstants.LEFT);
		validationPanel1.add(borrowValidationLabel);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		validationPanel1.add(horizontalStrut_1, BorderLayout.WEST);
		newBorrowButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Gadget gadget = library.getGadget(newBorrowTextField.getText());
				if(gadget == null){
					newBorrowButton.setToolTipText("Please check the gadgetId(consists of 19 numbers)");
					borrowValidationLabel.setText("Gadget with id: "+newBorrowTextField.getText()+" not available");
				}else{
					library.addLoan(gadget, customer);
				}
			}
		});
		
		for(Loan loan :library.getLoansFor(customer, true)){
			if(loan.isOverdue()){
				reservationValidationLabel.setText("Keine Reservation möglich: Überfällige vorhanden");
				newReservationButton.setEnabled(false);
				borrowValidationLabel.setText("Keine Ausleihe möglich: Überfällige vorhanden");
				newBorrowButton.setEnabled(false);
			}
		}
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
	
	public class CheckBorrowKeyListener extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent arg0) {
			if(newBorrowTextField.isValid()){
				newBorrowButton.setToolTipText("Ausleihen");
				newBorrowButton.setEnabled(true);
			}else{
				newBorrowButton.setToolTipText("Please check the gadgetId(consists of 19 numbers)");
				newBorrowButton.setEnabled(false);
			}
		}	
	}
	
	public class CheckReservationKeyListener extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent arg0) {
			if(newReservationTextField.isValid()){
				newReservationButton.setToolTipText("Reservation");
				newReservationButton.setEnabled(true);
			}else{
				newReservationButton.setToolTipText("Please check the gadgetId(consists of 19 numbers)");
				newReservationButton.setEnabled(false);
			}
		}	
	}
}
