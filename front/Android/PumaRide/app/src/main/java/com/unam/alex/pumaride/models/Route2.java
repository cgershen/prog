package com.unam.alex.pumaride.models;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by alex on 5/11/16.
 */
public class Route2  {
    private int id;
    private ArrayList<float[]> shortest_path;
    private float[] start;
    private float[] end;
    private int mode;
    private String image;

    public int getId() {
        return id;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
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

    public ArrayList<float[]> getShortest_path() {
        return shortest_path;
    }

    public void setShortest_path(ArrayList<float[]> shortest_path) {
        this.shortest_path = shortest_path;
    }

    public float[] getStart() {
        return start;
    }

    public void setStart(float[] start) {
        this.start = start;
    }

    public float[] getEnd() {
        return end;
    }

    public void setEnd(float[] end) {
        this.end = end;
    }
}
