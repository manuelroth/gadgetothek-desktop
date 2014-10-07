package pl;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingUtilities;

public class App {
    public static void main( String[] args ) throws IOException, InterruptedException, ExecutionException {
    	SwingUtilities.invokeLater(() -> {
			MasterGadgetFrame frame = new MasterGadgetFrame();
			frame.setVisible(true);
        });
    }
}
