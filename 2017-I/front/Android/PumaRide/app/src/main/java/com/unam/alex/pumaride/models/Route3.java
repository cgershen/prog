package com.unam.alex.pumaride.models;

/**
 * Created by alex on 9/11/16.
 */
public class Route3 {
    private int id;
    private String p_origen;
    private String p_destino;
    private String shortest_path;

    public String getShortest_path() {
        return shortest_path;
    }

    public void setShortest_path(String shortest_path) {
        this.shortest_path = shortest_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
