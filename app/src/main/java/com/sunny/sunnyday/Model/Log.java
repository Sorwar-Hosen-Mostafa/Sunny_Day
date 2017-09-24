package com.sunny.sunnyday.Model;

import com.sunny.sunnyday.R;

import java.util.ArrayList;

/**
 * Created by Jibunnisa on 8/31/2017.
 */

public class Log {
    private String  log_title;
    private String  log_suggesstion;
    private String  log_date;
    private String  log_day;
    private String log_textsymble;

    public Log() {
    }

    public Log(String  log_title, String log_suggesstion, String log_date, String log_day, String log_textsymble) {
        this.log_title = log_title;
        this.log_suggesstion = log_suggesstion;
        this.log_date = log_date;
        this.log_day = log_day;
        this.log_textsymble = log_textsymble;
    }

    public String getLog_title() {
        return log_title;
    }

    public String getLog_suggesstion() {
        return log_suggesstion;
    }

    public String getLog_date() {
        return log_date;
    }

    public String getLog_day() {
        return log_day;
    }

    public void setLog_title(String log_title) {
        this.log_title = log_title;
    }

    public void setLog_suggesstion(String log_suggesstion) {
        this.log_suggesstion = log_suggesstion;
    }

    public void setLog_date(String log_date) {
        this.log_date = log_date;
    }

    public void setLog_day(String log_day) {
        this.log_day = log_day;
    }

    public String getLog_textsymble() {
        return log_textsymble;
    }

    public void setLog_textsymble(String log_textsymble) {
        this.log_textsymble = log_textsymble;
    }

    public ArrayList<Log> getlogdata(){

        ArrayList<Log> logs = new ArrayList<>();

        String log_titles[]={"Period Starts","2nd Day","3rd Day","4rd Day"};
        String log_suggessions[]={"Make reminder of emergency.","Drink log of water not to be dehydrated.","Take bed rest. do not do any heavy work.","Eat healthy and keep yourself strong."};
        String log_dates[]={"7th july","8th july","9th july","10th july"};
        String log_days[]={"Sat","Sun","Mon","Wed"};
        String log_textsymbles[]={"PS","2D","3D","4D"};



        for(int i=0;i<log_titles.length;i++){
            Log log = new Log(log_titles[i],log_suggessions[i],log_dates[i],log_days[i],log_textsymbles[i]);
            logs.add(log);
        }


        return logs;
    }
}
