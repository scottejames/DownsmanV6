package com.scottejames.downsman.services;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.scottejames.downsman.utils.Config;

public class DatabaseService {

    private static DatabaseService instance = null;
    private AmazonDynamoDB client = null;
    private DynamoDB dynamoDB = null;

    private DatabaseService(){
        if (Config.getInstance().isDev()==true) {
            client = AmazonDynamoDBClientBuilder.standard()
                    .withEndpointConfiguration(new AwsClientBuilder
                            .EndpointConfiguration("http://localhost:8000", "us-east-1"))
                    .build();
        } else {
            client = AmazonDynamoDBClientBuilder.standard().build();

        }

        dynamoDB = new DynamoDB(client);
        DynamoDBMapper mapper = new DynamoDBMapper(client);

    }

    public synchronized static DatabaseService getInstance(){
        if (instance == null){
            instance = new DatabaseService();
        }
        return instance;
    }

    public DynamoDB getDynamoDB() {
        return dynamoDB;
    }
    public AmazonDynamoDB getClient() {
        return client;
    }
    public DynamoDBMapper getMapper() { return new DynamoDBMapper(client); }
}
