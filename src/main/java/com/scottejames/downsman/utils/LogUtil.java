package com.scottejames.downsman.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogUtil {
    static Logger log;
    static {
        log =  LoggerFactory.getLogger(LogUtil.class.getName());

                /*Logger.getLogger(LogUtil.class.getName());
        BasicConfigurator.configure(); */
        logEvent("Logging initialised");
    }
    public static void logEvent(String msg){

        log.info("[SJ] " +msg);
    }
    public static void logDebug(String msg){

        log.debug("[SJ] " +msg);
    }



}
