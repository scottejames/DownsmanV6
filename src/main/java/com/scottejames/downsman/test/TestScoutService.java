package com.scottejames.downsman.test;

import com.scottejames.downsman.model.ScoutModel;
import com.scottejames.downsman.services.ScoutService;
import com.scottejames.downsman.services.ServiceManager;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestScoutService {
    private static ScoutService service = new ScoutService();

    @BeforeEach
    void setup(){
        service = new ScoutService();

    }

    @Test
    public void addScout(){
        ScoutModel scout = new ScoutModel("Owner Id","This Is the Name", LocalDate.now());
        assertTrue(scout.isPersisted() == false);
        service.add(scout);
        assertTrue(scout.isPersisted() == true);
        service.remove(scout);
        assertTrue(scout.isPersisted() == false);


    }
    @Test
    public void retrieveScout(){
        ScoutModel scout = new ScoutModel("ID-12-34","A new scout", LocalDate.now());
        service.add(scout);
        List<ScoutModel> scoutList = service.getAll("ID-12-34");
        assertEquals(scoutList.size(),1);

        ScoutModel result = scoutList.get(0);
        assertTrue(scout.getFullName().equals(result.getFullName()));
        assertEquals(scout.getDob(),result.getDob());
        assertEquals(scout.getDobEpoch(),result.getDobEpoch());
        service.remove(scout);
    }
    @Test
    public void addScoutsToDifferentOwners(){
        ScoutModel scoutOne= new ScoutModel("ID-12-34","A new scout", LocalDate.now());
        ScoutModel scoutTwo = new ScoutModel("ID-12-35","A new scout", LocalDate.now());
        service.add(scoutOne);
        service.add(scoutTwo);

        List<ScoutModel> scoutList = service.getAll("ID-12-34");
        assertEquals(scoutList.size(),1);
        service.remove(scoutOne);
        service.remove(scoutTwo);

    }
    @Test
    public void addSeveralScoutsToSameOwner(){
        ScoutModel scoutOne= new ScoutModel("ID-12-34","A new scout", LocalDate.now());
        ScoutModel scoutTwo = new ScoutModel("ID-12-34","A new scout", LocalDate.now());
        service.add(scoutOne);
        service.add(scoutTwo);

        List<ScoutModel> scoutList = service.getAll("ID-12-34");
        assertEquals(scoutList.size(),2);
        service.remove(scoutOne);
        service.remove(scoutTwo);
    }

    @Test
    public void noIDea(){
        ScoutModel scoutOne= new ScoutModel("ID-12-36","A new scout", LocalDate.now());
        service.add(scoutOne);
        scoutOne.setFullName("New Name");
        service.add(scoutOne);
        List<ScoutModel> scoutList = service.getAll("ID-12-36");
        assertEquals(scoutList.size(),1);
        service.remove(scoutOne);

    }
}
