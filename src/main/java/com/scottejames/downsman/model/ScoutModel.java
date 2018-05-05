package com.scottejames.downsman.model;

public class ScoutModel extends Model{
    private String fullName = null;
    private String dob = null;
    private String gender = null;

    public ScoutModel() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ScoutModel(String fullName, String dob, String gender) {

        this.fullName = fullName;
        this.dob = dob;
        this.gender = gender;
    }
}
