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

public class TeamSignupReport {
    public static void main(String [] args) throws InterruptedException {
        try {
            String out = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            String fileName = "/tmp/Team-Report-" + out + ".csv";
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

        TeamService teamService = ServiceManager.getInstance().getTeamService();
        UserService userService = ServiceManager.getInstance().getUserService();

        List<UserModel> userList = userService.getAll();
        List<TeamModel> teamModelList =  teamService.getAllAll();
        int valid = 0;

        List<String> results = new ArrayList<>();
        for (TeamModel team: teamModelList){
            String ownerId = team.getOwnerID();
            UserModel owner = getUserById(userList, ownerId);

            int teamSize = team.getScoutsTeam().size();
            int serviceTeamSize = team.getSupportTeam().size();

            results.add(owner.getUsername() +  ", " + team.getTeamName() + ", " +
            team.getHikeClassAsString() + ", " + team.getGroupName() + ", " + team.getDistrict() + ", " + team.getSection() + ", " +
            team.getCounty()  +  ", "  + team.getPaymentAmount() + ", " + team.getEntranceFee()  + ", " + team.isPaymentRecieved() + ", " +
            team.isTeamSubmitted() + ", " + (teamService.validate(team).length == 0) + ", " + teamSize + ", " + serviceTeamSize);
            if (teamService.validate(team).length == 0){
                valid++;
            }

           // Thread.sleep(1000);

        }

            writer.write("Leader Name, Team Name, Class, Group Name, District, Section, County, Paid Amount, Entry Cost, Cost Validated, Submitted, Valid Team, Team Size, Service Team Size \n");


        for (String line: results){

            writer.write(line + "\n");
        }


            writer.write(results.size() + " teams entered of which " + valid + " are valid\n");
            writer.close();
        } catch (IOException e) {
            System.err.println("UNABLE TO WRITE TO FILE!");
            e.printStackTrace();
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
