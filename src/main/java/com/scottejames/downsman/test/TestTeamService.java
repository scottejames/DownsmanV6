package com.scottejames.downsman.test;

import com.scottejames.downsman.model.ScoutModel;
import com.scottejames.downsman.model.TeamModel;
import com.scottejames.downsman.services.ServiceManager;
import com.scottejames.downsman.services.TeamService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestTeamService {

    private static TeamService service = null;

    @BeforeEach
    void setup(){
        service = ServiceManager.getInstance().getTeamService();

    }

    @Test
    void addTeam(){

        TeamModel team = new TeamModel("ID-1","Team One");

        assertEquals(team.isPersisted(),false);
        assertEquals(team.getId(),null);

        service.add(team);
        assertEquals(team.isPersisted(), true);
        assertTrue(team.getId()!=null);

        service.remove(team);
    }
    @Test
    void addScoutToTeam(){

        TeamModel team = new TeamModel ("ID-2","TeamOne");
        service.add(team);

        ScoutModel scout = new ScoutModel("Scott", LocalDate.now());

        assertEquals(scout.isPersisted(),false);
        assertEquals(team.getScoutsTeam().size(),0);

        team.addScoutMember(scout);

        assertEquals(scout.isPersisted(),true);
        assertEquals(team.getScoutsTeam().size(),1);

        team.removeScoutMember(scout);
        assertEquals(scout.isPersisted(),false);

        assertEquals(team.getScoutsTeam().size(),0);

        service.remove(team);



    }
    @Test
    void addTwoScoutToTeam(){

        TeamModel team = new TeamModel ("ID-3","Team One");
        service.add(team);
        ScoutModel scoutOne = new ScoutModel("Scott", java.time.LocalDate.now());
        ScoutModel scoutTwo = new ScoutModel("Fred",java.time.LocalDate.now());
        ScoutModel scoutThree = new ScoutModel("Harry",java.time.LocalDate.now());
        team.addScoutMember(scoutOne);
        team.addScoutMember(scoutTwo);
        team.addScoutMember(scoutThree);

        assertEquals(team.getScoutsTeam().size(),3);

        team.removeScoutMember(scoutOne);
        team.removeScoutMember(scoutTwo);
        team.removeScoutMember(scoutThree);

        service.remove(team);
    }
    @Test
    void validateListOfScouts(){

        TeamModel team = new TeamModel ("ID-4","Team One");
        service.add(team);
        ScoutModel scoutOne = new ScoutModel("Scott", java.time.LocalDate.now());
        ScoutModel scoutTwo = new ScoutModel("Fred",java.time.LocalDate.now());
        ScoutModel scoutThree = new ScoutModel("Harry",java.time.LocalDate.now());
        team.addScoutMember(scoutOne);
        team.addScoutMember(scoutTwo);
        team.addScoutMember(scoutThree);

        List<ScoutModel> list = team.getScoutsTeam();
        assertEquals(list.size(),3);
        boolean result = true;
        for (ScoutModel m : list){
            switch (m.getFullName()){
                case "Scott":
                    break;
                case "Fred":
                    break;
                case "Harry":
                    break;
                default:
                    result = false;
            }
        }
        assertTrue(result);
        service.remove(team);


    }

    @Test
    void removeScoutFromTeam(){
        TeamModel team = new TeamModel ("ID-4","Team One");
        service.add(team);
        ScoutModel scoutOne = new ScoutModel("Scott", java.time.LocalDate.now());
        ScoutModel scoutTwo = new ScoutModel("Fred",java.time.LocalDate.now());
        ScoutModel scoutThree = new ScoutModel("Harry",java.time.LocalDate.now());
        team.addScoutMember(scoutOne);
        team.addScoutMember(scoutTwo);
        team.addScoutMember(scoutThree);

        assertEquals(team.getScoutsTeam().size(),3);

        team.removeScoutMember(scoutOne);
        assertEquals(team.getScoutsTeam().size(),2);

        team.removeScoutMember(scoutTwo);
        assertEquals(team.getScoutsTeam().size(),1);
        team.removeScoutMember(scoutThree);
        assertEquals(team.getScoutsTeam().size(),0);

        service.remove(team);

    }

    @Test
    void testGetAll(){
        TeamModel teamOne = new TeamModel("ID-5","Team One");
        TeamModel teamTwo = new TeamModel("ID-5", "Team Two");
        service.add(teamOne);
        service.add(teamTwo);

        List <TeamModel> results = service.getAll("ID-5");
        assertTrue(results.size() == 2);
        service.remove(teamOne);
        service.remove(teamTwo);
    }

    @Test
    void testGetAllAll(){
        TeamModel teamOne = new TeamModel("ID-6","Team One");
        TeamModel teamTwo = new TeamModel("ID-6", "Team Two");

        TeamModel teamThree = new TeamModel("ID-7", "Team Three");
        TeamModel teamFour = new TeamModel("ID-9", "Team Four");

        service.add(teamOne);
        service.add(teamTwo);
        service.add(teamThree);
        service.add(teamFour);

        List <TeamModel> results = service.getAll("ID-6");
        assertTrue(results.size() == 2);

        List <TeamModel> allAllResults = service.getAllAll();
        assertTrue(allAllResults.size() == 4);

        service.remove(teamOne);
        service.remove(teamTwo);
        service.remove(teamThree);
        service.remove(teamFour);

    }
}
