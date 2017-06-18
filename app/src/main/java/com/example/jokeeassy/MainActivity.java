package com.example.jokeeassy;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.widget.TitleBar;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            /*int option = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);*/
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }
        ButterKnife.bind(this);
        TitleBar titleBar = (TitleBar) findViewById(R.id.title);
        titleBar.setOnCustomClicklistener(new TitleBar.OnCommonClickListener() {

            @Override
            public void onCustomLeftClick(View view) {
                Toast.makeText(getApplicationContext(), "left click", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCustomRightClick(View view) {
                Toast.makeText(getApplicationContext(), "right click", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCustomSearchClick(View view) {
                Toast.makeText(getApplicationContext(), "title click", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @OnClick(R.id.click_btn)
    public void onClick(View view) {

        Toast.makeText(this, "测试按钮", Toast.LENGTH_SHORT).show();
    }
}
