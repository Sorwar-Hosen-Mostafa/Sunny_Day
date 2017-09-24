package com.sunny.sunnyday.Model;

/**
 * Created by Jibunnisa on 9/1/2017.
 */

public class UserData {



    private String user_device_id;

    public String getUser_device_id() {
        return user_device_id;
    }

    public void setUser_device_id(String user_device_id) {
        this.user_device_id = user_device_id;
    }

    public UserData() {

    }

    public UserData(String user_device_id, boolean user_wantToPragenet, String user_lastPeriodDate, String user_avaragePeriodDuration, String user_avaragePeriodCycleLength, String user_birthYear) {

        this.user_device_id = user_device_id;
        this.user_wantToPragenet = user_wantToPragenet;
        this.user_lastPeriodDate = user_lastPeriodDate;
        this.user_avaragePeriodDuration = user_avaragePeriodDuration;
        this.user_avaragePeriodCycleLength = user_avaragePeriodCycleLength;
        this.user_birthYear = user_birthYear;
    }

    private boolean user_wantToPragenet;
    private String user_lastPeriodDate;
    private String user_avaragePeriodDuration;
    private String user_avaragePeriodCycleLength;
    private String user_birthYear;


    public boolean isUser_wantToPragenet() {
        return user_wantToPragenet;
    }
    public void setUser_wantToPragenet(boolean user_wantToPragenet) {
        this.user_wantToPragenet = user_wantToPragenet;
    }
    public String getUser_lastPeriodDate() {
        return user_lastPeriodDate;
    }
    public void setUser_lastPeriodDate(String user_lastPeriodDate) {
        this.user_lastPeriodDate = user_lastPeriodDate;
    }
    public String getUser_avaragePeriodDuration() {
        return user_avaragePeriodDuration;
    }
    public void setUser_avaragePeriodDuration(String user_avaragePeriodDuration) {
        this.user_avaragePeriodDuration = user_avaragePeriodDuration;
    }
    public String getUser_avaragePeriodCycleLength() {
        return user_avaragePeriodCycleLength;
    }
    public void setUser_avaragePeriodCycleLength(String user_avaragePeriodCycleLength) {
        this.user_avaragePeriodCycleLength = user_avaragePeriodCycleLength;
    }
    public String getUser_birthYear() {
        return user_birthYear;
    }
    public void setUser_birthYear(String user_birthYear) {
        this.user_birthYear = user_birthYear;
    }
}
