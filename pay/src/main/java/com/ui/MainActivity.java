package com.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.pay.R;
import com.pay.listener.IPrepayCallback;
import com.pay.managers.PayManager;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private PayManager payManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        payManager = new PayManager(this);
    }

    public void aliPay(View view){
        payManager.pay(null, PayManager.PAY_TYPE_ALI, new IPrepayCallback< Map<String, String>>() {

            @Override
            public void result(Map<String, String> result) {

            }
        });
    }
}
