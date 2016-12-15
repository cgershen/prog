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
    private String guardar;
    private String p_origen;
    private String p_destino;
    private String user_id;

    public String getGuardar() {
        return guardar;
    }

    public void setGuardar(String guardar) {
        this.guardar = guardar;
    }

    public String getP_origen() {
        return p_origen;
    }

    public void setP_origen(String p_origen) {
        this.p_origen = p_origen;
    }

    public String getP_destino() {
        return p_destino;
    }

    public void setP_destino(String p_destino) {
        this.p_destino = p_destino;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
