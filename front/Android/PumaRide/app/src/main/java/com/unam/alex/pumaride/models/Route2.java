package com.unam.alex.pumaride.models;

import java.util.ArrayList;

/**
 * Created by alex on 9/11/16.
 */
public class Route2 {
    private int id;
    private float[] origin_point;
    private float[] destination_point;
    private ArrayList<float[]> shortest_path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float[] getOrigin_point() {
        return origin_point;
    }

    public void setOrigin_point(float[] origin_point) {
        this.origin_point = origin_point;
    }

    public float[] getDestination_point() {
        return destination_point;
    }

    public void setDestination_point(float[] destination_point) {
        this.destination_point = destination_point;
    }

    public ArrayList<float[]> getShortest_path() {
        return shortest_path;
    }

    public void setShortest_path(ArrayList<float[]> shortest_path) {
        this.shortest_path = shortest_path;
    }
}
