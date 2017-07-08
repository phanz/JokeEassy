package com.example.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by phanz on 2017/7/6.
 */

public class Record {

    @SerializedName("group")
    private Group group;
    @SerializedName("comments")
    private List<Comment> comments;
    @SerializedName("type")
    private double type;
    @SerializedName("display_time")
    private long displayTime;
    @SerializedName("online_time")
    private long onlineTime;
}
