package com.example;


import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Created by phanz on 2017/8/27.
 */

@GlideModule
public class CustomAppGlideModel extends AppGlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context)
                .setMemoryCacheScreens(2)
                .build();
        //builder.setMemoryCache(new LruResourceCache(calculator.getMemoryCacheSize()));
        //builder.setDiskCache(new ExternalCacheDiskCacheFactory(context));
        //builder.setDefaultRequestOptions(new RequestOptions());

        builder.setMemoryCache(new LruResourceCache(10 * 1024 * 1024));
    }
}
