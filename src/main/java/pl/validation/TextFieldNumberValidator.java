package pl.validation;

public class TextFieldNumberValidator implements ErrorTextFieldValidator {

	@Override
	public boolean validate(String currentText) {
		return currentText.matches("-?\\d+(\\.\\d+)?");
	}

}
