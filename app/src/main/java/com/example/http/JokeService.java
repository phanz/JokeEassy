package com.example.http;


import com.example.model.JsonResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by phanz on 2017/7/4.
 */

public interface JokeService {

    @GET("/neihan/stream/mix/v1")
    Observable<JsonResponse> jokeTales(@QueryMap Map<String,String> options);

}
