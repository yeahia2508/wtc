package com.dhakadigital.tdd.wtc.model;

/**
 * Created by y34h1a on 2/4/17.
 */

public class OrgInfo {
    private int id;
    private String user_id;
    private String name;
    private String address;

    public OrgInfo() {
    }

    public OrgInfo(String user_id, String name, String address) {
        this.user_id = user_id;
        this.name = name;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return name;
    }
}
