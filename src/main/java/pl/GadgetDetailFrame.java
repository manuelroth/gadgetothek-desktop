package pl;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import java.awt.GridLayout;

import javax.swing.JButton;

import java.awt.FlowLayout;

import javax.swing.SwingConstants;

import bl.Gadget;

import java.awt.Dimension;

import javax.swing.DefaultComboBoxModel;

import bl.Gadget.Condition;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

public class GadgetDetailFrame implements Observer{

	private JFrame frame;
	private JTextField name;
	private JTextField hersteller;
	private JTextField preis;
	private JLabel id;
	private JComboBox zustand;
	private Gadget gadget;

	/**
	 * Create the application.
	 * @param gadget 
	 */
	public GadgetDetailFrame(Gadget gadget) {
		gadget.addObserver(this);
		this.gadget = gadget;
		initialize(); 
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setMinimumSize(new Dimension(400, 300));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{40, 83, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{40, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblId = new JLabel("Id");
		GridBagConstraints gbc_lblId = new GridBagConstraints();
		gbc_lblId.anchor = GridBagConstraints.WEST;
		gbc_lblId.insets = new Insets(0, 0, 5, 5);
		gbc_lblId.gridx = 1;
		gbc_lblId.gridy = 1;
		frame.getContentPane().add(lblId, gbc_lblId);
		
		id = new JLabel(this.gadget.getInventoryNumber());
		id.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 2;
		gbc_label.gridy = 1;
		frame.getContentPane().add(id, gbc_label);
		
		JLabel lblName = new JLabel("Name");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.WEST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 1;
		gbc_lblName.gridy = 2;
		frame.getContentPane().add(lblName, gbc_lblName);
		
		name = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 2;
		frame.getContentPane().add(name, gbc_textField);
		name.setColumns(10);
		name.setText(this.gadget.getName());
		
		JLabel lblHersteller = new JLabel("Hersteller");
		GridBagConstraints gbc_lblHersteller = new GridBagConstraints();
		gbc_lblHersteller.anchor = GridBagConstraints.WEST;
		gbc_lblHersteller.insets = new Insets(0, 0, 5, 5);
		gbc_lblHersteller.gridx = 1;
		gbc_lblHersteller.gridy = 3;
		frame.getContentPane().add(lblHersteller, gbc_lblHersteller);
		
		hersteller = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 2;
		gbc_textField_1.gridy = 3;
		frame.getContentPane().add(hersteller, gbc_textField_1);
		hersteller.setColumns(10);
		hersteller.setText(this.gadget.getManufacturer());
		
		JLabel lblPreis = new JLabel("Preis");
		GridBagConstraints gbc_lblPreis = new GridBagConstraints();
		gbc_lblPreis.anchor = GridBagConstraints.WEST;
		gbc_lblPreis.insets = new Insets(0, 0, 5, 5);
		gbc_lblPreis.gridx = 1;
		gbc_lblPreis.gridy = 4;
		frame.getContentPane().add(lblPreis, gbc_lblPreis);
		
		preis = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 2;
		gbc_textField_2.gridy = 4;
		frame.getContentPane().add(preis, gbc_textField_2);
		preis.setColumns(10);
		preis.setText(String.valueOf(this.gadget.getPrice()));

		
		JLabel lblZustand = new JLabel("Zustand");
		GridBagConstraints gbc_lblZustand = new GridBagConstraints();
		gbc_lblZustand.insets = new Insets(0, 0, 5, 5);
		gbc_lblZustand.anchor = GridBagConstraints.WEST;
		gbc_lblZustand.gridx = 1;
		gbc_lblZustand.gridy = 5;
		frame.getContentPane().add(lblZustand, gbc_lblZustand);
		
		zustand = new JComboBox();
		zustand.setModel(new DefaultComboBoxModel(Condition.values()));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 5;
		frame.getContentPane().add(zustand, gbc_comboBox);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.anchor = GridBagConstraints.EAST;
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 2;
		gbc_panel_1.gridy = 6;
		frame.getContentPane().add(panel_1, gbc_panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton btnAbbruch = new JButton("Abbruch");
		btnAbbruch.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(btnAbbruch);
		btnAbbruch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		
		JButton btnSpeichern = new JButton("Speichern");
		btnSpeichern.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(btnSpeichern);
		btnSpeichern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gadget.setData(gadget);
				//Add to GadgetListModel
			}
		});
	}

	@Override
	public void update(Observable o, Object arg) {
		Gadget gadget = (Gadget)o;
		name.setText(gadget.getName());
		hersteller.setText(gadget.getManufacturer());
		preis.setText(String.valueOf(gadget.getPrice()));
		id.setText(String.valueOf(gadget.getInventoryNumber()));
		zustand.setSelectedItem(gadget.getCondition());
	}

}
