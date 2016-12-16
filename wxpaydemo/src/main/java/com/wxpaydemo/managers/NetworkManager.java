package com.wxpaydemo.managers;

import android.util.Log;

import com.thoughtworks.xstream.XStream;
import com.wxpaydemo.Constants;
import com.wxpaydemo.model.Order;
import com.wxpaydemo.model.PrepayInfo;
import com.wxpaydemo.utils.OKHttpUtil;

/**
 * Package com.wxpaydemo.managers.
 * Created by yaoh on 2016/12/15.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public class NetworkManager {

    /**
     * 本地模拟 统一下单 生成微信预支付prepareId 这一步放在服务器端生成
     *
     */
    public static void UnfiedOrder(Order order, final ResultListerner listerner) {
        //生成sign签名
        String sign = WxPayManager.getSign(order);

        //生成所需参数，为xml格式
        order.setSign(sign.toUpperCase());
        XStream xstream = new XStream();
        xstream.alias("xml", Order.class);
        final String xml = xstream.toXML(order).replaceAll("__", "_");

        //获取预支付id sign...
        OKHttpUtil.post(Constants.UNIFIED_ORDER, new OKHttpUtil.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                Log.e("TAG", "response: " + response);

                String data = response;
                data = data.replaceAll("<!\\[CDATA\\[", "").replaceAll("]]>", "");

                XStream stream = new XStream();
                stream.processAnnotations(PrepayInfo.class);
                PrepayInfo prepayInfo = (PrepayInfo) stream.fromXML(data);
                listerner.Success(prepayInfo);
            }

            @Override
            public void onFailure(Exception e) {
                 listerner.Faiulre(e.toString());
            }
        }, xml);
    }



    public interface ResultListerner<T> {
        void Success(T data);
        void Faiulre(String data);
    }
}
