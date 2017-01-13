package com.yaoh.view.progressbar;

import android.content.Context;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.widget.ProgressBar;


/**
 * Package com.yaoh.view.progressbar.
 * Created by yaoh on 2017/01/13.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public class RoundProgressBar extends ProgressBar{

    public RoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec,
                                          int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public final int roundCorners = 15;

//    @Override
//    Shape getDrawableShape() {
//        final float[] roundedCorners = new float[] { 0, 0, 0, 0, 0, 0, 0, 0 };
//        for(int i=0;i<roundedCorners.length;i++){
//            roundedCorners[i] = dp2px(getContext(), roundCorners);
//        }
//        return new RoundRectShape(roundedCorners, null, null);
//    }


    /**dpתpx*/
    public static float dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dp * scale;
    }
}
