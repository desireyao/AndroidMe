package com.diygreen.slidingpanelayoutdemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.diygreen.slidingpanelayoutdemo.R;

/**
 * Package com.diygreen.slidingpanelayoutdemo.widget.
 * Created by yaoh on 2016/12/19.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public class DragViewGroup extends RelativeLayout {
    private final String TAG = "DragViewGroup";

    private RelativeLayout.LayoutParams layoutParams;
    private int marginLeft;

    public DragViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        layoutParams = new RelativeLayout.LayoutParams(1080,1920);
//        View view = LayoutInflater.from(context).inflate(R.layout.item_left_layout,null);
//        RelativeLayout.LayoutParams params = new LayoutParams(1080,1920);
//        addView(view,params);
        setX(-1000f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
         setMeasuredDimension(1080,1920);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View child = getChildAt(0);
        Log.e(TAG,"getChildCount: " + getChildCount() + " child: " + child);
        child.setVisibility(View.VISIBLE);
        child.measure(r-l, b-t);
        child.layout(0,0,1080,1920);
}

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("TAG", "ACTION_DOWN");
                return true;
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getRawX();
                marginLeft = -1000 + x;
                if(marginLeft < 0){
                    setX(marginLeft);
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.e("TAG", "ACTION_UP");
                x = (int) event.getRawX();
                if (x > 540)
                    setX(0f);
                else
                    setX(-1000f);
                setLayoutParams(layoutParams);
                break;
        }
        return super.onTouchEvent(event);
    }
}
