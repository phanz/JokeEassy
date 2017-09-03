package com.example.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by phanz on 2017/7/5.
 */

public class Group implements Serializable{
    @SerializedName("text")
    private String text;
    @SerializedName("neihan_hot_start_time")
    private String neiHanHotStartTime;
    @SerializedName("dislike_reason")
    private List<DisLikeReason> disLikeReasonList;
    @SerializedName("create_time")
    private long createTime;
    @SerializedName("id")
    private long id;
    @SerializedName("favorite_count")
    private int favoriteCount;
    @SerializedName("go_detail_count")
    private int goDetailCount;
    @SerializedName("user_favorite")
    private int userFavorite;
    @SerializedName("share_type")
    private int shareType;
    @SerializedName("user")
    private User user;
    @SerializedName("is_can_share")
    private int isCanShare;
    @SerializedName("category_type")
    private int categoryType;
    @SerializedName("share_url")
    private String shareUrl;
    @SerializedName("label")
    private int label;
    @SerializedName("content")
    private String content;
    @SerializedName("comment_count")
    private int commentCount;
    @SerializedName("id_str")
    private String idStr;
    @SerializedName("media_type")
    private int mediaType;
    @SerializedName("share_count")
    private int shareCount;
    @SerializedName("type")
    private int type;
    @SerializedName("status")
    private int status;
    @SerializedName("has_comments")
    private int hasComments;
    @SerializedName("user_bury")
    private int userBury;
    @SerializedName("activity")
    private Map<String,String> activity;
    @SerializedName("status_desc")
    private String statusDesc;
    @SerializedName("quick_comment")
    private boolean quickComment;
    @SerializedName("display_type")
    private int displayType;
    @SerializedName("neihan_hot_end_time")
    private String neiHotEndTime;
    @SerializedName("user_digg")
    private int userDigg;
    @SerializedName("online_time")
    private long onlineTime;
    @SerializedName("category_name")
    private String categoryName;
    @SerializedName("category_visible")
    private boolean categoryVisible;
    @SerializedName("bury_count")
    private int buryCount;
    @SerializedName("is_anonymous")
    private boolean isAnonymous;
    @SerializedName("repin_count")
    private int repinCount;
    @SerializedName("is_neihan_hot")
    private boolean isNeiHanHot;
    @SerializedName("digg_count")
    private int diggCount;
    @SerializedName("has_hot_comments")
    private int hasHotComments;
    @SerializedName("allow_dislike")
    private boolean allowDislike;
    @SerializedName("user_repin")
    private int userRepin;
    @SerializedName("neihan_hot_link")
    private Map<String,String> neiHanHotLink;
    @SerializedName("group_id")
    private long groupId;
    @SerializedName("category_id")
    private int categoryId;


    @SerializedName("max_screen_width_percent")
    private double maxScreenWidthPercent;
    @SerializedName("min_screen_width_percent")
    private double minScreenHeightPercent;
    @SerializedName("large_image")
    private ImageBean largeImage;
    @SerializedName("large_image_list")
    private List<ImageBean> largeImageList;
    @SerializedName("thumb_image_list")
    private List<ImageBean> thumbImageList;

    @SerializedName("image_status")
    private int imageStatus;
    @SerializedName("middle_image")
    private ImageBean middleImage;


    /**=================以下为ViewGroup独有字段===================*/
    @SerializedName("video_id")
    private String videoId;
    @SerializedName("360p_video")
    private VideoBean video360p;
    @SerializedName("mp4_url")
    private String mp4Url;
    @SerializedName("720p_video")
    private VideoBean video720p;
    @SerializedName("duration")
    private double duration;
    @SerializedName("480p_video")
    private VideoBean video480p;
    @SerializedName("keywords")
    private String keywords;
    @SerializedName("danmanku_attrs")
    private DanmakuAttrs danmakuAttrs;
    @SerializedName("m3u8_url")
    private String m3u8Url;
    @SerializedName("large_cover")
    private ImageBean largeCover;
    @SerializedName("title")
    private String title;
    @SerializedName("video_height")
    private int videoHeight;
    @SerializedName("cover_image_uri")
    private String coverImageUri;
    @SerializedName("publish_time")
    private String publishTime;
    @SerializedName("play_count")
    private int playCount;
    @SerializedName("medium_cover")
    private ImageBean mediumCover;
    @SerializedName("video_width")
    private int videoWidth;
    @SerializedName("flash_url")
    private String flashUrl;
    @SerializedName("uri")
    private String uri;
    @SerializedName("is_public_url")
    private int isPublicUrl;
    @SerializedName("origin_video")
    private VideoBean originVideo;
    @SerializedName("cover_image_url")
    private String coverImageUrl;
    @SerializedName("is_video")
    private int isVideo;

