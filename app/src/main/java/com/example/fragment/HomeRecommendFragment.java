package com.example.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.http.HttpDataRepository;
import com.example.jokeeassy.R;
import com.example.model.JsonResponse;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


public class HomeRecommendFragment extends Fragment {

    public static final String TAG = "HomeRecommendFragment";

    public Button mTestBtn;
    private ImageView mTestImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_recommend, container, false);
        mTestImage = (ImageView) view.findViewById(R.id.test_image);
        mTestBtn = (Button) view.findViewById(R.id.test_btn);
        mTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImage(mTestImage);
                /*loadContent();*/
            }
        });
        return view;
    }

    public void loadContent(){
        HttpDataRepository.getInstance().getTales(new Observer<JsonResponse>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull JsonResponse jsonResponse) {
                Log.d(TAG, "onNext: ");
                Gson gson = new Gson();
                String json = gson.toJson(jsonResponse);
                Log.d(TAG, "onNext: " + json);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }

    public void loadImage(final ImageView imageView){
        String url = "http://p3.pstatp.com/medium/2c6a000553977384ef19";
        ImageLoader.getInstance().loadImage(url, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                Log.d(TAG, "onLoadingStarted: ");
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                Log.d(TAG, "onLoadingFailed: ");
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                Log.d(TAG, "onLoadingComplete: ");
                imageView.setImageBitmap(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                Log.d(TAG, "onLoadingCancelled: ");
            }
        });
    }

}
