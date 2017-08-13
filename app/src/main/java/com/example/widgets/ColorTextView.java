package com.example.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;


public class ColorTextView extends TextView {

    public static final String TAG = "ColorTextView";

    private Paint mPaint;

    private int mTextStartX;
    private int mTextStartY;

    private Rect mCursorRect;
    private int mTextOriginColor;
    private int mTextChangeColor;

    public ColorTextView(Context context) {
        super(context);
        init();
    }

    public ColorTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextOriginColor = 0xff000000;
        mTextChangeColor = 0xffffffff;
        mCursorRect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureText();

        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }

    private void measureText() {
        mPaint.setTextSize(getTextSize());

        String text = getText().toString();
        int textWidth = (int) mPaint.measureText(text);
        mTextStartX = getMeasuredWidth() / 2 - textWidth / 2;
        mTextStartY = getMeasuredHeight() / 2 - (int)((mPaint.descent() + mPaint.ascent()) / 2);
    }

    public void setPositionOffset(float positionOffsetStart,float positionOffsetEnd,float scale){
        float scaleOffsetWidth = (1 - scale) / 2 * getWidth();
        float scaleOffsetHeight = (1 - scale) / 2 * getHeight();
        mCursorRect.left = (int)(positionOffsetStart * getWidth() + scaleOffsetWidth);
        mCursorRect.right = (int)(positionOffsetEnd * getWidth() - scaleOffsetWidth);

        mCursorRect.top -= (int)(getTop() + scaleOffsetHeight);
        mCursorRect.bottom -= (int)(getTop() - scaleOffsetHeight);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        drawTextRect(canvas, mCursorRect);
    }

    private void drawTextRect(Canvas canvas,Rect cursorRect){

        int textLeft = mTextStartX;
        int textRight = mTextStartX + (int)mPaint.measureText(getText().toString());

        if(cursorRect.right <= textLeft || cursorRect.left >= textRight){//不相交
            drawColorText(canvas, mTextOriginColor,textLeft,textRight);

        }else if(cursorRect.right <= textRight){//左交
            drawColorText(canvas,mTextChangeColor,textLeft,cursorRect.right);
            drawColorText(canvas, mTextOriginColor,cursorRect.right,textRight);

        }else if(cursorRect.left > textLeft && cursorRect.right < textRight){//内含
            drawColorText(canvas, mTextOriginColor,textLeft,cursorRect.left);
            drawColorText(canvas,mTextChangeColor,cursorRect.left,cursorRect.right);
            drawColorText(canvas, mTextOriginColor,cursorRect.right,textRight);

        }else if(cursorRect.left < textLeft && cursorRect.right > textRight){//外围
            drawColorText(canvas,mTextChangeColor,textLeft,textRight);

        }else if(cursorRect.left <= textRight){ //右交
            drawColorText(canvas, mTextOriginColor,textLeft,cursorRect.left);
            drawColorText(canvas,mTextChangeColor,cursorRect.left,textRight);
        }else{
            Log.d(TAG,"状态判定有遗漏");
        }

    }

    private void drawColorText(Canvas canvas, int color, int startX, int endX) {
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.save(Canvas.CLIP_SAVE_FLAG);
        canvas.clipRect(startX, 0, endX, getMeasuredHeight());// left, top,
        canvas.drawText(getText().toString(), mTextStartX,mTextStartY, mPaint);
        canvas.restore();
    }

    public void setTextOriginColor(int textOriginColor){
        mTextOriginColor = textOriginColor;
    }

    public void setTextChangeColor(int textChangeColor){
        mTextChangeColor = textChangeColor;
    }

}
