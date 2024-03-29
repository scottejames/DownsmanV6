package com.scottejames.downsman.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

@DynamoDBTable(tableName = "EventLog")
public class LogModel {

    private String who;
    private String when;
    private String what;
    private String id;

    @DynamoDBHashKey
    public String getWho() {
        return who;
    }
    @DynamoDBRangeKey
    @DynamoDBAutoGeneratedKey
    public String getId(){ return id;}
    public void setId(String id){ this.id = id;}

    public void setWho(String who) {
        this.who = who;
    }
    @DynamoDBAttribute
    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }
    @DynamoDBAttribute
    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public String toString(){ return "At " + when + " - " + who + " did " + what + " to " + id;}
}
