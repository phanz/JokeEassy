package com.example.widgets;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hanzai.peng on 2017/4/13.
 */

public class SimplePaddingDecoration extends RecyclerView.ItemDecoration{

    private int dividerHeight;

    public SimplePaddingDecoration(){
        dividerHeight = 25;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = dividerHeight;
    }
}
