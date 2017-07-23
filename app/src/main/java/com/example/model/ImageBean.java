package com.example.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by phanz on 2017/7/9.
 */

public class ImageBean implements Serializable{
    @SerializedName("url")
    private String url;
    @SerializedName("url_list")
    private List<UrlBean> urlList;
    @SerializedName("uri")
    private String uri;
    @SerializedName("height")
    private int height;
    @SerializedName("width")
    private int width;
    @SerializedName("r_width")
    private int rWidth;
    @SerializedName("r_height")
    private int rHeight;
    @SerializedName("is_gif")
    private boolean isGif;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

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

    public int getrWidth() {
        return rWidth;
    }

    public void setrWidth(int rWidth) {
        this.rWidth = rWidth;
    }

    public int getrHeight() {
        return rHeight;
    }

    public void setrHeight(int rHeight) {
        this.rHeight = rHeight;
    }

    public boolean isGif() {
        return isGif;
    }

    public void setGif(boolean gif) {
        isGif = gif;
    }
}
