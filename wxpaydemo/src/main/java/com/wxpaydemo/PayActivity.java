package com.wxpaydemo;

import com.thoughtworks.xstream.XStream;
import com.wxpaydemo.managers.NetworkManager;
import com.wxpaydemo.model.Order;
import com.wxpaydemo.model.PrepayInfo;
import com.wxpaydemo.utils.WXUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PayActivity extends Activity {
    private static final String TAG = "PayActivity";

    WXUtil wxUtils;
    TextView tv_result;
    PrepayInfo prepayInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay);
        wxUtils = new WXUtil(this);

        tv_result = (TextView) findViewById(R.id.tv_result);

        Button btn_order = (Button) findViewById(R.id.btn_order);
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkManager.UnfiedOrder(getOrder(), new NetworkManager.ResultListerner() {
                    @Override
                    public void Success(String data) {
                        tv_result.setText("请求预付单的结果：\n \n" + data);
                        XStream stream = new XStream();
                        stream.processAnnotations(PrepayInfo.class);
                        prepayInfo = (PrepayInfo) stream.fromXML(data);
                    }

                    @Override
                    public void Faiulre(String data) {
                    }
                });
            }
        });

        Button btn_pay = (Button) findViewById(R.id.btn_pay);
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
//                Toast.makeText(PayActivity.this, String.valueOf(isPaySupported), Toast.LENGTH_SHORT).show();
                  wxUtils.pay(prepayInfo);
            }
        });
    }

    private Order getOrder(){
        Order order = new Order(Constants.APP_ID,
                Constants.PARTNER_ID,
                WXUtil.getNonceStr(),
                "腾讯充值中心-QQ会员充值",
                WXUtil.getOutTradeNo(),
                "1",
                "127.0.0.1",
                "http://www.weixin.qq.com/wxpay/pay.php",
                "APP");
        return order;
    }
}
