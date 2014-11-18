package pl;


import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class App 
{
    public static void main( String[] args ) throws IOException, InterruptedException, ExecutionException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
    {
    	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    	EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppFrame frame = new AppFrame();
					frame.setVisible(true);
					frame.setMinimumSize(new Dimension(900, 360));
					frame.setMaximumSize(new Dimension(900, 450));
					frame.setPreferredSize(new Dimension(900, 450));
					frame.pack();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }
}
