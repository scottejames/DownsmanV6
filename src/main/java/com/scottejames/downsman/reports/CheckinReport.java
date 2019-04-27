package com.scottejames.downsman.reports;

import com.scottejames.downsman.model.ReferenceData;
import com.scottejames.downsman.model.ScoutModel;
import com.scottejames.downsman.model.SupportModel;
import com.scottejames.downsman.model.TeamModel;
import com.scottejames.downsman.services.ServiceManager;
import com.scottejames.downsman.services.TeamService;
import com.scottejames.downsman.services.UserService;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class CheckinReport {
    public static void main(String [] args) {


        TeamService teamService = ServiceManager.getInstance().getTeamService();
        List<TeamModel> dynTeamModelList = teamService.getAllAll();
        List<TeamModel> teamModelList = new LinkedList<>();
        for (TeamModel model : dynTeamModelList)
            teamModelList.add(model);

        Collections.sort(teamModelList,new TeamComparitor());
        for (String hikeClass : ReferenceData.HIKE_CLASSES) {
            System.out.println("Printing teams for " + hikeClass);
            System.out.println("");
            for (TeamModel team : teamModelList) {
                if (team.getHikeClass().equals(hikeClass)) {
                    System.out.println("Leader Name : " + team.getLeaderName() + ", Team Name : '" + team.getTeamName() + ", Group Name : " + team.getGroupName());
                    System.out.println("County: " + team.getCounty() + ", District: " + team.getDistrict() + ", Section: " + team.getSection());
                    System.out.println("Team phone : " + team.getActiveMobile() + ", Home contact landline : " + team.getEmergencyContactLandline());
                    List<ScoutModel> scouts = team.getScoutsTeam();
                    for (ScoutModel scout: scouts){
                        System.out.println(scout.getFullName() + ", " + scout.getDob());
                    }
                    System.out.println("");

                }

            }
        }
    }
}


