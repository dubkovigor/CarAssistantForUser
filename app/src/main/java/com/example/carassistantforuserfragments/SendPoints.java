package com.example.carassistantforuserfragments;

public class SendPoints {
    private static String origin;
    private static String destination;
    private static String via;

    public static String getVia() {
        return via;
    }

    public static void setVia(String via) {
        SendPoints.via = via;
    }

    public static String getOrigin() {
        return origin;
    }

    public static void setOrigin(String origin) {
        SendPoints.origin = origin;
    }

    public static String getDestination() {
        return destination;
    }

    public static void setDestination(String destination) {
        SendPoints.destination = destination;
    }
}
