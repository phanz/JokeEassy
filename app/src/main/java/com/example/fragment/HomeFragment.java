package com.example.fragment;

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
import com.example.http.DataRepository;
import com.example.http.HttpDataRepository;
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

    private ImageView mRefreshImage;

    public HomeFragment(){
        mTitleList = new ArrayList<>();
        mTitleList.add("推荐");
        mTitleList.add("视频");
        mTitleList.add("段友秀");
        mTitleList.add("图片");
        mTitleList.add("段子");
        mTitleList.add("订阅");

        HomeContentFragment homeRecommendFragment = new HomeContentFragment().setContentType("-101");
        HomeContentFragment homeVideoFragment = new HomeContentFragment().setContentType("-104");
        HomeContentFragment homeTaleShowFragment = new HomeContentFragment();
        HomeContentFragment homePictureFragment = new HomeContentFragment().setContentType("-103");
        HomeContentFragment homeTaleFragment = new HomeContentFragment().setContentType("-102");
        HomeContentFragment homeSubscribeFragment = new HomeContentFragment();

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
