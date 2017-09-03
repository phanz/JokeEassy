package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.GlideApp;
import com.example.jokeeassy.CommentActivity;
import com.example.jokeeassy.R;
import com.example.model.Comment;
import com.example.model.Group;
import com.example.model.ImageBean;
import com.example.model.Record;
import com.example.utils.DateUtils;
import com.example.utils.Utils;
import com.example.widgets.SurfaceVideoView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phanz on 2017/7/8.
 */

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.RecordHolder> {
    private static final String TAG = "ContentAdapter";

    private List<Record> mRecordList;
    private Context mContext;
    private LayoutInflater mInflater;
    private int mMaxWidth;

    public ContentAdapter(Context context){
        mContext = context;
        mRecordList = new ArrayList<>();
        mInflater = LayoutInflater.from(mContext);
    }

    public void setMaxWidth(int width){
        mMaxWidth = width;
    }

    public void addRecords(boolean headInsert,List<Record> recordList){
        for(Record record : recordList){
            if(record.getGroup() != null){
                if(headInsert){
                    mRecordList.add(0,record);
                }else{
                    mRecordList.add(record);
                }
            }else{
                Log.d(TAG,"监测到空Group");
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public RecordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_content,parent,false);
        RecordHolder holder = new RecordHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecordHolder holder, int position) {
        Record record = mRecordList.get(position);
        final Group group = record.getGroup();
        if(group == null){
            Log.e(TAG,"Group为空：" + record.toString());
        }else{
            bindCommonInfo(holder, group,record.getComments());
            bindLargeImg(holder, group);
            bindThumbImgList(holder, group);
            bindVideoInfo(holder, group);
        }
    }

    private void bindCommonInfo(RecordHolder holder,final Group group,List<Comment> commentList) {
        boolean isHot = group.getStatusDesc().equals("热门投稿");
        holder.hotLabelImage.setVisibility(isHot ? View.VISIBLE : View.GONE);
        holder.avatarImage.setImageResource(R.drawable.default_round_head);
        holder.userNameText.setText(group.getUser().getName());
        holder.contentText.setText(group.getContent());
        holder.categoryText.setText(group.getCategoryName());
        holder.diggCountText.setText(group.getDiggCount() + "");
        holder.buryCountText.setText(group.getBuryCount() + "");
        holder.commentCountText.setText(group.getCommentCount() + "");

        Glide.with(mContext).load(group.getUser().getAvatarUrl()).into(holder.avatarImage);

        if(group.getHasComments() > 0){
            holder.hotCommentLabel.setVisibility(View.VISIBLE);
            holder.hotCommentList.setVisibility(View.VISIBLE);
            ContentHotCommentAdapter commentAdapter = new ContentHotCommentAdapter(mContext);
            commentAdapter.setCommentList(commentList);
            holder.hotCommentList.setAdapter(commentAdapter);
        }else{
            holder.hotCommentLabel.setVisibility(View.GONE);
            holder.hotCommentList.setVisibility(View.GONE);
        }

        holder.commentCountText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent commentIntent = new Intent(mContext, CommentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("group_id",group.getGroupId() + "");
                bundle.putSerializable("group",group);
                commentIntent.putExtras(bundle);
                mContext.startActivity(commentIntent);
            }
        });
    }

    private void bindLargeImg(final RecordHolder holder, Group group) {
        //单张大图片
        if(group.getLargeImage() != null){
            ImageBean imageBean = group.getLargeImage();
            final String url = imageBean.getUrlList().get(0).getUrl();
            holder.largeImage.setVisibility(View.VISIBLE);
            holder.largeImage.setScaleType(ImageView.ScaleType.CENTER);

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)holder.largeImage.getLayoutParams();
            int width = mMaxWidth - params.getMarginStart() - params.getMarginEnd();
            int height = (int)(imageBean.getHeight() * (float)width / (float)imageBean.getWidth());
            params.width = width;
            params.height = height;
            holder.largeImage.setLayoutParams(params);

            Log.d(TAG,"加载大图：" + url);
            GlideApp.with(mContext)
                    .load(url)
                    .placeholder(R.drawable.large_loading)
                    .override(width,height)
                    .centerCrop()
                    .into(holder.largeImage);


        }else{
            holder.largeImage.setVisibility(View.GONE);
        }
    }

    private void bindThumbImgList(RecordHolder holder, Group group) {
        //多张缩略图
        if(group.getThumbImageList() != null){
            holder.thumbParentLayout.setVisibility(View.VISIBLE);
            List<ImageBean> thumbImageList = group.getThumbImageList();
            assert  thumbImageList.size() <= 9;
            Log.d(TAG,"加载缩略图，数量：" + thumbImageList.size());
            for(int j = 0; j < thumbImageList.size(); j++){
                ImageBean imageBean = thumbImageList.get(j);
                Log.d(TAG,"缩略图地址：" + imageBean.getUrl());
                holder.thumbImageList[j].setVisibility(View.VISIBLE);
                GlideApp.with(mContext)
                        .load(imageBean.getUrl())
                        .override(imageBean.getWidth(),imageBean.getHeight())
                        .placeholder(R.drawable.large_loading)
                        .into(holder.thumbImageList[j]);
            }
            for(int j = thumbImageList.size(); j < holder.thumbImageList.length; j++){
                holder.thumbImageList[j].setVisibility(View.GONE);
            }
        }else{
            holder.thumbParentLayout.setVisibility(View.GONE);
        }
    }

    private void bindVideoInfo(final RecordHolder holder, Group group) {
        if(group.getIsVideo() == 1){
            SurfaceVideoView playerView = holder.playerView;
            playerView.setVisibility(View.VISIBLE);

            String captureImageUrl = group.getLargeCover().getUrlList().get(0).getUrl();
            playerView.setVideoUri(group.getMp4Url());
            Log.d(TAG,String.format("加载视频,封面地址：%s\n视频地址：%s",captureImageUrl,group.getMp4Url()));

            SpannableString spanStr = Utils.makeColorSpan(
                    new String[]{group.getPlayCount()+"","次播放"},
                    new int[]{Color.argb(0xff,0xff,0x84,0),Color.WHITE});
            playerView.setLeftBottomText(spanStr);
            playerView.setRightBottomText(DateUtils.secondToDuration((int)group.getDuration()));

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)playerView.getLayoutParams();
            int width = mMaxWidth - params.getMarginStart() - params.getMarginEnd();
            int height = (int)(group.getVideoHeight() * (float)width / group.getVideoWidth());

            params.width = width;
            params.height = height;
            playerView.setLayoutParams(params);

            params = (RelativeLayout.LayoutParams)playerView.getCaptureImage().getLayoutParams();
            params.width = width;
            params.height = height;
            playerView.getCaptureImage().setScaleType(ImageView.ScaleType.CENTER);
            playerView.getCaptureImage().setLayoutParams(params);
            GlideApp.with(mContext)
                    .load(captureImageUrl)
                    .placeholder(R.drawable.large_loading)
                    .override(width,height)
                    .centerCrop()
                    .into(playerView.getCaptureImage());

        } else {
            holder.playerView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mRecordList.size();
    }

    public static class RecordHolder extends RecyclerView.ViewHolder{
        public ImageView hotLabelImage;
        public ImageView avatarImage;
        public TextView userNameText;
        public TextView contentText;
        public TextView categoryText;

        public ImageView largeImage;
        public GridLayout thumbParentLayout;
        public ImageView[] thumbImageList;

        public SurfaceVideoView playerView;

        public TextView hotCommentLabel;
        public ListView hotCommentList;

        public TextView diggCountText;
        public TextView buryCountText;
        public TextView commentCountText;

        public RecordHolder(View itemView) {
            super(itemView);
            hotLabelImage = (ImageView) itemView.findViewById(R.id.hot_label_image);
            avatarImage = (ImageView) itemView.findViewById(R.id.user_avatar_image);
            userNameText = (TextView) itemView.findViewById(R.id.user_name_text);
            contentText = (TextView) itemView.findViewById(R.id.content_text);
            categoryText = (TextView) itemView.findViewById(R.id.category_text);
            largeImage = (ImageView) itemView.findViewById(R.id.large_image);
            thumbParentLayout = (GridLayout) itemView.findViewById(R.id.thumb_image_list);
            thumbImageList = new ImageView[thumbParentLayout.getChildCount()];
            for(int i = 0; i < thumbParentLayout.getChildCount(); i++){
                thumbImageList[i] = (ImageView) thumbParentLayout.getChildAt(i);
            }

            playerView = (SurfaceVideoView) itemView.findViewById(R.id.player_view);

            hotCommentLabel = (TextView) itemView.findViewById(R.id.hot_comment_label);
            hotCommentList = (ListView) itemView.findViewById(R.id.hot_comment_list);

            diggCountText = (TextView) itemView.findViewById(R.id.digg_count_text);
            buryCountText = (TextView) itemView.findViewById(R.id.bury_count_text);
            commentCountText = (TextView) itemView.findViewById(R.id.comment_count_text);
        }
    }
}
