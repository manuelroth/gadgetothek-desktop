package pl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import dl.MessageData;
import bl.Customer;
import bl.Library;
import bl.Gadget;
import bl.Loan;
import bl.Reservation;

public class BorrowTableModel extends AbstractTableModel implements Observer{
	
	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"Name", "Ausgeliehen am", "Zurueck bis", "Faellig", "Reserviert", "Ruecknahme"};
	private Class<?>[] columnTypes = new Class<?>[] {String.class, String.class, String.class, Boolean.class, Boolean.class, JButton.class};
	private Library library;
	private Customer customer;
	
	public BorrowTableModel(Library library, Customer customer){
		this.library=library;
		library.addObserver(this);
		this.customer = customer;
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
		if(!data.getTarget().equals("loan")) return;
		
		int pos = library.getLoansFor(customer, true).indexOf((Loan)data.getData());
		if(data.getType().equals("add")) {
			fireTableRowsInserted(pos, pos);
		} else if(data.getType().equals("update")) {
			fireTableRowsUpdated(pos, pos);			
		}
	}

	@Override
	public int getRowCount() {
		return library.getLoansFor(customer, true).size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Loan loan = library.getLoansFor(customer, true).get(rowIndex);
		Gadget gadget = library.getGadget(loan.getGadgetId());
		
		switch(columnIndex){
		case 0:
			return gadget.getName();
		case 1:
			return loan.getPickupDate().toLocaleString();
		case 2:
			return loan.overDueDate().toLocaleString();
		case 3:
			if(loan.isOverdue()){
				return true;
			}
			return false;
		case 4:
			if(library.getReservatonFor(gadget, true).isEmpty()){
				return false;
			}
			return true;
		case 5:
			final JButton returnButton = new JButton("Rueckgabe");
			returnButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					loan.returnCopy();
					propagateUpdate(rowIndex);
				}
			});
			return returnButton;
		default:
			return "Error";
		}
	}
}
