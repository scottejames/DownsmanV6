package com.scottejames.downsman.reports;

import com.scottejames.downsman.model.UserModel;
import com.scottejames.downsman.services.ServiceManager;
import com.scottejames.downsman.services.UserService;

import java.util.List;

public class UserReport {

    public static void main (String [] args){
        UserService service = ServiceManager.getInstance().getUserService();

        List<UserModel> userList = service.getAll();

        System.out.println("UserName, Email");
        for (UserModel user: userList){
            System.out.println(user.getUsername() + "," + user.getEmail());
        }

    }
}
