package com.example.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by phanz on 2017/7/1.
 */

public class NavigatorView extends RelativeLayout {

    private Paint mPaint;
    private LinearLayout mTabLayout;
    private Context mContext;

    private ImageView mCursorImage;

    private int mCursorWidth;
    private int mCursorHeight;
    public NavigatorView(Context context) {
        super(context);
        init(context);
    }

    public NavigatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context){
        setWillNotDraw(false);//ViewGroup本身不含内容，默认为透明的，不触发onDraw操作，需要手动开启
        mContext = context;
        mPaint = new Paint();
        mPaint.setColor(Color.YELLOW);
        LinearLayout.LayoutParams tabLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mTabLayout = new LinearLayout(context);
        mTabLayout.setLayoutParams(tabLayoutParams);
        addView(mTabLayout);

        mCursorHeight = 50;
        mCursorWidth = 50;
    }

    public void setTabList(List<TextView> tabList){
        if(tabList == null || tabList.size() == 0){
            return;
        }

        for(TextView textView : tabList){
            mTabLayout.addView(textView);
        }
    }

    public void setCursorSize(int width,int height){
        mCursorWidth = width;
        mCursorHeight = height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0,0,mCursorWidth,mCursorHeight,mPaint);
    }
}
