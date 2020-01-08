package com.example.carassistantforuserfragments.entity.map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Duration extends Value {

    @SerializedName("text")
    @Expose
    private String text;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
