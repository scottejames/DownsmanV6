package com.scottejames.downsman.ui.validators;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;

public class EmailValidator extends AbstractValidator<String> {
    com.vaadin.flow.data.validator.EmailValidator validator = null;


    public EmailValidator(String errorMessage) {
        super(errorMessage);
        this.validator = new com.vaadin.flow.data.validator.EmailValidator(errorMessage);
    }
    @Override
    public ValidationResult apply(String value, ValueContext context) {
        if (value.length() == 0)
            return ValidationResult.ok();
        return validator.apply(value,context);
    }
}

