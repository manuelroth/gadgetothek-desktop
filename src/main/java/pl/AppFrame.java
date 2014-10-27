package pl;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;

import bl.Library;
import dl.LocalLibrary;

public class AppFrame extends JFrame {

	private JPanel contentPane;

	public AppFrame() {
		Library library = new Library(new LocalLibrary());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("Gadgets Bibliothek");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JFrame gadgetMasterFrame = new GadgetMasterFrame(library);
		tabbedPane.addTab("Gadgets", null, gadgetMasterFrame.getContentPane(), null);
		
		JFrame borrowMasterFrame = new BorrowMasterFrame(library);
		tabbedPane.addTab("Ausleihen & Rückgabe", null, borrowMasterFrame.getContentPane(), null);
	}

}
