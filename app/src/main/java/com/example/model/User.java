package com.example.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by phanz on 2017/7/5.
 */

public class User {
    @SerializedName("user_id")
    private long userId;
    @SerializedName("name")
    private String name;
    @SerializedName("followings")
    private int followings;
    @SerializedName("user_verified")
    private boolean userVerified;
    @SerializedName("ugc_count")
    private int ugcCount;
    @SerializedName("avatar_url")
    private String avatarUrl;
    @SerializedName("followers")
    private int followers;
    @SerializedName("is_following")
    private boolean isFollowing;
    @SerializedName("is_pro_user")
    private boolean isProUser;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFollowings() {
        return followings;
    }

    public void setFollowings(int followings) {
        this.followings = followings;
    }

    public boolean isUserVerified() {
        return userVerified;
    }

    public void setUserVerified(boolean userVerified) {
        this.userVerified = userVerified;
    }

    public int getUgcCount() {
        return ugcCount;
    }

    public void setUgcCount(int ugcCount) {
        this.ugcCount = ugcCount;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public boolean isFollowing() {
        return isFollowing;
    }

    public void setFollowing(boolean following) {
        isFollowing = following;
    }

    public boolean isProUser() {
        return isProUser;
    }

    public void setProUser(boolean proUser) {
        isProUser = proUser;
    }
}
