package com.example.byblosmobileapplication;

import java.util.ArrayList;

public class Branch {
    private String employee;
    private String phoneNumber;
    private String address;
    private String startTimeWeekday;
    private String startTimeWeekend;
    private String endTimeWeekday;
    private String endTimeWeekend;
//    private int rate;
    //private Arraylist<ServiceList> serviceClass;

    public Branch(){

    }
    public Branch(String employee, String phoneNumber, String address) {
        this.employee = employee;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.startTimeWeekday = "9:00";
        this.startTimeWeekend = "11:00";
        this.endTimeWeekday = "17:00";
        this.endTimeWeekend = "16:00";
//        this.rate=0;
    }

    public String getStartTimeWeekday() {
        return startTimeWeekday;
    }

    public String getStartTimeWeekend() {
        return startTimeWeekend;
    }

    public String getEndTimeWeekday() {
        return endTimeWeekday;
    }

    public String getEndTimeWeekend() {
        return endTimeWeekend;
    }

    public String getEmployee() {
        return employee;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

//    public int getRate(){return rate;}

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setStartTimeWeekday(String startTimeWeekday) {
        this.startTimeWeekday = startTimeWeekday;
    }

    public void setStartTimeWeekend(String startTimeWeekend) {
        this.startTimeWeekend = startTimeWeekend;
    }

    public void setEndTimeWeekday(String endTimeWeekday) {
        this.endTimeWeekday = endTimeWeekday;
    }

    public void setEndTimeWeekend(String endTimeWeekend) {
        this.endTimeWeekend = endTimeWeekend;
    }

//    public void setRate(int rate){this.rate=rate;}

}
