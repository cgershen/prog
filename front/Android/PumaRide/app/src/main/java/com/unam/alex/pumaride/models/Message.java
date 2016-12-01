package com.unam.alex.pumaride.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by alex on 25/10/16.
 */
public class Message extends RealmObject {
    @PrimaryKey
    private int id;
    private long datetime;
    private int type_;
    private int user_id;
    private String message;
    private int user_id2;
    private boolean readed;

    public boolean isReaded() {
        return readed;
    }

    public void setReaded(boolean readed) {
        this.readed = readed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public int getType_() {
        return type_;
    }

    public void setType_(int type) {
        this.type_ = type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUser_id2() {
        return user_id2;
    }

    public void setUser_id2(int user_id2) {
        this.user_id2 = user_id2;
    }
}
