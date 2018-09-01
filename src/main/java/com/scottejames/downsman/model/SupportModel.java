package com.scottejames.downsman.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

@DynamoDBTable(tableName="Support")
public class SupportModel  {

    private String ownerID = null;
    private String id = null;
    private String fullName = null;
    private String phoneNumber = null;
    private String from = null;
    private String to = null;

    public SupportModel(){

    }
    public SupportModel( String fullName, String phoneNumber, String from, String to) {
        this.ownerID = ownerID;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.from = from;
        this.to = to;
    }
    public SupportModel(String ownerID, String fullName, String phoneNumber, String from, String to) {
        this.ownerID = ownerID;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.from = from;
        this.to = to;
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
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @DynamoDBAttribute
    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }

    @DynamoDBAttribute
    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }

    @DynamoDBIgnore
    public boolean isPersisted() {
        if (id == null) return false;
        else return true;
    }

}
