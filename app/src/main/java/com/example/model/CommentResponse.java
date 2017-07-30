package com.example.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by phanz on 2017/7/23.
 */

public class CommentResponse implements Serializable{
    @SerializedName("total_number")
    private String totalNumber;
    @SerializedName("new_comment")
    private String newComment;
    @SerializedName("group_id")
    private String groupId;
    @SerializedName("has_more")
    private boolean hasMore;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private CommentSet commentSet;

    public String getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(String totalNumber) {
        this.totalNumber = totalNumber;
    }

    public String getNewComment() {
        return newComment;
    }

    public void setNewComment(String newComment) {
        this.newComment = newComment;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CommentSet getCommentSet() {
        return commentSet;
    }

    public void setCommentSet(CommentSet commentSet) {
        this.commentSet = commentSet;
    }
}
