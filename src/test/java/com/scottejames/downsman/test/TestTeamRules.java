package com.scottejames.downsman.test;

import com.scottejames.downsman.model.ReferenceData;
import com.scottejames.downsman.model.ScoutModel;
import com.scottejames.downsman.model.TeamModel;
import com.scottejames.downsman.services.ServiceManager;
import com.scottejames.downsman.services.TeamService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTeamRules {
    private static TeamService service = null;

    public static LocalDate now;
    public static TeamModel team;
    public static ScoutModel scoutElevenYears;
    public static ScoutModel scoutFourYears;
    public static ScoutModel scoutTwelveYears;
    public static ScoutModel scoutThirteenYears;
    public static ScoutModel leader;

    private static LocalDate dobFromAge(int years, int months) {
        return LocalDate.of(now.getYear() - years, now.getMonthValue() - months, now.getDayOfMonth());
    }

    private static ScoutModel copyScout(ScoutModel s) {
        ScoutModel scout = new ScoutModel(s.getOwnerID(), s.getFullName(), s.getDob());
        scout.setLeader(s.isLeader());
        return scout;

    }

    @Test
    public void testAgeCalcuation(){
        LocalDate dateTwenty = dobFromAge(20,0);
        System.out.println(dateTwenty);
        assertEquals(now.getYear() - dateTwenty.getYear() , 20);



    }
    @BeforeEach
    public void createScouts() {
        service = ServiceManager.getInstance().getTeamService();

        now = LocalDate.now();

        scoutElevenYears = new ScoutModel("Owner1", "Eleven", dobFromAge(11,0));
        scoutFourYears = new ScoutModel("Owner1", "Four", dobFromAge(4,0));
        scoutTwelveYears = new ScoutModel("Owner1", "Twelve", dobFromAge(12,0));
        scoutThirteenYears = new ScoutModel("Owner1", "Thirteen", dobFromAge(13,0));
        leader = new ScoutModel();
        leader.setOwnerID("Owner1");
        leader.setFullName("Leader");
        leader.setLeader(true);

        team = new TeamModel("OwnerId", "TeamName");
        service.add(team);
        team.setActiveMobile("XXX");
        team.setBackupMobile("XXX");
        team.setEmergencyContactEmail("XXX");
        team.setEmergencyContactLandline("XXX");
        team.setEmergencyContactMobile("XXX");
        team.setEmergencyContactName("XXX");
    }

    @Test
    public void testOpenRulesWithLeader() {
        team.setHikeClass(ReferenceData.OPENPLUMPTONITFORD);

        team.addScoutMember(copyScout(scoutElevenYears));
        team.addScoutMember(copyScout(scoutElevenYears));
        String[] results = service.validate(team);

        assertTrue(results.length == 2, "Must have a leader, AND only 2 scouts " + results.length); // must have a leader, only two scouts

        team.addScoutMember(copyScout(scoutElevenYears));
        results = service.validate(team);
        assertTrue(results.length == 1,"Must have a leader " +results.length); // must have a leader

        team.addScoutMember(copyScout(leader));
        results = service.validate(team);
        assertTrue(results.length == 0); //team good


        team.addScoutMember(copyScout(scoutElevenYears));
        team.addScoutMember(copyScout(scoutElevenYears));
        team.addScoutMember(copyScout(scoutElevenYears));
        team.addScoutMember(copyScout(scoutElevenYears));
        results = service.validate(team);
        assertTrue(results.length == 1); //team too big
    }
    @Test
    public void testOpenRulesOverTwelve() {
        team.setHikeClass(ReferenceData.OPENPLUMPTONITFORD);
        team.addScoutMember(copyScout(scoutThirteenYears));
        team.addScoutMember(copyScout(scoutThirteenYears));
        team.addScoutMember(copyScout(scoutThirteenYears));
        team.addScoutMember(copyScout(scoutThirteenYears));
        String[] results = service.validate(team);
        for (String result : results){
            System.out.println(result);
        }
        assertTrue(results.length == 0); // must have a leader, only two scouts
    }
}
