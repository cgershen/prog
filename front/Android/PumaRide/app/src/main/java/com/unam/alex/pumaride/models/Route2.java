package com.unam.alex.pumaride.models;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by alex on 5/11/16.
 */
public class Route2 {
    private int id;
    private ArrayList<float[]> shortest_path;
    private float[] origin_point;
    private float[] destination_point;
    private String tipo_transporte;
    private String image;

    public int getId() {
        return id;
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

    public String getTipo_transporte() {
        return tipo_transporte;
    }

    public void setTipo_transporte(String tipo_transporte) {
        this.tipo_transporte = tipo_transporte;
    }
}
