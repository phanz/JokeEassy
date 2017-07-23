package com.example.http;


import com.example.model.CommentResponse;
import com.example.model.ContentResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by phanz on 2017/7/4.
 */

public interface HttpService {

    @GET("/neihan/stream/mix/v1")
    Observable<ContentResponse> loadContents(@QueryMap Map<String,String> options);

    @GET("/2/data/v2/get_essay_comments/")
    Observable<CommentResponse> loadComments(@QueryMap Map<String,String> options);

}
