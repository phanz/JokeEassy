package com.example.http;

import android.database.Observable;
import android.util.Log;

import com.example.AppConfig;
import com.example.model.Contributor;
import com.example.model.JsonResponse;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.reactivestreams.Subscriber;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
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
    private JokeService mJokeService;

    private HttpDataRepository(){
        mRetrofitMap = new HashMap<>();
        Retrofit testRetrofit = getRetrofit(AppConfig.GIT_HUB_BASE_URL);
        Retrofit jokeRetrofit = getRetrofit(AppConfig.JOKE_BASE_URL);
        mGitHubService = testRetrofit.create(TestService.class);
        mJokeService = jokeRetrofit.create(JokeService.class);
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

    public void getTales(Observer<JsonResponse> observer) {

        Map<String,String> queryParams = getBaseQueryMap();
        mJokeService.jokeTales(queryParams)
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

    private Map<String,String> getBaseQueryMap(){
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("mpic", "1");
        queryParams.put("essence", "1");
        queryParams.put("content_type", "-102");
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

    private Retrofit getRetrofit(String url){
        Retrofit retrofit = null;
        if(mRetrofitMap.containsKey(url)){
            retrofit = mRetrofitMap.get(url);
        }else{
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    //.client(getOkHttpClient())
                    .build();
            mRetrofitMap.put(url,retrofit);
        }
        return retrofit;
    }

    private OkHttpClient getOkHttpClient() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("okhttp", "OkHttp====Message:" + message);
            }
        });
        loggingInterceptor.setLevel(level);
        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient
                .Builder();
        //OkHttp进行添加拦截器loggingInterceptor
        httpClientBuilder.addInterceptor(loggingInterceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        return null;
                    }
                });
        return httpClientBuilder.build();
    }
}
