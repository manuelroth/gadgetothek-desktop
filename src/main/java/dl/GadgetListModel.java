package dl;

import java.util.Observable;
import java.util.Observer;
import javax.swing.AbstractListModel;
import bl.Library;
import bl.Gadget;

public class GadgetListModel extends AbstractListModel<Gadget> implements Observer{
	
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
		MessageData data = (MessageData) arg1;
		if (data.getData() instanceof Gadget){
			int pos = library.getGadgets().indexOf((Gadget)data.getData());
			fireContentsChanged(this, pos, pos);						
		}
	}

	@Override
	public Gadget getElementAt(int index) {
		return library.getGadgets().get(index);
	}

	@Override
	public int getSize() {
		return library.getGadgets().size();
	}
}
