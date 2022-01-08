package com.example.byblosmobileapplication;

public class Rating {

    String rating;
    String feedback;
    String customerUserName;
    String employeeUserName;

    public Rating(String rating, String feedback, String customerUserName, String employeeUserName) {
        this.rating = rating;
        this.feedback = feedback;
        this.customerUserName = customerUserName;
        this.employeeUserName = employeeUserName;
    }

    public String getCustomerUserName() {
        return customerUserName;
    }

    public void setCustomerUserName(String customerUserName) {
        this.customerUserName = customerUserName;
    }

    public String getEmployeeUserName() {
        return employeeUserName;
    }

    public void setEmployeeUserName(String employeeUserName) {
        this.employeeUserName = employeeUserName;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return "Branch User Name: "+getEmployeeUserName()+"\n"
                +"Customer User Name: " +getCustomerUserName()+"\n"
                +"Rating: " +getRating()+"\n"
                +"Feedback: " + getFeedback();
    }
}
