package com.example.attamechanics.Adapters;

import android.os.Parcel;
import android.os.Parcelable;

public  class GaragesAdapter implements Parcelable {

    private String garagename;
    private String officenumber;
    private String garageId;
    private String idnumber;
    private  String description;


    public GaragesAdapter(String garagename,  String officenumber, String garageId) {
        this.garagename = garagename;
        this.officenumber = officenumber;
        this.garageId = garageId;
        this.description = description;

    }


    public GaragesAdapter(String garageID, String idnumber) {
        this.idnumber = idnumber;
    }

    public GaragesAdapter (String description){
        this.description= description;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String garageId() {
        return garageId;
    }

    public void setGarageId(String garageId) {
        this.garageId = garageId;
    }

public  GaragesAdapter(String id, String s, String specialit, String garageID, String garagenam) {

}

    protected GaragesAdapter(Parcel in) {
        garagename = in.readString();
        officenumber = in.readString();
        idnumber = in.readString();
    }

    public static final Creator<GaragesAdapter> CREATOR = new Creator<GaragesAdapter>() {
        @Override
        public GaragesAdapter createFromParcel(Parcel in) {
            return new GaragesAdapter(in);
        }

        @Override
        public GaragesAdapter[] newArray(int size) {
            return new GaragesAdapter[size];
        }
    };

    public String getGaragename() {
        return garagename;
    }

    public String getOfficenumber() {
        return officenumber;
    }


    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }


    public void setGaragename(String garagename) {
        this.garagename = garagename;
    }

    public void setOfficenumber(String officenumber) {
        this.officenumber = officenumber;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(garagename);
        parcel.writeString(garageId);
        parcel.writeString(officenumber);
        parcel.writeString(idnumber);
    }
}
