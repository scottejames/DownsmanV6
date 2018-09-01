package com.scottejames.downsman.test;

import com.scottejames.downsman.model.UserModel;
import com.scottejames.downsman.services.ServiceManager;
import com.scottejames.downsman.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestUserService {
    private static UserService service = null;

    @BeforeEach
    void setup(){

        service = ServiceManager.getInstance().getUserService();
    }

    @Test
    void loginNoUsers(){
        UserModel u = service.login("scott","password");
        assertEquals (u,null);
    }

    @Test
    void failedLoginWithUsers(){
        UserModel user  = new UserModel("fred","password");

        service.add(user);
        UserModel u = service.login("scott","password");
        assertEquals (u,null);
        service.remove(user);
    }

    @Test
    void loginWithUsers(){
        UserModel user  = new UserModel("fred","password");

        service.add(user);

        UserModel u = service.login("fred","password");
        assertNotNull(u);
        assertEquals (u.getUsername(),"fred");
        service.remove(user);

    }

    @Test
    public void addUser(){
        UserModel user = new UserModel("Add User","Password");


        service.add(user);
        service.remove(user);


    }
    @Test
    public void retrieveUser(){
        UserModel scout = new UserModel("Test Two","Password");
        service.add(scout);
        List<UserModel> userList = service.getAll();
        assertEquals(userList.size(),1);

        UserModel result = userList.get(0);
        assertTrue(scout.getUsername().equals(result.getUsername()));
        assertTrue(scout.getPassword().equals(result.getPassword()));

        service.remove(scout);
    }

    @Test
    public void addSeveralUsersFindOne(){
        UserModel userOne= new UserModel("Test Three","PasswordOne");
        UserModel userTwo = new UserModel("Test Three.1","PasswordTwo");
        service.add(userOne);
        service.add(userTwo);

        List<UserModel> userList = service.getAll();
        assertEquals(userList.size(),2);

        UserModel foundUser = service.findByUserName("Test Three");
        assertNotNull(foundUser);
        assertTrue(foundUser.getPassword().equals(userOne.getPassword()));

        service.remove(userOne);
        service.remove(userTwo);
    }


}
