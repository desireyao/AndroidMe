package com.desireyao.horizontalprogressbarwithnumber.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.desireyao.horizontalprogressbarwithnumber.commons.SizeUtils;
import com.orhanobut.logger.Logger;

/**
 * Package com.desireyao.horizontalprogressbarwithnumber.view.
 * Created by yaoh on 2016/09/23.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:横向的progressBar
 */
public class HProgressBar extends ProgressBar{

    private static final float DEFAULT_TEXT_SIZE = 10.0f;
    private static final int DEFAULT_TEXT_COLOR = 0XFFFC00D1;
    private static final int DEFAULT_COLOR_UNREACHED_COLOR = 0xFFd3d6da;
    private static final int DEFAULT_HEIGHT_REACHED_PROGRESS_BAR = 2;
    private static final int DEFAULT_HEIGHT_UNREACHED_PROGRESS_BAR = 2;
    private static final int DEFAULT_SIZE_TEXT_OFFSET = 10;

    protected int mTextColor = DEFAULT_TEXT_COLOR;
    /**
     * size of text (sp)
     */
    protected int mTextSize = 20;

    /**
     * offset of draw progress
     */
    protected int mTextOffset = 0;
    protected int mRealWidth;

    private Paint mPaint = new Paint();


    private Context mContext;

    public HProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setHorizontalScrollBarEnabled(true);
    }


    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (heightMode != MeasureSpec.EXACTLY) {

            float textHeight = (mPaint.descent() + mPaint.ascent());
//            int exceptHeight = (int) (getPaddingTop() + getPaddingBottom()
//                    + Math.max(Math.max(mReachedProgressBarHeight, mUnReachedProgressBarHeight), Math.abs(textHeight)));
            int exceptHeight = 1;
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(exceptHeight, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
//        //画笔平移到指定paddingLeft， getHeight() / 2位置，注意以后坐标都为以此为0，0
//        canvas.translate(getPaddingLeft(), getHeight() / 2);

        boolean noNeedBg = false;
        //当前进度和总值的比例
        float radio = getProgress() * 1.0f / getMax();

        //已到达的宽度
        float progressPosX = (int) (mRealWidth * radio);

        //绘制的文本
        String text = getProgress() + "%";

        //拿到字体的宽度和高度
        float textWidth = mPaint.measureText(text);
        float textHeight = (mPaint.descent() + mPaint.ascent()) / 2;

        //如果到达最后，则未到达的进度条不需要绘制
        if (progressPosX + textWidth > mRealWidth)
        {
            progressPosX = mRealWidth - textWidth;
            noNeedBg = true;
        }

        // 绘制已到达的进度
        float endX = progressPosX - mTextOffset / 2;
        if (endX > 0)
        {
            mPaint.setColor(Color.parseColor("#00F5FF"));
            mPaint.setStrokeWidth(10);
            canvas.drawLine(0, 0, endX, 0, mPaint);
        }

        // 绘制文本
        mPaint.setColor(mTextColor);
        canvas.drawText(text, progressPosX, -textHeight, mPaint);


        // 绘制未到达的进度条
        if (!noNeedBg)
        {
            float start = progressPosX + mTextOffset / 2 + textWidth;
            mPaint.setColor(Color.parseColor("#eeeeee"));
            mPaint.setStrokeWidth(5);
            canvas.drawLine(start, 0, mRealWidth, 0, mPaint);
        }

        canvas.restore();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRealWidth = w - getPaddingRight() - getPaddingLeft();

        Logger.e("mRealWidth: " + mRealWidth);
    }
}
