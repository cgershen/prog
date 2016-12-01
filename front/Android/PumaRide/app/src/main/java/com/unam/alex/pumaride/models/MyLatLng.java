package com.unam.alex.pumaride.models;

import io.realm.RealmObject;

/**
 * Created by alex on 30/11/16.
 */
public class MyLatLng extends RealmObject {

    private double latitude;
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
