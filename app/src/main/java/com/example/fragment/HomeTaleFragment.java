package com.example.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

public class HomeTaleFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = HomeTaleFragment.class.getSimpleName();

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mTaleListView;
    private RecordAdapter mRecordAdapter;

    public HomeTaleFragment(){

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mRecordAdapter = new RecordAdapter(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_tale, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.tale_swipe_refresh_view);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mTaleListView = (ListView) view.findViewById(R.id.tale_list_view);
        mTaleListView.setAdapter(mRecordAdapter);
        return view;
    }



    @Override
    public void onRefresh() {
        Toast.makeText(getActivity(),"监测到下拉，开始刷新",Toast.LENGTH_SHORT).show();
        HttpDataRepository.getInstance().getTales(new Observer<JsonResponse>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull JsonResponse jsonResponse) {
                List<Record> recordList = jsonResponse.getData().getData();

                mRecordAdapter.addRecords(recordList);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                Log.d(TAG, "onError: " + "段子页内容加载错误");
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onComplete() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
