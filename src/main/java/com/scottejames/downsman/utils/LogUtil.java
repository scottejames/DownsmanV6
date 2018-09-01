package com.scottejames.downsman.utils;

import com.scottejames.downsman.services.ScoutService;

import java.util.logging.Logger;

public class LogUtil {
    private static final Logger log = Logger.getLogger(LogUtil.class.getName());
    static Logger logService = Logger.getLogger(ScoutService.class.getName());
    public static void logEvent(String msg){
        log.info(msg);
    }
    public static void logDebug(String msg){

        log.fine(msg);
    }
    public static void logServiceEvent(String msg){
        log.info(msg);
    }



}
