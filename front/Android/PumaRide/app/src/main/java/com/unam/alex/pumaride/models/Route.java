package com.unam.alex.pumaride.models;

import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by alex on 5/11/16.
 */
public class Route extends RealmObject{
    private int id;
    private RealmList<MyLatLng> shortest_path = new RealmList<MyLatLng>();
    private String start;
    private String end;
    private int mode;
    private String image;
    private Match match;
    public static final int WALK = 0;
    public static final int CAR = 1;
    public static final int BIKE  =2;

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

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

        ArrayList<String> points = new ArrayList<String>();
        for(MyLatLng latlng:shortest_path){
            points.add(latlng.getLatitude()+","+latlng.getLongitude());
        }
        String joined = TextUtils.join("|",points);
        String image = "https://maps.googleapis.com/maps/api/staticmap?size=800x800&path=color:0x0000ff|weight:8|"+joined+"&key=AIzaSyDdxYk4MlX8JIUuJBgD26rwBUkA8tuUno4";
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public RealmList<MyLatLng> getShortest_path() {
        return shortest_path;
    }

    public void setShortest_path(RealmList<MyLatLng> shortest_path) {
        this.shortest_path = shortest_path;
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
