package com.yaoh.view.arc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.yaoh.view.R;
import com.yaoh.view.utils.ViewUtil;

/**
 * Created by Administrator on 2017/07/10 0010.
 */

public class SportProgressView extends View {
    private static final String TAG = "SportProgressView";
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

        canvas.drawArc(rect, 180, 90, false, mPaintProgress);
    }
}
