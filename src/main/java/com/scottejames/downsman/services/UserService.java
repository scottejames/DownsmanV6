package com.scottejames.downsman.services;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.scottejames.downsman.model.UserModel;
import com.scottejames.downsman.utils.HashHelper;

import javax.xml.crypto.Data;
import java.util.List;

public class UserService{


    public UserService(){
    }

    public UserModel login(String username, String password){
        // if user or password is null dont try to loging
        if (password == null || password.length() == 0 || username == null || username.length() == 0)
            return null;

        UserModel user = findByUserName(username);
        if (user == null) // cant find user so bomb
            return null;
        
        String hash = HashHelper.hashPassword(password);
        if (user.getPassword().equals(hash))
            return user;
        else
            return null;
    }

    public List<UserModel> getAll(){
        UserModel query = new UserModel();

        DynamoDBQueryExpression<UserModel> queryExpression = (new DynamoDBQueryExpression<UserModel>().
                withHashKeyValues(query));
        List <UserModel> results = DatabaseService.getInstance().getMapper().
                query(UserModel.class,queryExpression);
        return results;
    }

    public UserModel findByUserName(String username){
        // Can do this in DB later but for quick n cheap
        List< UserModel> userList = getAll();
        for (UserModel user : userList) {
            // should check for password really
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public void add(UserModel user) {
        DatabaseService.getInstance().getMapper().save(user);
    }


    public void remove(UserModel user) {
        DatabaseService.getInstance().getMapper().delete(user);
    }

    public void update(UserModel user) {
        DatabaseService.getInstance().getMapper().save(user);

    }
}
