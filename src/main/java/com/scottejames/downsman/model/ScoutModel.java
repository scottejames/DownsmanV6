package com.scottejames.downsman.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.document.Item;

import java.time.LocalDate;
import java.time.Period;

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

        this.ownerID = ownerID;
        this.fullName = fullName;
        this.dob = dob.toEpochDay();
    }
    public ScoutModel(String ownerID, String fullName, java.time.LocalDate dob) {

        this.ownerID = ownerID;
        this.fullName = fullName;
        this.dob = dob.toEpochDay();
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
        return LocalDate.ofEpochDay(dob);
    }
    @DynamoDBIgnore
    public String getDobString(){
        LocalDate dob = LocalDate.ofEpochDay(this.dob);
        String result = dob.getDayOfMonth() + " / " + dob.getMonth() + " / " + dob.getYear() + " Age at hike " + calculateAge();
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

    public int calculateAge() {

        if ((dob != 0) && (ReferenceData.HIKE_DATE != null)) {
            return Period.between(LocalDate.ofEpochDay(dob), ReferenceData.HIKE_DATE).getYears();
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
