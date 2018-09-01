package com.scottejames.downsman.services;

import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.scottejames.downsman.model.ScoutModel;
import com.scottejames.downsman.model.SupportModel;
import com.scottejames.downsman.model.TeamModel;
import com.scottejames.downsman.model.UserModel;

import javax.xml.crypto.Data;

public class CreateDynamoTables {
    public static void createTables(){
        CreateTableRequest req =
                DatabaseService.getInstance().getMapper().generateCreateTableRequest(TeamModel.class);
        req.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

        DatabaseService.getInstance().getClient().createTable(req);


    }
    public static void main(String [] args){

        createTables();


    }

}
