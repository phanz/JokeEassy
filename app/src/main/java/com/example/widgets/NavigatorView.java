package com.example.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
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

    private Paint mPaint;
    private LinearLayout mTabLayout;

    private Context mContext;

    private static final int TAB_NUM_DEFAULT = 6;
    private int mTabCount;
    private int mScreenWidth;
    private int tabWidth;
    private int tabHeight;

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

        mCursorOffsetX = 0;
        mCursorOffsetY = 0;
        mCursorWidth = tabWidth;
        mCursorHeight = tabHeight;
    }

    public void setTabList(List<String> tabList){
        if(tabList == null || tabList.size() == 0){
            return;
        }

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(tabWidth,tabHeight);
        for(int i = 0; i < tabList.size(); i++){
            String title = tabList.get(i);
            TextView textView = new TextView(mContext);
            textView.setText(title);
            textView.setGravity(Gravity.CENTER);
            //textView.setBackgroundColor( (i % 2 == 1) ? Color.RED : Color.BLUE);
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
        invalidate();
    }

    public void setCursorPosition(int position, float positionOffset){
        mCursorOffsetX = position * tabWidth + (int)(positionOffset * tabWidth);
        invalidate();
    }

    public void setCursorSize(int width,int height){
        mCursorWidth = width;
        mCursorHeight = height;
    }

    public void setOnTabListener(OnTabClickListener onTabListener){
        mOnTabClickListener = onTabListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(mCursorOffsetX,
                mCursorOffsetY,
                mCursorOffsetX + mCursorWidth,
                mCursorOffsetY + mCursorHeight,
                mPaint);
    }

    public interface OnTabClickListener{
        void onTabClick(int index);
    }
}
