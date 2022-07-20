package com.example.attamechanics.Adapters;

import android.widget.EditText;

public class CarsRVModal {

    private EditText carMake;
    private EditText carModel;
    private EditText caryear;
    private EditText carappliedmodel;
    private EditText NumberPlates;

    public CarsRVModal (EditText carMake, EditText carModel, EditText caryear, EditText carappliedmodel, EditText numberPlates)  {

    }

    public EditText getCarMake() {
        return carMake;
    }

    public void setCarMake(EditText carMake) {
        this.carMake = carMake;
    }

    public EditText getCarModel() {
        return carModel;
    }

    public void setCarModel(EditText carModel) {
        this.carModel = carModel;
    }

    public EditText getCaryear() {
        return caryear;
    }

    public void setCaryear(EditText caryear) {
        this.caryear = caryear;
    }

    public EditText getCarappliedmodel() {
        return carappliedmodel;
    }

    public void setCarappliedmodel(EditText carappliedmodel) {
        this.carappliedmodel = carappliedmodel;
    }

    public EditText getNumberPlates() {
        return NumberPlates;
    }

    public void setNumberPlates(EditText numberPlates) {
        NumberPlates = numberPlates;
    }
}
