package com.yaoh.code;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;

public class RevealActivity extends AppCompatActivity {

    View myView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveal);

        myView = findViewById(R.id.my_view);
    }

//    // 圆圈爆炸效果显示
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public static void animateRevealShow(
//            final Context context, final View view,
//            final int startRadius, @ColorRes final int color,
//            final OnRevealAnimationListener listener) {
//        int cx = (view.getLeft() + view.getRight()) / 2;
//        int cy = (view.getTop() + view.getBottom()) / 2;
//
//        float finalRadius = (float) Math.hypot(view.getWidth(), view.getHeight());
//
//        // 设置圆形显示动画
//        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, startRadius, finalRadius);
//        anim.setDuration(300);
//        anim.setInterpolator(new AccelerateDecelerateInterpolator());
//        anim.addListener(new AnimatorListenerAdapter() {
//            @Override public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                view.setVisibility(View.VISIBLE);
//                listener.onRevealShow();
//            }
//
//            @Override public void onAnimationStart(Animator animation) {
//                super.onAnimationStart(animation);
//                view.setBackgroundColor(ContextCompat.getColor(context, color));
//            }
//        });
//
//        anim.start();
//    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void Show(View view){
        myView.setVisibility(View.VISIBLE);
// get the center for the clipping circle
        int cx = (myView.getLeft() + myView.getRight()) / 2;
        int cy = (myView.getTop() + myView.getBottom()) / 2;
// get the final radius for the clipping circle
        int finalRadius = myView.getWidth();

        // create and start the animator for this view
       // (the start radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal(myView, 0, 0, 0, 2203);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                myView.setVisibility(View.VISIBLE);
            }
        });
        anim.setDuration(300);
        anim.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void hideView(View view){

       // get the center for the clipping circle
        int cx = (myView.getLeft() + myView.getRight()) / 2;
        int cy = (myView.getTop() + myView.getBottom()) / 2;

       // get the initial radius for the clipping circle
        int initialRadius = myView.getWidth();

        // create the animation (the final radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal(myView, 0, 0, 2203, 0);
        anim.setDuration(300);
        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                myView.setVisibility(View.INVISIBLE);
            }
        });

        // start the animation
        anim.start();
    }
}
