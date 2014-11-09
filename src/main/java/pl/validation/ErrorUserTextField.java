package pl.validation;


import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ErrorUserTextField extends JTextField {
	ErrorTextFieldValidator validator;
	boolean error;

    public ErrorUserTextField(){
    	this(new ErrorTextFieldValidator () {
    		@Override
    		public boolean validate(String currentText) {
    			return true;
    		}
    	});
    }
	
    public ErrorUserTextField(ErrorTextFieldValidator validator){
    	this.validator = validator;
    	setOpaque(true);
    	
    	addKeyListener(new KeyAdapter() {
	
			@Override
			public void keyReleased(KeyEvent e) {
				error = !validator.validate(getText());
				ErrorUserTextField field = (ErrorUserTextField) e.getSource();		
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


