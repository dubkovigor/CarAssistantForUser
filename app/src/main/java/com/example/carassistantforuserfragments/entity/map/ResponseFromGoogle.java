package com.example.carassistantforuserfragments.entity.map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseFromGoogle {

    @SerializedName("destination_addresses")
    @Expose
    private ArrayList<String> destination_addresses;

    @SerializedName("origin_addresses")
    @Expose
    private ArrayList<String> origin_addresses;

    @SerializedName("rows")
    @Expose
    private ArrayList<Rows> rows;

    @SerializedName("status")
    @Expose
    private String status;

    public ArrayList<Rows> getRows() {
        return rows;
    }

    public void setRows(ArrayList<Rows> rows) {
        this.rows = rows;
    }

    public ArrayList<String> getDestination_addresses() {
        return destination_addresses;
    }

    public void setDestination_addresses(ArrayList<String> destination_addresses) {
        this.destination_addresses = destination_addresses;
    }

    public ArrayList<String> getOrigin_addresses() {
        return origin_addresses;
    }

    public void setOrigin_addresses(ArrayList<String> origin_addresses) {
        this.origin_addresses = origin_addresses;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ResponseFromGoogle{" +
                "destination_addresses=" + destination_addresses +
                ", origin_addresses=" + origin_addresses +
                ", rows=" + rows +
                ", status='" + status + '\'' +
                '}';
    }
}
