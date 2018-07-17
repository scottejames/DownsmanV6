package com.scottejames.downsman.services;

import com.scottejames.downsman.model.UserModel;
import com.scottejames.downsman.utils.HashHelper;

import java.util.List;

public class UserService extends Service<UserModel> {


    public UserService(){
        super(false);
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
