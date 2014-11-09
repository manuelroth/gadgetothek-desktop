package pl;

import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

import dl.MessageData;
import bl.Library;
import bl.Gadget;
import bl.Loan;

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
		if(!data.getTarget().equals("gadget")) return;
		
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
		List<Loan> list = library.getLoansFor(gadget, true);

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
		case 5:
			if(list.isEmpty()){
				return new Date().toLocaleString();
			}else{
				return list.get(0).overDueDate().toLocaleString();
			}
		case 6:
			if(list.isEmpty()){
				return null;
			}else{
				return library.getCustomer(list.get(0).getCustomerId()).getName();
			}
		default:
			return null;
		}
	}
	
	public Gadget getGadget(int rowIndex){
		return library.getGadgets().get(rowIndex);
	}
}
