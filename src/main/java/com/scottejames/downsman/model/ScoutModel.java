package com.scottejames.downsman.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.document.Item;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

@DynamoDBTable(tableName = "Scouts")
public class ScoutModel{

    private String ownerID = null;
    private String id = null;
    private String fullName = null;
    private long dob = 0;
    private boolean leader = false;
    private String medicalNotes = null;

    public ScoutModel() {
    }
    public ScoutModel(String fullName, java.time.LocalDate dob) {

        this.ownerID = "No Owner";
        this.fullName = fullName;
        this.dob = dob.toEpochDay();
    }
    public ScoutModel(String ownerID, String fullName, java.time.LocalDate dob) {

        this.ownerID = ownerID;
        this.fullName = fullName;
        if (dob != null)
            this.dob = dob.toEpochDay();
        else
            this.dob = 0;
    }
    @DynamoDBRangeKey
    @DynamoDBAutoGeneratedKey
    public String getId(){ return id;}
    public void setId(String id){ this.id = id;}

    @DynamoDBHashKey
    public String getOwnerID() { return ownerID; }
    public void setOwnerID(String ownerID) { this.ownerID = ownerID;}

    @DynamoDBAttribute
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @DynamoDBAttribute
    public Long getDobEpoch() {
        return dob;
    }
    public void setDobEpoch(Long dob) {
        this.dob = dob;
    }

    @DynamoDBIgnore
    public java.time.LocalDate getDob() {
        if(this.dob == 0) {
            return null;
        }else
            return LocalDate.ofEpochDay(dob);
    }
    @DynamoDBIgnore
    public String getDobString(){
        LocalDate dob = LocalDate.ofEpochDay(this.dob);
        String result = null;

        if (this.dob == 0) {
            result = "Not Entered";
        } else {
            String age = String.format("%.2f",calculateAge());

            result = dob.getDayOfMonth() + " / " + dob.getMonth() + " / " + dob.getYear() + " Age at hike " + age ;
        }
        return result;


    };
    public void setDob(java.time.LocalDate dob) {
        if (dob != null)
            this.dob = dob.toEpochDay();
        else
            this.dob = 0;
    }

    @DynamoDBAttribute
    public String getMedicalNotes() { return medicalNotes; }
    public void setMedicalNotes(String menicalNotes) { this.medicalNotes = menicalNotes; }

    @DynamoDBAttribute
    public boolean isLeader() {
        return leader;
    }
    public void setLeader(boolean adult) {
        this.leader = adult;
    }

    public float calculateAge() {
        DecimalFormat df = new DecimalFormat("#.##");

        if ((dob != 0) && (ReferenceData.HIKE_DATE != null)) {
            LocalDate dateOfBirth = LocalDate.ofEpochDay(dob);
            LocalDate hikeDate = ReferenceData.HIKE_DATE;
            float days = ChronoUnit.DAYS.between(dateOfBirth,hikeDate);
            float age = days/365;

            return age;
        } else {
            return 0;
        }
    }
    @DynamoDBIgnore
    public boolean isPersisted() {
        if (id == null) return false;
        else return true;
    }
}
