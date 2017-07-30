package com.example.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by phanz on 2017/7/23.
 */

public class CommentSet implements Serializable{
    @SerializedName("top_comments")
    private List<Comment> topComments;
    @SerializedName("recent_comments")
    private List<Comment> recentComments;
    @SerializedName("stick_comments")
    private List<Comment> stickComments;

    public List<Comment> getTopComments() {
        return topComments;
    }

    public void setTopComments(List<Comment> topComments) {
        this.topComments = topComments;
    }

    public List<Comment> getRecentComments() {
        return recentComments;
    }

    public void setRecentComments(List<Comment> recentComments) {
        this.recentComments = recentComments;
    }

    public List<Comment> getStickComments() {
        return stickComments;
    }

    public void setStickComments(List<Comment> stickComments) {
        this.stickComments = stickComments;
    }
}
