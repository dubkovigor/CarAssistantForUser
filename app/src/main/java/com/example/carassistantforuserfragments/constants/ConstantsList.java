package com.example.carassistantforuserfragments.constants;

import java.util.HashMap;
import java.util.Map;

public class ConstantsList {

    public static final String GOOGLE_KEY = "AIzaSyBock4oM86IK4lDca6crhgTgNL8AXlljBI";
    public static final String MODE = "car";
    public static final String LANGUAGE = "ru-RU";
    public static final String SENSOR = "true";

    public static final Map<String, String> STATES = new HashMap<String, String>() {{
        put("locked", "Закрыто");
        put("unlocked", "Открыто");

    }};
}
