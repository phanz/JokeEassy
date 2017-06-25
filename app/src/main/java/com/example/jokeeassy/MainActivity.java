package com.example.jokeeassy;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.fragment.DiscoveryFragment;
import com.example.fragment.HomeFragment;
import com.example.fragment.MessageFragment;
import com.example.fragment.ReviewFragment;
import com.example.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends FragmentActivity {
    public static final String TAG = "MainActivity";

    @BindView(R.id.home_image)
    public ImageView mHomeImage;
    @BindView(R.id.discovery_image)
    public ImageView mDiscoveryImage;
    @BindView(R.id.review_image)
    public ImageView mReviewImage;
    @BindView(R.id.msg_image)
    public ImageView mMessageImage;

    private Fragment mFragmentNow;

    private Fragment mHomeFragment;
    private Fragment mDiscoveryFragment;
    private Fragment mReviewFragment;
    private Fragment mMessageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusBar));
        }
        ButterKnife.bind(this);
        TitleBar titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setLeftImageResource(R.drawable.ic_discovery_default_avatar);
        titleBar.setTitleBackground(R.drawable.ic_neihan_logo);
        titleBar.setDividerColor(android.R.color.black);
        titleBar.addAction(new TitleBar.ImageAction(R.drawable.ic_publish) {
            @Override
            public void performAction(View view) {

            }
        });

        mHomeFragment = new HomeFragment();
        mDiscoveryFragment = new DiscoveryFragment();
        mReviewFragment = new ReviewFragment();
        mMessageFragment = new MessageFragment();
        changeFragment(mHomeFragment);
        mHomeImage.setBackgroundResource(R.drawable.ic_tab_home_pressed);
    }

    @OnClick({R.id.home_bottom_view, R.id.discovery_bottom_view,
            R.id.review_bottom_view, R.id.msg_bottom_view})
    public void onBottomClick(View view) {
        mHomeImage.setBackgroundResource(R.drawable.ic_tab_home_normal);
        mDiscoveryImage.setBackgroundResource(R.drawable.ic_tab_discovery_normal);
        mReviewImage.setBackgroundResource(R.drawable.ic_tab_review_normal);
        mMessageImage.setBackgroundResource(R.drawable.ic_tab_msg_normal);

        int id = view.getId();
        switch (id){
            case R.id.home_bottom_view:
                mHomeImage.setBackgroundResource(R.drawable.ic_tab_home_pressed);
                changeFragment(mHomeFragment);
                break;
            case R.id.discovery_bottom_view:
                mDiscoveryImage.setBackgroundResource(R.drawable.ic_tab_discovery_pressed);
                changeFragment(mDiscoveryFragment);
                break;
            case R.id.review_bottom_view:
                mReviewImage.setBackgroundResource(R.drawable.ic_tab_review_pressed);
                changeFragment(mReviewFragment);
                break;
            case R.id.msg_bottom_view:
                mMessageImage.setBackgroundResource(R.drawable.ic_tab_msg_pressed);
                changeFragment(mMessageFragment);
                break;

            default:
                break;
        }
    }

    public void changeFragment(Fragment fragment){
        if(fragment != mFragmentNow){
            mFragmentNow = fragment;
            Bundle bundle = new Bundle();
            bundle.putString("key","Activity Data");//Fragment数据传递
            fragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fl_content,fragment).commit();
        }
    }
}
