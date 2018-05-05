package com.scottejames.downsman.model;

public class ScoutModel extends Model{
    private String fullName = null;
    private java.time.LocalDate dob = null;
    private String gender = null;

    public ScoutModel() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public java.time.LocalDate getDob() {
        return dob;
    }

    public void setDob(java.time.LocalDate dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ScoutModel(String fullName, java.time.LocalDate dob, String gender) {

        this.fullName = fullName;
        this.dob = dob;
        this.gender = gender;
    }
}
