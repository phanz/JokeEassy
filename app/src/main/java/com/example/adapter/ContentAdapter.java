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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
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
    private int mItemWidth;

    private Point mScreenSize;

    public ContentAdapter(Context context){
        mContext = context;
        mRecordList = new ArrayList<>();
        mInflater = LayoutInflater.from(mContext);
        mScreenSize = new Point();
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getSize(mScreenSize);
    }

    public void addRecords(List<Record> recordList){
        //if(mRecordList.size() > 0) return;
        for(Record record : recordList){
            if(record.getGroup() != null){
                mRecordList.add(0,record);
                //break;
            }else{
                Log.d(TAG,"监测到空Group");
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public RecordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mItemWidth = parent.getWidth();
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

            int width = mScreenSize.x;
            int height = (int)(imageBean.getHeight() * (float)mScreenSize.x / imageBean.getWidth());

            ViewGroup.LayoutParams params = holder.largeImage.getLayoutParams();
            params.width = width;
            params.height = height;
            holder.largeImage.setLayoutParams(params);

            GlideApp.with(mContext).load(url)
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
            Log.d(TAG,"多张缩略图");
            holder.thumbParentLayout.setVisibility(View.VISIBLE);
            List<ImageBean> thumbImageList = group.getThumbImageList();
            assert  thumbImageList.size() <= 9;
            for(int j = 0; j < thumbImageList.size(); j++){
                ImageBean imageBean = thumbImageList.get(j);

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

            SpannableString spanStr = Utils.makeColorSpan(
                    new String[]{group.getPlayCount()+"","次播放"},
                    new int[]{Color.argb(0xff,0xff,0x84,0),Color.WHITE});
            playerView.setLeftBottomText(spanStr);
            playerView.setRightBottomText(DateUtils.secondToDuration((int)group.getDuration()));

            int width = mScreenSize.x;
            int height = (int)(group.getVideoHeight() * (float)width / group.getVideoWidth());
            ViewGroup.LayoutParams params = playerView.getLayoutParams();
            params.width = width;
            params.height = height;
            playerView.setLayoutParams(params);

            params = playerView.getCaptureImage().getLayoutParams();
            params.width = width;
            params.height = height;
            playerView.getCaptureImage().setLayoutParams(params);
            GlideApp.with(mContext).
                    load(captureImageUrl)
                    .centerCrop()
                    .into(playerView.getCaptureImage());

        }else{
            holder.playerView.setVisibility(View.GONE);
        }
    }

    private void loadResizableImage(final ImageView imageView,int placeholderResId, String url) {



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
            thumbImageList = new ImageView[9];
            thumbImageList[0] = (ImageView) itemView.findViewById(R.id.thumb_image_1);
            thumbImageList[1] = (ImageView) itemView.findViewById(R.id.thumb_image_2);
            thumbImageList[2] = (ImageView) itemView.findViewById(R.id.thumb_image_3);
            thumbImageList[3] = (ImageView) itemView.findViewById(R.id.thumb_image_4);
            thumbImageList[4] = (ImageView) itemView.findViewById(R.id.thumb_image_5);
            thumbImageList[5] = (ImageView) itemView.findViewById(R.id.thumb_image_6);
            thumbImageList[6] = (ImageView) itemView.findViewById(R.id.thumb_image_7);
            thumbImageList[7] = (ImageView) itemView.findViewById(R.id.thumb_image_8);
            thumbImageList[8] = (ImageView) itemView.findViewById(R.id.thumb_image_9);

            playerView = (SurfaceVideoView) itemView.findViewById(R.id.player_view);

            hotCommentLabel = (TextView) itemView.findViewById(R.id.hot_comment_label);
            hotCommentList = (ListView) itemView.findViewById(R.id.hot_comment_list);

            diggCountText = (TextView) itemView.findViewById(R.id.digg_count_text);
            buryCountText = (TextView) itemView.findViewById(R.id.bury_count_text);
            commentCountText = (TextView) itemView.findViewById(R.id.comment_count_text);
        }
    }
}
