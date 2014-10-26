package pl;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;

public class AppFrame extends JFrame {

	private JPanel contentPane;

	public AppFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("Gadgets Bibliothek");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.NORTH);
		
		JFrame gadgetMasterFrame = new GadgetMasterFrame();
		tabbedPane.addTab("Gadgets", null, gadgetMasterFrame.getContentPane(), null);
		
		JPanel borrowTab = new JPanel();
		tabbedPane.addTab("Ausleihen & Rückgabe", null, borrowTab, null);
		borrowTab.setLayout(new BorderLayout(0, 0));	
	}

}
