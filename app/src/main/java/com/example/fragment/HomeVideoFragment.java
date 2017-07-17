package com.example.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adapter.RecordAdapter;
import com.example.http.HttpDataRepository;
import com.example.jokeeassy.R;
import com.example.model.JsonResponse;
import com.example.model.Record;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


public class HomeVideoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = "HomeVideoFragment";

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mVideoListView;
    private RecordAdapter mRecordAdapter;

    public HomeVideoFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mRecordAdapter = new RecordAdapter(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_video, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.video_swipe_refresh_view);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mVideoListView = (ListView) view.findViewById(R.id.video_list_view);
        mVideoListView.setAdapter(mRecordAdapter);
        return view;
    }


    @Override
    public void onRefresh() {
        Toast.makeText(getActivity(),"监测到下拉，开始刷新刷频",Toast.LENGTH_SHORT).show();
        fetchContent(null);
    }

    public void fetchContent(final ImageView refreshImage){
        HttpDataRepository.getInstance().getVideos(new Observer<JsonResponse>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull JsonResponse jsonResponse) {
                List<Record> recordList = jsonResponse.getData().getData();

                mRecordAdapter.addRecords(recordList);
                if(refreshImage != null){
                    refreshImage.clearAnimation();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                Log.d(TAG, "onError: " + "视频页内容加载错误");
                mSwipeRefreshLayout.setRefreshing(false);
                if(refreshImage != null){
                    refreshImage.clearAnimation();
                }
            }

            @Override
            public void onComplete() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
