package com.example.attamechanics.Adapters;

import android.os.Parcel;
import android.os.Parcelable;

public class AppointmentsRV implements Parcelable {
    private String appointmentName;
    private String timeDate;
    private String carImage;
    private String appointmentLink;
    private String appointmentId;

    public AppointmentsRV(String courseID, String courseName, String courseImg, String datetime) {

    }

    protected AppointmentsRV(Parcel in) {
        appointmentName = in.readString();
        timeDate = in.readString();
        carImage = in.readString();
        appointmentLink = in.readString();
        appointmentId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(appointmentName);
        dest.writeString(timeDate);
        dest.writeString(carImage);
        dest.writeString(appointmentLink);
        dest.writeString(appointmentId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppointmentsRV> CREATOR = new Creator<AppointmentsRV>() {
        @Override
        public AppointmentsRV createFromParcel(Parcel in) {
            return new AppointmentsRV(in);
        }

        @Override
        public AppointmentsRV[] newArray(int size) {
            return new AppointmentsRV[size];
        }
    };

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }


    public String getAppointmentName() {
        return appointmentName;
    }

    public void setAppointmentName(String appointmentName) {
        this.appointmentName = appointmentName;
    }

    public String getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(String timeDate) {
        this.timeDate = timeDate;
    }

    public String getCarImage() {
        return carImage;
    }

    public void setCarImage(String carImage) {
        this.carImage = carImage;
    }

    public String getAppointmentLink() {
        return appointmentLink;
    }

    public void setAppointmentLink(String appointmentLink) {
        this.appointmentLink = appointmentLink;
    }
}
