package com.example.carassistantforuserfragments.service;

import com.example.carassistantforuserfragments.entity.Car;
import com.example.carassistantforuserfragments.entity.UsCar;
import com.example.carassistantforuserfragments.entity.User;
import com.example.carassistantforuserfragments.entity.map.ResponseFromGoogle;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JSONPlaceHolderApi {

    String googleKey = "AIzaSyBock4oM86IK4lDca6crhgTgNL8AXlljBI";

    @POST("/api/users/login")
    public Call<UsCar> getUserByNumber(@Body User user);

    @POST("/api/users/register")
    public Call<User> registerUser(@Body User user);

    @GET("/api/cars/users/{userId}")
    public Call<List<Car>> findCarsByUserId(@Path("userId") String userId);

    @POST("/api/cars/{carId}/state")
    public Call<Car> doorState(@Path("carId") String carId, @Body Car car);

    @GET("/api/cars/{carId}/state")
    public Call<Car> checkStatusCar(@Path("carId") String carId);

    @GET("/maps/api/distancematrix/json")
    public Call<ResponseFromGoogle> getAllRows(@Query("origins") String origins,
                                               @Query("destinations") String destinations,
                                               @Query("mode") String mode,
                                               @Query("language") String language,
                                               @Query("key") String key,
                                               @Query("sensor") String sensor);
}