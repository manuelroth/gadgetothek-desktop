package pl.validation;


import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ErrorTextField extends JTextField {
	ErrorTextFieldValidator validator;
	boolean error;

    public ErrorTextField(){
    	this(new ErrorTextFieldValidator () {
    		@Override
    		public boolean validate(String currentText) {
    			return true;
    		}
    	});
    }
	
    public ErrorTextField(ErrorTextFieldValidator validator){
    	this.validator = validator;
    	setOpaque(true);
    	
    	addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				error = !validator.validate(getText());
				ErrorTextField field = (ErrorTextField) e.getSource();
				if(error) {
					field.setBackground(Color.red);
				} else {
					field.setBackground(Color.green);
				}
				repaint();
			}
		});
    }
    
    public boolean isValid() {
    	return !error;
    }

}


