package pl;

import javax.swing.JFrame;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import javax.swing.JButton;

import java.awt.FlowLayout;

import javax.swing.SwingConstants;

import bl.Gadget;
import bl.Library;

import java.awt.Dimension;

import javax.swing.DefaultComboBoxModel;

import bl.Gadget.Condition;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

public class GadgetDetailFrame implements Observer{

	private JFrame frame;
	private JTextField nameTextField;
	private JTextField manufactorerTextField;
	private JTextField prizeTextField;
	private JLabel idLabel;
	private JComboBox<Condition> conditionTextField;
	private Gadget gadget;
	private Library library;
	private boolean isNewGadget;

	/**
	 * Create the application.
	 * @param gadget 
	 */
	public GadgetDetailFrame(Gadget gadget, Library library, boolean isNewGadget) {
		this.gadget = gadget;
		this.library = library;
		this.isNewGadget = isNewGadget;
		initialize(); 
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setMinimumSize(new Dimension(400, 300));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 83, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{40, 0, 0, 0, 0, 0, 44, -20, 0};
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
		
		idLabel = new JLabel(this.gadget.getInventoryNumber());
		idLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_idLabel = new GridBagConstraints();
		gbc_idLabel.anchor = GridBagConstraints.WEST;
		gbc_idLabel.insets = new Insets(0, 0, 5, 5);
		gbc_idLabel.gridx = 2;
		gbc_idLabel.gridy = 1;
		frame.getContentPane().add(idLabel, gbc_idLabel);
		
		JLabel lblName = new JLabel("Name");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.WEST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 1;
		gbc_lblName.gridy = 2;
		frame.getContentPane().add(lblName, gbc_lblName);
		
		nameTextField = new JTextField();
		GridBagConstraints gbc_nameTextField = new GridBagConstraints();
		gbc_nameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_nameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameTextField.gridx = 2;
		gbc_nameTextField.gridy = 2;
		frame.getContentPane().add(nameTextField, gbc_nameTextField);
		nameTextField.setColumns(10);
		nameTextField.setText(this.gadget.getName());
		
		JLabel lblHersteller = new JLabel("Hersteller");
		GridBagConstraints gbc_lblHersteller = new GridBagConstraints();
		gbc_lblHersteller.anchor = GridBagConstraints.WEST;
		gbc_lblHersteller.insets = new Insets(0, 0, 5, 5);
		gbc_lblHersteller.gridx = 1;
		gbc_lblHersteller.gridy = 3;
		frame.getContentPane().add(lblHersteller, gbc_lblHersteller);
		
		manufactorerTextField = new JTextField();
		GridBagConstraints gbc_manufactorerTextField = new GridBagConstraints();
		gbc_manufactorerTextField.insets = new Insets(0, 0, 5, 5);
		gbc_manufactorerTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_manufactorerTextField.gridx = 2;
		gbc_manufactorerTextField.gridy = 3;
		frame.getContentPane().add(manufactorerTextField, gbc_manufactorerTextField);
		manufactorerTextField.setColumns(10);
		manufactorerTextField.setText(this.gadget.getManufacturer());
		
		JLabel lblPreis = new JLabel("Preis");
		GridBagConstraints gbc_lblPreis = new GridBagConstraints();
		gbc_lblPreis.anchor = GridBagConstraints.WEST;
		gbc_lblPreis.insets = new Insets(0, 0, 5, 5);
		gbc_lblPreis.gridx = 1;
		gbc_lblPreis.gridy = 4;
		frame.getContentPane().add(lblPreis, gbc_lblPreis);
		
		prizeTextField = new JTextField();
		GridBagConstraints gbc_prizeTextField = new GridBagConstraints();
		gbc_prizeTextField.insets = new Insets(0, 0, 5, 5);
		gbc_prizeTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_prizeTextField.gridx = 2;
		gbc_prizeTextField.gridy = 4;
		frame.getContentPane().add(prizeTextField, gbc_prizeTextField);
		prizeTextField.setColumns(10);
		prizeTextField.setText(String.valueOf(this.gadget.getPrice()));

		
		JLabel lblZustand = new JLabel("Zustand");
		GridBagConstraints gbc_lblZustand = new GridBagConstraints();
		gbc_lblZustand.insets = new Insets(0, 0, 5, 5);
		gbc_lblZustand.anchor = GridBagConstraints.WEST;
		gbc_lblZustand.gridx = 1;
		gbc_lblZustand.gridy = 5;
		frame.getContentPane().add(lblZustand, gbc_lblZustand);
		
		conditionTextField = new JComboBox<Condition>();
		conditionTextField.setModel(new DefaultComboBoxModel<Condition>(Condition.values()));
		GridBagConstraints gbc_conditionTextField = new GridBagConstraints();
		gbc_conditionTextField.insets = new Insets(0, 0, 5, 5);
		gbc_conditionTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_conditionTextField.gridx = 2;
		gbc_conditionTextField.gridy = 5;
		frame.getContentPane().add(conditionTextField, gbc_conditionTextField);
		
		JPanel buttonPanel = new JPanel();
		GridBagConstraints gbc_buttonPanel = new GridBagConstraints();
		gbc_buttonPanel.anchor = GridBagConstraints.EAST;
		gbc_buttonPanel.insets = new Insets(0, 0, 5, 5);
		gbc_buttonPanel.fill = GridBagConstraints.BOTH;
		gbc_buttonPanel.gridx = 2;
		gbc_buttonPanel.gridy = 6;
		frame.getContentPane().add(buttonPanel, gbc_buttonPanel);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton cancelButton = new JButton("Abbruch");
		cancelButton.setHorizontalAlignment(SwingConstants.RIGHT);
		buttonPanel.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		
		JButton saveButton = new JButton();
		if(isNewGadget){
			frame.setTitle("Neues Gadget erfassen");
			saveButton.setText("Erfassen");
		}else{
			frame.setTitle("Gadget editieren");
			saveButton.setText("Speichern");
		}
		
		saveButton.setHorizontalAlignment(SwingConstants.RIGHT);
		buttonPanel.add(saveButton);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isNewGadget){
					library.addGadget(gadget);
				}else{
					library.updateGadget(gadget);
				}
				frame.dispose();
			}
		});
	}

	@Override
	public void update(Observable o, Object arg) {
		Gadget gadget = (Gadget) arg;
		nameTextField.setText(gadget.getName());
		manufactorerTextField.setText(gadget.getManufacturer());
		prizeTextField.setText(String.valueOf(gadget.getPrice()));
		idLabel.setText(String.valueOf(gadget.getInventoryNumber()));
		conditionTextField.setSelectedItem(gadget.getCondition());
	}

}
