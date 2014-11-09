package pl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import dl.MessageData;
import bl.Customer;
import bl.Library;
import bl.Gadget;
import bl.Reservation;

public class ReservationTableModel extends AbstractTableModel implements Observer{
	
	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"Name", "Warteschlange", "Ausleihen", "Löschen"};
	private Class<?>[] columnTypes = new Class<?>[] {String.class, Integer.class, JButton.class, JButton.class};
	private Library library;
	private Customer dummyCustomer;

	
	public ReservationTableModel(Library library){
		this.library=library;
		library.addObserver(this);
		dummyCustomer = library.getCustomers().get(1);
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
		
		int pos = library.getReservatonFor(dummyCustomer, true).indexOf((Reservation)data.getData());
		if(data.getType().equals("add")) {
			fireTableRowsInserted(pos, pos);
		} else if(data.getType().equals("update")) {
			fireTableRowsUpdated(pos, pos);			
		}
	}

	@Override
	public int getRowCount() {
		return library.getReservatonFor(dummyCustomer, true).size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Reservation reservation = library.getReservatonFor(dummyCustomer, true).get(rowIndex);
		Gadget gadget = library.getGadget(reservation.getGadgetId());
		switch(columnIndex){
		case 0:
			return gadget.getName();
		case 1:
			return library.getReservatonFor(gadget, true).size();
		case 2:
			final JButton ausleihenButton = new JButton("Ja");
			if((library.getReservatonFor(gadget, true).size()) > 0){
				ausleihenButton.setEnabled(false);
				ausleihenButton.setText("Nein");
			}
			ausleihenButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(ausleihenButton), 
                            "Ausleihen Button clicked for row "+rowIndex);
					library.addLoan(gadget, dummyCustomer);
				}
			});
			return ausleihenButton;
		case 3:
			final JButton deleteButton = new JButton("Löschen");
			deleteButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(deleteButton), 
                            "Löschen Button clicked for row "+rowIndex);
					}
				//ToDo delete Reservation
			});
			return deleteButton;
		default:
			return "Error";
		}
	}
}
