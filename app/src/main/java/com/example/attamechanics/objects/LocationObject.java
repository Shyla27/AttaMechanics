package com.example.attamechanics.objects;

import com.google.android.gms.maps.model.LatLng;

public class LocationObject {
    private LatLng coordinates;
    private String name = "";


    public LocationObject(LatLng coordinates, String name){
        this.coordinates = coordinates;
        this.name = name;
    }

    public LocationObject(){
    }

    public Object getCoordinates() {
        return coordinates;
    }


    public void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
