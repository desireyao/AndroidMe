package com.yaoh.code;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        addGuideImage(); // 添加引导页图片
//        showMyDialog();

    }

    private void showMyDialog() {
        final Dialog dialog = new Dialog(this, R.style.Theme_Light_Dialog);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.my_dialog,null);
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        //将设置好的属性set回去
        window.setAttributes(lp);

        //将自定义布局加载到dialog上
        dialog.setContentView(dialogView);
        dialog.show();
    }


    /**
     * 添加引导图片
     */
    public void addGuideImage() {
        View view = findViewById(R.id.framlayout);//查找通过setContentView上的根布局
        ViewParent viewParent = view.getParent();

        if(viewParent instanceof RelativeLayout){
            final RelativeLayout frameLayout = (RelativeLayout)viewParent;

            final ImageView guideImage = new ImageView(this);
            View topView = LayoutInflater.from(this).inflate(R.layout.my_dialog,null);

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT);

             topView.setLayoutParams(params);
//                guideImage.setScaleType(ImageView.ScaleType.FIT_XY);
//                guideImage.setImageResource(R.drawable.framlayout);
//                guideImage.setBackgroundColor(getResources().getColor(R.color.gray));
            topView.setAlpha(0.8f);
            topView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        frameLayout.removeView(guideImage);
                    }
                });



                // 添加引导图片
                frameLayout.addView(topView);
            }
        }
}
