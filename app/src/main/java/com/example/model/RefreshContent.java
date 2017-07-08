package com.example.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by phanz on 2017/7/6.
 */

public class RefreshContent {
    @SerializedName("has_more")
    private boolean hasMore;

    @SerializedName("tip")
    private String tip;

    @SerializedName("has_new_message")
    private boolean hasNewMessage;

    @SerializedName("max_time")
    private double maxTime;

    @SerializedName("min_time")
    private double minTime;

    @SerializedName("data")
    private List<Record> data;

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public boolean isHasNewMessage() {
        return hasNewMessage;
    }

    public void setHasNewMessage(boolean hasNewMessage) {
        this.hasNewMessage = hasNewMessage;
    }

    public double getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(double maxTime) {
        this.maxTime = maxTime;
    }

    public double getMinTime() {
        return minTime;
    }

    public void setMinTime(double minTime) {
        this.minTime = minTime;
    }

    public List<Record> getData() {
        return data;
    }

    public void setData(List<Record> data) {
        this.data = data;
    }
}
