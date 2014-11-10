package pl;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import bl.Library;

public class BorrowMasterPanel extends JPanel {

	public BorrowMasterPanel(Library library) {
		setBounds(100, 100, 450, 300);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new GridLayout(1, 0, 0, 0));		
		
		JPanel userMasterPanel = new UserMasterPanel(library);
		add(userMasterPanel);
		
		JPanel userDetailPanel = new UserDetailPanel(library);
		add(userDetailPanel);		
	}
}
