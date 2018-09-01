package com.scottejames.downsman.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.document.Item;

@DynamoDBTable(tableName = "Test")
public class TestModel {

    private String name = null;
    private String id = null;



    public TestModel(){
        super();
    }
    public TestModel(String name) {

        this.name = name;
    }

    @DynamoDBIndexHashKey
    public String getId() {
        return id;
    }

    @DynamoDBAttribute
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }





}
