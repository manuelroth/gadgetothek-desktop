package pl;

import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

import dl.MessageData;
import bl.Library;
import bl.Gadget;

public class GadgetTableModel extends AbstractTableModel implements Observer{
	
	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"Id", "Name", "Hersteller", "Preis", "Zustand", "Verfügbar ab", "Ausgeliehen an"};
	private Library library;

	
	public GadgetTableModel(Library library){
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
		if (data.getData() instanceof Gadget){
			int pos = library.getGadgets().indexOf((Gadget)data.getData());
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
			return gadget.getInventoryNumber();
		case 1:
			return gadget.getName();
		case 2:
			return gadget.getManufacturer();
		case 3:
			return gadget.getPrice();
		case 4:
			return gadget.getCondition();
		default:
			return null;
		}
	}
	
	public Gadget getGadget(int rowIndex){
		return library.getGadgets().get(rowIndex);
	}
}
