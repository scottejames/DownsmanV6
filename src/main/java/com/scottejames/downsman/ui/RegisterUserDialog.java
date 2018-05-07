package com.scottejames.downsman.ui;

import com.scottejames.downsman.model.UserModel;
import com.scottejames.downsman.services.ServiceManager;
import com.scottejames.downsman.utils.LogUtil;
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
    Label status = new Label();
    public RegisterUserDialog (Runnable onEnter){

        this.onEnter = onEnter;
        setCloseOnEsc(false);
        setCloseOnEsc(false);


        VerticalLayout layout = new VerticalLayout();
        layout.add(userNameField);
        layout.add(passwordField);
        layout.add(checkPasswordField);
        layout.add(status);

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

        LogUtil.logDebug("Trying to register new user ");

        boolean hasLetter = false;
        boolean hasDigit = false;

        for (int i = 0; i < password.length(); i++) {
            char x = password.charAt(i);
            if (Character.isLetter(x)) {

                hasLetter = true;
            } else if (Character.isDigit(x)) {

                hasDigit = true;
            }
            // no need to check further, break the loop
            if (hasLetter && hasDigit) {

                break;
            }
        }

        UserModel priorUser = ServiceManager.getInstance().getUserService().findByUserName(username);

        if (priorUser != null){
            LogUtil.logDebug("Username has already been taken");
            status.setText("Username has already been taken");
        } else if (password.equals(checkPassword)==false){
            LogUtil.logDebug("Passwords dont match");
            status.setText("Passwords Dont Match");
        } else if (password.length() < 6) {
            LogUtil.logDebug("Password too short (less than 6 chars)");
            status.setText("Password should be more than 6 chars");
        } else if (!(hasDigit && hasLetter)) {
            LogUtil.logDebug("Passwword does not contain letters and numbers");
            status.setText("Password should contain letters and numbers");

        }
        else{
            LogUtil.logEvent("Registered user " + username);

            ServiceManager.getInstance().getUserService().add(new UserModel(username,password));
            close();
        }
    }
}
