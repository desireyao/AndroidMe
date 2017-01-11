package com.yaoh.view.viewgroup.drag;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yaoh.view.R;

/**
 * Package com.diygreen.slidinglayoutdemo.widget.
 * Created by yaoh on 2016/12/19.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public class DragViewGroup extends FrameLayout {
    private final String TAG = "DragViewGroup";

    private int touchMode;
    private int mRawX;
    private int mRawY;

    private int dragViewWidth = 0;

    private int mScreenWidth;    // 屏幕的宽
    private int mScreenHeight;   // 屏幕的高

    private int curTranslationX; // 当前坐标

    private RelativeLayout main_layout;
    private RelativeLayout content_layout;
    private ImageView dragview;

    private AppCompatActivity mActivity;

    private IDragListener iDragListener;

    public DragViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        mActivity = (AppCompatActivity) context;
        DisplayMetrics dm = getResources().getDisplayMetrics();
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;
        setX(-mScreenWidth);

        init();
    }

    /**
     * 创建用于拖拽的view,和展示界面的fragment
     */
    private void init() {
        dragview = new ImageView(getContext());
        RelativeLayout.LayoutParams img_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        img_params.addRule(RelativeLayout.CENTER_VERTICAL);
        img_params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        dragview.setImageResource(R.drawable.img_dragleft);
        dragview.setLayoutParams(img_params);
    }

    /**
     * 设置标签是否隐藏
     *
     * @param visiable
     */
    public void setDragviewVisiable(boolean visiable) {
        if (visiable)
            dragview.setVisibility(View.VISIBLE);
        else {
            dragview.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e(TAG, "onMeasure");
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mScreenWidth + dragViewWidth, mScreenHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e(TAG, "onLayout");
        if (content_layout == null) {
            content_layout = (RelativeLayout) findViewById(R.id.content_layout);
            main_layout = (RelativeLayout) findViewById(R.id.main_layout);
            main_layout.addView(dragview);
        }
        dragViewWidth = dragview.getWidth();
        if (dragViewWidth != 0) {
            LayoutParams mainParams = (LayoutParams) main_layout.getLayoutParams();
            Log.e(TAG, "dragViewWidth: " + dragViewWidth + " mainParams.width: " + mainParams.width);
            if (mainParams.width == -1) {
                main_layout.setLayoutParams(new LayoutParams(mScreenWidth + dragViewWidth, mScreenHeight));
            }
        }

        RelativeLayout.LayoutParams content_param = (RelativeLayout.LayoutParams) content_layout.getLayoutParams();
        Log.e(TAG,"content_param.width: " + content_param.width + " mScreenWidth: " + mScreenWidth);

        if (content_param.width != mScreenWidth) {
            content_param.width = mScreenWidth;
            content_param.height = mScreenHeight;
            content_layout.setLayoutParams(content_param);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getRawX();
                int y = (int) event.getRawY();
                touchMode = 0;
                if (dragview.getVisibility() == View.INVISIBLE) {
                    return false;
                }
                if (getX() == 0 || (y > dragview.getHeight() + mScreenHeight / 2 || y < mScreenHeight / 2 - dragview.getHeight())) {
                    return false;
                } else {
                    return true;
                }
            case MotionEvent.ACTION_MOVE:
//                    Log.e(TAG, "ACTION_MOVE");
                touchMode = 1;
                x = (int) event.getRawX();
                if (x < dragview.getWidth())
                    return false;
                y = (int) event.getRawY();
                mRawX = -(dragViewWidth + mScreenWidth) + x;
                if (mRawX < 0) {
                    setX(mRawX);
                    curTranslationX = mRawX;
                }
                return true;
            case MotionEvent.ACTION_UP:
                x = (int) event.getRawX();
                y = (int) event.getRawY();
//              Log.e(TAG, "ACTION_UP" + " x: " + x + " y: " + y
//                       + "dragview.getWidth(): " +  dragview.getWidth()
//                       + " dragview.getHeight(): " + dragview.getHeight());
                // 可拖拽view 的坐标范围
                if (x <= dragViewWidth && (y <= dragview.getHeight() + mScreenHeight / 2 && y > mScreenHeight / 2 - dragview.getHeight())) {
                    curTranslationX = -mScreenWidth;
                    openAnimation();
                    curTranslationX = 0;
                } else if (x > mScreenWidth / 2) {
                    openAnimation();
                    curTranslationX = 0;
                } else {
                    closeAnimation();
                    curTranslationX = -mScreenWidth;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void closeAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "translationX", curTranslationX, -mScreenWidth);
        animator.setDuration(200);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (iDragListener != null) {
                    iDragListener.hide();
                }
            }
        });
        animator.start();
    }

    private void openAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "translationX", curTranslationX, 0);
        animator.setDuration(200);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (iDragListener != null) {
                    iDragListener.show();
                }
            }
        });
        animator.start();
    }

    public void setDragListener(IDragListener iDragListener) {
        this.iDragListener = iDragListener;
    }

    public interface IDragListener {
        void show();
        void hide();
    }
}
