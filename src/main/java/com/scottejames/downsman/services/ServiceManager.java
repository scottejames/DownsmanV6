package com.scottejames.downsman.services;

import com.scottejames.downsman.model.ScoutModel;
import com.scottejames.downsman.model.SupportModel;
import com.scottejames.downsman.model.TeamModel;
import com.scottejames.downsman.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class ServiceManager {

    private static ServiceManager instance = null;

    public static synchronized ServiceManager getInstance() {
        if (instance == null) {
            instance = new ServiceManager();
            // Create some sample data
            instance.createTestData();
        }
        return instance;
    }
    private TeamService teamService = new TeamService();
    private TestService testService = new TestService();
    private UserService userService = new UserService();

    public TeamService getTeamService() {
        return teamService;
    }
    public TestService getTestService() { return testService; }
    public UserService getUserService() { return userService; }
    public void createTestData() {

        UserModel userOne = new UserModel("scott","password");
        UserModel userTwo = new UserModel("fred","password");
        userService.add(userOne);
        userService.add(userTwo);



    }
}