    /**=================以下为段友秀页新增字段===================*/
    @SerializedName("video_wh_ratio")
    private double videoWhRatio;
    @SerializedName("download_url")
    private String downloadUrl;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNeiHanHotStartTime() {
        return neiHanHotStartTime;
    }

    public void setNeiHanHotStartTime(String neiHanHotStartTime) {
        this.neiHanHotStartTime = neiHanHotStartTime;
    }

    public List<DisLikeReason> getDisLikeReasonList() {
        return disLikeReasonList;
    }

    public void setDisLikeReasonList(List<DisLikeReason> disLikeReasonList) {
        this.disLikeReasonList = disLikeReasonList;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public int getGoDetailCount() {
        return goDetailCount;
    }

    public void setGoDetailCount(int goDetailCount) {
        this.goDetailCount = goDetailCount;
    }

    public int getUserFavorite() {
        return userFavorite;
    }

    public void setUserFavorite(int userFavorite) {
        this.userFavorite = userFavorite;
    }

    public int getShareType() {
        return shareType;
    }

    public void setShareType(int shareType) {
        this.shareType = shareType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getIsCanShare() {
        return isCanShare;
    }

    public void setIsCanShare(int isCanShare) {
        this.isCanShare = isCanShare;
    }

    public int getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(int categoryType) {
        this.categoryType = categoryType;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public int getMediaType() {
        return mediaType;
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getHasComments() {
        return hasComments;
    }

    public void setHasComments(int hasComments) {
        this.hasComments = hasComments;
    }

    public int getUserBury() {
        return userBury;
    }

    public void setUserBury(int userBury) {
        this.userBury = userBury;
    }

    public Map<String, String> getActivity() {
        return activity;
    }

    public void setActivity(Map<String, String> activity) {
        this.activity = activity;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public boolean isQuickComment() {
        return quickComment;
    }

    public void setQuickComment(boolean quickComment) {
        this.quickComment = quickComment;
    }

    public int getDisplayType() {
        return displayType;
    }

    public void setDisplayType(int displayType) {
        this.displayType = displayType;
    }

    public String getNeiHotEndTime() {
        return neiHotEndTime;
    }

    public void setNeiHotEndTime(String neiHotEndTime) {
        this.neiHotEndTime = neiHotEndTime;
    }

    public int getUserDigg() {
        return userDigg;
    }

    public void setUserDigg(int userDigg) {
        this.userDigg = userDigg;
    }

    public long getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(long onlineTime) {
        this.onlineTime = onlineTime;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isCategoryVisible() {
        return categoryVisible;
    }

    public void setCategoryVisible(boolean categoryVisible) {
        this.categoryVisible = categoryVisible;
    }

    public int getBuryCount() {
        return buryCount;
    }

    public void setBuryCount(int buryCount) {
        this.buryCount = buryCount;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }

    public int getRepinCount() {
        return repinCount;
    }

    public void setRepinCount(int repinCount) {
        this.repinCount = repinCount;
    }

    public boolean isNeiHanHot() {
        return isNeiHanHot;
    }

    public void setNeiHanHot(boolean neiHanHot) {
        isNeiHanHot = neiHanHot;
    }

    public int getDiggCount() {
        return diggCount;
    }

    public void setDiggCount(int diggCount) {
        this.diggCount = diggCount;
    }

    public int getHasHotComments() {
        return hasHotComments;
    }

    public void setHasHotComments(int hasHotComments) {
        this.hasHotComments = hasHotComments;
    }

    public boolean isAllowDislike() {
        return allowDislike;
    }

    public void setAllowDislike(boolean allowDislike) {
        this.allowDislike = allowDislike;
    }

    public int getUserRepin() {
        return userRepin;
    }

    public void setUserRepin(int userRepin) {
        this.userRepin = userRepin;
    }

    public Map<String, String> getNeiHanHotLink() {
        return neiHanHotLink;
    }

    public void setNeiHanHotLink(Map<String, String> neiHanHotLink) {
        this.neiHanHotLink = neiHanHotLink;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public double getMaxScreenWidthPercent() {
        return maxScreenWidthPercent;
    }

    public void setMaxScreenWidthPercent(double maxScreenWidthPercent) {
        this.maxScreenWidthPercent = maxScreenWidthPercent;
    }

    public double getMinScreenHeightPercent() {
        return minScreenHeightPercent;
    }

    public void setMinScreenHeightPercent(double minScreenHeightPercent) {
        this.minScreenHeightPercent = minScreenHeightPercent;
    }

    public ImageBean getLargeImage() {
        return largeImage;
    }

    public void setLargeImage(ImageBean largeImage) {
        this.largeImage = largeImage;
    }

    public List<ImageBean> getLargeImageList() {
        return largeImageList;
    }

    public void setLargeImageList(List<ImageBean> largeImageList) {
        this.largeImageList = largeImageList;
    }

    public List<ImageBean> getThumbImageList() {
        return thumbImageList;
    }

    public void setThumbImageList(List<ImageBean> thumbImageList) {
        this.thumbImageList = thumbImageList;
    }

    public int getImageStatus() {
        return imageStatus;
    }

    public void setImageStatus(int imageStatus) {
        this.imageStatus = imageStatus;
    }

    public ImageBean getMiddleImage() {
        return middleImage;
    }

    public void setMiddleImage(ImageBean middleImage) {
        this.middleImage = middleImage;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public VideoBean getVideo360p() {
        return video360p;
    }

    public void setVideo360p(VideoBean video360p) {
        this.video360p = video360p;
    }

    public String getMp4Url() {
        return mp4Url;
    }

    public void setMp4Url(String mp4Url) {
        this.mp4Url = mp4Url;
    }

    public VideoBean getVideo720p() {
        return video720p;
    }

    public void setVideo720p(VideoBean video720p) {
        this.video720p = video720p;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public VideoBean getVideo480p() {
        return video480p;
    }

    public void setVideo480p(VideoBean video480p) {
        this.video480p = video480p;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public DanmakuAttrs getDanmakuAttrs() {
        return danmakuAttrs;
    }

    public void setDanmakuAttrs(DanmakuAttrs danmakuAttrs) {
        this.danmakuAttrs = danmakuAttrs;
    }

    public String getM3u8Url() {
        return m3u8Url;
    }

    public void setM3u8Url(String m3u8Url) {
        this.m3u8Url = m3u8Url;
    }

    public ImageBean getLargeCover() {
        return largeCover;
    }

    public void setLargeCover(ImageBean largeCover) {
        this.largeCover = largeCover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(int videoHeight) {
        this.videoHeight = videoHeight;
    }

    public String getCoverImageUri() {
        return coverImageUri;
    }

    public void setCoverImageUri(String coverImageUri) {
        this.coverImageUri = coverImageUri;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public ImageBean getMediumCover() {
        return mediumCover;
    }

    public void setMediumCover(ImageBean mediumCover) {
        this.mediumCover = mediumCover;
    }

    public int getVideoWidth() {
        return videoWidth;
    }

    public void setVideoWidth(int videoWidth) {
        this.videoWidth = videoWidth;
    }

    public String getFlashUrl() {
        return flashUrl;
    }

    public void setFlashUrl(String flashUrl) {
        this.flashUrl = flashUrl;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getIsPublicUrl() {
        return isPublicUrl;
    }

    public void setIsPublicUrl(int isPublicUrl) {
        this.isPublicUrl = isPublicUrl;
    }

    public VideoBean getOriginVideo() {
        return originVideo;
    }

    public void setOriginVideo(VideoBean originVideo) {
        this.originVideo = originVideo;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public int getIsVideo() {
        return isVideo;
    }

    public void setIsVideo(int isVideo) {
        this.isVideo = isVideo;
    }

    public double getVideoWhRatio() {
        return videoWhRatio;
    }

    public void setVideoWhRatio(double videoWhRatio) {
        this.videoWhRatio = videoWhRatio;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
