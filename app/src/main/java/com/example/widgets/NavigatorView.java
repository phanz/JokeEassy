package com.example.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.jokeeassy.R;
import com.example.utils.DisplayUtils;
import com.example.utils.RectUtils;

import java.util.List;

/**
 * Created by phanz on 2017/7/1.
 */

public class NavigatorView extends RelativeLayout implements View.OnTouchListener{

    public static final String TAG = "NavigatorView";

    private Paint mPaint;
    private LinearLayout mTabLayout;

    private Context mContext;

    private int mScreenWidth;
    private static final int TAB_NUM_DEFAULT = 5;
    private int mTabCount;
    private int tabWidth;
    private int tabHeight;

    private static final float mRadius = 16;
    private float mCursorScale = 0.8f;

    private RectF mCursorRectF;

    private boolean mClickSwitching;
    private int mClickIndex;

    public OnTabClickListener mOnTabClickListener;

    private GestureDetector mGestureDetector;

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
        tabHeight = DisplayUtils.dp2px(mContext,45);
        mPaint = new Paint();
        mPaint.setColor(context.getResources().getColor(R.color.tab_cursor_bg));
        LinearLayout.LayoutParams tabLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mTabLayout = new LinearLayout(context);
        mTabLayout.setLayoutParams(tabLayoutParams);
        addView(mTabLayout);

        ImageView rightBarImage = new ImageView(mContext);
        rightBarImage.setBackgroundResource(R.drawable.ic_topbar_arrow);
        RelativeLayout.LayoutParams rightBarParams
                = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,tabHeight);
        rightBarParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
        addView(rightBarImage,rightBarParams);

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mScreenWidth = windowManager.getDefaultDisplay().getWidth();
        setTabCount(TAB_NUM_DEFAULT);

        mClickSwitching = false;
        mClickIndex = 0;

        mGestureDetector = new GestureDetector(mContext,new SimpleGestureListener());
        setLongClickable(true);
        setOnTouchListener(this);
        rightBarImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"点击",Toast.LENGTH_SHORT).show();
                int lastIndex = mTabLayout.getChildCount() - 1;
                int tabTotalWidth = mTabLayout.getChildAt(lastIndex).getRight();
                int scrollX = mTabLayout.getScrollX();
                int offset = tabTotalWidth - scrollX - mScreenWidth;
                mTabLayout.scrollBy(offset,0);
                mCursorRectF.left += -offset;
                mCursorRectF.right = mCursorRectF.left + tabWidth;
            }
        });

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
            textView.setTextSize(DisplayUtils.sp2px(mContext,16));
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

        mCursorRectF.left = 0;
        mCursorRectF.top = 0;
        mCursorRectF.right = tabWidth;
        mCursorRectF.bottom = tabHeight;

        invalidate();
    }

    private float preTotalPosition = 0;
    public void setCursorPosition(int position, float positionOffset){
        if(position + positionOffset == preTotalPosition) return;

        if(mClickSwitching){
            if(position == mClickIndex && positionOffset == 0) {
                mClickSwitching = false;
            }
            return;
        }

        boolean isScrollRight = position + positionOffset > preTotalPosition;

        if(isScrollRight && (mCursorRectF.right >= mScreenWidth)){//向右将要滑出屏幕
            int layoutOffset = (int)((position + positionOffset + 1 - mTabCount) * tabWidth);
            mTabLayout.scrollTo(layoutOffset,0);

        }else if(!isScrollRight && mCursorRectF.left == 0){//向左将要滑出屏幕
            float layoutOffset = (position + positionOffset) * tabWidth;
            mTabLayout.scrollTo((int)layoutOffset,0);

        }else{
            float cursorRectX = (position + positionOffset) * tabWidth - mTabLayout.getScrollX();
            mCursorRectF.left = cursorRectX ;
            mCursorRectF.right = mCursorRectF.left + tabWidth;
        }

        preTotalPosition = position + positionOffset;


        ColorTextView textView = (ColorTextView) mTabLayout.getChildAt(position);
        textView.setCursorRect(mCursorRectF,mCursorScale);

        if(position + 1 < mTabLayout.getChildCount()){
            textView = (ColorTextView) mTabLayout.getChildAt(position + 1);
            textView.setCursorRect(mCursorRectF,mCursorScale);
        }

        invalidate();
    }

    public void skipToPosition(int position){
        mCursorRectF.left = 0;
        mCursorRectF.right = 0;
        ColorTextView colorTextView = null;
        for(int i = 0; i < mTabLayout.getChildCount(); i++){
            colorTextView = (ColorTextView)mTabLayout.getChildAt(i);
            colorTextView.setCursorRect(mCursorRectF,mCursorScale);
        }
        colorTextView = (ColorTextView)mTabLayout.getChildAt(position);
        int scrollX = mTabLayout.getScrollX();
        mCursorRectF.left = tabWidth * position - scrollX;
        mCursorRectF.right = mCursorRectF.left + tabWidth;
        colorTextView.setCursorRect(mCursorRectF,mCursorScale);
        invalidate();
    }

    public void setOnTabListener(OnTabClickListener onTabListener){
        mOnTabClickListener = onTabListener;
    }

    private RectF mCursorDrawRect = new RectF();
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectUtils.rectConvert(mCursorRectF, mCursorDrawRect,mCursorScale);
        canvas.drawRoundRect(mCursorDrawRect,mRadius,mRadius, mPaint);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return mGestureDetector.onTouchEvent(motionEvent);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mGestureDetector.onTouchEvent(ev);
    }

    public interface OnTabClickListener{
        void onTabClick(int index);
    }

    private class SimpleGestureListener extends GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onDown(MotionEvent e) {
            return super.onDown(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d(TAG, "onFling: ");
            //return false;
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            int lastIndex = mTabLayout.getChildCount() - 1;
            int tabTotalWidth = mTabLayout.getChildAt(lastIndex).getRight();
            int scrollX = mTabLayout.getScrollX();
            if(distanceX < 0 && mTabLayout.getScrollX() > 0) {//向右拖动
                int offset = Math.min(-(int)distanceX,mTabLayout.getScrollX());
                mTabLayout.scrollBy(-offset,0);
                mCursorRectF.left += offset;
                mCursorRectF.right = mCursorRectF.left + tabWidth;

            }else if(distanceX > 0 && (tabTotalWidth - scrollX) > mScreenWidth){//向左拖动
                int offset = Math.min((int)distanceX,tabTotalWidth - scrollX - mScreenWidth);
                mTabLayout.scrollBy(offset,0);
                mCursorRectF.left += -offset;
                mCursorRectF.right = mCursorRectF.left + tabWidth;
            }
            invalidate();

            return true;
        }
    }
}
