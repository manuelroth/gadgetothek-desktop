package pl;


import java.awt.EventQueue;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class App 
{
    public static void main( String[] args ) throws IOException, InterruptedException, ExecutionException
    {
    	EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppFrame frame = new AppFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }
}
