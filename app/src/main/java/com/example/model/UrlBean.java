package com.example.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by phanz on 2017/7/9.
 */

public class UrlBean implements Serializable{
    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
