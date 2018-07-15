package com.scottejames.downsman.model;

import java.sql.Ref;

public class UserModel extends Model {

    private String username = null;
    private String password = null;
    private Boolean admin = false;

    public UserModel(String u, String p) {
        username = u;
        password = p;
    }
    public String toString(){
        return " U : " + username + " P : " + password;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
}
