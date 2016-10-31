package com.yaoh.view.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Package com.yaoh.view2.views.
 * Created by yaoh on 2016/10/11.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public class CenterView extends RelativeLayout {

    public CenterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            Log.e("TAG", "CenterView onInterceptTouchEvent/ACTION_DOWN");
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            Log.e("TAG", "CenterView onInterceptTouchEvent/ACTION_MOVE");
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            Log.e("TAG", "CenterView onInterceptTouchEvent/ACTION_UP");
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            Log.e("TAG", "CenterView onTouchEvent/ACTION_DOWN");
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            Log.e("TAG", "CenterView onTouchEvent/ACTION_MOVE");
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            Log.e("TAG", "CenterView onTouchEvent/ACTION_UP");
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            Log.e("TAG", "CenterView dispatchTouchEvent/ACTION_DOWN");
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            Log.e("TAG", "CenterView dispatchTouchEvent/ACTION_MOVE");
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            Log.e("TAG", "CenterView dispatchTouchEvent/ACTION_UP");
        }
        return super.dispatchTouchEvent(ev);
    }
}
