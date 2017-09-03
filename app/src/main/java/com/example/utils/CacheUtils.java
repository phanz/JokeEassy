package com.example.utils;

import android.content.Context;
import android.os.Environment;

import com.example.AppConfig;
import com.example.JokeApp;
import com.example.model.Record;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by phanz on 2017/9/2.
 */

public class CacheUtils {

    public static DiskLruCache open(Context context) throws IOException {
        File cacheFile = getDiskCacheDir(context, AppConfig.CACHE_FILE_NAME);
        return DiskLruCache.open(cacheFile,1,1,AppConfig.CACHE_SIZE);
    }

    public static void saveContentCache(String contentType,List<Record> recordList){
        String key = "content" + contentType;
        try {
            DiskLruCache diskLruCache = CacheUtils.open(JokeApp.getContext());
            Gson gson = new Gson();
            DiskLruCache.Editor editor = diskLruCache.edit(key);
            editor.set(0,gson.toJson(recordList));
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Record> getContentCache(String contentType){
        String key = "content" + contentType;
        try {
            DiskLruCache diskLruCache = CacheUtils.open(JokeApp.getContext());
            DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
            if(snapshot != null){
                String content = snapshot.getString(0);
                Gson gson = new Gson();
                List<Record> recordList = gson.fromJson(content,new TypeToken<List<Record>>(){}.getType());
                return recordList;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }
}
