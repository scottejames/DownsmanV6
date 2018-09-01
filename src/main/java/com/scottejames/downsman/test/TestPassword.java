package com.scottejames.downsman.test;

import com.scottejames.downsman.utils.HashHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestPassword {

    @Test
    public void testHashIsSame(){
        String s = "Test";

        String hashOne = HashHelper.hashPassword(s);
        String hashTwo = HashHelper.hashPassword(s);

        assertTrue(hashOne.equals(hashTwo));

    }

    @Test
    public void testHashIsSameDiffString(){
        String sOne = "Test";
        String sTwo = "Test";


        String hashOne = HashHelper.hashPassword(sOne);
        String hashTwo = HashHelper.hashPassword(sTwo);

        assertTrue(hashOne.equals(hashTwo));

    }

    @Test void testHashDiff(){
        String hashOne = HashHelper.hashPassword("One");
        String hashTwo = HashHelper.hashPassword("Two");
        assertTrue(hashOne.equals(hashTwo) == false);
    }

    @Test
    public void showPasswordHash(){
        String hash = HashHelper.hashPassword("password");
        System.out.println("hash " + hash);
    }
}
