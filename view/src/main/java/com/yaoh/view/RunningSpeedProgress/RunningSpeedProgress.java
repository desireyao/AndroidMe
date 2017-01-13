package com.yaoh.view.RunningSpeedProgress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yaoh.view.R;
import com.yaoh.view.utils.ViewUtil;

/**
 * Package com.yaoh.view.RunningSpeedProgress.
 * Created by yaoh on 2017/01/11.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:跑步的每KM的配速图
 */
public class RunningSpeedProgress extends View {
    private final String TAG = "RunningSpeedProgress";

    private int mProgressColor;            // 进度条的颜色
    private int mProgressBackgroundColor;  // 背景的颜色
    private int mProgressTextSize;         // 文字的大小

    private Paint mPaint;
    private Paint mPaintText;              // 文字的画笔

    private int mWidth;                    // 控件的宽高
    private int mHeight;

    private int mScreenWidth;              // 屏幕的宽高
    private int mScreenHeight;

    private int radius;                    // 进度条的半径

    private String mTextLeft;              // 文字
    private String mTextCenter;
    private String mTextRight;

    private float mProgress;                 // 绘制的进度

    public RunningSpeedProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RunningSpeedProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RunningSpeedProgress, defStyleAttr, 0);
        mProgressColor = a.getColor(R.styleable.RunningSpeedProgress_ProgressColor, Color.parseColor("#FF8247"));
        mProgressBackgroundColor = a.getColor(R.styleable.RunningSpeedProgress_ProgressBackgroundColor, Color.parseColor("#FF4040"));
        mProgressTextSize =  a.getDimensionPixelSize(R.styleable.RunningSpeedProgress_ProgressTextSize, 18);
        Log.e(TAG,"mProgressTextSize: " + mProgressTextSize);

        a.recycle();
        init();
    }

    private void init() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);

        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText.setStyle(Paint.Style.STROKE);
        mPaintText.setColor(Color.WHITE);
        mPaintText.setTextSize(mProgressTextSize);
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
            int width = mScreenWidth;
            if (widthSpecMode == MeasureSpec.AT_MOST) ;
            {
                mWidth = Math.min(width, widthSpecSize);
            }
        }

        if (heightSpecMode == MeasureSpec.EXACTLY)// match_parent , accurate
        {
            mHeight = heightSpecSize;
        } else {
            int height = ViewUtil.dp2px(30);
            if (heightSpecMode == MeasureSpec.AT_MOST)// wrap_content
            {
                mHeight = Math.min(height, heightSpecSize);
            }
        }
        radius = (mHeight - getPaddingBottom() - getPaddingTop()) / 2;
        setMeasuredDimension(mWidth, mHeight);
    }

    private boolean isFisrtDraw = true;
    @Override
    protected void onDraw(Canvas canvas) {
        if(isFisrtDraw){
           isFisrtDraw = false;
           drawBackgroudProgress(canvas);
        }
        drawProgress(canvas);
        drawLeftText(canvas);
        drawCenterText(canvas);
        drawRightText(canvas);
    }

    /**
     * 绘制背景进度条
     */
     private void drawBackgroudProgress(Canvas canvas){
         mPaint.setColor(mProgressBackgroundColor);
         canvas.drawRoundRect(new RectF(getPaddingLeft(), getPaddingTop(), mWidth - getPaddingRight(), mHeight - getPaddingBottom()), radius, radius, mPaint);
     }

    /**
     * 绘制进度
     * @param canvas
     */
    private void drawProgress(Canvas canvas) {
        mPaint.setColor(mProgressColor);
        float progressWidth = mProgress * (mWidth - getPaddingLeft() - getPaddingRight());
        float right = progressWidth + getPaddingLeft();
        float bottom = mHeight - getPaddingBottom();
        mPaint.setColor(mProgressColor);
        canvas.drawRoundRect(new RectF(getPaddingLeft(), getPaddingTop(), right, bottom), radius, radius, mPaint);
        if(progressWidth <= radius){
            mPaint.setColor(mProgressBackgroundColor);
        }
        canvas.drawRect(new RectF(getPaddingLeft(), getPaddingTop(),radius*1.5f, bottom), mPaint);
    }

    /**
     * 获取文字的大小
     */
    private void drawLeftText(Canvas canvas) {
        float baseY = getTextCenterY();
        float baseX = ViewUtil.sp2px(20);
        canvas.drawText(mTextLeft, baseX, baseY, mPaintText);
    }

    /**
     * 绘制中间的文字
     * @param canvas
     */
    private void drawCenterText(Canvas canvas) {
        float baseY = getTextCenterY();
        float baseX = mWidth / 4;
        canvas.drawText(mTextCenter, baseX, baseY, mPaintText);
    }

    /**
     * 绘制左边的文字
     * @param canvas
     */
    private void drawRightText(Canvas canvas) {
        float baseY = getTextCenterY();
        float baseX = mWidth -getPaddingRight() - mPaintText.measureText(mTextRight) - ViewUtil.sp2px(10);
        canvas.drawText(mTextRight, baseX, baseY, mPaintText);
    }

    public void setParams(float progress, String left, String center, String right) {
        mProgress = progress;
        mTextLeft = left;
        mTextCenter = center;
        mTextRight = right;
//        invalidate();
    }

    /**
     * 获取文字的中间基准线
     * @return
     */
    private float getTextCenterY(){
        Paint.FontMetricsInt fontMetrics = mPaintText.getFontMetricsInt();
        float baseY = (getPaddingTop() + (mHeight - getPaddingBottom())) / 2;
        baseY = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom + baseY;
        return baseY;
    }
}
