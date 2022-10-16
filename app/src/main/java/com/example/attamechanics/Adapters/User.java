package com.example.attamechanics.Adapters;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;

import java.io.Serializable;

public class User implements Serializable {

    @PropertyName("Username")
    public String name;

    public String uid;
//   public String name;
    @SuppressWarnings("WeakerAccess")
   public String email;
    @Exclude
            public boolean isAuthenticated;
    @Exclude
            public boolean isNew, isCreated;
    String image;
    String userContactNumber;
    String userPassword;
    boolean isNotificationEnable;



    public  User() {

    }
    public User(String name,   String uid, String email) {
        this.name= name;
        this.email= email;
        this.uid = uid;
    }

    public User (String name,
               String email, String userContactNumber, String userPassword) {
        this.name = name;
        this.email = email;
        this.userContactNumber = userContactNumber;
        this.userPassword = userPassword;
    }

    public User(String email, String username, String url, boolean b) {

        this.email = email;
        this.name = username;
        this.image = url;
        this.isNotificationEnable = b;

    }


    public String getname() {
        return name;
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

    public void setname(String username) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserContactNumber(String userContactNumber) {
        this.userContactNumber = userContactNumber;
    }
}
