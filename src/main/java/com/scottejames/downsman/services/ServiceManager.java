package com.scottejames.downsman.services;

import com.scottejames.downsman.model.UserModel;

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

        UserModel userOne = new UserModel("scott","password");
        UserModel userTwo = new UserModel("fred","password");
        userService.add(userOne);
        userService.add(userTwo);



    }
}