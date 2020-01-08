package com.example.carassistantforuserfragments;


import com.example.carassistantforuserfragments.entity.User;

public class SendUserFromAuthOrRegistrToMain {

    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        SendUserFromAuthOrRegistrToMain.user = user;
    }
}
