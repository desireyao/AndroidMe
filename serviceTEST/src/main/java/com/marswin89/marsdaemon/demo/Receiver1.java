package com.marswin89.marsdaemon.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * DO NOT do anything in this Receiver!<br/>
 *
 * Created by Mars on 12/24/15.
 */
public class Receiver1 extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        context.startService(new Intent(context, Service1.class));

        Log.e("TAG","接收到广播，action: " + action);

        if(action == "android.intent.action.SCREEN_ON"){
            Log.e("TAG","接收到亮屏广播");
//            context.startService(new Intent(context, Service1.class));
        }
    }
}
