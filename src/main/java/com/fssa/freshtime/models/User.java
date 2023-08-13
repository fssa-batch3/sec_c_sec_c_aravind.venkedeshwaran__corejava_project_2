package com.fssa.freshtime.models;

public class User {
    private String emailId;
    private String userName;
    private String password;
    private String phoneNumber;


    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNumber;
    }

    public void setPhoneNo(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }



}
