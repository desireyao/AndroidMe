package com.yaoh.view.chart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.LinearInterpolator;

import com.yaoh.view.R;
import com.yaoh.view.utils.ViewUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by yaoh on 2016/12/24.
 */

public class LineChartView extends View{
    public static final String TAG = "LineChartView";
    //线条画笔
    private Paint mLinePaint;
    //圆点画笔
    private Paint mCirclePaint;
    //文字画笔
    private Paint mTextPaint;

    private Path mPath;
    private Paint mPaint;
    private PathMeasure mPathMeasure;
    private float mLength;
    private float mAnimValue;
    private PathEffect mEffect;


    //正常显示颜色
    private String pointColor = "#07D6ED";
    //横线显示颜色
    private String normalColor = "#66000000";
    //文字大小
    private int textSize = 20;
    //圆点半径
    private int r = 5;
    private int mScreenWidth;      // 屏幕的宽高
    private int mScreenHeight;

    //控件宽
    int mWidth;
    //控件高
    int mHeight;

    //y轴总共分的段数
    private int y_count = 5;
    //y轴最小值
    private int y_min = 10;

    private int x_count = 5;
    private int x_min = 1;

    // y轴的刻度
    private List<String> mListY = new ArrayList<>();

    // x轴的刻度
    private List<String> mListX = new ArrayList<>();

    //坐标点
    private List<Point> points = new ArrayList<>();

    public LineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScreenWidth = ViewUtil.getScreenWidth();
        mScreenHeight = ViewUtil.getScreenHeight();
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        textSize = ViewUtil.sp2px(12);

