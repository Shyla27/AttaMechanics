package com.example.attamechanics.Adapters;

import android.widget.EditText;

public  class GaragesAdapter {

    private String garagename;
    private String speciality;
    private String officenumber;
    private String Location;
    public  GaragesAdapter(String garagename, String speciality, String officenumber) {

    }

    public GaragesAdapter(EditText garagename, EditText speciality, EditText officenumber) {
    }

    public String getGaragename() {
        return garagename;
    }

    public String getOfficenumber() {
        return officenumber;
    }

    public String getSpeciality() {
        return speciality;
    }


    public void setGaragename(String garagename) {
        this.garagename = garagename;
    }

    public void setOfficenumber(String officenumber) {
        this.officenumber = officenumber;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

}
