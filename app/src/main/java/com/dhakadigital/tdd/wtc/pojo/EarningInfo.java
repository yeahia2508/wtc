package com.dhakadigital.tdd.wtc.pojo;

/**
 * Created by mishu on 2/4/2017.
 */

public class EarningInfo {
    public int id;
    public String sheet_uid;
    public String date_in_millis;
    public String start_time_millis;
    public String duration;
    public String wages;

    public EarningInfo() {
    }

    public EarningInfo(String sheet_uid, String date_in_millis, String start_time_millis, String duration, String wages) {
        this.sheet_uid = sheet_uid;
        this.date_in_millis = date_in_millis;
        this.start_time_millis = start_time_millis;
        this.duration = duration;
        this.wages = wages;
    }

    public String getSheet_uid() {
        return sheet_uid;
    }

    public void setSheet_uid(String sheet_uid) {
        this.sheet_uid = sheet_uid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate_in_millis() {
        return date_in_millis;
    }

    public void setDate_in_millis(String date_in_millis) {
        this.date_in_millis = date_in_millis;
    }

    public String getStart_time_millis() {
        return start_time_millis;
    }

    public void setStart_time_millis(String start_time_millis) {
        this.start_time_millis = start_time_millis;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getWages() {
        return wages;
    }

    public void setWages(String wages) {
        this.wages = wages;
    }
}
