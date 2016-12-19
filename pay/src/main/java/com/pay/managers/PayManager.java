package com.pay.managers;

import android.app.Activity;

import com.pay.listener.IPay;
import com.pay.listener.IPrepayCallback;

/**
 * Package com.pay.managers.
 * Created by yaoh on 2016/12/16.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:用于调用支付的功能类
 */
public class PayManager {
    private Activity mActivity;
    private IPay pay;

    public PayManager(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void setPay(IPay basePay){
        this.pay = basePay;
    }

    public void pay(final Object data,IPrepayCallback callback) {
        pay.pay(data, callback);
    }
}
