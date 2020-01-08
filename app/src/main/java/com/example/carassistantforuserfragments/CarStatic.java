package com.example.carassistantforuserfragments;


import com.example.carassistantforuserfragments.entity.Car;

public class CarStatic {
    private static Car car;

    public static Car getCar() {
        return car;
    }

    public static void setCar(Car car) {
        CarStatic.car = car;
    }
}
