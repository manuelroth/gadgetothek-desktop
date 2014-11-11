package pl;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

import dl.MessageData;
import bl.Customer;
import bl.Library;
import bl.Gadget;
import bl.Loan;
import bl.Reservation;

public class CustomerTableModel extends AbstractTableModel implements Observer{
	
	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"KundenId", "Name", "Reservationen", "Ausleihen", "Ueberfaellige"};
	private Library library;
	private List<Customer> customers;

	
	public CustomerTableModel(Library library){
		this.library=library;
		this.customers = library.getCustomers();
		library.addObserver(this);
	}
	
	public void propagateUpdate(int pos) {
		fireTableDataChanged();
	}
	
	public String getColumnName(int col) {
        return columnNames[col];
    }

	@Override
	public void update(Observable obj, Object arg1) {
		MessageData data = (MessageData) arg1;
		if (data.getData() instanceof Customer){
			int pos = customers.indexOf((Customer)data.getData());
			fireTableRowsUpdated(pos, pos);			
		}
	}

	@Override
	public int getRowCount() {
		return customers.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Customer customer = customers.get(rowIndex);
		switch(columnIndex){
		case 0:
			return customer.getStudentNumber();
		case 1:
			return customer.getName();
		case 2:
			List<Reservation> reservations = library.getReservatonFor(customer, true);
			List<String> reservedGadgetNames = new ArrayList<String>();
			for(Reservation reservation : reservations) {
				String reservedGadgetId = reservation.getGadgetId();
				Gadget reservedGadget = library.getGadget(reservedGadgetId);
				reservedGadgetNames.add(reservedGadget.getName());
			}
			return String.join(",", reservedGadgetNames);
		case 3:
			List<Loan> borrowedGadgets = library.getLoansFor(customer, true);
			List<String> borrowedGadgetNames = new ArrayList<String>();
			for(Loan loan : borrowedGadgets) {
				String borrowedGadgetId = loan.getGadgetId();
				Gadget borrowedGadget = library.getGadget(borrowedGadgetId);
				borrowedGadgetNames.add(borrowedGadget.getName());
			}
			return String.join(",", borrowedGadgetNames);
		case 4:
			return null;
		default:
			return null;
		}
	}
	
	public Gadget getGadget(int rowIndex){
		return library.getGadgets().get(rowIndex);
	}
}
