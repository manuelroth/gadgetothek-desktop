package pl.validation;

public class TextFieldGadgetIdValidator implements ErrorTextFieldValidator{
	
	@Override
	public boolean validate(String currentText) {
		return currentText.matches("[0-9]{19}");
	}
}
