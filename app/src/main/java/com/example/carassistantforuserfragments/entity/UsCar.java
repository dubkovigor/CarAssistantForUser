package com.example.carassistantforuserfragments.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collection;

public class UsCar {
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("cars")
    @Expose
    private Collection<Car> cars;


    public Collection<Car> getCars() {
        return cars;
    }

    public void setCars(Collection<Car> cars) {
        this.cars = cars;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
