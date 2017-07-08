package com.example.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jokeeassy.R;

public class HomeTaleFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mTaleListView;
    private Handler mHandler;

    public HomeTaleFragment(){
        mHandler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_tale, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.tale_swipe_refresh_view);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mTaleListView = (ListView) view.findViewById(R.id.tale_list_view);
        return view;
    }



    @Override
    public void onRefresh() {
        Toast.makeText(getActivity(),"监测到下拉，开始刷新",Toast.LENGTH_SHORT).show();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        },3000);
    }
}
