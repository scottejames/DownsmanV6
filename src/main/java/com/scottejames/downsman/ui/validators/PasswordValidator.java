package com.scottejames.downsman.ui.validators;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;

public class PasswordValidator extends AbstractValidator<String> {


    public PasswordValidator(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public ValidationResult apply(String s, ValueContext valueContext) {
        return null;
    }
}
