package com.unam.alex.pumaride.models;

import java.util.ArrayList;

/**
 * Created by alex on 6/12/16.
 */
public class ReverseGeoCodeResult {
    private ArrayList<ReverseGeoCodeAddress> results;
    private String status;

    public ArrayList<ReverseGeoCodeAddress> getResult() {
        return results;
    }

    public void setResult(ArrayList<ReverseGeoCodeAddress> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
