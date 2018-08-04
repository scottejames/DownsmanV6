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

class TestScoutService {

    private static TeamService service = null;

    @BeforeEach
    void setup(){
        service = ServiceManager.getInstance().getTeamService();
        service.reset();

    }

    @Test
    void addTeam(){

        TeamModel team = new TeamModel("Team One");

        assertEquals(team.isPersisted(),false);
        assertEquals(team.getId(),0);

        service.add(team);
        assertEquals(team.isPersisted(), true);
        assertEquals(team.getId(),1);

        TeamModel result = service.getById(1);
        assertEquals(team, result);
    }
    @Test
    void addScoutToTeam(){

        TeamModel team = new TeamModel ("Team One");
        service.add(team);

        ScoutModel scout = new ScoutModel("Scott", LocalDate.now());

        assertEquals(scout.isPersisted(),false);
        assertEquals(team.getScoutsTeam().size(),0);

        team.addScoutMember(scout);

        assertEquals(scout.isPersisted(),true);
        assertEquals(team.getScoutsTeam().size(),1);
    }
    @Test
    void addTwoScoutToTeam(){

        TeamModel team = new TeamModel ("Team One");
        service.add(team);

        team.addScoutMember(new ScoutModel("Scott", java.time.LocalDate.now()));
        team.addScoutMember(new ScoutModel("Fred",java.time.LocalDate.now()));
        team.addScoutMember(new ScoutModel("Harry",java.time.LocalDate.now()));

        assertEquals(team.getScoutsTeam().size(),3);
    }
    @Test
    void validateListOfScouts(){

        TeamModel team = new TeamModel ("Team One");
        service.add(team);
        team.addScoutMember(new ScoutModel("Scott", java.time.LocalDate.now()));
        team.addScoutMember(new ScoutModel("Fred",java.time.LocalDate.now()
        ));
        team.addScoutMember(new ScoutModel("Harry",java.time.LocalDate.now()));

        List<ScoutModel> list = team.getScoutsTeam();
        assertEquals(list.size(),3);
        assertTrue(list.get(0).getFullName().equals("Scott"));
        assertTrue(list.get(1).getFullName().equals("Fred"));
        assertTrue(list.get(2).getFullName().equals("Harry"));

    }

    @Test
    void removeScoutFromTeam(){
        TeamModel team = new TeamModel ("Team One");
        service.add(team);
        team.addScoutMember(new ScoutModel("Scott", java.time.LocalDate.now()));
        team.addScoutMember(new ScoutModel("Fred",java.time.LocalDate.now()));
        team.addScoutMember(new ScoutModel("Harry",java.time.LocalDate.now()));

        ScoutModel scout = team.getScoutsTeam().get(0);
        assertEquals(team.getScoutsTeam().size(),3);

        team.removeScoutMember(scout);
        assertEquals(team.getScoutsTeam().size(),2);


    }
}
