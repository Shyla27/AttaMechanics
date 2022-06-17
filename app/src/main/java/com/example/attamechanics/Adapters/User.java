package com.example.attamechanics.Adapters;

public class User {

    String username;
    String email;
    String userContactNumber;
    String userPassword;
    public  User() {

    }
    public User(String username, String email) {
        this.username= username;
        this.email= email;
    }

    public User (String username, String email, String userContactNumber, String userPassword) {
        this.username = username;
        this.email = email;
        this.userContactNumber = userContactNumber;
        this.userPassword = userPassword;
    }
    public String getUsername() {
        return username;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getUserContactNumber() {
        return userContactNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserContactNumber(String userContactNumber) {
        this.userContactNumber = userContactNumber;
    }
}
