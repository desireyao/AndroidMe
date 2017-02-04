package com.yaoh.view.activitytest;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yaoh.view.R;
import com.yaoh.view.activitytest.model.Model;

public class ActivityTest extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        textView = (TextView) findViewById(R.id.textView);


//        Model model = new Model();
//        model.count = 1;
//        model.count2 = 2;
//        model.count3 = 3;
//        Log.e("TAG","model.count2: " + model.count2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
//                Looper.prepare();//给当前线程初始化Looper
                textView.setText("---------------- name:" + Thread.currentThread().getName());
//                Toast.makeText(getApplicationContext(),"你猜我能不能弹出来～～",0).show();//Toast初始化的时候会new Handler();无参构造默认获取当前线程的Looper，如果没有prepare过，则抛出题主描述的异常。上一句代码初始化过了，就不会出错。
//                Looper.loop();//这句执行，Toast排
            }
        }).start();
    }

    public void onClick(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
//                Looper.prepare();//给当前线程初始化Looper
//                textView.setText("---------------- name:" + Thread.currentThread().getName());
                Toast.makeText(getApplicationContext(),"你猜我能不能弹出来～～",0).show();//Toast初始化的时候会new Handler();无参构造默认获取当前线程的Looper，如果没有prepare过，则抛出题主描述的异常。上一句代码初始化过了，就不会出错。
//                Looper.loop();//这句执行，Toast排
            }
        }).start();
    }
}
