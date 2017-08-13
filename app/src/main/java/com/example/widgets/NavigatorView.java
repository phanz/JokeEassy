package com.example.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.jokeeassy.R;

import java.util.ArrayList;
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
    private int mTabWidth;
    private int mTabHeight;
    private int mTabCount;
    private float mCursorScale;
    private float mCursorRadius;
    private int mTextSize;
    private int mTextOriginColor;
    private int mTextChangeColor;
    private OnTabClickListener mOnTabClickListener;

    private RectF mCursorRectF;
    private RectF mCursorScaleRect;

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
        mCursorScaleRect = new RectF();

        mContext = context;
        mPaint = new Paint();
        mPaint.setColor(context.getResources().getColor(R.color.tab_cursor_bg));

        LinearLayout.LayoutParams tabLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mTabLayout = new LinearLayout(context);
        mTabLayout.setLayoutParams(tabLayoutParams);
        addView(mTabLayout);

        mGestureDetector = new GestureDetector(mContext,new SimpleGestureListener());
        setLongClickable(true);
        setOnTouchListener(this);
    }

    private void setTabList(List<String> tabList){
        if(tabList == null || tabList.size() == 0){
            return;
        }

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(mTabWidth, mTabHeight);
        for(int i = 0; i < tabList.size(); i++){
            ColorTextView textView = new ColorTextView(mContext,null);
            textView.setText(tabList.get(i));
            textView.setLayoutParams(params);
            textView.setTextSize(mTextSize);
            textView.setTextOriginColor(mTextOriginColor);
            textView.setTextChangeColor(mTextChangeColor);
            textView.setGravity(Gravity.CENTER);
            mTabLayout.addView(textView);

            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mOnTabClickListener != null){
                        int index = ((ViewGroup)view.getParent()).indexOfChild(view);
                        setCurrentPosition(index);
                        mOnTabClickListener.onTabClick(index);
                    }
                }
            });

        }
        setCurrentPosition(1);
    }

    private float preTotalPosition = 0;
    public void setCursorPosition(int position, float positionOffset){
        float currentTotalPosition = position + positionOffset;

        if(currentTotalPosition == preTotalPosition) return;

        boolean isScrollRight = position + positionOffset > preTotalPosition;

        if(isScrollRight && (mCursorRectF.right >= mScreenWidth)){//向右将要滑出屏幕
            int layoutOffset = (int)((currentTotalPosition + 1 - mTabCount) * mTabWidth);
            mTabLayout.scrollTo(layoutOffset,0);

        }else if(!isScrollRight && mCursorRectF.left == 0){//向左将要滑出屏幕
            float layoutOffset = currentTotalPosition * mTabWidth;
            mTabLayout.scrollTo((int)layoutOffset,0);

        }else{
            float cursorRectX = currentTotalPosition * mTabWidth - mTabLayout.getScrollX();
            mCursorRectF.left = cursorRectX ;
            mCursorRectF.right = mCursorRectF.left + mTabWidth;
        }

        preTotalPosition = position + positionOffset;

        ColorTextView textView = (ColorTextView) mTabLayout.getChildAt(position);
        textView.setPositionOffset(positionOffset,1,mCursorScale);

        if(position + 1 < mTabLayout.getChildCount()){
            textView = (ColorTextView) mTabLayout.getChildAt(position + 1);
            textView.setPositionOffset(0,positionOffset,mCursorScale);
        }

        invalidate();
    }

    public void setCurrentPosition(int position){
        mCursorRectF.left = 0;
        mCursorRectF.right = 0;
        ColorTextView colorTextView = null;
        for(int i = 0; i < mTabLayout.getChildCount(); i++){
            colorTextView = (ColorTextView)mTabLayout.getChildAt(i);
            colorTextView.setPositionOffset(0,0,mCursorScale);
        }
        colorTextView = (ColorTextView)mTabLayout.getChildAt(position);
        int scrollX = mTabLayout.getScrollX();
        mCursorRectF.left = mTabWidth * position - scrollX;
        mCursorRectF.right = mCursorRectF.left + mTabWidth;
        colorTextView.setPositionOffset(0,1,mCursorScale);
        invalidate();
    }

    private void addRightAction(int rightResId){
        if(rightResId == 0){
            return;
        }
        ImageView rightBarImage = new ImageView(mContext);
        rightBarImage.setBackgroundResource(rightResId);
        RelativeLayout.LayoutParams rightBarParams
                = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, mTabHeight);
        rightBarParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
        addView(rightBarImage,rightBarParams);
        rightBarImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int lastIndex = mTabLayout.getChildCount() - 1;
                int tabTotalWidth = mTabLayout.getChildAt(lastIndex).getRight();
                int scrollX = mTabLayout.getScrollX();
                int offset = tabTotalWidth - scrollX - mScreenWidth;
                mTabLayout.scrollBy(offset,0);
                mCursorRectF.left += -offset;
                mCursorRectF.right = mCursorRectF.left + mTabWidth;
            }
        });
    }

    public void apply(NavigatorParams params){
        mContext = params.mContext;
        mScreenWidth = params.mScreenWidth;
        mTabCount = params.mTabCount;
        mTabWidth = params.mTabWidth;
        mTabHeight = dp2px(params.mContext,params.mTabHeight);
        mCursorScale = params.mCursorScale;
        mCursorRadius = params.mCursorRadius;
        mTextSize = params.mTextSize;
        mTextOriginColor = params.mTextColor;
        mTextChangeColor = params.mTextChangeColor;
        setTabList(params.mTabList);
        mOnTabClickListener = params.mOnTabClickListener;
        addRightAction(params.mRightResId);
        mCursorRectF.right = mTabWidth;
        mCursorRectF.bottom = mTabHeight;

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        rectConvert(mCursorRectF, mCursorScaleRect,mCursorScale);
        canvas.drawRoundRect(mCursorScaleRect, mCursorRadius, mCursorRadius, mPaint);
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

    public static int dp2px(Context context,float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context,float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    public static void rectConvert(RectF src,RectF dest,float scale){
        int width = (int)(src.right - src.left);
        int height = (int)(src.bottom - src.top);
        dest.left = (int)src.left + (int)(width * (1 - scale) / 2);
        dest.right = dest.left + (int)(width * scale);

        dest.top = (int)src.top + (int)(height * (1 - scale) / 2);
        dest.bottom = dest.top + (int)(height * scale);
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
                mCursorRectF.right = mCursorRectF.left + mTabWidth;

            }else if(distanceX > 0 && (tabTotalWidth - scrollX) > mScreenWidth){//向左拖动
                int offset = Math.min((int)distanceX,tabTotalWidth - scrollX - mScreenWidth);
                mTabLayout.scrollBy(offset,0);
                mCursorRectF.left += -offset;
                mCursorRectF.right = mCursorRectF.left + mTabWidth;
            }
            invalidate();

            return true;
        }
    }

    public static class NavigatorViewBuilder{

        private NavigatorParams params;

        public NavigatorViewBuilder(Context context){
            params = new NavigatorParams(context);
        }

        public NavigatorViewBuilder setTabCount(int tabCount){
            params.mTabCount = tabCount;
            WindowManager windowManager = (WindowManager) params.mContext.getSystemService(Context.WINDOW_SERVICE);
            int screenWidth = windowManager.getDefaultDisplay().getWidth();
            params.mScreenWidth = screenWidth;
            params.mTabWidth = screenWidth / tabCount;
            return this;
        }

        public NavigatorViewBuilder setTabWidth(int tabWidth){
            params.mTabWidth = tabWidth;
            return this;
        }

        public NavigatorViewBuilder setTabHeight(int tabHeight){
            params.mTabHeight = tabHeight;
            return this;
        }

        public NavigatorViewBuilder setCursorScale(float cursorScale){
            params.mCursorScale = cursorScale;
            return this;
        }

        public NavigatorViewBuilder setCursorRadius(int cursorRadius){
            params.mCursorRadius = cursorRadius;
            return this;
        }

        public NavigatorViewBuilder setTextSize(int textSize){
            params.mTextSize = textSize;
            return this;
        }

        public NavigatorViewBuilder setTextOriginColor(int textColor){
            params.mTextColor = textColor;
            return this;
        }

        public NavigatorViewBuilder setTextChangeColor(int textChangeColor){
            params.mTextChangeColor = textChangeColor;
            return this;
        }

        public NavigatorViewBuilder setTabList(List<String> tabList){
            params.mTabList = tabList;
            return this;
        }

        public NavigatorViewBuilder setOnTabListener(OnTabClickListener onTabListener){
            params.mOnTabClickListener = onTabListener;
            return this;
        }

        public NavigatorViewBuilder setRightImage(int resId){
            params.mRightResId = resId;
            return this;
        }

        public void apply(NavigatorView navigatorView){
            navigatorView.apply(params);
        }

        public NavigatorView create(){
            NavigatorView navigatorView = new NavigatorView(params.mContext);
            navigatorView.apply(params);
            return navigatorView;
        }

    }

    private static class NavigatorParams{
        Context mContext;
        int mScreenWidth;
        int mTabCount = 5;
        int mTabWidth = 60;
        int mTabHeight = 45;//dp
        float mCursorScale = 0.8f;
        float mCursorRadius = 16;
        int mTextSize = 16;//sp
        int mTextColor = 0xff000000;
        int mTextChangeColor = 0xffffffff;
        OnTabClickListener mOnTabClickListener;
        List<String> mTabList = new ArrayList<>();
        int mRightResId = 0;

        public NavigatorParams(Context context){
            mContext = context;
        }
    }
}
