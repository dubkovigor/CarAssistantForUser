package com.example.carassistantforuserfragments.entity.map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Rows {

    @SerializedName("elements")
    @Expose
    private ArrayList<Elementss> elements;

    public ArrayList<Elementss> getElements() {
        return elements;
    }

    public void setElements(ArrayList<Elementss> elements) {
        this.elements = elements;
    }

    @Override
    public String toString() {
        return "Rows{" +
                "elements=" + elements +
                '}';
    }
}
