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

    private static final float mRadius = 16;
    private float mCursorScale = 0.8f;

    private RectF mCursorRectF;

    private boolean mClickSwitching;
    private int mClickIndex;

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
        mCursorRectF = new RectF(0,0,0,0);
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

        mClickSwitching = false;
        mClickIndex = 0;


    }

    public void setTabList(List<String> tabList){
        if(tabList == null || tabList.size() == 0){
            return;
        }

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(tabWidth,tabHeight);
        for(int i = 0; i < tabList.size(); i++){
            ColorTextView textView = new ColorTextView(mContext,null);
            textView.setText(tabList.get(i));
            textView.setLayoutParams(params);
            mTabLayout.addView(textView);

            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mOnTabClickListener != null){
                        int index = ((ViewGroup)view.getParent()).indexOfChild(view);
                        mClickSwitching = true;
                        mClickIndex = index;
                        skipToPosition(index);
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

        mCursorRectF.left = tabWidth * (1 - mCursorScale)/2;
        mCursorRectF.top = tabHeight * (1 - mCursorScale)/2;
        mCursorRectF.right = mCursorRectF.left + mCursorScale * tabWidth;
        mCursorRectF.bottom = mCursorRectF.top + mCursorScale * tabHeight;

        invalidate();
    }

    public void setCursorPosition(int position, float positionOffset){
        if(mClickSwitching){
            if(position == mClickIndex && positionOffset == 0) {
                mClickSwitching = false;
            }
            return;
        }
        float totalOffsetX = (position + positionOffset) * tabWidth;

        mCursorRectF.left = tabWidth * (1 - mCursorScale)/2 + totalOffsetX ;
        mCursorRectF.right = mCursorRectF.left + tabWidth * mCursorScale;

         ColorTextView textView = (ColorTextView) mTabLayout.getChildAt(position);
        textView.setCursorRect(mCursorRectF);

        if(position + 1 < mTabLayout.getChildCount()){
            textView = (ColorTextView) mTabLayout.getChildAt(position + 1);
            textView.setCursorRect(mCursorRectF);
        }

        invalidate();
    }

    public void skipToPosition(int position){
        int count = mTabLayout.getChildCount();
        mCursorRectF.left = 0;
        mCursorRectF.right = 0;
        ColorTextView colorTextView = null;
        for(int i = 0; i < count; i++){
            colorTextView = (ColorTextView)mTabLayout.getChildAt(i);
            colorTextView.setCursorRect(mCursorRectF);
        }
        colorTextView = (ColorTextView)mTabLayout.getChildAt(position);
        mCursorRectF.left = tabWidth * position + tabWidth * (1 - mCursorScale)/2 ;
        mCursorRectF.right = mCursorRectF.left + tabWidth * mCursorScale;
        colorTextView.setCursorRect(mCursorRectF);
        invalidate();
    }

    public void setOnTabListener(OnTabClickListener onTabListener){
        mOnTabClickListener = onTabListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRoundRect(mCursorRectF,mRadius,mRadius, mPaint);
    }

    public interface OnTabClickListener{
        void onTabClick(int index);
    }
}
