package com.example.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jokeeassy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeSubscribeFragment extends Fragment {

    public static final String  TAG = "HomeSubscribeFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home_subscribe, container, false);
    }

}
