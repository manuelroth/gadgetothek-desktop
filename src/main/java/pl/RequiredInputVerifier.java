package pl;

import java.awt.Color;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

public class RequiredInputVerifier extends InputVerifier {
	
	@Override
	public boolean verify(JComponent input) {
		JTextField textField = (JTextField) input;
        if (textField.getText().isEmpty()) {
        	textField.setBackground(Color.RED);
            return false;
        } else {
        	textField.setBackground(Color.GREEN);
            return true;
            
        }
	}

}
