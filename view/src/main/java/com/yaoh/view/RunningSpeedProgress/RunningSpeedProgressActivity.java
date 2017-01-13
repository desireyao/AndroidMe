package com.yaoh.view.RunningSpeedProgress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yaoh.view.R;

public class RunningSpeedProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_speed_progress);

        RunningSpeedProgress speedProgress = (RunningSpeedProgress) findViewById(R.id.id_progress);
        speedProgress.setParams(1f,"1","05'33''","10'00''");
    }
}
