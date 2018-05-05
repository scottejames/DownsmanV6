package com.scottejames.downsman.ui;

import com.scottejames.downsman.model.UserModel;
import com.scottejames.downsman.services.ServiceManager;
import com.scottejames.downsman.services.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinSession;

public class LoginDialog extends Dialog {
    public static final String SESSION_USERNAME = "username";

    TextField userNameField = new TextField();
    TextField passwordField = new TextField();

    String username = null;
    Runnable onEnter = null;
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    String password = null;

    public LoginDialog(Runnable onEnter){
        this.onEnter = onEnter;
        setCloseOnEsc(false);
        setCloseOnEsc(false);

        add(new Label("UserName: "));
        add(userNameField);
        add(new Label("password: "));
        add(passwordField);

        add(new Button("Enter", e->enter()));
        add(new Button("Cancel", e->cancel()));
    }

    public void enter(){
        UserService service = ServiceManager.getInstance().getUserService();

        username = userNameField.getValue();
        password = passwordField.getValue();

        UserModel user = service.login(username,password);
        if (user != null) {
            VaadinSession.getCurrent().setAttribute(
                    SESSION_USERNAME, username);
        } // TODO handle error message for not logged in user


        if (onEnter != null) onEnter.run();

        close();
    }
    public void cancel(){
        username = null;
        password = null;
        close();
    }

    public UserModel getLoggedInUser(){

        UserService service = ServiceManager.getInstance().getUserService();
        String username = (String)VaadinSession.getCurrent().getAttribute(
                SESSION_USERNAME);

        return service.findByUserName(username);
    }

    public static boolean isAuthenticated() {
        return (VaadinSession.getCurrent()
                .getAttribute(SESSION_USERNAME) != null);
    }

    public void logout() {
        VaadinSession.getCurrent()
                .setAttribute(SESSION_USERNAME,null);
    }
}
