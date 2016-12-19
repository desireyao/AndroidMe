package com.pay.listener;

/**
 * Package com.pay.listener.
 * Created by yaoh on 2016/12/19.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public interface IPay {
    void pay(Object data,IPrepayCallback callback);
}
