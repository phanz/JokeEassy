package com.example.jokeeassy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.widgets.ColorTextView;

public class TestActivity extends AppCompatActivity {

    private Button mTestBtn;
    private ColorTextView mColorTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mColorTextView = (ColorTextView) findViewById(R.id.color_text_view);

        mTestBtn = (Button) findViewById(R.id.test_btn);
        mTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mColorTextView.setCursorRect(null,1);
            }
        });
    }
}
