package com.wxpaydemo;

import com.wxpaydemo.model.PrepayInfo;
import com.wxpaydemo.managers.WxPayManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PayActivity extends Activity {
    private static final String TAG = "PayActivity";

    WxPayManager wxUtils;
    TextView tv_result;
    PrepayInfo prepayInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay);
        initData();

        tv_result = (TextView) findViewById(R.id.tv_result);

        Button btn_order = (Button) findViewById(R.id.btn_order);
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   wxUtils.unifiedOrder(); // 下单
            }
        });

        Button btn_pay = (Button) findViewById(R.id.btn_pay);
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
//                Toast.makeText(PayActivity.this, String.valueOf(isPaySupported), Toast.LENGTH_SHORT).show();
                if(prepayInfo != null)
                    wxUtils.pay(prepayInfo); // 请求支付
            }
        });
    }

    private void initData() {
        wxUtils = new WxPayManager(this, new WxPayManager.WxPayCallback() {
            @Override
            public void getOrderInfoSuccess(PrepayInfo data) {
                prepayInfo = data;
                tv_result.setText("请求预付单的结果：\n \n" + data.toString());
            }

            @Override
            public void onFailure(String message) {
                tv_result.setText("请求失败：\n \n" + message);
            }
        });
    }
}
