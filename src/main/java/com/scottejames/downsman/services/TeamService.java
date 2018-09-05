package com.scottejames.downsman.services;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.scottejames.downsman.model.ScoutModel;
import com.scottejames.downsman.model.SessionState;
import com.scottejames.downsman.model.SupportModel;
import com.scottejames.downsman.model.TeamModel;
import elemental.html.Database;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public class TeamService {
    
    public TeamService(){

    }

    public List<TeamModel> getAllAll(){
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        List<TeamModel> results = DatabaseService.getInstance().getMapper().scan(TeamModel.class,scanExpression);
        return results;
    }
    public List<TeamModel> getAll(){
        return getAll(SessionState.getInstance().getCurrentUser().getId());
    }
    public List<TeamModel> getAll(String teamId){

        TeamModel query = new TeamModel();
        query.setOwnerID(teamId);

        DynamoDBQueryExpression<TeamModel> queryExpression = (new DynamoDBQueryExpression<TeamModel>().withHashKeyValues(query));
        List <TeamModel> results = DatabaseService.getInstance().getMapper().query(TeamModel.class,queryExpression);
        return results;
    }




    public TeamModel getById(String id) {
        return null;
    }

    public void add(TeamModel team) {
        DatabaseService.getInstance().getMapper().save(team);
    }
    public void update(TeamModel team) {
        DatabaseService.getInstance().getMapper().save(team);

    }
    public void remove(TeamModel team) {
        for (ScoutModel m: team.getScoutsTeam())
            DatabaseService.getInstance().getMapper().delete(m);
        for (SupportModel m: team.getSupportTeam())
            DatabaseService.getInstance().getMapper().delete(m);

        DatabaseService.getInstance().getMapper().delete(team);
        team.setId(null);

    }


    private boolean nullorEmpty(String s){
        if ((s==null) || s.isEmpty())
            return true;
        else
            return false;
    }
    public String [] validate(TeamModel model){

            String validation = "";
            ArrayList<String> results = new ArrayList<>();

            // Mandatory Fields
            if (nullorEmpty(model.getTeamName())){
                results.add("Team Name Cant Be Empty");
            }
            if (nullorEmpty(model.getHikeClass())){
                results.add("Hike Class Cant Be Empty");
            }
            if (nullorEmpty(model.getActiveMobile())){
                results.add("Active mobile cant be empty");
            }
            if (nullorEmpty(model.getBackupMobile())){
                results.add("Backup mobile cant be empty");
            }
            if ((nullorEmpty(model.getEmergencyContactLandline())) ||
                    (nullorEmpty(model.getEmergencyContactEmail())) ||
                    (nullorEmpty(model.getEmergencyContactName())) ||
                    (nullorEmpty(model.getEmergencyContactMobile()))){
                results.add("Complete emergency contact information");
            }

            List<ScoutModel> scouts = model.getScoutsTeam();
            List<SupportModel> support = model.getSupportTeam();
            if ((scouts == null) || (scouts.size() ==0)) {
                results.add("Missing some scouts?");
            }

            //If we have errors above then fix those FIRST.
            if (results.size()!=0) {
                results.add("Missing mandatory fields fix these before validation can be completed");
                return results.toArray(new String[results.size()]);
            }


             //Validation based on hike class
            String hikeClass = model.getHikeClass();
            if (hikeClass != null) {
                int teamSize = model.getScoutsTeam().size();
                boolean leader = false;
                float combinedAge = 0;
                int intCombinedAge = 0;

                ArrayList<Float> ages = new ArrayList<>();
                ArrayList<Integer> intAges = new ArrayList<>();
                for (ScoutModel s : scouts) {
                    if (s.isLeader() == true)
                        leader = true;
                    else {
                        ages.add(s.calculateAge());
                        intAges.add((int) s.calculateAge());
                    }
                }
                float minAge = Integer.MAX_VALUE;
                float maxAge = 0;

                int intMinAge = Integer.MAX_VALUE;
                int intMaxAge = 0;

                for (Integer age: intAges){
                        intCombinedAge += age;
                        if (intMinAge > age) minAge = age;
                        if (intMaxAge < age) maxAge = age;
                }
                for (Float age : ages) {
                    combinedAge += age;
                    if (minAge > age) minAge = age;
                    if (maxAge < age) maxAge = age;
                }
                boolean serviceCrew = false;
                if (support.size() != 0)
                    serviceCrew = true;

                // Do the validation
                if (minAge == 0){
                    results.add("Please ensure date of birth is entered for all (non leader) hikers");
                }
                switch (hikeClass) {
                    case "Open":
                        if ((teamSize < 3) || (teamSize > 6))
                            results.add("For Open, team size must be between 3 and 6");
                        if ((minAge < 12) && (leader == false))
                            results.add("For Open, if min age is less than 12 you have to have a leader hiking");
                        break;
                    case "B-Class":
                        if (teamSize != 4)
                            results.add("For B-Class team size must be 4 your team is " + teamSize);
                        if (leader == true)
                            results.add("For B-Class leaders may not hike");
                        if (combinedAge < 48)
                            results.add("For B-Class combined age must be more than 48 your combined age is " + intCombinedAge);
                        if (serviceCrew == false)
                            results.add("Service crew required for B-Class");
                        if (maxAge > 18)
                            results.add("For B-Class may not have hikers over 18 your max age is " + intMaxAge);
                        break;
                    case "A-Class":
                        if (teamSize != 3)
                            results.add("For A-Class team size must be 3 your team is " + teamSize);
                        if (leader == true)
                            results.add("For A-Class leaders may not hike");
                        if (combinedAge < 48)
                            results.add("For A-Class combined age must be more than 48 your combined age is " + intCombinedAge);
                        if (serviceCrew == false)
                            results.add("Service crew required for A-Class");
                        break;
                    case "V-Class":
                        if (teamSize != 3)
                            results.add("For V-Class team size must be 3 your team size is " + teamSize);
                        if (leader == true)
                            results.add("For V-Class leaders may not hike");
                        if (combinedAge < 100)
                            results.add("For V-Class combined age must be more than 100 your combined age is " + intCombinedAge);
                        if (serviceCrew == false)
                            results.add("Service crew required for V-Class");
                        break;
                    case "S-Class":
                        if (teamSize != 4)
                            results.add("For S-Class team size must be 4 your team size is " + teamSize);
                        if (leader == true)
                            results.add("For S-Class leaders may not hike");
                        if (combinedAge < 48)
                            results.add("For S-Class combined age must be more than 48 your combined age is " + intCombinedAge);
                        if (maxAge > 14.5)
                            results.add("For S-Class max age must be less than 14.5 your max age is " + maxAge);
                        if (serviceCrew == false)
                            results.add("Service crew required for S-Class");
                        break;
                    case "E-Class":
                        if (teamSize != 4)
                            results.add("For E-Class team size must be 4 your team size is " + teamSize);
                        if (leader == true)
                            results.add("For E-Class leaders may not hike");
                        if (combinedAge < 48)
                            results.add("For E-Class combined age must be more than 48 your combined age is " + intCombinedAge);
                        if (combinedAge < 62)
                            results.add("For E-Class combined age must be less than 62 your combined age is " + intCombinedAge);
                        if (serviceCrew == false)
                            results.add("Service crew required for E-Class");
                        if (maxAge > 18)
                            results.add("For E-Class may not have hikers over 18 your max age is "  + intAges);
                        break;
                }
            }
            return results.toArray(new String[results.size()]);
        }

}
