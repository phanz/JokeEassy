package com.example.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by phanz on 2017/7/6.
 */

public class ContentResponse implements Serializable{
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private ContentSet data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ContentSet getData() {
        return data;
    }

    public void setData(ContentSet data) {
        this.data = data;
    }
}
