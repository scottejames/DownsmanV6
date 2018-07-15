package com.scottejames.downsman.services;

import com.scottejames.downsman.model.UserModel;
import com.scottejames.downsman.utils.HashHelper;

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
    private final TeamService teamService = new TeamService();
    private final TestService testService = new TestService();
    private final UserService userService = new UserService();

    public TeamService getTeamService() {
        return teamService;
    }
    public TestService getTestService() { return testService; }
    public UserService getUserService() { return userService; }

    private void createTestData() {


        UserModel userOne = new UserModel("scott",HashHelper.hashPassword("knot8gen"));
        userOne.setAdmin(true);
        UserModel userTwo = new UserModel("fred",HashHelper.hashPassword("knot8gen"));
        userTwo.setAdmin(false);

        userService.add(userOne);
        userService.add(userTwo);



    }
}