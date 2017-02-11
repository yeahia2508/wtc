package com.dhakadigital.tdd.wtc.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by mishu on 2/9/2017.
 */

public class TimeMoneyCalculator {
    private Date startDate, endDate;//date variable could get null pointer exception if so then do startDate = null;
    private long endTime, startTime, hour, minute;
    Calendar cc = Calendar.getInstance();
    int wagePerHour;
    private String hourMinute, workedHourMinute, totalWage;








    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getHour() {
        return hour;
    }

    public void setHour(long hour) {
        this.hour = hour;
    }

    public long getMinute() {
        return minute;
    }

    public void setMinute(long minute) {
        this.minute = minute;
    }

    public int getWagePerHour() {
        return wagePerHour;
    }

    public void setWagePerHour(int wagePerHour) {
        this.wagePerHour = wagePerHour;
    }

    public String getHourMinute() {
        return hourMinute;
    }

    public void setHourMinute(String hourMinute) {
        this.hourMinute = hourMinute;
    }

    public String getWorkedHourMinute() {
        return workedHourMinute;
    }

    public void setWorkedHourMinute(String workedHourMinute) {
        this.workedHourMinute = workedHourMinute;
    }

    public String getTotalWage() {
        return totalWage;
    }

    public void setTotalWage(String totalWage) {
        this.totalWage = totalWage;
    }
}
