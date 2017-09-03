package com.example;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;


/**
 * Created by phanz on 2017/9/2.
 */

public class JokeApp extends Application {

    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        LeakCanary.install(this);

    }

    public static Context getContext(){
        return mContext;
    }
}
