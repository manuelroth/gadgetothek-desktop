package pl.validation;

public class TextFieldNotEmptyValidator implements ErrorTextFieldValidator {

	@Override
	public boolean validate(String currentText) {
		return !currentText.isEmpty();
	}

}
