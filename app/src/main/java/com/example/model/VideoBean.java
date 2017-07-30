package com.example.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by phanz on 2017/7/9.
 */

public class VideoBean implements Serializable{
    @SerializedName("url_list")
    private List<UrlBean> urlList;
    @SerializedName("uri")
    private String uri;
    @SerializedName("height")
    private int height;
    @SerializedName("width")
    private int width;

    public List<UrlBean> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<UrlBean> urlList) {
        this.urlList = urlList;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
