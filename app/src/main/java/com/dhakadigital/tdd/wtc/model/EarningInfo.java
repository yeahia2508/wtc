package com.dhakadigital.tdd.wtc.model;

/**
 * Created by mishu on 2/4/2017.
 */

public class EarningInfo {
    private int id;
    private String sheet_uid;
    private String user_uid;
    private String entry_date;
    private String start_time;
    private double duration;
    private double wages;

    public EarningInfo() {
    }

    public EarningInfo(String sheet_uid, String user_uid, String entry_date, String start_time, double duration, double wages) {
        this.sheet_uid = sheet_uid;
        this.user_uid = user_uid;
        this.entry_date = entry_date;
        this.start_time = start_time;
        this.duration = duration;
        this.wages = wages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSheet_uid() {
        return sheet_uid;
    }

    public void setSheet_uid(String sheet_uid) {
        this.sheet_uid = sheet_uid;
    }

    public String getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(String user_uid) {
        this.user_uid = user_uid;
    }

    public String getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(String entry_date) {
        this.entry_date = entry_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getWages() {
        return wages;
    }

    public void setWages(double wages) {
        this.wages = wages;
    }
}
