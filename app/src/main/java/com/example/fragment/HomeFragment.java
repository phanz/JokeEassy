package com.example.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adapter.FragmentAdapter;
import com.example.jokeeassy.R;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener{
    public static final String TAG = "HomeFragment";

    private ViewPager mViewPager;
    private List<Fragment> mHomeSubFragment;
    private FragmentAdapter mFragmentAdapter;

    public HomeFragment(){
        HomeRecommendFragment homeRecommendFragment = new HomeRecommendFragment();
        HomeVideoFragment homeVideoFragment = new HomeVideoFragment();
        HomeTaleShowFragment homeTaleShowFragment = new HomeTaleShowFragment();
        HomePictureFragment homePictureFragment = new HomePictureFragment();
        HomeTaleFragment homeTaleFragment = new HomeTaleFragment();
        HomeSubscribeFragment homeSubscribeFragment = new HomeSubscribeFragment();
        mHomeSubFragment = new ArrayList<>();
        mHomeSubFragment.add(homeRecommendFragment);
        mHomeSubFragment.add(homeVideoFragment);
        mHomeSubFragment.add(homeTaleShowFragment);
        mHomeSubFragment.add(homePictureFragment);
        mHomeSubFragment.add(homeTaleFragment);
        mHomeSubFragment.add(homeSubscribeFragment);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.recommend_view_pager);

        FragmentManager fm = getChildFragmentManager();
        mFragmentAdapter = new FragmentAdapter(fm,mHomeSubFragment);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOnPageChangeListener(this);
        return view;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.d(TAG, "onPageScrolled: ");
    }

    @Override
    public void onPageSelected(int position) {
        Log.d(TAG, "onPageSelected: ");
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.d(TAG, "onPageScrollStateChanged: ");
    }


}
