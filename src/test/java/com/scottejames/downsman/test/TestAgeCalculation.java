package com.scottejames.downsman.test;


import com.scottejames.downsman.model.ScoutModel;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAgeCalculation {

    @Test
    public void testYearsCalculation(){

        LocalDate date = LocalDate.of(2017, Month.APRIL,6);
        ScoutModel scout = new ScoutModel();
        scout.setDob(date);
        float getAge = scout.calculateAge();
        assertEquals(getAge,1.5f,0.5f);

    }

    @Test
    public void testYearsAndOneCalculation(){

        LocalDate date = LocalDate.of(2016, Month.APRIL,6);
        ScoutModel scout = new ScoutModel();
        scout.setDob(date);
        float getAge = scout.calculateAge();
        assertEquals(getAge,2.5f,0.5f);

    }
}
