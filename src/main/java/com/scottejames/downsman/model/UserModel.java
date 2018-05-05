package com.scottejames.downsman.model;

public class UserModel extends Model {

    String username = null;
    String password = null;
    String firstName = null;
    String lastName = null;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
