package com.example.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by phanz on 2017/7/6.
 */

public class Comment implements Serializable{
    @SerializedName("text")
    private String text;
    @SerializedName("create_time")
    private long createTime;
    @SerializedName("user_verified")
    private boolean userVerified;
    @SerializedName("user_bury")
    private int user_bury;
    @SerializedName("user_id")
    private long userId;
    @SerializedName("bury_count")
    private int buryCount;
    @SerializedName("share_url")
    private String shareUrl;
    @SerializedName("id")
    private long id;
    @SerializedName("platform")
    private String platform;
    @SerializedName("is_digg")
    private int isDigg;
    @SerializedName("user_name")
    private String userName;
    @SerializedName("user_profile_image_url")
    private String userProfileImageUrl;
    @SerializedName("status")
    private int status;
    @SerializedName("description")
    private  String description;
    @SerializedName("comment_id")
    private long commentId;
    @SerializedName("user_digg")
    private int userDigg;
    @SerializedName("user_profile_url")
    private String userProfileUrl;
    @SerializedName("share_type")
    private int shareType;
    @SerializedName("digg_count")
    private int diggCount;
    @SerializedName("is_pro_user")
    private boolean isProUser;
    @SerializedName("platform_id")
    private String platformId;
    @SerializedName("avatar_url")
    private String avatarUrl;
    @SerializedName("group_id")
    private long groupId;
    @SerializedName("second_level_comments_count")
    private int secondLevelCommentsCount;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public boolean isUserVerified() {
        return userVerified;
    }

    public void setUserVerified(boolean userVerified) {
        this.userVerified = userVerified;
    }

    public int getUser_bury() {
        return user_bury;
    }

    public void setUser_bury(int user_bury) {
        this.user_bury = user_bury;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getBuryCount() {
        return buryCount;
    }

    public void setBuryCount(int buryCount) {
        this.buryCount = buryCount;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public int getIsDigg() {
        return isDigg;
    }

    public void setIsDigg(int isDigg) {
        this.isDigg = isDigg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserProfileImageUrl() {
        return userProfileImageUrl;
    }

    public void setUserProfileImageUrl(String userProfileImageUrl) {
        this.userProfileImageUrl = userProfileImageUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public int getUserDigg() {
        return userDigg;
    }

    public void setUserDigg(int userDigg) {
        this.userDigg = userDigg;
    }

    public String getUserProfileUrl() {
        return userProfileUrl;
    }

    public void setUserProfileUrl(String userProfileUrl) {
        this.userProfileUrl = userProfileUrl;
    }

    public int getShareType() {
        return shareType;
    }

    public void setShareType(int shareType) {
        this.shareType = shareType;
    }

    public int getDiggCount() {
        return diggCount;
    }

    public void setDiggCount(int diggCount) {
        this.diggCount = diggCount;
    }

    public boolean isProUser() {
        return isProUser;
    }

    public void setProUser(boolean proUser) {
        isProUser = proUser;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public int getSecondLevelCommentsCount() {
        return secondLevelCommentsCount;
    }

    public void setSecondLevelCommentsCount(int secondLevelCommentsCount) {
        this.secondLevelCommentsCount = secondLevelCommentsCount;
    }
}
