package com.scottejames.downsman.services;

import com.scottejames.downsman.model.UserModel;
import com.scottejames.downsman.utils.HashHelper;

import java.util.List;

public class UserService extends Service<UserModel> {

    //TODO: Should add uniqueness constraint on username or this will get confused.
    //TODO: Really should check passwords hash things - do something useful
    public UserService(){
        setTestUser("FIXED");
    }

    public UserModel login(String username, String password){
        UserModel user = findByUserName(username);
        String hash = HashHelper.hashPassword(password);
        if (user.getPassword().equals(hash))
            return user;
        else
            return null;
    }

    public UserModel findByUserName(String username){
        List<UserModel> userList = getAll();

        for (UserModel user : userList) {
            // should check for password really
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
