package com.sunny.sunnyday.Model;

/**
 * Created by Jibunnisa on 11/14/2017.
 */

public class History {
    private String  HistoryId;
    private int HistoryMonth;
    private int HistoryYear;
    private String  HistoryStartDate;
    private String  HistoryEndDate;

    public History(){

    }

    public String getHistoryId() {
        return HistoryId;
    }

    public void setHistoryId(String historyId) {
        HistoryId = historyId;
    }

    public int getHistoryMonth() {
        return HistoryMonth;
    }

    public void setHistoryMonth(int historyMonth) {
        HistoryMonth = historyMonth;
    }

    public int getHistoryYear() {
        return HistoryYear;
    }

    public void setHistoryYear(int historyYear) {
        HistoryYear = historyYear;
    }

    public String  getHistoryStartDate() {
        return HistoryStartDate;
    }

    public void setHistoryStartDate(String  historyStartDate) {
        HistoryStartDate = historyStartDate;
    }

    public String  getHistoryEndDate() {
        return HistoryEndDate;
    }

    public void setHistoryEndDate(String  historyEndDate) {
        HistoryEndDate = historyEndDate;
    }
}
