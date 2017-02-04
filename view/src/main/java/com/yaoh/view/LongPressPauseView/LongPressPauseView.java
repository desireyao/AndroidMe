package com.yaoh.view.LongPressPauseView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.yaoh.view.R;
import com.yaoh.view.utils.ViewUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LongPressPauseView extends View implements View.OnLongClickListener {
    private final String TAG = "LongPressPauseView";
    private Path mDst;
    private Path mPath;

    private Paint mPaint;
    private Paint mPaintText;

    private PathMeasure mPathMeasure; // 用来截取片段

    private float mLength;
    private float mAnimValue = 0.25f;
    private float mAnimValueMax = 1.25f;
    private int mPadding;             // view内边距
    private int mRadius;              // 椭圆的半径值
    private int mRadiusBg;            // 填充背景椭圆的半径
    private int mStrokeWidth;         // 外层椭圆的线宽

    private int INIT_MODE = 0;
    private int DRAWING_MODE = 1;
    private int CLEAR_MODE = 2;
    private int MODE_OF_DRAW = INIT_MODE;

    private int mScreenWidth;   // 屏幕的宽
    private int mScreenHeight;  // 屏幕的高

    private int mWidth;         // 此view的宽高
    private int mHeight;

    private int mPauseSpeed;                    // 长按暂停的速度
    private int mPauseTextSize;                 // 文字颜色
    private String mPauseText;                  // 文字
    private int mPauseBackgroudColor;           // 背景块的颜色
    private int mPauseProgressColor;            // 前景进度条的颜色
    private int PauseProgressSecondColor;       // 第二进度条的颜色

    private LongClickListener longClickListener;

    public void setLongClickListener(LongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public interface LongClickListener {
        void onLongClick();
    }

    public LongPressPauseView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LongPressPauseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LongPressPauseView, defStyleAttr, 0);
        mPauseSpeed = a.getInt(R.styleable.LongPressPauseView_PauseSpeed, 0);
        mPauseTextSize = a.getDimensionPixelSize(R.styleable.LongPressPauseView_PauseTextSize, 14);
        mPauseText = a.getString(R.styleable.LongPressPauseView_PauseText);
        mPauseBackgroudColor = a.getColor(R.styleable.LongPressPauseView_PauseBackgroudColor, getResources().getColor(R.color.colorPrimary));
        mPauseProgressColor = a.getColor(R.styleable.LongPressPauseView_PauseProgressColor, getResources().getColor(R.color.white_bg));
        PauseProgressSecondColor = a.getColor(R.styleable.LongPressPauseView_PauseProgressSecondColor, getResources().getColor(R.color.gray_text_light));
        a.recycle();

        init();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        }

        initData();
        setMeasuredDimension(mWidth, mHeight);
    }

    private void initData() {
        mRadius = (mHeight - mPadding * 2) / 2;
        mRadiusBg = (mHeight - mPadding * 4) / 2;
        init();
    }

    private void init() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;
        mWidth = (int) (mScreenWidth * 0.5f); // 默认设置此view的宽高值
        mHeight = mScreenWidth / 5;
        mPadding = ViewUtil.dp2px(5);
        mStrokeWidth = ViewUtil.dp2px(3);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText.setAntiAlias(true);
        mPaintText.setStyle(Paint.Style.STROKE);
        mPaintText.setColor(Color.WHITE);
        mPaintText.setTextAlign(Paint.Align.CENTER);
        mPaintText.setTextSize(mPauseTextSize);

        mPath = new Path();
        mDst = new Path();
        RectF rectF = new RectF(mPadding, mPadding, mWidth - mPadding, mHeight - mPadding);
        mPath.addRoundRect(rectF, mRadius, mRadius, Path.Direction.CW);

        mPathMeasure = new PathMeasure();
        mPathMeasure.setPath(mPath, true);
        mLength = mPathMeasure.getLength();
    }

    float stop = 0;
    float start = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        mDst.reset();
        mDst.lineTo(0, 0);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mPauseBackgroudColor);
        RectF bgRect = new RectF(mPadding * 2, mPadding * 2, mWidth - mPadding * 2, mHeight - mPadding * 2);
        canvas.drawRoundRect(bgRect, mRadiusBg, mRadiusBg, mPaint);

        int leftText = mWidth / 2;
        Paint.FontMetricsInt fontMetrics = mPaintText.getFontMetricsInt();
        float baseline = (bgRect.bottom + bgRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        canvas.drawText(mPauseText, leftText, baseline, mPaintText);

        if (MODE_OF_DRAW == DRAWING_MODE) {
            RectF rect = new RectF(mPadding, mPadding, mWidth - mPadding, mHeight - mPadding);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(mStrokeWidth);
            mPaint.setColor(PauseProgressSecondColor);
            canvas.drawRoundRect(rect, mRadius, mRadius, mPaint);

            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(mStrokeWidth);
            mPaint.setColor(mPauseProgressColor);
            if (mAnimValue < 1.f) {
                start = mLength * 0.25f;
                stop = mLength * (mAnimValue);
                mPathMeasure.getSegment(start, stop, mDst, true);
            } else if (mAnimValue >= 1.f && mAnimValue <= 1.25f) {
                mPathMeasure.getSegment(mLength * 0.25f, mLength, mDst, true);
                canvas.drawPath(mDst, mPaint);
                stop = mLength * (mAnimValue - 1);
                start = 0;
                mPathMeasure.getSegment(start, stop, mDst, true);
            }
            canvas.drawPath(mDst, mPaint);
        } else if (MODE_OF_DRAW == CLEAR_MODE) {
            mDst.reset();
            mDst.lineTo(0, 0);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                executeFixedDelay();
                return true;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                executor.shutdown();
                if (mAnimValue < mAnimValueMax) {
                    MODE_OF_DRAW = CLEAR_MODE;
                    invalidate();
                }
                mAnimValue = 0.25f;
                break;
        }
        return super.onTouchEvent(event);
    }

    private ScheduledExecutorService executor;

    /**
     * 以固定频率执行任务
     */
    public void executeFixedDelay() {
        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleWithFixedDelay(
                new Runnable() {
                    @Override
                    public void run() {
                        mAnimValue += 0.002f * mPauseSpeed;
                        if (mAnimValue <= mAnimValueMax) {
                            MODE_OF_DRAW = DRAWING_MODE;
                            postInvalidate();
                        } else {
                            if (longClickListener != null) {
                                handler.sendEmptyMessage(0);
                            }
                            executor.shutdown();
                            MODE_OF_DRAW = CLEAR_MODE;
                            postInvalidate();
                        }
                    }
                }, 0, 5, TimeUnit.MILLISECONDS);
    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            longClickListener.onLongClick();
        }
    };
}
