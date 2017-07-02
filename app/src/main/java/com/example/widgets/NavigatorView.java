package com.example.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jokeeassy.R;
import com.example.utils.DisplayUtils;

import java.util.List;

/**
 * Created by phanz on 2017/7/1.
 */

public class NavigatorView extends RelativeLayout {

    public static final String TAG = "NavigatorView";

    private Paint mPaint;
    private LinearLayout mTabLayout;

    private Context mContext;

    private int mScreenWidth;
    private static final int TAB_NUM_DEFAULT = 6;
    private int mTabCount;
    private int tabWidth;
    private int tabHeight;

    private int mCursorIndex;
    private static final float mRadius = 16;
    private float mCursorScale = 0.8f;

    private int mCursorOffsetX;
    private int mCursorOffsetY;
    private int mCursorWidth;
    private int mCursorHeight;

    public OnTabClickListener mOnTabClickListener;
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
        mPaint.setColor(context.getResources().getColor(R.color.tab_cursor_bg));
        LinearLayout.LayoutParams tabLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mTabLayout = new LinearLayout(context);
        mTabLayout.setLayoutParams(tabLayoutParams);
        addView(mTabLayout);

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mScreenWidth = windowManager.getDefaultDisplay().getWidth();
        setTabCount(TAB_NUM_DEFAULT);

    }

    public void setTabList(List<String> tabList){
        if(tabList == null || tabList.size() == 0){
            return;
        }

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(tabWidth,tabHeight);
        for(int i = 0; i < tabList.size(); i++){
            String title = tabList.get(i);
            /*TextView textView = new TextView(mContext);
            textView.setText(title);
            textView.setGravity(Gravity.CENTER);*/
            //textView.setBackgroundColor( (i % 2 == 1) ? Color.RED : Color.BLUE);

            ColorTextView textView = new ColorTextView(mContext,null);
            textView.setText(title);
            textView.setLayoutParams(params);
            mTabLayout.addView(textView);

            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mOnTabClickListener != null){
                        int index = ((ViewGroup)view.getParent()).indexOfChild(view);
                        mOnTabClickListener.onTabClick(index);
                    }
                }
            });

        }
    }

    public void setTabCount(int tabCount){
        mTabCount = tabCount;
        tabWidth = mScreenWidth / mTabCount;
        tabHeight = DisplayUtils.dp2px(mContext,40);

        mCursorOffsetX = (int)(tabWidth * (1 - mCursorScale)/2);
        mCursorOffsetY = (int)(tabHeight * (1 - mCursorScale)/2);
        mCursorWidth = (int)(mCursorScale * tabWidth);
        mCursorHeight = (int)(mCursorScale * tabHeight);

        invalidate();
    }

    public void setCursorPosition(int position, float positionOffset){
        mCursorOffsetX = (int)(tabWidth * (1 - mCursorScale)/2) + position * tabWidth + (int)(positionOffset * tabWidth);
        mCursorIndex = position;

        Rect rect = new Rect(0,0,0,0);
        ColorTextView textView = null;

        textView = (ColorTextView) mTabLayout.getChildAt(position);
        int tempX = (int)(tabWidth * (1 - mCursorScale)/2) + (int)(positionOffset * tabWidth);
        rect.left = tempX;
        rect.right = tempX + mCursorWidth;
        textView.setCursorRect(rect);

        if(position + 1 < mTabLayout.getChildCount()){
            textView = (ColorTextView) mTabLayout.getChildAt(position + 1);
            rect.left = 0;
            rect.right = tempX;
            textView.setCursorRect(rect);
        }

        invalidate();
    }

    public void setOnTabListener(OnTabClickListener onTabListener){
        mOnTabClickListener = onTabListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF(mCursorOffsetX,
                mCursorOffsetY,
                mCursorOffsetX + mCursorWidth,
                mCursorOffsetY + mCursorHeight);

        String log = String.format("Left:%d\tTop:%d\tRight:%d\tBottom:%d",
                mCursorOffsetX,mCursorOffsetY,mCursorOffsetX + mCursorWidth,mCursorOffsetY + mCursorHeight);
        //Log.d(TAG,log);
        canvas.drawRoundRect(rectF,mRadius,mRadius, mPaint);
    }

    public interface OnTabClickListener{
        void onTabClick(int index);
    }
}
