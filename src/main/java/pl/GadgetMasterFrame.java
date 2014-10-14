package pl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JList;
import javax.swing.JButton;
import bl.Gadget;
import bl.Library;
import dl.GadgetListModel;
import dl.LocalLibrary;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GadgetMasterFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField searchTextField;
	
	private GadgetListModel gadgetListModel = new GadgetListModel(new Library(new LocalLibrary()));

	/**
	 * Create the frame.
	 */
	public GadgetMasterFrame() {
		setMinimumSize(new Dimension(540, 340));
		setSize(new Dimension(540, 340));
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel gadgetsTab = new JPanel();
		tabbedPane.addTab("Gadgets", null, gadgetsTab, null);
		gadgetsTab.setLayout(new BorderLayout(0, 0));
		
		JPanel gadgetsPanel = new JPanel();
		gadgetsTab.add(gadgetsPanel, BorderLayout.NORTH);
		gadgetsPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		gadgetsTab.add(scrollPane, BorderLayout.CENTER);
		
		JScrollBar scrollBar = new JScrollBar();
		gadgetsTab.add(scrollBar, BorderLayout.EAST);
		
		JList<Gadget> gadgetsList = new JList<Gadget>();
		gadgetsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		gadgetsList.setModel(gadgetListModel);
		scrollPane.setViewportView(gadgetsList);
		
		searchTextField = new JTextField();
		searchTextField.setText("Suchen...");
		searchTextField.setToolTipText("Suchen...");
		gadgetsPanel.add(searchTextField);
		searchTextField.setColumns(10);
		
		JPanel buttonPanel = new JPanel();
		gadgetsPanel.add(buttonPanel);
		buttonPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton newGadgetButton = new JButton("Gadget erfassen");
		buttonPanel.add(newGadgetButton);
		newGadgetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GadgetDetailFrame window = new GadgetDetailFrame(new Gadget(), new Library(new LocalLibrary()), true);
			}
		});
		
		JButton editGadgetButton = new JButton("Gadget editieren");
		buttonPanel.add(editGadgetButton);
		if(gadgetsList.isSelectionEmpty()){
			editGadgetButton.setEnabled(false);
		}
		editGadgetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Gadget gadget = (Gadget) gadgetsList.getSelectedValue();
				GadgetDetailFrame window = new GadgetDetailFrame(gadget, new Library(new LocalLibrary()), false);
			}
		});
		
		JPanel ausleihenTab = new JPanel();
		tabbedPane.addTab("Ausleihen & Rückgabe", null, ausleihenTab, null);
	}
}
