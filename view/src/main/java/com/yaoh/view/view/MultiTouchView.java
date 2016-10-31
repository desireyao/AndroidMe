package com.yaoh.view.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import static android.view.KeyEvent.ACTION_DOWN;
import static android.view.KeyEvent.ACTION_UP;
import static android.view.MotionEvent.*;
//import static android.view.MotionEvent.ACTION_MOVE;
//import static android.view.MotionEvent.ACTION_OUTSIDE;
//import static android.view.MotionEvent.ACTION_POINTER_DOWN;
//import static android.view.MotionEvent.ACTION_POINTER_UP;
//import static android.view.MotionEvent.ACTION_SCROLL;

/**
 * Package com.yaoh.view.view.
 * Created by yaoh on 2016/10/28.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public class MultiTouchView extends ImageView {

    public MultiTouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(onTouchListener);
    }

    OnTouchListener onTouchListener = new OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            StringBuilder result = new StringBuilder(500);

            //手指的动作
            int action = event.getAction();
            switch (action){
                case ACTION_DOWN:
                    Log.v("MultiTouchView", "ACTION_DOWN");
                    break;
                case ACTION_UP:
                    Log.v("teMultiTouchViewst", "ACTION_UP");
                    break;
                case ACTION_MOVE:
                    Log.v("MultiTouchView", "ACTION_MOVE");
                    break;
                case ACTION_CANCEL:
                    Log.v("MultiTouchView", "ACTION_CANCEL");
                    break;
                case ACTION_OUTSIDE:
                    Log.v("MultiTouchView", "ACTION_OUTSIDE");
                    break;
                case ACTION_POINTER_DOWN:
                    Log.v("MultiTouchView", "ACTION_POINTER_DOWN");
                    break;
                case ACTION_POINTER_UP:
                    Log.v("MultiTouchView", "ACTION_POINTER_UP");
                    break;
                case ACTION_SCROLL:
                    Log.v("MultiTouchView", "ACTION_SCROLL");
                    break;
            }
            //几根手指
            int points = event.getPointerCount();
//            result.append("action:" + action)
//                    .append(",point_count:" + points);
            //输出此刻所有手指的状态
            for (int i = 0; i < points; i++) {
                result.append(" |index:")
                        .append(i)
                        .append(",id:" + event.getPointerId(i));

                result.append(",getX:")
                        .append(event.getX(i))
                        .append(",getRawX:" + event.getRawX())
                        .append(",getY:")
                        .append(event.getY(i))
                        .append(",getRawY:" + event.getRawY());
            }

            if(points == 2)
               Log.v("MultiTouchView", result.toString() + "spacing: " + spacing(event));
            return true;
        }
    };

    private double spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return Math.sqrt(x * x + y * y);
    }
}
