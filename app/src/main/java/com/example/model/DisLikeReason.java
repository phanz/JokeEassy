package com.example.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by phanz on 2017/7/5.
 */

public class DisLikeReason {

    @SerializedName("type")
    private int type;
    @SerializedName("id")
    private long id;
    @SerializedName("title")
    private String title;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
