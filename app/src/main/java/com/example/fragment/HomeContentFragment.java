package com.example.fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.adapter.RecordAdapter;
import com.example.http.HttpDataRepository;
import com.example.jokeeassy.R;
import com.example.model.Group;
import com.example.model.ImageBean;
import com.example.model.JsonResponse;
import com.example.model.Record;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


public class HomeContentFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener,AbsListView.OnScrollListener{
    public static final String TAG = "HomeRecommendFragment";

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mContentListView;
    private RecordAdapter mRecordAdapter;
    private String mContentType;

    public HomeContentFragment(){

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mRecordAdapter = new RecordAdapter(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_content, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.content_swipe_refresh_view);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mContentListView = (ListView) view.findViewById(R.id.content_list_view);
        mContentListView.setAdapter(mRecordAdapter);
        mContentListView.setOnScrollListener(this);
        return view;
    }


    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        switch (scrollState){
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE://停止滚动
                mRecordAdapter.setScrollState(false);
                int count = absListView.getChildCount();//获取屏幕中的视图个数
                for(int i = 0; i < count; i++){
                    View childView = absListView.getChildAt(i);
                    int position = absListView.getPositionForView(childView);
                    Record record = (Record) mRecordAdapter.getItem(position);
                    Group group = record.getGroup();
                    if(group != null && group.getLargeImage() != null){
                        ImageBean imageBean = group.getLargeImage();
                        String url = imageBean.getUrlList().get(0).getUrl();
                        ImageView largeImage = (ImageView) childView.findViewById(R.id.large_image);
                        Glide.with(getActivity()).load(url).into(largeImage);
                    }
                    if(group != null && group.getThumbImageList() != null){
                        List<ImageBean> thumbImageList = group.getThumbImageList();
                        for(int j = 0; j < thumbImageList.size(); j++){
                            ImageBean imageBean = thumbImageList.get(j);
                            RecordAdapter.RecordHolder holder = (RecordAdapter.RecordHolder)childView.getTag();
                            ViewGroup.LayoutParams thumbParams = holder.thumbImageList[j].getLayoutParams();
                            thumbParams.width = imageBean.getWidth();
                            thumbParams.height = imageBean.getHeight();
                            loadImage(holder.thumbImageList[j],imageBean.getUrl(),thumbParams);
                        }
                    }

                    if(group != null && group.getIsVideo() == 1){
                        ImageView videoCaptureImage = (ImageView) childView.findViewById(R.id.video_capture_image);
                        final String captureImageUrl = group.getLargeCover().getUrlList().get(0).getUrl();
                        loadImage(videoCaptureImage,captureImageUrl,null);
                    }

                }
                break;

            case AbsListView.OnScrollListener.SCROLL_STATE_FLING://滚动做出了抛的动作
                mRecordAdapter.setScrollState(true);
                break;

            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL://正在滚动
                mRecordAdapter.setScrollState(true);
                break;
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }

    @Override
    public void onRefresh() {
        Toast.makeText(getActivity(),"监测到下拉，开始刷新推荐内容",Toast.LENGTH_SHORT).show();
        fetchContent(null);
    }

    public HomeContentFragment setContentType(String contentType){
        mContentType = contentType;
        return this;
    }

    public void fetchContent(OnFetchCompleteListener listener){
        getContent(mContentType,listener);
    }

    private void getContent(String contentType, final OnFetchCompleteListener listener){
        HttpDataRepository.getInstance().getContents(contentType,new Observer<JsonResponse>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull JsonResponse jsonResponse) {
                List<Record> recordList = jsonResponse.getData().getData();
                mRecordAdapter.addRecords(recordList);
                if(listener != null){
                    listener.onFetchComplete(0);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                Log.d(TAG, "onError: " + "推荐页内容加载错误");
                mSwipeRefreshLayout.setRefreshing(false);
                if(listener != null){
                    listener.onFetchComplete(1);
                }
            }

            @Override
            public void onComplete() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
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

    public interface OnFetchCompleteListener{
        /**
         * 0:成功
         * 1:失败
         * @param result
         */
        void onFetchComplete(int result);
    }


}
