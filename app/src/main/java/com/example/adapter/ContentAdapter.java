package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.jokeeassy.CommentActivity;
import com.example.jokeeassy.R;
import com.example.model.Comment;
import com.example.model.Group;
import com.example.model.ImageBean;
import com.example.model.Record;

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

    public ContentAdapter(Context context){
        mContext = context;
        mRecordList = new ArrayList<>();
        mInflater = LayoutInflater.from(mContext);
    }

    public void addRecords(List<Record> recordList){
        mRecordList.addAll(0,recordList);
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

    private void bindLargeImg(RecordHolder holder, Group group) {
        //单张大图片
        if(group.getLargeImage() != null){
            String url = group.getLargeImage().getUrlList().get(0).getUrl();

            ViewGroup.LayoutParams largeImageParams = holder.largeImage.getLayoutParams();
            largeImageParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            if(holder.largeImage.getId() == R.id.large_image){
                Log.d(TAG,"发现大图URL");
            }
            // TODO: 2017/7/15 setAdjustViewBounds据说要和setMaxWidth或setMaxHeight配合使用
            holder.largeImage.setAdjustViewBounds(true);
            holder.largeImage.setLayoutParams(largeImageParams);
            holder.largeImage.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(url).into(holder.largeImage);
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
                RequestOptions options = new RequestOptions()
                        .override(imageBean.getWidth(),imageBean.getHeight());
                Glide.with(mContext).load(imageBean.getUrl()).apply(options).into(holder.thumbImageList[j]);
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
            holder.videoLayout.setVisibility(View.VISIBLE);
            holder.videoCaptureImage.setVisibility(View.VISIBLE);
            holder.videoPlayIcon.setVisibility(View.VISIBLE);
            holder.videoView.setVisibility(View.VISIBLE);

            String captureImageUrl = group.getLargeCover().getUrlList().get(0).getUrl();
            Glide.with(mContext).load(captureImageUrl).into(holder.videoCaptureImage);

            ViewGroup.LayoutParams params = holder.videoView.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            holder.videoView.setLayoutParams(params);

            Uri uri = Uri.parse(group.getMp4Url());
            MediaController controller = new MediaController(mContext);
            controller.setVisibility(View.INVISIBLE);
            holder.videoView.setMediaController(controller);
            holder.videoView.setVideoURI(uri);
            holder.videoView.requestFocus();
            holder.videoPlayIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.videoCaptureImage.setVisibility(View.GONE);
                    holder.videoPlayIcon.setVisibility(View.GONE);
                    if(holder.videoView.isPlaying()){
                        holder.videoView.resume();
                    }else{
                        holder.videoView.start();
                    }
                }
            });
            holder.videoLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(holder.videoView.isPlaying()){
                        holder.videoView.pause();
                        holder.videoPlayIcon.setVisibility(View.VISIBLE);
                    }
                }
            });
            holder.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    holder.videoPlayIcon.setVisibility(View.VISIBLE);
                }
            });
        }else{
            holder.videoLayout.setVisibility(View.GONE);
            holder.videoView.setVisibility(View.GONE);
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

        public FrameLayout videoLayout;
        public VideoView videoView;
        public ImageView videoCaptureImage;
        public ImageView videoPlayIcon;

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

            videoLayout = (FrameLayout) itemView.findViewById(R.id.video_layout);
            videoView = (VideoView) itemView.findViewById(R.id.video_view);
            videoCaptureImage = (ImageView) itemView.findViewById(R.id.video_capture_image);
            videoPlayIcon = (ImageView) itemView.findViewById(R.id.video_play_icon);

            hotCommentLabel = (TextView) itemView.findViewById(R.id.hot_comment_label);
            hotCommentList = (ListView) itemView.findViewById(R.id.hot_comment_list);

            diggCountText = (TextView) itemView.findViewById(R.id.digg_count_text);
            buryCountText = (TextView) itemView.findViewById(R.id.bury_count_text);
            commentCountText = (TextView) itemView.findViewById(R.id.comment_count_text);
        }
    }
}
