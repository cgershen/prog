package com.unam.alex.pumaride.models;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by alex on 5/11/16.
 */
public class Route {
    private int id;
    ArrayList<LatLng> points;
    private String start;
    private String end;
    private int type_;
    private String image;
    public static final int WALK = 0;
    public static final int CAR = 1;
    public static final int BIKE  =2;

    public int getId() {
        return id;
    }

    public int getType_() {
        return type_;
    }

    public void setType_(int type_) {
        this.type_ = type_;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<LatLng> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<LatLng> points) {
        this.points = points;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
