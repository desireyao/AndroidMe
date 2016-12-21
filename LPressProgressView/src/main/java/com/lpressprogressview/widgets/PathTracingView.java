package com.lpressprogressview.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.lpressprogressview.utils.ViewUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PathTracingView extends View implements View.OnLongClickListener{
    private final String TAG = "PathTracingView";
    private Path mDst;
    private Path mPath;
    private Path mInnerPath;

    private Paint mPaint;
    private Paint mPaintInner;
    private Paint mPaintBg;    // 绘制背景的paint
    private Paint mPaintText;  // 绘制背景上的文字

    private float mLength;
    private float mAnimValue = 0.25f;
    private float mAnimValueMax = 1.25f;
    private int mPadding = 15;
    private int radius;

    private PathMeasure mPathMeasure;

    private int MODE_OF_DRAW = 0;

    private int mScreenWidth;   // 屏幕的宽
    private int mScreenHeight;  // 屏幕的高

    private int ViewWidth;     // 此view的宽高
    private int ViewHeight;

    public PathTracingView(Context context) {
        super(context);
    }

//    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
          setMeasuredDimension(ViewWidth,ViewHeight);
    }

    public PathTracingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;
        ViewWidth = (int) (mScreenWidth * 0.5f);
        ViewHeight = mScreenWidth / 5;
        Log.e(TAG,"ViewWidth: " + ViewWidth + "ViewHeight: " + ViewHeight);

        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.WHITE);

        mPaintInner = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintInner.setStyle(Paint.Style.STROKE);
        mPaintInner.setStrokeWidth(10);
        mPaintInner.setColor(Color.DKGRAY);

        mPaintBg = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBg.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintBg.setStrokeWidth(10);
        mPaintBg.setColor(Color.RED);

        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setColor(Color.WHITE);
        mPaintText.setTextAlign(Paint.Align.CENTER);
        mPaintText.setTextSize(48);
        mPaintText.setStyle(Paint.Style.FILL);

        mPath = new Path();
        mDst = new Path();
        mInnerPath = new Path();

        radius = (ViewWidth - mPadding - mPadding) / 2;
        RectF rectF = new RectF(mPadding,mPadding,ViewWidth - mPadding,ViewHeight - mPadding);
        mPath.addRoundRect(rectF,radius,radius,Path.Direction.CW);
        mInnerPath.addRoundRect(rectF,radius,radius,Path.Direction.CW);

        mPathMeasure = new PathMeasure();
        mPathMeasure.setPath(mPath, true);
        mLength = mPathMeasure.getLength();
    }

    public PathTracingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    float stop = 0;
    float start =0;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        mDst.reset();
//        mDst.lineTo(0, 0);
//        Log.e(TAG,"MODE_OF_DRAW: " + MODE_OF_DRAW);
        if(MODE_OF_DRAW == 1){
            if(mAnimValue <= 1){
                start = mLength * 0.25f - 10;
                stop = mLength * mAnimValue + 40;
                mPathMeasure.getSegment(start, stop, mDst, true);
            }else if(mAnimValue > 1 && mAnimValue <= 1.25f){
                stop = mLength * (mAnimValue - 1) + 40;
                start = 0;
                mPathMeasure.getSegment(start, stop, mDst, true);
            }
            canvas.drawPath(mInnerPath, mPaintInner);
            canvas.drawPath(mDst, mPaint);
        }else if(MODE_OF_DRAW == 0){
            mDst.reset();
            mDst.lineTo(0, 0);
        }

        int radusBg = (ViewHeight - mPadding*4) / 2;
        canvas.drawRoundRect(new RectF(mPadding*2,mPadding*2,ViewWidth - mPadding*2,ViewHeight - mPadding*2),radusBg,radusBg,mPaintBg);
        int leftText = ViewWidth / 2;
        int heightText = ViewHeight / 2;
        canvas.drawText("长按暂停", leftText,heightText + 24, mPaintText);
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    private long pressTime;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                executeFixedDelay();
                return true;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                executor.shutdown();
                if(mAnimValue < mAnimValueMax){
                    MODE_OF_DRAW = 0;
                    invalidate();
                }
                mAnimValue = 0.25f;
                Log.e(TAG,"ACTION_UP");
                break;
        }
        return super.onTouchEvent(event);
    }

    ScheduledExecutorService executor;
    public void executeFixedDelay() {
        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleWithFixedDelay(
                new Runnable() {
                    @Override
                    public void run() {
                        mAnimValue += 0.01f;
                        if(mAnimValue <= mAnimValueMax){
                            MODE_OF_DRAW = 1;
                            postInvalidate();
                        }
                    }
                }, 0, 8, TimeUnit.MILLISECONDS);
    }
}
