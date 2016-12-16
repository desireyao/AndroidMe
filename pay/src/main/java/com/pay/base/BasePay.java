package com.pay.base;

import android.app.Activity;

import com.pay.listener.IPrepayCallback;

/**
 * Package com.pay.module.
 * Created by yaoh on 2016/12/16.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public abstract class BasePay {

    protected Activity mActivity;

    public BasePay(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public abstract void pay(Object data,IPrepayCallback callback);
    public abstract void parse();
}
