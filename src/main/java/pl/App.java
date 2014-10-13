package pl;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingUtilities;

public class App {
    public static void main( String[] args ) throws IOException, InterruptedException, ExecutionException {
    	SwingUtilities.invokeLater(() -> {
			GadgetMasterFrame frame = new GadgetMasterFrame();
			frame.setVisible(true);
        });
    }
}
