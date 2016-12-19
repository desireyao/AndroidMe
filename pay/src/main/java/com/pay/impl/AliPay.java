package com.pay.impl;

import android.app.Activity;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.pay.listener.IPay;
import com.pay.listener.IPrepayCallback;

import java.util.Map;

/**
 * Package com.pay.module.
 * Created by yaoh on 2016/12/16.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public class AliPay implements IPay {

    private String prePayinfo;
    private Activity mActivity;

    public AliPay(Activity mActivity) {
        this.mActivity = mActivity;
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
}
