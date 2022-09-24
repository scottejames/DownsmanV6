package com.scottejames.downsman.reports;

import com.scottejames.downsman.model.*;
import com.scottejames.downsman.services.ScoutService;
import com.scottejames.downsman.services.ServiceManager;
import com.scottejames.downsman.services.TeamService;
import com.scottejames.downsman.services.UserService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collector;

public class EntryReport {

    public static void main(String [] args) throws InterruptedException, IOException {
        TeamService teamService = ServiceManager.getInstance().getTeamService();
        String out = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        String fileName = "/tmp/Entry-Report-" + out + ".csv";
        BufferedWriter    writer = new BufferedWriter(new FileWriter(fileName));
        List<String> results = new ArrayList<>();

        List<TeamModel> dynTeamModelList = teamService.getAllAll();
        List<TeamModel> teamModelList = new LinkedList<>();
        for(TeamModel model : dynTeamModelList)
            teamModelList.add(model);
        Arrays.sort(ReferenceData.HIKE_CLASSES);
        int bibNumber = 1;
        Collections.sort(teamModelList,new TeamComparitor());
        for (String hikeClass : ReferenceData.HIKE_CLASSES) {
            results.add("Printing teams for " + hikeClass +"=========================\n");
            for (TeamModel team : teamModelList) {
                if (team.getHikeClass().equals(hikeClass)) {
                    results.add("Bib Number = " + bibNumber++);
                    results.add("\nLeader Name : " + team.getLeaderName() + ", Team Name : '" + team.getTeamName()) ;
                    results.add("Active Phone: '" + team.getActiveMobile() + ", Backup Phone : '" + team.getBackupMobile());
//                    if (team.isCommittedToRun() == true){
//                        results.add("Team is committed to run");
//                    }
                    List<ScoutModel> scouts = team.getScoutsTeam();
                    results.add("");

                    results.add("Name, DOB, Leader, Medical notes");

                    for (ScoutModel scout: scouts){
                        results.add(scout.getFullName() + ", " + scout.getDob() + ", " + scout.isLeader() + ", " + emptyString(scout.getMedicalNotes()));
                    }
                    results.add("");
                    List<SupportModel> supportTeam = team.getSupportTeam();
                    results.add("Support Team");
                    results.add("Name, Phone, From, To");

                    for (SupportModel support: supportTeam){
                        results.add(support.getFullName() + ", '" + support.getPhoneNumber() + ", " + support.getFrom() + ", " + support.getTo());

                    }
                    results.add("");
                    results.add("Emergency Contact");
                    results.add("Name: " + team.getEmergencyContactName());
                    results.add("LandLine: '" + team.getEmergencyContactLandline());
                    results.add("Mobile: '" + team.getEmergencyContactMobile());
                    results.add("");
                }

            }
        }
        for (String line: results){

            writer.write(line + "\n");
        }
        writer.close();
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
        return o1.getGroupName().compareToIgnoreCase(o2.getGroupName());
    }
}
