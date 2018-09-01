package com.scottejames.downsman.services;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.scottejames.downsman.model.ScoutModel;
import com.scottejames.downsman.model.SupportModel;

import java.util.List;

public class SupportService {
    public SupportService(){
    }

    public List<SupportModel> getAll(String teamId) {

        SupportModel query = new SupportModel();
        query.setOwnerID(teamId);

        DynamoDBQueryExpression<SupportModel> queryExpression =
                (new DynamoDBQueryExpression<SupportModel>().
                withHashKeyValues(query));
        List < SupportModel> results = DatabaseService.getInstance().getMapper().
                query(SupportModel.class,queryExpression);
        return results;

    }

    public void remove(SupportModel support) {
        DatabaseService.getInstance().getMapper().delete(support);
        support.setId(null);
    }

    public void add(SupportModel support) {
        DatabaseService.getInstance().getMapper().save(support);
    }

    public void update(SupportModel support) {
        DatabaseService.getInstance().getMapper().save(support);
    }
}