        mLinePaint = new Paint();
        mLinePaint.setColor(Color.parseColor(normalColor));
        mLinePaint.setAntiAlias(true);

        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.parseColor(normalColor));
        mCirclePaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setColor(getResources().getColor(R.color.white));
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(textSize);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        mPath = new Path();
    }

    //测量View的宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        //如果宽高都是warp_content时，设置控件的宽高的大小
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mScreenWidth, mScreenHeight / 3);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mScreenWidth, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, mScreenHeight / 3);
        }else{
            setMeasuredDimension(widthSpecSize, heightSpecSize);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawXY(canvas);

        canvas.drawPath(mPath,mPaint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        mHeight = getHeight() - getPaddingTop() - getPaddingBottom();
        Log.e(TAG,"onLayout mWidth: " + mWidth + " mHeight: " + mHeight);
        initData();
    }

    private void initData() {
//        for (int i = 0; i < x_count; i++){
//            int randomPoint = new Random().nextInt(5) + 1;
//            Point point = new Point();
//            point.setX(mWidth / x_count * randomPoint);
//            point.setY(mHeight / y_count * randomPoint);
//            points.add(point);
//        }

//        Point point = points.get(0);
//        mPath.moveTo(point.getX(),point.getY());
//        for (int i = 0; i < x_count; i++){
//            Point _point = points.get(i);
//            mPath.lineTo(_point.getX(),_point.getY());
//            Log.e(TAG,"_point.getX(): " + _point.getX() +  "_point.getY(): " + _point.getY());
//        }
//        Point point = points.get(0);
        mPath.moveTo(mWidth / 10  ,mHeight / 10);
        mPath.lineTo(mWidth / 10 * 2,mHeight / 10 * 8);
        mPath.lineTo(mWidth / 10 * 3,mHeight / 10 * 2);
        mPath.lineTo(mWidth / 10 * 4,mHeight / 10 * 6);
        mPath.lineTo(mWidth / 10 * 5,mHeight / 10 * 9);
        mPath.lineTo(mWidth / 10 * 6,mHeight / 10 * 1);
        mPath.lineTo(mWidth / 10 * 7,mHeight / 10 * 2);
        mPath.lineTo(mWidth / 10 * 8,mHeight / 10 * 8);
        mPath.lineTo(mWidth / 10 * 9,mHeight / 10 * 1);
//        mPath.lineTo(mWidth / 10 * 2,mHeight / 10 * 2);
//        mPath.lineTo(mWidth / 10 * 6,mHeight / 10 * 6);
//        mPath.lineTo(mWidth / 10 * 3,mHeight / 10 * 3);

        mPathMeasure = new PathMeasure();
        mPathMeasure.setPath(mPath, true);
        mLength = mPathMeasure.getLength();

        ValueAnimator animator = ValueAnimator.ofFloat(1, 0);
        animator.setDuration(2000);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAnimValue = (float) valueAnimator.getAnimatedValue();
                mEffect = new DashPathEffect(new float[]{mLength, mLength}, mLength * mAnimValue);
                mPaint.setPathEffect(mEffect);
                invalidate();
            }
        });
        animator.start();
    }

    /**
     * 初始化坐标轴
     *
     * @param canvas
     */
    private void drawXY(Canvas canvas) {
        mListY.clear();
        for (int i = 1; i <= y_count; i++) {
            mListY.add(y_min * i + "");
        }

        for (int i = 1; i < x_count;i++){
            mListX.add(x_min * i + "");
        }
        drawAxis(canvas);
    }


    /**
     * 绘制坐标轴
     *
     * @param canvas
     */
    private void drawAxis(Canvas canvas) {
        //计算出y轴刻度的间距
        float h = (float) ((mHeight - ViewUtil.dp2px(50)) / y_count);
        for (int i = 0; i < y_count; i++) {
            canvas.drawText(mListY.get(i),getPaddingLeft(),getPaddingTop() + h * i + getTextHeight(mListY.get(i),mTextPaint), mTextPaint);
//            canvas.drawLine(getPaddingLeft() + getTextWidth(mList.get(0), mTextPaint) * 2,
//                    getTextHeight(normalText, mTextPaint) * 2 + getPaddingTop() + h * i - (float) getTextHeight(mList.get(0), mTextPaint) / 4,
//                    getWidth() - getPaddingRight(),
//                    getTextHeight(normalText, mTextPaint) * 2 + getPaddingTop() + h * i - (float) getTextHeight(mList.get(0), mTextPaint) / 4,
//                    mTextPaint);

//            Log.e(TAG, "drawXY: xi="+(getPaddingLeft()+getTextWidth(mList.get(0), mTextPaint) * 2));
//             Log.e(TAG, "drawXY: yj="+(getTextHeight(normalText, mTextPaint)*2 + getPaddingTop() + h * i - (float) getTextHeight(mList.get(0), mTextPaint) / 4));

//            if (i == mList.size() - 1) {
//                float v = (width - (float) getTextHeight(mList.get(0), mTextPaint) * 2) / 7;
//                // Log.e(TAG, "drawData: w2="+v);
//
//                for (int j = 0; j < mDateList.size(); j++) {
//                    canvas.drawLine(getPaddingLeft() + getTextWidth(mList.get(0), mTextPaint) * 2 + v * j,
//                            getTextHeight(normalText, mTextPaint) * 2 + getPaddingTop() + h * i - (float) getTextHeight(mList.get(0), mTextPaint) / 4,
//                            getPaddingLeft() + getTextWidth(mList.get(0), mTextPaint) * 2 + v * j,
//                            getTextHeight(normalText, mTextPaint) * 2 + getPaddingTop() + h * i - (float) getTextHeight(mList.get(0), mTextPaint) / 4 - 10,
//                            mTextPaint);
//                    canvas.drawText(mDateList.get(j).substring(8, 10), getPaddingLeft() + getTextWidth(mList.get(0), mTextPaint) * 2 + v * j + v / 2 - (float) getTextWidth(mDateList.get(0).substring(8, 10), mTextPaint) / 2, (float) getTextHeight(mDateList.get(0), mTextPaint) * 2 + getTextHeight(normalText, mTextPaint) * 2 + getPaddingTop() + h * i, mTextPaint);
//                }
//            }
        }

        canvas.drawLine(getPaddingLeft(), mHeight - getTextHeight(mListX.get(0),mTextPaint),
                       mWidth - getPaddingRight(),mHeight - getTextHeight(mListX.get(0),mTextPaint),
                       mTextPaint);

        int x_space = (mWidth - getPaddingRight() - getPaddingLeft() - getTextWidth(mListX.get(0), mTextPaint) - ViewUtil.dp2px(10)) / x_count;
        for (int j = 0; j < x_count; j++) {
                    canvas.drawLine(getPaddingLeft() + getTextWidth(mListY.get(0), mTextPaint) + x_space * j,
                            mHeight - getTextHeight(mListX.get(0),mTextPaint),
                            getPaddingLeft() + getTextWidth(mListY.get(0), mTextPaint) + x_space * j,
                            mHeight - getTextHeight(mListX.get(0),mTextPaint) - ViewUtil.dp2px(5),
                            mTextPaint);

            // TODO: 2016/12/25 x轴刻度的文字
//            canvas.drawText(mListX.get(j), getPaddingLeft() + getTextWidth(mList.get(0), mTextPaint) * 2 + v * j + v / 2 - (float) getTextWidth(mDateList.get(0).substring(8, 10), mTextPaint) / 2,
//                                        (float) getTextHeight(mDateList.get(0), mTextPaint) * 2 + getTextHeight(normalText, mTextPaint) * 2 + getPaddingTop() + h * i, mTextPaint);
        }
    }


    /**
     * @param text  绘制的文字
     * @param paint 画笔
     * @return 文字的宽度
     */
    public int getTextWidth(String text, Paint paint) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int width = bounds.width();
        return width;
    }

    /**
     * @param text  绘制的文字
     * @param paint 画笔
     * @return 文字的高度
     */
    public int getTextHeight(String text, Paint paint) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int height = bounds.height();
        Log.e(TAG,"bounds.left: "  + bounds.left + "bounds.top: " + bounds.top
                + "bounds.right: " + bounds.right
                + "bounds.bottom: " + bounds.bottom
                + "bounds.height(): " + bounds.height());

        return height;
    }



    static class Point {
        private int x;
        private int y;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}

