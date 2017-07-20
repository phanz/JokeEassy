package com.example.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.jokeeassy.R;
import com.example.model.Group;
import com.example.model.ImageBean;
import com.example.model.Record;
import com.example.utils.DisplayUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phanz on 2017/7/8.
 */

public class RecordAdapter extends BaseAdapter {
    private static final String TAG = "RecordAdapter";

    private List<Record> mRecordList;
    private Context mContext;
    private LayoutInflater mInflater;
    private DisplayUtils displayUtils;
    private boolean mScrollState;

    public RecordAdapter(Context context){
        mContext = context;
        mRecordList = new ArrayList<>();
        mInflater = LayoutInflater.from(mContext);
        mScrollState = true;
    }

    public void setScrollState(boolean scrollState) {
        mScrollState = scrollState;
    }

    public void addRecords(List<Record> recordList){
        mRecordList.addAll(0,recordList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mRecordList.size();
    }

    @Override
    public Object getItem(int i) {
        return mRecordList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        RecordHolder holder = null;
        if(view == null){
            view = mInflater.inflate(R.layout.item_common,viewGroup,false);
            ImageView avatarImage = (ImageView) view.findViewById(R.id.user_avatar_image);
            TextView userNameText = (TextView) view.findViewById(R.id.user_name_text);
            TextView contentText = (TextView) view.findViewById(R.id.content_text);
            TextView categoryText = (TextView) view.findViewById(R.id.category_text);
            ImageView largeImage = (ImageView) view.findViewById(R.id.large_image);
            GridLayout thumbLayout = (GridLayout) view.findViewById(R.id.thumb_image_list);
            ImageView thumbImage1 = (ImageView) view.findViewById(R.id.thumb_image_1);
            ImageView thumbImage2 = (ImageView) view.findViewById(R.id.thumb_image_2);
            ImageView thumbImage3 = (ImageView) view.findViewById(R.id.thumb_image_3);
            ImageView thumbImage4 = (ImageView) view.findViewById(R.id.thumb_image_4);
            ImageView thumbImage5 = (ImageView) view.findViewById(R.id.thumb_image_5);
            ImageView thumbImage6 = (ImageView) view.findViewById(R.id.thumb_image_6);
            ImageView thumbImage7 = (ImageView) view.findViewById(R.id.thumb_image_7);
            ImageView thumbImage8 = (ImageView) view.findViewById(R.id.thumb_image_8);
            ImageView thumbImage9 = (ImageView) view.findViewById(R.id.thumb_image_9);

            FrameLayout videoLayout = (FrameLayout) view.findViewById(R.id.video_layout);
            VideoView videoView = (VideoView) view.findViewById(R.id.video_view);
            ImageView videoCaptureImage = (ImageView) view.findViewById(R.id.video_capture_image);
            ImageView playIcon = (ImageView) view.findViewById(R.id.video_play_icon);

            TextView diggCountText = (TextView) view.findViewById(R.id.digg_count_text);
            TextView buryCountText = (TextView) view.findViewById(R.id.bury_count_text);
            TextView commentCountText = (TextView) view.findViewById(R.id.comment_count_text);

            holder = new RecordHolder();
            holder.avatarImage = avatarImage;
            holder.userNameText = userNameText;
            holder.contentText = contentText;
            holder.categoryText = categoryText;
            holder.largeImage = largeImage;
            holder.thumbParentLayout = thumbLayout;
            holder.thumbImageList = new ImageView[9];
            holder.thumbImageList[0] = thumbImage1;
            holder.thumbImageList[1] = thumbImage2;
            holder.thumbImageList[2] = thumbImage3;
            holder.thumbImageList[3] = thumbImage4;
            holder.thumbImageList[4] = thumbImage5;
            holder.thumbImageList[5] = thumbImage6;
            holder.thumbImageList[6] = thumbImage7;
            holder.thumbImageList[7] = thumbImage8;
            holder.thumbImageList[8] = thumbImage9;

            holder.videoLayout = videoLayout;
            holder.videoView = videoView;
            holder.videoCaptureImage = videoCaptureImage;
            holder.videoPlayIcon = playIcon;

            holder.diggCountText = diggCountText;
            holder.buryCountText = buryCountText;
            holder.commentCountText = commentCountText;

            view.setTag(holder);

        }else{
            holder = (RecordHolder) view.getTag();
        }
        Record record = mRecordList.get(i);
        Group group = record.getGroup();
        if(group == null){
            Log.e(TAG,"Group为空：" + record.toString());
        }else{
            holder.avatarImage.setImageResource(R.drawable.default_round_head);
            holder.userNameText.setText(group.getUser().getName());
            holder.contentText.setText(group.getContent());
            holder.categoryText.setText(group.getCategoryName());
            holder.diggCountText.setText(group.getDiggCount() + "");
            holder.buryCountText.setText(group.getBuryCount() + "");
            holder.commentCountText.setText(group.getCommentCount() + "");
            final ImageView imageView = holder.avatarImage;
            ViewGroup.LayoutParams avatarParams = imageView.getLayoutParams();
            avatarParams.width = DisplayUtils.dp2px(mContext,35);
            avatarParams.height = DisplayUtils.dp2px(mContext,35);
            loadImage(imageView,group.getUser().getAvatarUrl(), avatarParams);
            //单张大图片
            if(group.getLargeImage() != null){
                ImageBean imageBean = group.getLargeImage();
                String url = imageBean.getUrlList().get(0).getUrl();
                ViewGroup.LayoutParams largeImageParams = holder.largeImage.getLayoutParams();

                largeImageParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                /*largeImageParams.height = ViewGroup.LayoutParams.MATCH_PARENT;*/
//                largeImageParams.width = imageBean.getWidth();
//                largeImageParams.height = imageBean.getHeight();

                String sizeInfo = String.format("(%d,%d)",imageBean.getrWidth(),imageBean.getrHeight());
                //holder.contentText.append(s);
                if(holder.largeImage.getId() == R.id.large_image){
                    Log.d(TAG,"发现大图URL");
                }
                // TODO: 2017/7/15 setAdjustViewBounds据说要和setMaxWidth或setMaxHeight配合使用
                holder.largeImage.setAdjustViewBounds(true);
                holder.largeImage.setLayoutParams(largeImageParams);
                holder.largeImage.setVisibility(View.VISIBLE);
                //loadImage(holder.largeImage,url,largeImageParams);
                if(mScrollState){//滑动中不加载图片
                    holder.largeImage.setImageResource(R.drawable.large_loading);
                }else{
                    Glide.with(mContext).load(url).into(holder.largeImage);
                }

            }else{
                holder.largeImage.setVisibility(View.GONE);
            }
            //多张缩略图
            if(group.getThumbImageList() != null){
                Log.d(TAG,"多张缩略图");
                holder.thumbParentLayout.setVisibility(View.VISIBLE);
                List<ImageBean> thumbImageList = group.getThumbImageList();
                assert  thumbImageList.size() <= 9;
                for(int j = 0; j < thumbImageList.size(); j++){
                    ImageBean imageBean = thumbImageList.get(j);
                    ViewGroup.LayoutParams thumbParams = holder.thumbImageList[j].getLayoutParams();
                    thumbParams.width = imageBean.getWidth();
                    thumbParams.height = imageBean.getHeight();
                    if(mScrollState){
                        holder.thumbImageList[j].setImageResource(R.drawable.large_loading);
                    }else{
                        loadImage(holder.thumbImageList[j],imageBean.getUrl(),thumbParams);
                    }
                }
                for(int j = thumbImageList.size(); j < holder.thumbImageList.length; j++){
                    holder.thumbImageList[j].setVisibility(View.GONE);
                }
            }else{
                holder.thumbParentLayout.setVisibility(View.GONE);

            }

            if(group.getIsVideo() == 1){
                final FrameLayout videoLayout = holder.videoLayout;
                final ImageView videoCaptureImage = holder.videoCaptureImage;
                final VideoView videoView = holder.videoView;
                final ImageView videoPlayIcon = holder.videoPlayIcon;

                videoLayout.setVisibility(View.VISIBLE);
                videoCaptureImage.setVisibility(View.VISIBLE);
                videoPlayIcon.setVisibility(View.VISIBLE);
                final String captureImageUrl = group.getLargeCover().getUrlList().get(0).getUrl();
                //Glide.with(mContext).load(captureImageUrl).into(holder.videoCaptureImage);
                if(mScrollState){
                    holder.videoCaptureImage.setImageResource(R.drawable.large_loading);
                }else{
                    loadImage(holder.videoCaptureImage,captureImageUrl,null);
                }
                Log.d("GlideListener",captureImageUrl);
                int width = group.getVideoWidth();
                int height = group.getVideoHeight();
                ViewGroup.LayoutParams params = videoView.getLayoutParams();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                //params.height = height;
                videoView.setLayoutParams(params);
                videoView.setVisibility(View.VISIBLE);
                Uri uri = Uri.parse(group.getMp4Url());
                MediaController controller = new MediaController(mContext);
                controller.setVisibility(View.INVISIBLE);
                videoView.setMediaController(controller);
                videoView.setVideoURI(uri);
                videoView.requestFocus();
                holder.videoPlayIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        videoCaptureImage.setVisibility(View.GONE);
                        videoPlayIcon.setVisibility(View.GONE);
                        if(videoView.isPlaying()){
                            videoView.resume();
                        }else{
                            videoView.start();
                        }
                    }
                });
                holder.videoLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(videoView.isPlaying()){
                            videoView.pause();
                            videoPlayIcon.setVisibility(View.VISIBLE);
                        }else{
                            videoView.resume();
                            videoPlayIcon.setVisibility(View.GONE);
                        }
                    }
                });
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        videoPlayIcon.setVisibility(View.VISIBLE);
                    }
                });

            }else{
                holder.videoLayout.setVisibility(View.GONE);
                holder.videoView.setVisibility(View.GONE);
            }


        }

        return view;
    }

    private void loadImage(final ImageView imageView, String url, final ViewGroup.LayoutParams params){
        ImageLoader.getInstance().loadImage(url, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                Log.d(TAG, "onLoadingStarted: ");
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                Log.d(TAG, "onLoadingFailed: ");
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                Log.d(TAG, "onLoadingComplete: ");
                if(loadedImage != null){
                    if(params != null){
                        imageView.setLayoutParams(params);
                    }
                    //imageView.setImageBitmap(loadedImage);
                    imageView.setImageBitmap(loadedImage);
                    imageView.setVisibility(View.VISIBLE);
                }else{
                    Log.e(TAG, "onLoadingComplete: loadedImage is null");
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                Log.d(TAG, "onLoadingCancelled: ");
            }
        });
    }

    public static class RecordHolder{
        public ImageView avatarImage;
        public TextView userNameText;
        public TextView contentText;
        public TextView categoryText;

        public ImageView largeImage;
        public GridLayout thumbParentLayout;
        public ImageView[] thumbImageList;
        public TextView diggCountText;
        public TextView buryCountText;
        public TextView commentCountText;

        public FrameLayout videoLayout;
        public VideoView videoView;
        public ImageView videoCaptureImage;
        public ImageView videoPlayIcon;
    }
}
