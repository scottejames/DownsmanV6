package com.scottejames.downsman.services;

import com.scottejames.downsman.model.LogModel;
import com.scottejames.downsman.model.SessionState;
import com.scottejames.downsman.model.UserModel;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LogService {

    public static void logEvent(String what){
        UserModel user = SessionState.getInstance().getCurrentUser();
        String who;
        if (user == null){
            who = "not logged in";
        } else {
            who = user.getUsername();
        }
        String when = LocalDateTime.now().toString();

        LogModel log = new LogModel();
        log.setWhat(what);
        log.setWhen(when);
        log.setWho(who);

        DatabaseService.getInstance().getMapper().save(log);
    }
}
