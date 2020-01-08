package com.example.carassistantforuserfragments.entity.map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Elementss {

    @SerializedName("distance")
    @Expose
    private Disctance disctance;

    @SerializedName("duration")
    @Expose
    private Duration durations;

    public Disctance getDisctance() {
        return disctance;
    }

    public void setDisctance(Disctance disctance) {
        this.disctance = disctance;
    }

    public Duration getDurations() {
        return durations;
    }

    public void setDurations(Duration durations) {
        this.durations = durations;
    }

    public Value getValueByName(String nameOfFiled) {
        if ("Duration".equalsIgnoreCase(nameOfFiled)) {
            return getDurations();
        }
        return getDisctance();
    }

    @Override
    public String toString() {
        return "Elementss{" +
                "disctance=" + disctance +
                ", durations=" + durations +
                '}';
    }
}
