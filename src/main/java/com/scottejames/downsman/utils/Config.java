package com.scottejames.downsman.utils;

import java.util.HashMap;

public class Config {
    private static Config _instance = null;
    private HashMap<String, String> config = new HashMap<String, String>();

    private Config() {
        config.put("test", "test");

        String dev = System.getenv("DM_DEV");
        String lock = System.getenv("DM_LOCK");

        if (dev == null) {
            dev = "true"; // if variable is not set then assume we are in dev
        }

        String bankDetails = System.getenv("DM_BANKDETS");
        if (bankDetails == null)
            bankDetails="<EMPTY STRING>";

        config.put("dev", dev);
        config.put("bank_details",bankDetails);
        if (lock == null)
            config.put("lock","false");
        else
            config.put("lock","true");


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
    public boolean isLocked(){
        return config.get("lock").equals("true");
    }
}
