package com.scottejames.downsman.model;

import java.time.Period;

public class ScoutModel extends Model{
    private String fullName = null;
    private java.time.LocalDate dob = null;
    private boolean leader = false;


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


    public boolean isLeader() {
        return leader;
    }

    public void setLeader(boolean adult) {
        this.leader = adult;
    }
    public ScoutModel(String fullName, java.time.LocalDate dob) {

        this.fullName = fullName;
        this.dob = dob;
    }
    public int calculateAge() {

        if ((dob != null) && (ReferenceData.HIKE_DATE != null)) {
            return Period.between(dob, ReferenceData.HIKE_DATE).getYears();
        } else {
            return 0;
        }
    }
}
