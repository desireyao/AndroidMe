package com.yaoh.animationdemo;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView img;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = (ImageView) findViewById(R.id.img);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//
//      Animation animation = AnimationUtils.loadAnimation(this,R.anim.set_group);
//      img.startAnimation(animation);

//        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(this,R.animator.property_animator);
//        set.setTarget(this);
//        set.start();
//         ObjectAnimator.ofFloat(img, "rotationY", 0.0f, 70.0f).setDuration(1000).start();

        //测试属性动画
        PropertyValuesHolder a1 = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        PropertyValuesHolder a2 = PropertyValuesHolder.ofFloat("translationY", 0, 500);
        PropertyValuesHolder a3 = PropertyValuesHolder.ofFloat("translationX", 0, 500);
        PropertyValuesHolder a4 = PropertyValuesHolder.ofFloat("rotationY", 0.f, 360.f);
        ObjectAnimator.ofPropertyValuesHolder(img, a1, a2,a3,a4).setDuration(5000).start();
    }
}
