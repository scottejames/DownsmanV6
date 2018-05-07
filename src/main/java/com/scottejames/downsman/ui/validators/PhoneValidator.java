package com.scottejames.downsman.ui.validators;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;

public class PhoneValidator extends AbstractValidator<String> {

    public PhoneValidator(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public ValidationResult apply(String value, ValueContext context) {
        String val = value;

        if (val.length() == 0)
            return ValidationResult.ok();

        val = val.replace(" ","");
        if (hasOnlyDigits(val))
            return ValidationResult.ok();
        else
            return ValidationResult.error("Not a valid telephone number, must be digits only");
    }

    private boolean hasOnlyDigits(String phone) {
        return phone.chars().allMatch(Character::isDigit);
    }


}
