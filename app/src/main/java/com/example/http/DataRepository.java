package com.example.http;


/**
 * Created by phanz on 2017/7/4.
 */

public class DataRepository {
    public static final String TAG = DataRepository.class.getSimpleName();

    private static DataRepository sInstance = null;

    private DataRepository() {


    }

    public static DataRepository getInstance() {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository();
                }
            }
        }
        return sInstance;
    }

}
