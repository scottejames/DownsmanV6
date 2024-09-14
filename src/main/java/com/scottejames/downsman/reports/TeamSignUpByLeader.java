package com.scottejames.downsman.reports;

import com.scottejames.downsman.model.TeamModel;
import com.scottejames.downsman.model.UserModel;
import com.scottejames.downsman.services.ServiceManager;
import com.scottejames.downsman.services.TeamService;
import com.scottejames.downsman.services.UserService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TeamSignUpByLeader {
    public static void main(String [] args) throws InterruptedException {
            String leader = args[0];
            System.out.println("Getting teams by leaders: " + leader);

            TeamService teamService = ServiceManager.getInstance().getTeamService();
            UserService userService = ServiceManager.getInstance().getUserService();

            List<UserModel> userList = userService.getAll();
            List<TeamModel> teamModelList = teamService.getAllAll();
            int valid = 0;

            for (TeamModel team : teamModelList) {
                String ownerId = team.getOwnerID();
                UserModel owner = getUserById(userList, ownerId);
                if (owner.getUsername().equals(leader)) {
                    int teamSize = team.getScoutsTeam().size();
                    int serviceTeamSize = team.getSupportTeam().size();
                    String [] results = teamService.validate(team);
                    if (results.length == 0){
                        System.out.println("Team : " + team.getTeamName() + " is valid");
                    } else
                    {
                        System.out.println("Team : " + team.getTeamName() + " is not valid");
                    }
                    for (String result : teamService.validate(team)) {
                        System.out.println(result);
                    }
                }

                // Thread.sleep(1000);
            }


    }

    public static UserModel getUserById(List<UserModel> userList,String id){
        for (UserModel user: userList){
            if (user.getId().equals(id)){
                return user;
            }
        }
        return null;

    }
}
