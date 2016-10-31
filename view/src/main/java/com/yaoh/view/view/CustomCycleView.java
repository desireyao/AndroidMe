package com.yaoh.view.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

import static java.lang.Thread.sleep;

/**
 * Package com.yaoh.view.view.
 * Created by yaoh on 2016/10/11.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public class CustomCycleView extends View {

    private Paint mPaint;
    private int mCircleWidth = 20;
    private String mFirstColor = "#ff0000";
    private String mSecondColor = "#000000";
    private String mThirdColor = "#ffee00";
    private String mFourColor = "#eeeeee";

    private Handler handler = new Handler(Looper.getMainLooper());

    public CustomCycleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    int startAngle = -90;

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        int centre = getWidth() / 2;                // 获取圆心的x坐标
        int radius = centre - mCircleWidth / 2;     // 半径
        mPaint.setStrokeWidth(mCircleWidth);        // 设置圆环的宽度
        mPaint.setAntiAlias(true);                  // 消除锯齿
        mPaint.setStyle(Paint.Style.STROKE);        // 设置空心

        // 用于定义的圆弧的形状和大小的界限
        final RectF oval = new RectF(centre - radius, centre - radius,
                centre + radius, centre + radius);

        mPaint.setColor(Color.parseColor(mFirstColor)); // 设置圆环的颜色
        canvas.drawArc(oval, startAngle, 80, false, mPaint); // 根据进度画圆弧

        mPaint.setColor(Color.parseColor(mSecondColor)); // 设置圆环的颜色
        canvas.drawArc(oval, startAngle + 90, 80, false, mPaint); // 根据进度画圆弧

        mPaint.setColor(Color.parseColor(mThirdColor)); // 设置圆环的颜色
        canvas.drawArc(oval, startAngle + 180 , 80, false, mPaint); // 根据进度画圆弧

        mPaint.setColor(Color.parseColor(mFourColor)); // 设置圆环的颜色
        canvas.drawArc(oval, startAngle + 270, 80, false, mPaint); // 根据进度画圆弧

        new Thread(new Cycletask()).start();
    }

    class Cycletask implements Runnable{

        @Override
        public void run() {
            startAngle += 10;
            try {
                sleep(10);
                postInvalidate();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}
