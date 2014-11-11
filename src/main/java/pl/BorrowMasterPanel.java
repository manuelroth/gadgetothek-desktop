package pl;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import bl.Customer;
import bl.Library;

public class BorrowMasterPanel extends JPanel {
	private UserDetailPanel currentUserPanel;
	
	public BorrowMasterPanel(Library library) {
		setBounds(100, 100, 450, 300);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new GridLayout(1, 0, 0, 0));	
		
		JPanel userDetailPanel = new UserDetailPanel(library);
		add(userDetailPanel);

		JPanel userMasterPanel = new UserMasterPanel(library, userDetailPanel);
		add(userMasterPanel);	
		
		
		/*Customer initialCustomer = library.getCustomers().get(0);
		currentUserPanel = new UserDetailPanel(library, initialCustomer);
		add(currentUserPanel);*/
	}
	
	/*public void setCurrentUserPanel(UserDetailPanel panel) {
		this.remove(currentUserPanel);
		this.repaint();
		this.add(panel);
		this.currentUserPanel = panel;
		this.repaint();
	}
	
	public UserDetailPanel getCurrentUserDetailPanel() {
		return this.currentUserPanel;
	}*/
}
