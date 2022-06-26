package com.example.attamechanics.Adapters;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.EditText;

public  class GaragesAdapter implements Parcelable {

    private String garagename;
    private String speciality;
    private String officenumber;
    private String location;
    private String garageId;

    public String garageId() {
        return garageId;
    }

    public void setGarageId(String garageId) {
        this.garageId = garageId;
    }

public  GaragesAdapter() {

}

    protected GaragesAdapter(Parcel in) {
        garagename = in.readString();
        speciality = in.readString();
        officenumber = in.readString();
        location = in.readString();
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        location = location;
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

    public GaragesAdapter(String garagename, String garageId, String speciality, String officenumber, String location) {
        this.garagename = garagename;
        this.speciality = speciality;
        this.officenumber = officenumber;
        this.location = location;
        this.garageId = garageId;

    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(garagename);
        parcel.writeString(garageId);
        parcel.writeString(speciality);
        parcel.writeString(officenumber);
        parcel.writeString(location);
    }
}
