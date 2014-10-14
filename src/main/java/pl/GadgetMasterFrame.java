package pl;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.GridBagLayout;

import javax.swing.JList;

import java.awt.GridBagConstraints;

import javax.swing.JButton;

import java.awt.Insets;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import bl.Gadget;
import bl.Library;
import dl.GadgetListModel;
import dl.LocalLibrary;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GadgetMasterFrame extends JFrame {

	private JList gadgetList;
	private JScrollPane scrollPane;
	private JTextField newGadgetText;
	private JButton editGadgetButton;
	private JButton newGadgetButton;
	private JPanel navigationPanel;
	private JPanel listPanel;
	private GadgetListModel gadgetListModel = new GadgetListModel(new Library(new LocalLibrary()));

	/**
	 * Create the frame.
	 */
	public GadgetMasterFrame() {
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new java.awt.Dimension(300,200));
		setBounds(100, 100, 450, 300);
		scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		gadgetList = new JList();
		gadgetList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(gadgetList);
		gadgetList.setModel(gadgetListModel);
		
		navigationPanel = new JPanel();
		BorderLayout navigationPanelLayout = new BorderLayout();
		navigationPanel.setLayout(navigationPanelLayout);
		getContentPane().add(navigationPanel, BorderLayout.NORTH);
		
		newGadgetButton = new JButton();
		navigationPanel.add(newGadgetButton, BorderLayout.WEST);
		newGadgetButton.setText("Gadget erfassen");
		newGadgetButton.setPreferredSize(new java.awt.Dimension(140, 21));
		newGadgetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GadgetDetailFrame window = new GadgetDetailFrame(new Gadget(""));
			}
		});
		this.getRootPane().setDefaultButton(newGadgetButton);
		
		editGadgetButton = new JButton();
		navigationPanel.add(editGadgetButton, BorderLayout.EAST);
		editGadgetButton.setText("Gadget editieren");
		editGadgetButton.setPreferredSize(new java.awt.Dimension(140, 21));
		editGadgetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
	}
}
