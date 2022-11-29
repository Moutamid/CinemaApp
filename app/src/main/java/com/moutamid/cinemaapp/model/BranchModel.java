package com.moutamid.cinemaapp.model;

public class BranchModel {
    int BrachNum;
    String NumOfHalls, Location;

    public BranchModel() {
    }

    public BranchModel(int brachNum, String numOfHalls, String location) {
        BrachNum = brachNum;
        NumOfHalls = numOfHalls;
        Location = location;
    }

    public int getBrachNum() {
        return BrachNum;
    }

    public void setBrachNum(int brachNum) {
        BrachNum = brachNum;
    }

    public String getNumOfHalls() {
        return NumOfHalls;
    }

    public void setNumOfHalls(String numOfHalls) {
        NumOfHalls = numOfHalls;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}
