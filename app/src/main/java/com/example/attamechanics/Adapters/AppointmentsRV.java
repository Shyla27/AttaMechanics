package com.example.attamechanics.Adapters;

import android.os.Parcel;
import android.os.Parcelable;

public class AppointmentsRV implements Parcelable {
    private String timeDate;
    private String numberplates;
    private String carModel;
    private  String carProblem;
    private String appointmentId;

    public AppointmentsRV(String appointmentId, String timeDate, String numberplates, String carModel) {

    }

    protected AppointmentsRV(Parcel in) {
        numberplates = in.readString();
        timeDate = in.readString();
        carModel = in.readString();
        carProblem = in.readString();
        appointmentId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(carModel);
        dest.writeString(timeDate);
        dest.writeString(carProblem);
        dest.writeString(numberplates);
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
        return numberplates;
    }

    public void setAppointmentName(String appointmentName) {
        this.numberplates = appointmentName;
    }

    public String getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(String timeDate) {
        this.timeDate = timeDate;
    }

    public String getCarImage() {
        return carModel;
    }

    public void setCarImage(String carImage) {
        this.carModel = carImage;
    }

    public String getAppointmentLink() {
        return carProblem;
    }

    public void setAppointmentLink(String appointmentLink) {
        this.carProblem = appointmentLink;
    }
}
