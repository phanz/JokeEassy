package com.example.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.adapter.FragmentAdapter;
import com.example.jokeeassy.R;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener{
    public static final String TAG = "HomeFragment";

    private ViewPager mViewPager;
    private List<Fragment> mHomeSubFragment;
    private FragmentAdapter mFragmentAdapter;

    private ImageView mCursorImage;
    private LinearLayout.LayoutParams mCursorParams;
    private int tabWidth;

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
        mCursorImage = (ImageView) view.findViewById(R.id.tab_cursor_image);
        mCursorParams = (LinearLayout.LayoutParams) mCursorImage.getLayoutParams();
        WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();
        tabWidth = width / 5;
        FragmentManager fm = getChildFragmentManager();
        mFragmentAdapter = new FragmentAdapter(fm,mHomeSubFragment);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOnPageChangeListener(this);
        return view;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.d(TAG, "onPageScrolled: ");
        mCursorParams.leftMargin = positionOffsetPixels / 5 + tabWidth * position;
        mCursorImage.setLayoutParams(mCursorParams);

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
