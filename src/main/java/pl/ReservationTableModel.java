package pl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

import dl.MessageData;
import bl.Customer;
import bl.Library;
import bl.Gadget;
import bl.Reservation;

public class ReservationTableModel extends AbstractTableModel implements Observer{
	
	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"Name", "Warteschlange", "Ausleihen", "Loeschen"};
	private Class<?>[] columnTypes = new Class<?>[] {String.class, Integer.class, JButton.class, JButton.class};
	private Library library;
	private Customer customer;
	private List<Reservation> reservations;

	
	public ReservationTableModel(Library library, Customer customer){
		this.library=library;
		this.customer=customer;
		this.reservations = library.getReservatonFor(customer, true);
		library.addObserver(this);
	}
	
	public void propagateUpdate(int pos) {
		fireTableDataChanged();
	}
	
	public String getColumnName(int col) {
        return columnNames[col];
    }
	
	public Class<?> getColumnClass(int columnIndex){
		return columnTypes[columnIndex];
	}

	@Override
	public void update(Observable obj, Object arg1) {
		MessageData data = (MessageData) arg1;
		if(!data.getTarget().equals("reservation")) return;
		
		int pos = reservations.indexOf((Reservation)data.getData());
		if(data.getType().equals("add")) {
			fireTableRowsInserted(pos, pos);
		} else if(data.getType().equals("update")) {
			fireTableRowsUpdated(pos, pos);			
		}
	}

	@Override
	public int getRowCount() {
		return reservations.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Reservation reservation = reservations.get(rowIndex);
		Gadget gadget = library.getGadget(reservation.getGadgetId());
		switch(columnIndex){
		case 0:
			return gadget.getName();
		case 1:
			return reservations.size();
		case 2:
			final JButton ausleihenButton = new JButton("Ja");
			
			if(library.getLoansFor(gadget, true).size() > 0){
				ausleihenButton.setEnabled(false);
				ausleihenButton.setText("Nein");
			}
			ausleihenButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					library.addLoan(gadget, customer);
					setCustomer(customer);
				}
			});
			return ausleihenButton;
		case 3:
			final JButton deleteButton = new JButton("Loeschen");
			deleteButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					reservation.setFinished(true);
					setCustomer(customer);
					propagateUpdate(rowIndex);
				}
			});
			return deleteButton;
		default:
			return "Error";
		}
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
		this.reservations = library.getReservatonFor(customer, true);
		fireTableDataChanged();
		if(reservations.size() > 0) {
			fireTableRowsUpdated(0, reservations.size()-1);
		}
	}
}
