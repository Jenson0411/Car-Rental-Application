package com.example.byblosmobileapplication;

public class EmployeeAccount extends Account{
    private int EmployeeID;
    public EmployeeAccount(String username, String email, String password, String role){
        super(username,email,password,role);
    }
}
