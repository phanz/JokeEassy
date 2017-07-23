package com.example.http;

import com.example.AppConfig;
import com.example.model.CommentResponse;
import com.example.model.Contributor;
import com.example.model.ContentResponse;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by phanz on 2017/7/7.
 */

public class HttpDataRepository {
    public static final String TAG = HttpDataRepository.class.getSimpleName();
    private static HttpDataRepository sInstance = null;

    private Map<String,Retrofit> mRetrofitMap;
    private TestService mGitHubService;
    private HttpService mHttpService;

    private HttpDataRepository(){
        mRetrofitMap = new HashMap<>();
        Retrofit testRetrofit = getRetrofit(AppConfig.GIT_HUB_BASE_URL);
        Retrofit jokeRetrofit = getRetrofit(AppConfig.JOKE_BASE_URL);
        mGitHubService = testRetrofit.create(TestService.class);
        mHttpService = jokeRetrofit.create(HttpService.class);
    }

    public static HttpDataRepository getInstance() {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new HttpDataRepository();
                }
            }
        }
        return sInstance;
    }

    public void getTales(Observer<ContentResponse> observer) {

        Map<String,String> queryParams = getContentBaseQueryMap();
        queryParams.put("content_type", "-102");
        mHttpService.loadContents(queryParams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getPictures(Observer<ContentResponse> observer) {

        Map<String,String> queryParams = getContentBaseQueryMap();
        queryParams.put("content_type", "-103");
        mHttpService.loadContents(queryParams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getVideos(Observer<ContentResponse> observer) {

        Map<String,String> queryParams = getContentBaseQueryMap();
        queryParams.put("content_type", "-104");
        mHttpService.loadContents(queryParams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getRecommends(Observer<ContentResponse> observer) {

        Map<String,String> queryParams = getContentBaseQueryMap();
        queryParams.put("content_type", "-101");
        mHttpService.loadContents(queryParams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getContents(String contentType,Observer<ContentResponse> observer) {

        Map<String,String> queryParams = getContentBaseQueryMap();
        queryParams.put("content_type",contentType);
        mHttpService.loadContents(queryParams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getComments(String groupId,String offset,Observer<CommentResponse> observer) {

        Map<String,String> queryParams = getCommentBaseQueryMap();
        queryParams.put("group_id",groupId);
        queryParams.put("count","20");
        queryParams.put("offset",offset);
        mHttpService.loadComments(queryParams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getGitHubInfo(Observer<List<Contributor>> observer) {

        mGitHubService.rxContributors("square", "retrofit")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    private Map<String,String> getContentBaseQueryMap(){
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("mpic", "1");
        queryParams.put("essence", "1");
        queryParams.put("message_cursor", "-1");
        queryParams.put("bd_longitude", "121.189395");
        queryParams.put("bd_latitude", "31.049766");
        queryParams.put("bd_city", "上海市");
        queryParams.put("am_longitude", "121.189454");
        queryParams.put("am_latitude", "31.049796");
        queryParams.put("am_city", "上海市");
        queryParams.put("am_loc_time", "1499090269436");
        queryParams.put("count", "30");
        queryParams.put("min_time", "1497670601");
        queryParams.put("screen_width", "1080");
        queryParams.put("iid", "11273887282");
        queryParams.put("device_id", "36812288867");
        queryParams.put("ac", "wifi");
        queryParams.put("channel", "NHSQH5AN");
        queryParams.put("aid", "7");
        queryParams.put("app_name", "joke_essay");
        queryParams.put("version_code", "431");
        queryParams.put("device_platform", "android");
        queryParams.put("ssmix", "a");
        queryParams.put("device_type", "M355");
        queryParams.put("os_api", "21");
        queryParams.put("os_version", "5.0.1");
        queryParams.put("uuid", "863151025042125");
        queryParams.put("openudid", "7b58789a39c2ab62");
        queryParams.put("manifest_version_code", "431");
        return queryParams;
    }

    private Map<String,String> getCommentBaseQueryMap(){
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("iid", "11273887282");
        queryParams.put("device_id", "36812288867");
        queryParams.put("ac", "wifi");
        queryParams.put("channel", "NHSQH5AN");
        queryParams.put("aid", "7");
        queryParams.put("app_name", "joke_essay");
        queryParams.put("version_code", "431");
        queryParams.put("device_platform", "android");
        queryParams.put("ssmix", "a");
        queryParams.put("device_type", "M355");
        queryParams.put("os_api", "21");
        queryParams.put("os_version", "5.0.1");
        queryParams.put("uuid", "863151025042125");
        queryParams.put("openudid", "7b58789a39c2ab62");
        queryParams.put("manifest_version_code", "431");
        return queryParams;
    }

    private Retrofit getRetrofit(String url){
        Retrofit retrofit = null;
        if(mRetrofitMap.containsKey(url)){
            retrofit = mRetrofitMap.get(url);
        }else{
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient())
                    .build();
            mRetrofitMap.put(url,retrofit);
        }
        return retrofit;
    }

    private OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(loggingInterceptor);
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(15, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        OkHttpClient okHttpClient = builder.build();
        return okHttpClient;
    }
}
