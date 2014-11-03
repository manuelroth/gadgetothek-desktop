package pl;

import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

import dl.MessageData;
import bl.Library;
import bl.Gadget;

public class ReservationTableModel extends AbstractTableModel implements Observer{
	
	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"Name", "Warteschlange", "Ausleihen", "Löschen"};
	private Library library;

	
	public ReservationTableModel(Library library){
		this.library=library;
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
		if(!data.getTarget().equals("reservation")) return;
		
		int pos = library.getGadgets().indexOf((Gadget)data.getData());
		if(data.getType().equals("add")) {
			fireTableRowsInserted(pos, pos);
		} else if(data.getType().equals("update")) {
			fireTableRowsUpdated(pos, pos);			
		}
	}

	@Override
	public int getRowCount() {
		return library.getGadgets().size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Gadget gadget = library.getGadgets().get(rowIndex);
		switch(columnIndex){
		case 0:
			return gadget.getName();
		case 1:
			return gadget.getCondition();
		case 2:
			return gadget.getManufacturer();
		case 3:
			return gadget.getPrice();
		default:
			return null;
		}
	}
	
	public Gadget getGadget(int rowIndex){
		return library.getGadgets().get(rowIndex);
	}
}
