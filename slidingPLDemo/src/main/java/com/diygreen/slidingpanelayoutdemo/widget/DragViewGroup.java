package com.diygreen.slidingpanelayoutdemo.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.diygreen.slidingpanelayoutdemo.R;

/**
 * Package com.diygreen.slidingpanelayoutdemo.widget.
 * Created by yaoh on 2016/12/19.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public class DragViewGroup extends FrameLayout {
    private final String TAG = "DragViewGroup";

    private FrameLayout.LayoutParams layoutParams;
    private int mRawX;
    private int mRawY;

    private ImageView dragview;

    private int dragViewWidth = 0;

    public DragViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
//        layoutParams = new FrameLayout.LayoutParams(1200 ,LayoutParams.WRAP_CONTENT);
//        setLayoutParams(layoutParams);
//        dragview = (ImageView) findViewById(R.id.iv_dragview);
//        dragViewWidth = dragview.getWidth();
//        Log.e(TAG,"dragViewWidth: " + dragViewWidth);
         setX(-1080f);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        dragview = (ImageView) findViewById(R.id.iv_dragview);
        dragViewWidth = dragview.getWidth();
        Log.e(TAG,"dragViewWidth: " + dragViewWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        /**
//         * 获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
//         */
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
//        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(1080 + dragViewWidth,1920);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "ACTION_DOWN");
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "ACTION_MOVE");
                int x = (int) event.getRawX();
                mRawX = -(dragViewWidth + 1080) + x;
                if(mRawX < 0){
                    setX(mRawX);
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "ACTION_UP");
                x = (int) event.getRawX();
                if (x > 540)
                    setX(0f);
                else
                    setX(-1080f);
                break;
        }
        return super.onTouchEvent(event);
    }
}
