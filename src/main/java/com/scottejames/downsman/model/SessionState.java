package com.scottejames.downsman.model;

import com.vaadin.flow.server.VaadinSession;

public class SessionState {
    private static final String SESSION_USERNAME = "username";

    private static SessionState _instance = null;

    public static SessionState getInstance(){
        if (_instance == null)
            _instance = new SessionState();
        return _instance;
    }
    private SessionState(){

    }

    public UserModel getCurrentUser() {

        UserModel currentUser = (UserModel)VaadinSession.getCurrent().getAttribute(SESSION_USERNAME);
        return currentUser;
    }

    public void setCurrentUser(UserModel currentUser) {
        VaadinSession.getCurrent().setAttribute(
                SESSION_USERNAME, currentUser);
    }

    public boolean isAuthenticated() {
        return (VaadinSession.getCurrent()
                .getAttribute(SESSION_USERNAME) != null);
    }
}
