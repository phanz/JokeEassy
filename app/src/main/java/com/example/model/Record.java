package com.example.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by phanz on 2017/7/6.
 */

public class Record implements Serializable{

    @SerializedName("group")
    private Group group;
    @SerializedName("comments")
    private List<Comment> comments;
    @SerializedName("type")
    private double type;
    @SerializedName("display_time")
    private String displayTime;
    @SerializedName("online_time")
    private String onlineTime;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public double getType() {
        return type;
    }

    public void setType(double type) {
        this.type = type;
    }

    public String getDisplayTime() {
        return displayTime;
    }

    public void setDisplayTime(String displayTime) {
        this.displayTime = displayTime;
    }

    public String getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(String onlineTime) {
        this.onlineTime = onlineTime;
    }
}
