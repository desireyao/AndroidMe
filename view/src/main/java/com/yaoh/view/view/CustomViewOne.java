package com.yaoh.view.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Package com.yaoh.view.view.
 * Created by yaoh on 2016/09/29.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public class CustomViewOne extends View{

    private String text = "2048";
    private Bitmap bitmap;

    private Paint mPaint;

    private int mTextSize = 30;

    public CustomViewOne(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heughtSize = MeasureSpec.getMode(heightMeasureSpec);

        if(widthMode == MeasureSpec.EXACTLY){

        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Rect rect = new Rect();
        mPaint = new Paint();
        Rect mTextBound = new Rect();
        mPaint.setTextSize(mTextSize);
        mPaint.getTextBounds(text, 0, text.length(), mTextBound);

        //绘制矩形
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
//        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()),mPaint);

        //绘制圆形
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        canvas.drawCircle(150,150,80,mPaint);

        //绘制直线
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        canvas.drawLine(50,50,200,200,mPaint);

        // 绘制这个三角形,你可以绘制任意多边形
        Path path = new Path();
        path.moveTo(180, 200);// 此点为多边形的起点
        path.lineTo(300, 300);
        path.lineTo(80, 300);
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, mPaint);


        // 绘制贝赛尔曲线
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        Path path2=new Path();
        path2.moveTo(100, 320);//设置Path的起点
        path2.quadTo(150, 310, 170, 400); //设置贝塞尔曲线的控制点坐标和终点坐标
        canvas.drawPath(path2, mPaint);//画出贝塞尔曲线

    }
}
