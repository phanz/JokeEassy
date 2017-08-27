package com.example.widgets;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jokeeassy.R;
import com.example.utils.DateUtils;


/**
 * Created by phanz on 2017/8/26.
 */

public class SurfaceVideoView extends RelativeLayout implements SurfaceHolder.Callback{
    public static final String TAG = "SurfaceVideoView";

    //private String videoUrl = "http://www.zcycjy.com/coursePath/%E7%BB%A7%E7%BB%AD%E6%95%99%E8%82%B2/%E6%96%B0%E8%AF%BE%E7%A8%8B%E8%A7%86%E9%A2%91/%E2%80%9C%E8%90%A5%E6%94%B9%E5%A2%9E%E2%80%9D%E6%94%BF%E7%AD%96%E8%A7%A3%E8%AF%BB%E5%8F%8A%E4%BC%81%E4%B8%9A%E7%A8%8E%E5%8A%A1%E9%A3%8E%E9%99%A9%E4%B8%8E%E7%AD%B9%E5%88%92%E6%8E%A2%E8%AE%A81.mp4";
    private Uri videoUri;
    private String url;
    private Context mContext;
    private SurfaceView videoSurfaceView;// 绘图容器对象，用于把视频显示在屏幕上
    private ProgressBar videoProgressBar;

    private Button videoPlayBtn;// 用于开始和暂停的按钮
    private ImageView mCaptureImage;

    private RelativeLayout videoBottomLayout;
    private TextView videoPlayTimeText;
    private SeekBar videoSeekBar;// 进度条控件
    private TextView videoTotalTimeText;

    private MediaPlayer mediaPlayer; // 播放器控件

    private boolean isPlaying;
    private Handler mHandler;
    private static final int MSG_UPDATE_PROGRESS = 1;

    public SurfaceVideoView(Context context) {
        super(context);
    }

    public SurfaceVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.surface_player,this);
        videoSurfaceView = (SurfaceView) findViewById(R.id.video_surface_view);
        videoProgressBar = (ProgressBar) findViewById(R.id.video_progress_bar);

        videoPlayBtn = (Button) findViewById(R.id.video_play_btn);
        mCaptureImage = (ImageView) findViewById(R.id.capture_image);

        videoBottomLayout = (RelativeLayout)findViewById(R.id.bottom_group);
        videoPlayTimeText = (TextView) findViewById(R.id.video_play_time);
        videoSeekBar = (SeekBar) findViewById(R.id.video_time_seek_bar);
        videoTotalTimeText = (TextView) findViewById(R.id.video_total_time);

        videoSurfaceView.getHolder().addCallback(this);

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                int what = msg.what;
                switch (what){
                    case MSG_UPDATE_PROGRESS:
                        updateSeekBar();
                        if(isPlaying){
                            mHandler.sendEmptyMessageDelayed(MSG_UPDATE_PROGRESS,1000);
                        }
                        break;
                }
            }
        };
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated: ");
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.reset(); // 回复播放器默认
            mediaPlayer.setDataSource(url); // 设置播放路径
            mediaPlayer.setDisplay(videoSurfaceView.getHolder()); // 把视频显示在SurfaceView上
            mediaPlayer.prepareAsync(); // 准备播放
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    videoProgressBar.setVisibility(View.GONE);
                    videoPlayBtn.setVisibility(View.VISIBLE);
                }
            });
            videoPlayBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mediaPlayer.start();
                    mHandler.sendEmptyMessage(MSG_UPDATE_PROGRESS);
                    isPlaying = true;
                    videoPlayBtn.setVisibility(View.GONE);
                    mCaptureImage.setVisibility(View.GONE);
                    videoSeekBar.setVisibility(View.VISIBLE);
                    videoBottomLayout.setVisibility(View.GONE);
                }
            });
            videoSurfaceView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                        isPlaying = false;
                        videoPlayBtn.setVisibility(View.VISIBLE);
                        videoSeekBar.setVisibility(View.GONE);
                        videoBottomLayout.setVisibility(View.VISIBLE);
                    }
                }
            });

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    videoPlayBtn.setVisibility(View.VISIBLE);
                    videoSeekBar.setVisibility(View.GONE);
                    isPlaying = false;
                }
            });

            videoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    int value = videoSeekBar.getProgress() * mediaPlayer.getDuration() / videoSeekBar.getMax();
                    videoPlayTimeText.setText(DateUtils.secondToDuration(value / 1000));
                    mediaPlayer.seekTo(value);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged: ");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG,"surfaceDestroyed");
        if(mediaPlayer != null){
            if(mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }
            isPlaying = false;
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void updateSeekBar() {
        if (mediaPlayer == null) {
            isPlaying = false;
        } else if (mediaPlayer.isPlaying()) {
            isPlaying = true;
            int position = mediaPlayer.getCurrentPosition();
            int mMax = mediaPlayer.getDuration();
            int sMax = videoSeekBar.getMax();
            if (mMax > 0) {
                //videoPlayTimeText.setText(DateUtils.secondToDuration(position / 1000));
                //videoTotalTimeText.setText(DateUtils.secondToDuration(mMax / 1000));
                int result = position * sMax / mMax;
                videoSeekBar.setProgress(result);
                String log = String.format("position:%d, mMax:%d, sMax:%d,result:%d",position,mMax,sMax,result);
                Log.d(TAG,log);
            } else {
                Toast.makeText(mContext, "无法播放", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void setVideoUri(String url){
        this.url = url;
    }

    public ImageView getCaptureImage(){
        return mCaptureImage;
    }

    public void setLeftBottomText(CharSequence text){
        videoPlayTimeText.setText(text);
    }

    public void setRightBottomText(String text){
        videoTotalTimeText.setText(text);
    }

}
