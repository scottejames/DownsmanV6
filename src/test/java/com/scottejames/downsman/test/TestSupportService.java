package com.scottejames.downsman.test;

import com.scottejames.downsman.model.SupportModel;
import com.scottejames.downsman.services.SupportService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSupportService {

    private static SupportService service  = new SupportService();

    @Test
    public void addSupport(){
        SupportModel support = new SupportModel("OwnerID","Fred","0111","Start","Finish");
        assertTrue(support.isPersisted()== false);
        service.add(support);
        assertTrue(support.isPersisted() == true);
        service.remove(support);
        assertTrue(support.isPersisted()== false);

    }

    @Test
    public void retrieveSupport(){
        SupportModel support = new SupportModel("ID-1","Fred","0111","Start","Finish");
        service.add(support);
        List<SupportModel> supportList = service.getAll("ID-1");
        assertEquals(supportList.size(),1);

        SupportModel result = supportList.get(0);
        assertTrue(support.getFullName().equals(result.getFullName()));
        service.remove(result);
    }

}
