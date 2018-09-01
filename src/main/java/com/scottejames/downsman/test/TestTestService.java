package com.scottejames.downsman.test;

import com.scottejames.downsman.model.TestModel;
import com.scottejames.downsman.services.ServiceManager;
import com.scottejames.downsman.services.TestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestTestService {

    private static TestService service = null;

    @BeforeEach
    void setup(){

        service = ServiceManager.getInstance().getTestService();
        service.reset();
    }

    @Test
    void storeModelSetsId(){
        TestModel model= new TestModel("First");
        assertEquals(model.getId(),null);
        service.add(model);
        assertNotEquals(model.getId(),null);

    }

    @Test
    void storeModelGetSameModel(){
        TestModel model= new TestModel("Second");
        service.add(model);
        TestModel result = service.getById(model.getId());
        assertTrue(model.getId().equals(result.getId()));
    }

    @Test
    void addSeveral(){
        int size  = service.getAll().size();
        assertEquals(size,0);
        service.add(new TestModel("One"));
        service.add(new TestModel("Two"));
        size  = service.getAll().size();
        assertEquals(size,2);


    }
}
