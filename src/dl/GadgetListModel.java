package dl;

import java.util.Observable;
import java.util.Observer;
import javax.swing.AbstractListModel;
import bl.Library;
import bl.Gadget;

public class GadgetListModel extends AbstractListModel implements Observer{
	
	private static final long serialVersionUID = 1L;
	Library library;
	
	public GadgetListModel(Library library){
		this.library=library;
		library.addObserver(this);
	}
	
	public void propagateUpdate(int pos) {
		fireContentsChanged(this, pos, pos);
	}

	@Override
	public void update(Observable obj, Object arg1) {
		if (obj instanceof Gadget){
			int pos = library.getGadgets().indexOf((Gadget)obj);
			fireContentsChanged(this, pos, pos);						
		}
		if (obj instanceof Library){
			fireContentsChanged(this, 0, library.getGadgets().size());
		}
	}

	@Override
	public Object getElementAt(int index) {
		return library.getGadget(String.valueOf(index));
	}

	@Override
	public int getSize() {
		return library.getGadgets().size();
	}
}
