package com.unam.alex.pumaride.models;

import java.util.ArrayList;

/**
 * Created by alex on 9/11/16.
 */
public class Route2 {
    private int id;
    private float[] p_origen;
    private float[] p_destino;
    private ArrayList<float[]> shortest_path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float[] getP_origen() {
        return p_origen;
    }

    public void setP_origen(float[] p_origen) {
        this.p_origen = p_origen;
    }

    public float[] getP_destino() {
        return p_destino;
    }

    public void setP_destino(float[] p_destino) {
        this.p_destino = p_destino;
    }

    public ArrayList<float[]> getShortest_path() {
        return shortest_path;
    }

    public void setShortest_path(ArrayList<float[]> shortest_path) {
        this.shortest_path = shortest_path;
    }
}
