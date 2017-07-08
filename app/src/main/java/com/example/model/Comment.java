package com.example.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by phanz on 2017/7/6.
 */

public class Comment {
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
}
