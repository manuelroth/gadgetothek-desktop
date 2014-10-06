package pl;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingUtilities;

import com.alee.laf.WebLookAndFeel;

public class App {
    public static void main( String[] args ) throws IOException, InterruptedException, ExecutionException {
    	SwingUtilities.invokeLater(() -> {
    		WebLookAndFeel.install();
			MasterGadgetFrame frame = new MasterGadgetFrame();
			frame.setVisible(true);
        });
    }
}
