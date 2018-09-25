package com.scottejames.downsman.reports;

import com.scottejames.downsman.model.TeamModel;
import com.scottejames.downsman.model.UserModel;
import com.scottejames.downsman.services.ServiceManager;
import com.scottejames.downsman.services.TeamService;
import com.scottejames.downsman.services.UserService;

import java.util.ArrayList;
import java.util.List;

public class TeamSignupReport {
    public static void main(String [] args){
        TeamService teamService = ServiceManager.getInstance().getTeamService();
        UserService userService = ServiceManager.getInstance().getUserService();

        List<UserModel> userList = userService.getAll();
        List<TeamModel> teamModelList =  teamService.getAllAll();

        List<String> results = new ArrayList<>();
        for (TeamModel team: teamModelList){
            String ownerId = team.getOwnerID();
            UserModel owner = getUserById(userList, ownerId);



            results.add(owner.getUsername() + ", " + owner.getEmail() + ", " + team.getTeamName() + ", " +
            team.getHikeClass() + ", " + team.getGroupName() + ", " + team.getDistrict() + ", " + team.getSection() + ", " +
            team.getCounty() + ", " + team.isCampingAtStart() + ", " + team.isCommittedToRun() + ", " + team.isPaymentRecieved() + ", " +
            team.isTeamSubmitted() + ", " + (teamService.validate(team).length == 0));


        }
        System.out.println("Leader Name, Leader Email, Team Name, Class, Group Name, District, Section, County, Camping, Running, Paid, Submitted, Valid Team");

        for (String line: results){

            System.out.println(line);
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
