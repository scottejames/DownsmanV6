package com.scottejames.downsman.services;

import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.scottejames.downsman.model.*;

import javax.xml.crypto.Data;

public class CreateDynamoTables {
    public static void createTables(Class c){
        CreateTableRequest req =
                DatabaseService.getInstance().getMapper().generateCreateTableRequest(c);
        req.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

        DatabaseService.getInstance().getClient().createTable(req);


    }
    public static void main(String [] args){

        createTables(LogModel.class);
        createTables(ScoutModel.class);
        createTables(SupportModel.class);
        createTables(TeamModel.class);
        createTables(UserModel.class);


    }

}
