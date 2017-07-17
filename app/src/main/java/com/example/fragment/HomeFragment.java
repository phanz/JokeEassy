package com.example.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adapter.FragmentAdapter;
import com.example.jokeeassy.R;
import com.example.utils.DisplayUtils;
import com.example.widgets.NavigatorView;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener{
    public static final String TAG = "HomeFragment";

    private static final int TAB_NUM_DEFAULT = 5;
    private NavigatorView mNavigatorView;
    private List<String> mTitleList;
    private List<Fragment> mHomeSubFragment;

    private FragmentAdapter mFragmentAdapter;
    private ViewPager mViewPager;

    public HomeFragment(){
        mTitleList = new ArrayList<>();
        mTitleList.add("推荐");
        mTitleList.add("视频");
        mTitleList.add("段友秀");
        mTitleList.add("图片");
        mTitleList.add("段子");
        mTitleList.add("订阅");

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

        mNavigatorView = (NavigatorView) view.findViewById(R.id.tab_list_layout);
        mNavigatorView.setTabCount(TAB_NUM_DEFAULT);
        mNavigatorView.setTabList(mTitleList);
        mNavigatorView.setOnTabListener(new NavigatorView.OnTabClickListener() {
            @Override
            public void onTabClick(int index) {
                mViewPager.setCurrentItem(index);
            }
        });

        FragmentManager fm = getChildFragmentManager();
        mFragmentAdapter = new FragmentAdapter(fm,mHomeSubFragment);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(this);
        return view;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mNavigatorView.setCursorPosition(position,positionOffset);

    }

    @Override
    public void onPageSelected(int position) {
        Log.d(TAG, "onPageSelected,position: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //Log.d(TAG, "onPageScrollStateChanged: ");
    }

}
