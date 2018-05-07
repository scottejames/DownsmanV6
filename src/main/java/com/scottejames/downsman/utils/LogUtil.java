package com.scottejames.downsman.utils;

import com.scottejames.downsman.services.Service;

import java.util.logging.Logger;

public class LogUtil {
    static Logger log = Logger.getLogger(LogUtil.class.getName());
    static Logger logService = Logger.getLogger(Service.class.getName());
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
