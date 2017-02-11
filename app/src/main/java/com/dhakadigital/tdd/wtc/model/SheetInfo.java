package com.dhakadigital.tdd.wtc.model;

/**
 * Created by y34h1a on 2/5/17.
 */

public class SheetInfo {
    private int id;
    private String org_uid;
    private String user_uid;
    private String name;
    private String org_name;
    private String org_address;
    private Double hourRate;


    //----------------------------CONSTRUCTORS-----------------------------------


    public SheetInfo() {
    }

    public SheetInfo(String org_uid, String user_uid, String name, String org_name, String org_address, Double hourRate) {
        this.org_uid = org_uid;
        this.user_uid = user_uid;
        this.name = name;
        this.org_name = org_name;
        this.org_address = org_address;
        this.hourRate = hourRate;
    }

    //----------------------------GETTERS & SETTERS-----------------------------------


    public String getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(String user_uid) {
        this.user_uid = user_uid;
    }

    public Double getHourRate() {
        return hourRate;
    }

    public void setHourRate(Double hourRate) {
        this.hourRate = hourRate;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public String getOrg_address() {
        return org_address;
    }

    public void setOrg_address(String org_address) {
        this.org_address = org_address;
    }

    public String getOrg_uid() {
        return org_uid;
    }

    public void setOrg_uid(String org_uid) {
        this.org_uid = org_uid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
