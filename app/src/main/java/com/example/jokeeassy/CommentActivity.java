package com.example.jokeeassy;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.adapter.CommentAdapter;
import com.example.http.HttpDataRepository;
import com.example.model.Comment;
import com.example.model.CommentResponse;
import com.example.model.CommentSet;
import com.example.model.Group;
import com.example.widget.TitleBar;

import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class CommentActivity extends Activity implements View.OnTouchListener{

    public static final String TAG = "CommentActivity";

    private ScrollView mContentScrollView;
    private ImageView mUserAvatarImage;
    private TextView mUserNameText;
    private TextView mContentText;
    private TextView mCategoryText;
    private ImageView mLargeImage;
    private FrameLayout mVideoLayout;
    private ImageView mPlayIcon;
    private ImageView mVideoCaptureImage;
    private VideoView mVideoView;
    private TextView mDiggCountText;
    private TextView mBuryCountText;
    private TextView mCommentCountText;


    private TextView mTopCommentsText;
    private ListView mTopCommentsList;
    private TextView mRecentCommentsText;
    private ListView mRecentCommentsList;

    private CommentAdapter mTopAdapter;
    private CommentAdapter mRecentAdapter;

    private EditText mCommentInput;

    private GestureDetector mGestureDetector;

    private Group mGroup;

    private int mOffset;
    private boolean mHasMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_comment);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusBar));
        }

        ButterKnife.bind(this);
        TitleBar titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setLeftImageResource(R.drawable.ic_back_normal);
        titleBar.setTitle("详情");
        titleBar.addAction(new TitleBar.TextAction("举报") {
            @Override
            public void performAction(View view) {
                Toast.makeText(CommentActivity.this,"点击了举报按钮",Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = getIntent();
        // TODO: 2017/7/23  getStringExtra 和 getString 为什么不行
        //String groupId = intent.getStringExtra("group_id");
        Bundle bundle = intent.getExtras();
        mGroup = (Group)bundle.getSerializable("group");

        mContentScrollView = (ScrollView) findViewById(R.id.detail_layout);
        RelativeLayout contentLayout = (RelativeLayout) findViewById(R.id.item_content) ;
        mUserAvatarImage = (ImageView) contentLayout.findViewById(R.id.user_avatar_image);
        mUserNameText = (TextView) contentLayout.findViewById(R.id.user_name_text);
        mContentText = (TextView) contentLayout.findViewById(R.id.content_text);
        mCategoryText = (TextView) contentLayout.findViewById(R.id.category_text);
        mLargeImage = (ImageView) contentLayout.findViewById(R.id.large_image);

        mVideoLayout = (FrameLayout) contentLayout.findViewById(R.id.video_layout);
        mPlayIcon = (ImageView) contentLayout.findViewById(R.id.video_play_icon);
        mVideoCaptureImage = (ImageView) contentLayout.findViewById(R.id.video_capture_image);
        mVideoView = (VideoView) contentLayout.findViewById(R.id.video_view);
        mDiggCountText = (TextView) contentLayout.findViewById(R.id.digg_count_text);
        mBuryCountText = (TextView) contentLayout.findViewById(R.id.bury_count_text);
        mCommentCountText = (TextView) contentLayout.findViewById(R.id.comment_count_text);

        mGestureDetector = new GestureDetector(this,new SimpleGestureListener());
        mContentScrollView.setOnTouchListener(this);

        if(mGroup.getLargeImage() != null){
            Glide.with(this).load(mGroup.getLargeImage().getUrlList().get(0).getUrl()).into(mLargeImage);
            mLargeImage.setVisibility(View.VISIBLE);
        }else{
            mLargeImage.setVisibility(View.GONE);
        }

        if(mGroup.getIsVideo() == 1){

            mVideoCaptureImage.setVisibility(View.VISIBLE);
            mPlayIcon.setVisibility(View.VISIBLE);
            String captureImageUrl = mGroup.getLargeCover().getUrlList().get(0).getUrl();
            Glide.with(this).load(captureImageUrl).into(mVideoCaptureImage);
            ViewGroup.LayoutParams params = mVideoView.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            mVideoView.setLayoutParams(params);
            mVideoView.setVisibility(View.VISIBLE);
            Uri uri = Uri.parse(mGroup.getMp4Url());
            MediaController controller = new MediaController(this);
            controller.setVisibility(View.INVISIBLE);
            mVideoView.setMediaController(controller);
            mVideoView.setVideoURI(uri);
            mVideoView.requestFocus();
            mPlayIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mVideoCaptureImage.setVisibility(View.GONE);
                    mPlayIcon.setVisibility(View.GONE);
                    if(mVideoView.isPlaying()){
                        mVideoView.resume();
                    }else{
                        mVideoView.start();
                    }
                }
            });
            mVideoLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mVideoView.isPlaying()){
                        mVideoView.pause();
                        mPlayIcon.setVisibility(View.VISIBLE);
                    }else{
                        mVideoView.resume();
                        mPlayIcon.setVisibility(View.GONE);
                    }
                }
            });
            mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mPlayIcon.setVisibility(View.VISIBLE);
                }
            });



        }else{
            mVideoLayout.setVisibility(View.GONE);
            mVideoView.setVisibility(View.GONE);
        }

        Glide.with(this).load(mGroup.getUser().getAvatarUrl()).into(mUserAvatarImage);
        mUserNameText.setText(mGroup.getUser().getName());
        mContentText.setText(mGroup.getText());
        mCategoryText.setText(mGroup.getCategoryName());


        mTopCommentsText = (TextView) findViewById(R.id.top_comment_text);
        mTopCommentsList = (ListView) findViewById(R.id.top_comment_list);
        mRecentCommentsText = (TextView) findViewById(R.id.recent_text);
        mRecentCommentsList = (ListView) findViewById(R.id.recent_text_list);
        mCommentInput = (EditText) findViewById(R.id.comment_input);

        mTopAdapter = new CommentAdapter(this);
        mRecentAdapter = new CommentAdapter(this);

        mTopCommentsList.setAdapter(mTopAdapter);
        mRecentCommentsList.setAdapter(mRecentAdapter);

        mOffset = 0;
        mHasMore = true;
        HttpDataRepository.getInstance().getComments(
                mGroup.getGroupId() + "", mOffset + "", new Observer<CommentResponse>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull CommentResponse jsonResponse) {
                CommentSet commentSet = jsonResponse.getCommentSet();
                mHasMore = jsonResponse.isHasMore();
                List<Comment> topCommentList = commentSet.getTopComments();
                List<Comment> recentCommentList = commentSet.getRecentComments();
                mTopAdapter.setCommentList(topCommentList);
                mRecentAdapter.setCommentList(recentCommentList);
                mTopAdapter.notifyDataSetChanged();
                mRecentAdapter.notifyDataSetChanged();
                mTopCommentsText.setText(String.format("热门评论(%d)",topCommentList.size()));
                mRecentCommentsText.setText(String.format("新鲜评论(%d)",recentCommentList.size()));
                mTopCommentsText.setVisibility(topCommentList.size() > 0 ? View.VISIBLE : View.GONE);
                mRecentCommentsText.setVisibility(recentCommentList.size() > 0 ? View.VISIBLE : View.GONE);
                mOffset += recentCommentList.size();
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return mGestureDetector.onTouchEvent(motionEvent);
    }

    private class SimpleGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if(distanceY > 0){//下滑
                int measuredHeight = mContentScrollView.getChildAt(0).getMeasuredHeight();
                if(measuredHeight <= mContentScrollView.getScrollY() + mContentScrollView.getHeight()){
                    Toast.makeText(CommentActivity.this,"滑动到了底部",Toast.LENGTH_SHORT).show();
                    if(mHasMore){
                        HttpDataRepository.getInstance().getComments(mGroup.getGroupId() + "",mOffset + "",new Observer<CommentResponse>(){
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull CommentResponse commentResponse) {
                                CommentSet commentSet = commentResponse.getCommentSet();
                                mHasMore = commentResponse.isHasMore();
                                List<Comment> recentCommentList = commentSet.getRecentComments();
                                mRecentAdapter.addCommentList(recentCommentList);
                                mRecentAdapter.notifyDataSetChanged();
                                mOffset += recentCommentList.size();
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                    }
                }
            }
            return false;
        }
    }

}
