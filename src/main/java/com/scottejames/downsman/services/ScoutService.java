package com.scottejames.downsman.services;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.scottejames.downsman.model.ScoutModel;

import java.util.List;

public class ScoutService {

    public List<ScoutModel> getAll(String teamId) {
        ScoutModel query = new ScoutModel();
        query.setOwnerID(teamId);

        DynamoDBQueryExpression<ScoutModel> queryExpression = (new DynamoDBQueryExpression<ScoutModel>().
                withHashKeyValues(query));
        List < ScoutModel> results = DatabaseService.getInstance().getMapper().
                query(ScoutModel.class,queryExpression);
        return results;
    }

    public void remove(ScoutModel scout) {
        DatabaseService.getInstance().getMapper().delete(scout);
        scout.setId(null);
    }

    public void add(ScoutModel scout) {
        DatabaseService.getInstance().getMapper().save(scout);
    }

    public void update(ScoutModel scout) {
        DatabaseService.getInstance().getMapper().save(scout);
    }
}
