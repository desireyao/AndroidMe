package com.pay.managers;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import com.pay.listener.IPrepayCallback;
import com.pay.impl.AliPay;
import com.pay.base.BasePay;
import com.pay.impl.WxPay;

/**
 * Package com.pay.managers.
 * Created by yaoh on 2016/12/16.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:用于调用支付的功能类
 */
public class PayManager {
    public static final int PAY_TYPE_ALI = 0;
    public static final int PAY_TYPE_WX = 1;

    private Activity mActivity;

    private BasePay basePay;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    public PayManager(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void pay(final Object data, int pay_type, IPrepayCallback callback){
         if(PAY_TYPE_ALI == pay_type){
             basePay = new AliPay(mActivity);
             basePay.pay(data,callback);
         }else if(PAY_TYPE_WX == pay_type){
             basePay = new WxPay(mActivity);
             basePay.pay(data,callback);
         }
    }

    public void resultCallback(){

    }
}
