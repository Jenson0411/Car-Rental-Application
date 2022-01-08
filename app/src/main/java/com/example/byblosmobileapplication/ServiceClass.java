package com.example.byblosmobileapplication;

import java.util.ArrayList;

public class ServiceClass {
    private String serviceName;
    private double hourlyRate;
    private ArrayList<String> description;

    public ServiceClass() {
    }
    public ServiceClass(String serviceName, double hourlyRate,ArrayList<String> description) {
        this.serviceName = serviceName;
        this.hourlyRate = hourlyRate;
        this.description=description;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public ArrayList<String> getDescription(){
        return description;
    }
}
