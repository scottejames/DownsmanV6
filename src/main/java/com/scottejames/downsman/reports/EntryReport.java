package com.scottejames.downsman.reports;

import com.scottejames.downsman.model.*;
import com.scottejames.downsman.services.ScoutService;
import com.scottejames.downsman.services.ServiceManager;
import com.scottejames.downsman.services.TeamService;
import com.scottejames.downsman.services.UserService;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class EntryReport {

    public static void main(String [] args) throws InterruptedException {
        TeamService teamService = ServiceManager.getInstance().getTeamService();


        List<TeamModel> dynTeamModelList = teamService.getAllAll();
        List<TeamModel> teamModelList = new LinkedList<>();
        for(TeamModel model : dynTeamModelList)
            teamModelList.add(model);

        Collections.sort(teamModelList,new TeamComparitor());
        for (String hikeClass : ReferenceData.HIKE_CLASSES) {
            System.out.println("Printing teams for " + hikeClass);
            for (TeamModel team : teamModelList) {
                if (team.getHikeClass().equals(hikeClass)) {
                    System.out.println("Leader Name : " + team.getLeaderName() + ", Team Name : '" + team.getTeamName()) ;
                    System.out.println("Active Phone: '" + team.getActiveMobile() + ", Backup Phone : '" + team.getBackupMobile());
                    if (team.isCommittedToRun() == true){
                        System.out.println("Team is committed to run");
                    }
                    List<ScoutModel> scouts = team.getScoutsTeam();
                    System.out.println("");

                    System.out.println("Name, DOB, Leader, Medical notes");

                    for (ScoutModel scout: scouts){
                        System.out.println(scout.getFullName() + ", " + scout.getDob() + ", " + scout.isLeader() + ", " + emptyString(scout.getMedicalNotes()));
                    }
                    System.out.println("");
                    List<SupportModel> supportTeam = team.getSupportTeam();
                    System.out.println("Support Team");
                    System.out.println("Name, Phone, From, To");

                    for (SupportModel support: supportTeam){
                        System.out.println(support.getFullName() + ", '" + support.getPhoneNumber() + ", " + support.getFrom() + ", " + support.getTo());

                    }
                    System.out.println("");
                    System.out.println("Emergency Contact");
                    System.out.println("Name: " + team.getEmergencyContactName());
                    System.out.println("LandLine: '" + team.getEmergencyContactLandline());
                    System.out.println("Mobile: '" + team.getEmergencyContactMobile());
                    System.out.println("");
                }

            }
        }
    }
    private static String emptyString(String s){
        if (s==null)
            return "N/a";
        else {
            s = s.replace(","," ");
            s = s.replace("\n"," ");
            return s;

        }
    }


}
class TeamComparitor implements Comparator<TeamModel>{

    @Override
    public int compare(TeamModel o1, TeamModel o2) {
        return o1.getLeaderName().compareTo(o2.getLeaderName());
    }
}
