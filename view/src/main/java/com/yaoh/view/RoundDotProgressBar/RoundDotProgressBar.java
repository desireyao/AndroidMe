package com.yaoh.view.RoundDotProgressBar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.yaoh.view.R;

/**
 * Package com.yaoh.view.RoundDotProgressBar.
 * Created by yaoh on 2016/10/13.
 * Company Beacool IT Ltd.
 * <p/>
 * Description: 圆形圆点进度条
 */
public class RoundDotProgressBar extends View {

    private int CycleFinishColor;     // 完成进度的颜色
    private int CycleDefaultColor;    // 未完成进度圆点的颜色

    private float  radius_r0;         // 小圆半径
    private float  radius_r1 = 250.f; // 大圆半径

    private int r1_center_x;          // 大圆圆心x坐标
    private int r1_center_y;          // 大圆圆心y坐标

    private int progress;             // 进度

    private Paint mPaint;

    public RoundDotProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundDotProgressBar);
        init(ta);
    }

    private void init(TypedArray ta) {

        CycleFinishColor = ta.getColor(R.styleable.RoundDotProgressBar_CycleFinishColor,Color.parseColor("#ffffff"));
        CycleDefaultColor = ta.getColor(R.styleable.RoundDotProgressBar_CycleDefaultColor,Color.parseColor("#eeeeee"));

        ta.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    private float startX;
    private float startY;
    private float endX;
    private float endY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN){

            startX = event.getX();
            startY = event.getY();

            return true;

        }else if(event.getAction() == MotionEvent.ACTION_MOVE){

            endX = event.getX();
            endY = event.getY();
            progress = (int) (startY - endY) / 5;
            if(progress > 100)
                progress = 100;
            else if(progress < 0)
                progress = 0;

            postInvalidate();

        }else if(event.getAction() == MotionEvent.ACTION_UP){

            endX = event.getX();
            endY = event.getY();

            int diffx = (int) Math.abs(endX - startX);
            int diffy = (int) Math.abs(endY - startX);
            Log.e("TAG","diffx: " + diffx + " diffy: " + diffy);
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        r1_center_x = getWidth() / 2;
        r1_center_y = getHeight() / 2;

        radius_r0 = (float) (Math.sin(Math.toRadians(1.f)) / (Math.sin(Math.toRadians(1.f)) + 1) * radius_r1);

        for (int i = 0; i < 100; i++){

            //绘制未完成进度圆点
            mPaint.setStrokeWidth(1);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(CycleDefaultColor);
            float x0 = (float) (r1_center_x + Math.sin(Math.toRadians(1.8 * 2 * i)) * (radius_r1 - radius_r0));
            float y0 = (float) (r1_center_y - Math.cos(Math.toRadians(1.8 * 2 * i)) * (radius_r1 - radius_r0));
            canvas.drawCircle(x0,y0,radius_r0,mPaint);
        }

        for (int i = 0; i < progress; i++){

            //绘制有进度的原点
            mPaint.setStrokeWidth(1);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(CycleFinishColor);
            float x0 = (float) (r1_center_x + Math.sin(Math.toRadians(1.8 * 2 * i)) * (radius_r1 - radius_r0));
            float y0 = (float) (r1_center_y - Math.cos(Math.toRadians(1.8 * 2 * i)) * (radius_r1 - radius_r0));
            canvas.drawCircle(x0,y0,radius_r0,mPaint);
        }
    }

    class DrawProgressTask implements Runnable{

        @Override
        public void run() {
            if(progress == 100)
                progress = 0;
                 try {
                     Thread.sleep(50);
                     progress ++;
                     postInvalidate();
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }
    }
}
