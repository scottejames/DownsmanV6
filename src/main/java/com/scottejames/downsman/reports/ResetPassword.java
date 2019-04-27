package com.scottejames.downsman.reports;

import com.scottejames.downsman.model.UserModel;
import com.scottejames.downsman.services.ServiceManager;
import com.scottejames.downsman.services.UserService;
import com.scottejames.downsman.utils.HashHelper;


public class ResetPassword {

    public static void main(String [] args){
        String userName = "JD1cForest";
        String password = "sc0u75";
        String hashedPassword = HashHelper.hashPassword(password);
        UserService service = ServiceManager.getInstance().getUserService();

        UserModel user = service.findByUserName(userName);
        user.setPassword(hashedPassword);
        service.update(user);

    }
}
