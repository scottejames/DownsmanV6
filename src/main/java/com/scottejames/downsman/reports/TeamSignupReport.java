package com.scottejames.downsman.reports;

import com.scottejames.downsman.model.TeamModel;
import com.scottejames.downsman.model.UserModel;
import com.scottejames.downsman.services.ServiceManager;
import com.scottejames.downsman.services.TeamService;
import com.scottejames.downsman.services.UserService;

import java.util.ArrayList;
import java.util.List;

public class TeamSignupReport {
    public static void main(String [] args) throws InterruptedException {
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

            results.add(owner.getUsername() + ", " + owner.getEmail() + ", " + team.getTeamName() + ", " +
            team.getHikeClass() + ", " + team.getGroupName() + ", " + team.getDistrict() + ", " + team.getSection() + ", " +
            team.getCounty() + ", " + team.getEmergencyContactName() + ", " + team.getEmergencyContactLandline() + ", " + team.isCampingAtStart() + ", " + team.isCommittedToRun() + ", " + team.isPaymentRecieved() + ", " +
            team.isTeamSubmitted() + ", " + (teamService.validate(team).length == 0) + "," + teamSize + ", " +  serviceTeamSize);
            if (teamService.validate(team).length == 0){
                valid++;
            }
           // Thread.sleep(1000);

        }
        System.out.println("Leader Name, Leader Email, Team Name, Class, Group Name, District, Section, County, Emergency Contact, Emergency Contact Phone, Camping, Running, Paid, Submitted, Valid Team, Size, Service Team");

        for (String line: results){

            System.out.println(line);
        }

        System.out.println(results.size() + " teams entered of which " + valid + " are valid");
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
