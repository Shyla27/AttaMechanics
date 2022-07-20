package com.example.attamechanics.Adapters;

import android.os.Parcel;
import android.os.Parcelable;

public  class GaragesAdapter implements Parcelable {

    private String garagename;
    private String speciality;
    private String officenumber;
    private String latitude;
    private String longitude;
    private String garageId;
    private String idnumber;


    public GaragesAdapter(String garagename, String speciality, String officenumber, String latitude, String longitude, String garageId) {
        this.garagename = garagename;
        this.speciality = speciality;
        this.officenumber = officenumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.garageId = garageId;
        this.idnumber = idnumber;
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
        speciality = in.readString();
        officenumber = in.readString();
        latitude = in.readString();
        idnumber = in.readString();
        longitude = in.readString();
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

    public String getSpeciality() {
        return speciality;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(garagename);
        parcel.writeString(garageId);
        parcel.writeString(speciality);
        parcel.writeString(officenumber);
        parcel.writeString(latitude);
        parcel.writeString(idnumber);
        parcel.writeString(longitude);
    }
}
