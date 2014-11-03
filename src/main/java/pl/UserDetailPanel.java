package pl;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import bl.Library;

import javax.swing.border.TitledBorder;
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
	
	public UserDetailPanel(Library library){		
		TitledBorder title = BorderFactory.createTitledBorder("CustomerName");
		setBorder(title);
		setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel ReservationTablePanel = new JPanel();
		add(ReservationTablePanel);
		ReservationTablePanel.setLayout(new BoxLayout(ReservationTablePanel, BoxLayout.Y_AXIS));
		
		JLabel reservationLabel = new JLabel("Reservationen (1 von n)");
		reservationLabel.setVerticalAlignment(SwingConstants.TOP);
		reservationLabel.setHorizontalAlignment(SwingConstants.LEFT);
		ReservationTablePanel.add(reservationLabel);

		reservationTableModel = new ReservationTableModel(library);
		reservationTable = new JTable();
		reservationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		reservationTable.setModel(reservationTableModel);
		reservationSorter = new TableRowSorter<ReservationTableModel>(reservationTableModel);
		reservationTable.setRowSorter(reservationSorter);
		
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
		
		JPanel panel = new JPanel();
		NewReservationPanel.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel reservationValidationLabel = new JLabel("Validation message");
		panel.add(reservationValidationLabel, BorderLayout.CENTER);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut, BorderLayout.WEST);
		
		JPanel BorrowTablePanel = new JPanel();
		add(BorrowTablePanel);
		BorrowTablePanel.setLayout(new BoxLayout(BorrowTablePanel, BoxLayout.Y_AXIS));
		borrowTable = new JTable();
		borrowTableModel = new BorrowTableModel(library);
		borrowSorter = new TableRowSorter<BorrowTableModel>(borrowTableModel);
		borrowTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		borrowTable.setModel(borrowTableModel);
		borrowTable.setRowSorter(borrowSorter);
		
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
		
		JPanel panel_1 = new JPanel();
		NewBorrowPanel.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JLabel borrowValidationLabel = new JLabel("Validation message");
		borrowValidationLabel.setVerticalAlignment(SwingConstants.TOP);
		borrowValidationLabel.setHorizontalAlignment(SwingConstants.LEFT);
		panel_1.add(borrowValidationLabel);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		panel_1.add(horizontalStrut_1, BorderLayout.WEST);
		

	}
}
