package com.scottejames.downsman.utils;

import java.util.HashMap;

public class Config {
    private static Config _instance = null;
    private HashMap<String, String> config = new HashMap<String, String>();
    private Config(){
        config.put("test","test");
        config.put("dev","true");
    }
    static public Config getInstance(){
        if(_instance == null){
            _instance = new Config();
        }
        return _instance;
    }

    public String getConfigString(String test) {

        return config.get(test);
    }

    public boolean isDev(){
        return config.get("dev").equals("true");
    }
}
