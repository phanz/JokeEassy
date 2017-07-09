package com.example.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by phanz on 2017/7/6.
 */

public class JsonResponse {
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private RefreshContent data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RefreshContent getData() {
        return data;
    }

    public void setData(RefreshContent data) {
        this.data = data;
    }
}
