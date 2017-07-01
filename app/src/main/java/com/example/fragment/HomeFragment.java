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
import com.example.utils.DisplayUtils;
import com.example.widgets.ColorTrackView;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener{
    public static final String TAG = "HomeFragment";

    private static final int TAB_NUM = 6;
    private int tabWidth;
    private ImageView mCursorImage;
    private LinearLayout.LayoutParams mCursorParams;

    private LinearLayout mTabContainer;
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
        mCursorImage = (ImageView) view.findViewById(R.id.tab_cursor_image);
        mCursorParams = (LinearLayout.LayoutParams) mCursorImage.getLayoutParams();
        WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();
        tabWidth = width / TAB_NUM;

        mTabContainer = (LinearLayout) view.findViewById(R.id.tab_list_layout2);

        for(int i = 0; i < mTitleList.size(); i++){
            final ColorTrackView tabView = makeColorTrackView(mTitleList.get(i));
            if(i == 0) tabView.setProgress(1);
            mTabContainer.addView(tabView);
            ViewGroup.LayoutParams params = tabView.getLayoutParams();
            params.width = tabWidth;
            params.height = DisplayUtils.dp2px(getActivity(),40);
            tabView.setLayoutParams(params);
            final int index = i;
            tabView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*ColorTrackView preTrackView = (ColorTrackView) mTabContainer.getChildAt(preIndex);
                    if(preTrackView != null) preTrackView.setProgress(0);
                    tabView.setProgress(1);*/
                    mViewPager.setCurrentItem(index);
                }
            });
        }

        FragmentManager fm = getChildFragmentManager();
        mFragmentAdapter = new FragmentAdapter(fm,mHomeSubFragment);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(this);
        return view;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mCursorParams.leftMargin = positionOffsetPixels / TAB_NUM + tabWidth * position;
        mCursorImage.setLayoutParams(mCursorParams);
        String log = String.format("position:%d,\tpositionOffset:%f\tpositionOffsetPixels:%d",position,positionOffset,positionOffsetPixels);
        Log.d(TAG,log);
        if(positionOffset > 0){
            ColorTrackView left = (ColorTrackView) mTabContainer.getChildAt(position);
            ColorTrackView right = (ColorTrackView) mTabContainer.getChildAt(position + 1);
            left.setDirection(1);
            right.setDirection(0);
            left.setProgress(1 - positionOffset);
            right.setProgress(positionOffset);
        }

    }

    @Override
    public void onPageSelected(int position) {
        Log.d(TAG, "onPageSelected: ");
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.d(TAG, "onPageScrollStateChanged: ");
    }

    public ColorTrackView makeColorTrackView(String title){
        ColorTrackView tabView = new ColorTrackView(getActivity(),null);
        tabView.setText(title);
        tabView.setTextSize(DisplayUtils.sp2px(getActivity(),18));
        return tabView;
    }

}
