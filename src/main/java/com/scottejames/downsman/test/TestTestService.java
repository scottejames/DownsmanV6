package com.scottejames.downsman.test;

import com.scottejames.downsman.model.TestModel;
import com.scottejames.downsman.services.ServiceManager;
import com.scottejames.downsman.services.TestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTestService {

    private static TestService service = null;

    @BeforeEach
    public void setup(){

        service = ServiceManager.getInstance().getTestService();
        service.reset();
    }

    @Test
    public void storeModelSetsId(){
        TestModel model= new TestModel("First");
        service.add(model);
        assertEquals(model.getId(),1);
    }

    @Test
    public void storeModelGetSameModel(){
        TestModel model= new TestModel("First");
        service.add(model);
        TestModel result = service.getById(model.getId());
        assertEquals(model,result);
    }

    @Test
    public void addSeveral(){
        int size  = service.getAll().size();
        assertEquals(size,0);
        service.add(new TestModel("One"));
        service.add(new TestModel("Two"));
        size  = service.getAll().size();
        assertEquals(size,2);


    }
}
