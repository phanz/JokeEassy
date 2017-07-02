package com.example.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.jokeeassy.R;
import com.example.utils.DisplayUtils;


public class ColorTextView extends View {

    public static final String TAG = "ColorTextView";

    private Context mContext;
    private Paint mPaint;

    private String mText;

    private int mTextSize;
    private int mTextWidth;
    private int mTextHeight;

    private int mTextStartX;
    private int mTextStartY;

    private Rect mOriginRect;
    private Rect mCursorRect;
    private int mTextOriginColor = 0xff000000;
    private int mTextChangeColor = 0xffffffff;

    public ColorTextView(Context context) {
        super(context);
        mText = "默认文字";
        mTextSize = DisplayUtils.sp2px(mContext,18);
        init(context);
    }

    public ColorTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.ColorTrackView);
        mText = ta.getString(R.styleable.ColorTrackView_text);
        mTextSize = ta.getDimensionPixelSize(R.styleable.ColorTrackView_text_size, DisplayUtils.sp2px(context,18));
        ta.recycle();
        if(mText == null){
            mText = "默认文字";
        }

        init(context);
    }

    private void init(Context context){
        mContext = context;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(mTextSize);
        mOriginRect = new Rect();
        mCursorRect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureText();

        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);

        mTextStartX = getMeasuredWidth() / 2 - mTextWidth / 2;
        mTextStartY = getMeasuredHeight() / 2 - mTextHeight / 2;
    }

    private void measureText() {
        mTextWidth = (int) mPaint.measureText(mText);
        FontMetrics fm = mPaint.getFontMetrics();
        mTextHeight = (int) Math.ceil(fm.descent - fm.top);

        mPaint.getTextBounds(mText, 0, mText.length(), mOriginRect);
        mTextHeight = mOriginRect.height();
    }

    private int measureHeight(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int val = MeasureSpec.getSize(measureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = val;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = mOriginRect.height();
                result += getPaddingTop() + getPaddingBottom();
                break;
        }
        result = mode == MeasureSpec.AT_MOST ? Math.min(result, val) : result;
        return result;
    }

    private int measureWidth(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int val = MeasureSpec.getSize(measureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = val;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = mTextWidth;
                result += getPaddingLeft() + getPaddingRight();
                break;
        }
        result = mode == MeasureSpec.AT_MOST ? Math.min(result, val) : result;
        return result;
    }

    public void setCursorRect(RectF rect){
        mCursorRect.left = (int)(rect.left - getLeft());
        mCursorRect.right = (int)(rect.right - getLeft());
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTextRect(canvas, mCursorRect);
    }

    private void drawTextRect(Canvas canvas,Rect cursorRect){
        mPaint.setColor(Color.RED);

        canvas.save(Canvas.CLIP_SAVE_FLAG);

        int textLeft = mTextStartX;
        int textRight = mTextStartX + mTextWidth;

        if(cursorRect.right <= textLeft || cursorRect.left >= textRight){//不相交
            drawText_h(canvas,mTextOriginColor,textLeft,textRight);

        }else if(cursorRect.right <= textRight){//左交
            drawText_h(canvas,mTextChangeColor,textLeft,cursorRect.right);
            drawText_h(canvas,mTextOriginColor,cursorRect.right,textRight);

        }else if(cursorRect.left > textLeft && cursorRect.right < textRight){//内含
            drawText_h(canvas,mTextOriginColor,textLeft,cursorRect.left);
            drawText_h(canvas,mTextChangeColor,cursorRect.left,cursorRect.right);
            drawText_h(canvas,mTextOriginColor,cursorRect.right,textRight);

        }else if(cursorRect.left < textLeft && cursorRect.right > textRight){//外围
            drawText_h(canvas,mTextChangeColor,textLeft,textRight);

        }else if(cursorRect.left <= textRight){ //右交
            drawText_h(canvas,mTextOriginColor,textLeft,cursorRect.left);
            drawText_h(canvas,mTextChangeColor,cursorRect.left,textRight);
        }else{
            Log.d(TAG,"状态判定有遗漏");
        }

        canvas.restore();
    }

    private void drawText_h(Canvas canvas, int color, int startX, int endX) {
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.STROKE);
        //canvas.drawRect(startX, 0, endX, getMeasuredHeight(), mPaint);
        canvas.save(Canvas.CLIP_SAVE_FLAG);
        canvas.clipRect(startX, 0, endX, getMeasuredHeight());// left, top,
        // right, bottom
        canvas.drawText(mText, mTextStartX,
                getMeasuredHeight() / 2 - ((mPaint.descent() + mPaint.ascent()) / 2), mPaint);
        canvas.restore();
    }

    public void setText(String text) {
        this.mText = text;
        requestLayout();
        invalidate();
    }

    public int getTextWidth(){
        return mTextWidth;
    }

}
