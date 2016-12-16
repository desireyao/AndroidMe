package com.pay.impl;

import android.app.Activity;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.pay.base.BasePay;
import com.pay.listener.IPrepayCallback;

import java.util.Map;

/**
 * Package com.pay.module.
 * Created by yaoh on 2016/12/16.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public class AliPay extends BasePay {

    private String prePayinfo;

    public AliPay(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public void pay(final Object data, final IPrepayCallback callback) {
        prePayinfo = (String)data;
        new Thread(new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(mActivity);
                Map<String, String> result = alipay.payV2(prePayinfo, true);
                Log.e("alipay:", result.toString());
                callback.result(result);
            }
        }).start();
    }

    @Override
    public void parse() {

    }
}
