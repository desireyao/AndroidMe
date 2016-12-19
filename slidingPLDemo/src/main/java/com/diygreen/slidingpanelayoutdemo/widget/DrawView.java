package com.diygreen.slidingpanelayoutdemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Package com.diygreen.slidingpanelayoutdemo.widget.
 * Created by yaoh on 2016/12/19.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public class DrawView extends ImageView{

    private RelativeLayout.LayoutParams layoutParams;
    private int marginLeft;

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        layoutParams = new RelativeLayout.LayoutParams(1080,1920);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("TAG","ACTION_DOWN");
                return true;
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getRawX();
                marginLeft = -1000 + x;
                layoutParams.setMargins(marginLeft,0,0,0);
                setLayoutParams(layoutParams);
                break;
            case MotionEvent.ACTION_UP:
                Log.e("TAG","ACTION_UP");
                 x = (int) event.getRawX();
                if(x > 540)
                    layoutParams.setMargins(0,0,0,0);
                else
                    layoutParams.setMargins(-1000,0,0,0);
                setLayoutParams(layoutParams);
                break;
        }
        return super.onTouchEvent(event);
    }

}
