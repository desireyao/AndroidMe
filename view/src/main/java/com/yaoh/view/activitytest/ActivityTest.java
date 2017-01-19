package com.yaoh.view.activitytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.yaoh.view.R;
import com.yaoh.view.activitytest.model.Model;

public class ActivityTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Model model = new Model();
        model.count = 1;
        model.count2 = 2;
        model.count3 = 3;
        Log.e("TAG","model.count2: " + model.count2);
    }
}
