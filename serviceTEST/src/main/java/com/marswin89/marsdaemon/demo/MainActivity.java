package com.marswin89.marsdaemon.demo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

/**
 *
 * Created by Mars on 12/24/15.
 */
public class MainActivity extends Activity {

    private Receiver1 receiver1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //you have to start the service once.
//        startService(new Intent(MainActivity.this, Service1.class));

        final IntentFilter filter = new IntentFilter();
        // 屏幕灭屏广播
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        // 屏幕亮屏广播
        filter.addAction(Intent.ACTION_SCREEN_ON);
        // 屏幕解锁广播
        filter.addAction(Intent.ACTION_USER_PRESENT);
        /* 注册屏幕唤醒时的广播 */
        receiver1 = new Receiver1();
        registerReceiver(receiver1, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(receiver1 != null)
        unregisterReceiver(receiver1);

       startService(new Intent(this, Service1.class));
    }
}
