package com.scottejames.downsman.test;

import com.scottejames.downsman.utils.Config;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestConfig {

    @Test
    public void testGetConfiguration(){
        assertTrue(Config.getInstance().getConfigString("test").equals("test"));
    }
}
