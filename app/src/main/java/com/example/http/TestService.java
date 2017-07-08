package com.example.http;


import com.example.model.Contributor;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by phanz on 2017/7/4.
 */

public interface TestService {

    @GET("/repos/{owner}/{repo}/contributors")
    Call<List<Contributor>> contributors(@Path("owner") String owner, @Path("repo") String repo);

    @GET("repos/{owner}/{repo}/contributors")
    Observable<List<Contributor>> rxContributors(@Path("owner") String owner, @Path("repo") String repo);

}
