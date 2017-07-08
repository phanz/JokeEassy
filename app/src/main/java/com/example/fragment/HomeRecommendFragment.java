package com.example.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.http.HttpDataRepository;
import com.example.jokeeassy.R;
import com.example.model.JsonResponse;
import com.google.gson.Gson;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeRecommendFragment extends Fragment {

    public static final String TAG = "HomeRecommendFragment";

    public Button mTestBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_recommend, container, false);
        mTestBtn = (Button) view.findViewById(R.id.test_btn);
        mTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });
        return view;
    }

}
