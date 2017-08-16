package com.example.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.adapter.FragmentAdapter;
import com.example.jokeeassy.R;
import com.example.widgets.NavigatorView;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener{
    public static final String TAG = "HomeFragment";

    private NavigatorView mNavigatorView;
    private List<String> mTitleList;
    private List<Fragment> mHomeSubFragment;

    private FragmentAdapter mFragmentAdapter;
    private ViewPager mViewPager;

    private ImageView mRefreshImage;

    public HomeFragment(){

        mHomeSubFragment = new ArrayList<>();
        mHomeSubFragment.add(HomeContentFragment.newInstance("推荐","-101"));
        mHomeSubFragment.add(HomeContentFragment.newInstance("视频","-104"));
        mHomeSubFragment.add(HomeContentFragment.newInstance("段友秀",""));
        mHomeSubFragment.add(HomeContentFragment.newInstance("图片","-103"));
        mHomeSubFragment.add(HomeContentFragment.newInstance("段子","-102"));
        mHomeSubFragment.add(HomeContentFragment.newInstance("订阅",""));

        mTitleList = new ArrayList<>();
        for(Fragment fragment : mHomeSubFragment){
            HomeContentFragment contentFragment = (HomeContentFragment)fragment;
            mTitleList.add(contentFragment.getTitle());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.recommend_view_pager);

        mNavigatorView = (NavigatorView) view.findViewById(R.id.tab_list_layout);

        NavigatorView.NavigatorViewBuilder navigatorViewBuilder
                = new NavigatorView.NavigatorViewBuilder(getContext());

        navigatorViewBuilder.setTabCount(5)
                .setTabList(mTitleList)
                .setTabHeight(45)
                .setTextSize(16)
                .setTextOriginColor(Color.BLACK)
                .setTextChangeColor(Color.WHITE)
                .setCursorScale(0.8f)
                .setCursorRadius(16)
                .setTabList(mTitleList)
                .setOnTabListener(new NavigatorView.OnTabClickListener() {
                    @Override
                    public void onTabClick(int index) {
                        mViewPager.setCurrentItem(index,false);
                    }
                })
                .setRightImage(R.drawable.ic_topbar_arrow)
                .apply(mNavigatorView);

        FragmentManager fm = getChildFragmentManager();
        mFragmentAdapter = new FragmentAdapter(fm,mHomeSubFragment);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.addOnPageChangeListener(this);

        mRefreshImage = (ImageView) view.findViewById(R.id.refresh_btn);
        final Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.rotate);
        mRefreshImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int select = mViewPager.getCurrentItem();
                mRefreshImage.startAnimation(animation);
                HomeContentFragment contentFragment = (HomeContentFragment) mHomeSubFragment.get(select);
                contentFragment.fetchContent(new HomeContentFragment.OnFetchCompleteListener() {
                    @Override
                    public void onFetchComplete(int result) {
                        mRefreshImage.clearAnimation();
                    }
                });
            }
        });

        return view;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //Log.d(TAG, "onPageScrolled: \t position:" + position + "\tpositionOffset:" + positionOffset);
        mNavigatorView.setCursorPosition(position,positionOffset);
    }

    @Override
    public void onPageSelected(int position) {
        //Log.d(TAG, "onPageSelected,position: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //Log.d(TAG, "onPageScrollStateChanged: ");
    }

}
