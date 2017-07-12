package com.yaoh.view.arc;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;

import com.yaoh.view.R;
import com.yaoh.view.utils.ViewUtil;

/**
 * Created by Administrator on 2017/07/10 0010.
 */

public class SportProgressView extends View {
    private static final String TAG = "SportProgressView";
    private int[] colors = new int[]{Color.parseColor("#00CDCD")
            , Color.parseColor("#00CDCD"),Color.parseColor("#00CDCD")
            , Color.parseColor("#FF4500"),Color.parseColor("#FF4500")};

    private Paint mPaint;
    private Paint mPaintProgress;

    private int mWidth;
    private int mHeight;

    public SportProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(45);
        mPaint.setStyle(Paint.Style.STROKE);

        mPaintProgress = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintProgress.setStrokeWidth(45);
        mPaintProgress.setStyle(Paint.Style.STROKE);
        mPaintProgress.setStrokeCap(Paint.Cap.ROUND);
        mPaintProgress.setColor(getResources().getColor(R.color.colorPrimary));

        startAnimation();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthSpecMode == MeasureSpec.EXACTLY) {
            mWidth = widthSpecSize;
        } else {
            int width = ViewUtil.dp2px(120);
            if (widthSpecMode == MeasureSpec.AT_MOST) ;
            {
                mWidth = Math.min(width, widthSpecSize);
            }
        }

        if (heightSpecMode == MeasureSpec.EXACTLY)// match_parent , accurate
        {
            mHeight = heightSpecSize;
        } else {
            int height = ViewUtil.dp2px(120);
            if (heightSpecMode == MeasureSpec.AT_MOST)// wrap_content
            {
                mHeight = Math.min(height, heightSpecSize);
            }
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    private float mProgress;
    private boolean isFirst = true;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "onDraw--->");
        RectF rect = new RectF(50, 500, 1000, 1500);
        for (int i = 0; i < 36; i++) {
            if (i == 0 || i == 35) {
                mPaint.setStrokeCap(Paint.Cap.ROUND);
            } else {
                mPaint.setStrokeCap(Paint.Cap.BUTT);
            }

            if (i % 2 == 0) {
                mPaint.setColor(getResources().getColor(R.color.white_bg));
            } else {
                mPaint.setColor(getResources().getColor(R.color.gray_mid));
            }
            canvas.drawArc(rect, 180 + i * 5, 5, false, mPaint);
        }

//        mPaintProgress.setColor(ColorUtils.getCurrentColor(mProgress / 90f, colors));
        mPaintProgress.setShader(new SweepGradient(1080 / 2, 1920 / 2, colors, null));
        canvas.drawArc(rect, 180, mProgress, false, mPaintProgress);
        if (isFirst) {
            startAnimation();
            isFirst = false;
        }
    }


    public void startAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 180);
        animator.setDuration(1000).start();
//        animator.setInterpolator(90);
        animator.setInterpolator(new AccelerateInterpolator());//加速减速
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

}
