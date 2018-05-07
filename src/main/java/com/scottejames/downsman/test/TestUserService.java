package com.scottejames.downsman.test;

import com.scottejames.downsman.model.UserModel;
import com.scottejames.downsman.services.ServiceManager;
import com.scottejames.downsman.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestUserService {
    private static UserService service = null;

    @BeforeEach
    void setup(){

        service = ServiceManager.getInstance().getUserService();
        service.reset();
    }

    @Test
    void loginNoUsers(){
        UserModel u = service.login("scott","password");
        assertEquals (u,null);
    }

    @Test
    void failedLoginWithUsers(){
        service.add(new UserModel("fred","password"));
        UserModel u = service.login("scott","password");
        assertEquals (u,null);
    }

    @Test
    void loginWithUsers(){
        service.add(new UserModel("fred","password"));
        UserModel u = service.login("fred","password");
        assertEquals (u.getUsername(),"fred");
    }
}
