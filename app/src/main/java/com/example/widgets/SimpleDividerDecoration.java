package com.example.widgets;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hanzai.peng on 2017/4/13.
 */

public class SimpleDividerDecoration extends RecyclerView.ItemDecoration{

    private static final int DIVIDER_HEIGHT = 1;

    private int dividerHeight;
    private Paint dividerPaint;

    public SimpleDividerDecoration(){
        dividerHeight = DIVIDER_HEIGHT;
        dividerPaint = new Paint();
        dividerPaint.setColor(Color.RED);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = dividerHeight;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for(int i = 0; i< childCount - 1; i++){
            View view = parent.getChildAt(i);
            int top = view.getBottom();
            int bottom = top + dividerHeight;
            c.drawRect(left,top,right,bottom,dividerPaint);
        }
    }
}
