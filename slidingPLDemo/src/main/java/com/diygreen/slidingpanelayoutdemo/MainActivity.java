package com.diygreen.slidingpanelayoutdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClick(View view){
        startActivity(new Intent(this,SecondActivity.class));
        overridePendingTransition(R.anim.pull_in_left,R.anim.push_out_left);

    }
}
