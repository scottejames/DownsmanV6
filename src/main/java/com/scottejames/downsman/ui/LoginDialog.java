package com.scottejames.downsman.ui;

import com.scottejames.downsman.model.SessionState;
import com.scottejames.downsman.model.UserModel;
import com.scottejames.downsman.services.LogService;
import com.scottejames.downsman.services.ServiceManager;
import com.scottejames.downsman.services.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinSession;

class LoginDialog extends Dialog {

    private final TextField userNameField = new TextField();
    private final PasswordField passwordField = new PasswordField();

    private String username = null;
    private Runnable onEnter = null;
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    private String password = null;

    public LoginDialog(Runnable onEnter){
        this.onEnter = onEnter;
        setCloseOnEsc(false);
        setCloseOnEsc(false);

        add(new Label("UserName: "));
        add(userNameField);
        add(new Label("password: "));
        add(passwordField);

        Button enter = new Button("Enter", e->enter());
        add(enter);
        add(new Button("Cancel", e->cancel()));
        enter.getElement().setAttribute("theme", "contrast primary");

    }

    private void enter(){
        UserService service = ServiceManager.getInstance().getUserService();

        username = userNameField.getValue();
        password = passwordField.getValue();

        UserModel user = service.login(username,password);
        if (user != null) {
            SessionState.getInstance().setCurrentUser(user);
            if (onEnter != null) onEnter.run();
            close();
        } else {
            MessageDialog dialog = new MessageDialog("Login Failed", "Unable to login with that user check username or password", true);
            dialog.open();
        }
        LogService.logEvent("Logged in");



    }
    private void cancel(){
        username = null;
        password = null;
        close();
    }






}
