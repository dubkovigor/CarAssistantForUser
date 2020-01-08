package com.example.carassistantforuserfragments.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Car {

    @SerializedName("carId")
    @Expose
    private String carId;

    @SerializedName("name")
    @Expose
    private String carName;

    @SerializedName("doorsState")
    @Expose
    private String doorsState;

    @SerializedName("plateNumber")
    @Expose
    private String plateNumber;

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getDoorsState() {
        return doorsState;
    }

    public void setDoorsState(String doorsState) {
        this.doorsState = doorsState;
    }
}
