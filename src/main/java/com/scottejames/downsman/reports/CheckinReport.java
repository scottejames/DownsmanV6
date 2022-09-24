package com.scottejames.downsman.reports;

import com.amazonaws.AmazonWebServiceClient;
import com.amazonaws.metrics.AwsSdkMetrics;
import com.scottejames.downsman.model.ReferenceData;
import com.scottejames.downsman.model.ScoutModel;
import com.scottejames.downsman.model.SupportModel;
import com.scottejames.downsman.model.TeamModel;
import com.scottejames.downsman.services.ServiceManager;
import com.scottejames.downsman.services.TeamService;
import com.scottejames.downsman.services.UserService;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CheckinReport {
    public static void main(String [] args) {
        BufferedWriter writer = null;
        String out = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        String fileName = "/tmp/Checkin-Report-" + out + ".csv";
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
        } catch (IOException e) {
            System.err.println("UNABLE TO WRITE TO FILE!");
            e.printStackTrace();
        }
        List<String> results = new ArrayList<>();

        TeamService teamService = ServiceManager.getInstance().getTeamService();
        List<TeamModel> dynTeamModelList = teamService.getAllAll();
        List<TeamModel> teamModelList = new LinkedList<>();
        for (TeamModel model : dynTeamModelList)
            teamModelList.add(model);

        Collections.sort(teamModelList,new TeamComparitor());
        for (String hikeClass : ReferenceData.HIKE_CLASSES) {
            results.add("Printing teams for " + hikeClass);
            results.add("");
            for (TeamModel team : teamModelList) {
                if ((team.getHikeClass() == null)|| (team.getHikeClass().equals(hikeClass))) {
                    results.add("Leader Name : " + team.getLeaderName() + ", Team Name : '" + team.getTeamName() + ", Group Name : " + team.getGroupName());
                    results.add("County: " + team.getCounty() + ", District: " + team.getDistrict() + ", Section: " + team.getSection());
                    results.add("Team phone : " + team.getActiveMobile() + ", backup : " + team.getBackupMobile());
                    List<ScoutModel> scouts = team.getScoutsTeam();
                    for (ScoutModel scout: scouts){
                        results.add(scout.getFullName() + ", " + scout.getDob());
                    }
                    results.add("");

                }

            }
        }
        try {

            for (String line: results){

                writer.write(line + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


