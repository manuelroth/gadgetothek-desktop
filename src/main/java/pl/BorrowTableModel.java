package pl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
	private Gadget gadget;
	private List<Loan> loans;
	
	public BorrowTableModel(Library library, Customer customer){
		this.library=library;
		this.customer = null;
		this.loans = new ArrayList<Loan>();
		library.addObserver(this);
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
		
		int pos = loans.indexOf((Loan)data.getData());
		if(data.getType().equals("add")) {
			fireTableRowsInserted(pos, pos);
		} else if(data.getType().equals("update")) {
			fireTableRowsUpdated(pos, pos);			
		}
	}

	@Override
	public int getRowCount() {
		return loans.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Loan loan = loans.get(rowIndex);
		gadget = library.getGadget(loan.getGadgetId());
		

		System.out.println("getValueAt on BorrowTableModel = " + this.customer.getName());
		
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
					if(!library.getReservatonFor(gadget, true).isEmpty()){
						JOptionPane.showMessageDialog(null, "Gadget is reserved by "+library.getCustomer(library.getReservatonFor(gadget, true).get(0).getCustomerId()));
					}
					loan.returnCopy();
					fireTableDataChanged();
				}
			});
			return returnButton;
		default:
			return "Error";
		}
	}
	
	public void setCustomer(Customer customer) {
		System.out.println("setCustomer on BorrowTableModel = " + customer.getName());
		this.customer = customer;
		this.loans = library.getLoansFor(this.customer, true);
		fireTableDataChanged();
	}
	
}
