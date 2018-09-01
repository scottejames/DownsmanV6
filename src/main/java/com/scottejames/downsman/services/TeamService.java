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



    public String [] validate(TeamModel model){

//            String validation = "";
//            ArrayList<String> results = new ArrayList<>();
//
//            // Mandatory Fields
//            if ((model.getTeamName() == null) || (model.getTeamName().isEmpty())){
//                results.add("Team Name Cant Be Empty");
//            }
//            if ((model.getHikeClass() == null) || (model.getHikeClass().isEmpty())){
//                results.add("Hike Class Cant Be Empty");
//            }
//            if ((model.getActiveMobile() == null ) || (model.getActiveMobile().isEmpty())){
//                results.add("Active mobile cant be empty");
//            }
//            if ((model.getBackupMobile() == null) || (model.getBackupMobile().isEmpty())){
//                results.add("Backup mobile cant be empty");
//            }
//            if ((model.getEmergencyContactEmail().isEmpty()) ||
//                    (model.getEmergencyContactLandline().isEmpty()) ||
//                    (model.getEmergencyContactLandline().isEmpty()) ||
//                    (model.getEmergencyContactName().isEmpty())){
//                results.add("Complete emergency contact information");
//            }
//

            // Validation based on hike class
//            String hikeClass = model.getHikeClass();
//            if (hikeClass != null) {
//                int teamSize = scoutService.getAll(this.getId()).size();
//                boolean leader = false;
//                int combinedAge = 0;
//                ArrayList<Integer> ages = new ArrayList<>();
//                for (ScoutModel s : scoutService.getAll(this.getId()
//                )) {
//                    if (s.isLeader() == true)
//                        leader = true;
//                    else
//                        ages.add(s.calculateAge());
//                }
//                int minAge = Integer.MAX_VALUE;
//                int maxAge = 0;
//                for (Integer age : ages) {
//                    combinedAge += age;
//                    if (minAge > age) minAge = age;
//                    if (maxAge < age) maxAge = age;
//                }
//                boolean serviceCrew = false;
//                if (getSupportTeam().size() != 0)
//                    serviceCrew = true;
//
//                // Do the validation
//                if (minAge == 0){
//                    results.add("Please ensure date of birth is entered for all (non leader) hikers");
//                }
//                switch (hikeClass) {
//                    case "Open":
//                        if ((teamSize < 3) || (teamSize > 6))
//                            results.add("For Open, team size must be between 3 and 6");
//                        if ((minAge < 12) && (leader == false))
//                            results.add("For Open, if min age is less than 12 you have to have a leader hiking");
//                        break;
//                    case "B-Class":
//                        if (teamSize != 4)
//                            results.add("For B-Class team size must be 4");
//                        if (leader == true)
//                            results.add("For B-Class leaders may not hike");
//                        if (combinedAge > 48)
//                            results.add("For B-Class combined age must be more than 48 ");
//                        if (serviceCrew == false)
//                            results.add("InMemoryService crew required for B-Class");
//                        if (maxAge > 18)
//                            results.add("For B-Class may not have hikers over 18");
//                        break;
//                    case "A-Class":
//                        if (teamSize != 3)
//                            results.add("For A-Class team size must be 3");
//                        if (leader == true)
//                            results.add("For A-Class leaders may not hike");
//                        if (combinedAge > 48)
//                            results.add("For A-Class combined age must be more than 48 ");
//                        if (serviceCrew == false)
//                            results.add("InMemoryService crew required for A-Class");
//                    case "V-Class":
//                        if (teamSize != 3)
//                            results.add("For V-Class team size must be 3");
//                        if (leader == true)
//                            results.add("For V-Class leaders may not hike");
//                        if (combinedAge > 100)
//                            results.add("For V-Class combined age must be more than 100 ");
//                        if (serviceCrew == false)
//                            results.add("InMemoryService crew required for V-Class");
//                        break;
//                    case "S-Class":
//                        if (teamSize != 4)
//                            results.add("For S-Class team size must be 4");
//                        if (leader == true)
//                            results.add("For S-Class leaders may not hike");
//                        if (combinedAge > 48)
//                            results.add("For S-Class combined age must be more than 48");
//                        if (maxAge < 14.5)
//                            results.add("For S-Class max age must be less than 14.5");
//                        if (serviceCrew == false)
//                            results.add("InMemoryService crew required for S-Class");
//                        break;
//                    case "E-Class":
//                        if (teamSize != 4)
//                            results.add("For E-Class team size must be 4");
//                        if (leader == true)
//                            results.add("For E-Class leaders may not hike");
//                        if (combinedAge > 48)
//                            results.add("For E-Class combined age must be more than 48");
//                        if (combinedAge < 62)
//                            results.add("For E-Class combined age must be less than 62");
//                        if (serviceCrew == false)
//                            results.add("InMemoryService crew required for E-Class");
//                        if (maxAge > 18)
//                            results.add("For E-Class may not have hikers over 18");
//                        break;
//                }
//            }
    //        return results.toArray(new String[results.size()]);
        return null;
        }

}
