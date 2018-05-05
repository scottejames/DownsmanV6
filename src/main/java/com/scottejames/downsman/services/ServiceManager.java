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

        TeamModel one = new TeamModel("teamOne","Open","Submitted");
        TeamModel two = new TeamModel("teamTwo","Open","Draft");
        teamService.add(one);
        teamService.add(two);

        one.addScoutMember(new ScoutModel("Scott James","17-11-73","male"));
        one.addScoutMember(new ScoutModel("Thomas James","17-11-73","male"));

        two.addScoutMember(new ScoutModel("Maddie", "17-11-73","female"));



        SupportModel supportOne = new SupportModel("SCott","17-11-73","start","finish");
        SupportModel supportTwo  = new SupportModel("Anna","17-11-73","start","finish");
        one.addSupportMember(supportOne);
        one.addSupportMember(supportTwo);


        UserModel userOne = new UserModel("scottejames","password");
        UserModel userTwo = new UserModel("fred","password");
        userService.add(userOne);
        userService.add(userTwo);



    }
}