package com.scottejames.downsman.utils;


import org.apache.log4j.Logger;

public class LogUtil {
    static Logger log = Logger.getLogger(LogUtil.class.getName());

    public static void logEvent(String msg){

        log.info("[SJ] " +msg);
    }
    public static void logDebug(String msg){

        log.debug("[SJ] " +msg);
    }



}
