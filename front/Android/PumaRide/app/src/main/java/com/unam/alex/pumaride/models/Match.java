package com.unam.alex.pumaride.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by alex on 25/10/16.
 */
public class Match extends RealmObject {
    @PrimaryKey
    private int id;
    private String first_name;
    private String last_name;
    private String image;
    public int getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
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

}
