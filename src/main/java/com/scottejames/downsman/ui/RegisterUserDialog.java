package com.scottejames.downsman.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

public class RegisterUserDialog extends Dialog {
    Runnable onEnter = null;
    TextField userNameField = new TextField("UserName");

    PasswordField passwordField = new PasswordField("Password");
    PasswordField checkPasswordField = new PasswordField("Re-enter Password");

    public RegisterUserDialog (Runnable onEnter){

        this.onEnter = onEnter;
        setCloseOnEsc(false);
        setCloseOnEsc(false);


        VerticalLayout layout = new VerticalLayout();
        layout.add(userNameField);
        layout.add(passwordField);
        layout.add(checkPasswordField);

        add(layout);
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.add(new Button("Enter", e->enter()));
        buttons.add(new Button("Cancel", e->cancel()));
        add(buttons);

    }

    private void cancel() {
        close();
    }

    private void enter() {
        String username = userNameField.getValue();
        String password = passwordField.getValue();
        String checkPassword = checkPasswordField.getValue();
    }
}
