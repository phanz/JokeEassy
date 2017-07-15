package com.example.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by phanz on 2017/7/15.
 */

public class DanmakuAttrs {
    @SerializedName("allow_show_danmaku")
    private int allowShowDanmaku;
    @SerializedName("all_send_danmaku")
    private int allSendDanmaku;

    public int getAllowShowDanmaku() {
        return allowShowDanmaku;
    }

    public void setAllowShowDanmaku(int allowShowDanmaku) {
        this.allowShowDanmaku = allowShowDanmaku;
    }

    public int getAllSendDanmaku() {
        return allSendDanmaku;
    }

    public void setAllSendDanmaku(int allSendDanmaku) {
        this.allSendDanmaku = allSendDanmaku;
    }
}
