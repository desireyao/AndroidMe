package com.wxpaydemo.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 统一下单信息
 */
@XStreamAlias("xml")
public class Order {
    private String appid;
    private String mch_id;
    private String nonce_str;
    private String body;
    private String out_trade_no;
    private String total_fee;
    private String spbill_create_ip;
    private String notify_url;
    private String trade_type;
    private String sign;

    public Order(String appid,
                 String mch_id,
                 String nonce_str,
                 String body,
                 String out_trade_no,
                 String total_fee,
                 String spbill_create_ip,
                 String notify_url,
                 String trade_type) {

        this.appid = appid;
        this.mch_id = mch_id;
        this.nonce_str = nonce_str;
        this.body = body;
        this.out_trade_no = out_trade_no;
        this.total_fee = total_fee;
        this.spbill_create_ip = spbill_create_ip;
        this.notify_url = notify_url;
        this.trade_type = trade_type;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    //按照A-Z,a-z 进行排序
    @Override
    public String toString() {
        return "appid=" + appid  + '&' +
                "body=" + body + '&' +
                "mch_id=" + mch_id  + '&' +
                "nonce_str=" + nonce_str  + '&' +
                "notify_url=" + notify_url + '&' +
                "out_trade_no=" + out_trade_no + '&' +
                "spbill_create_ip=" + spbill_create_ip + '&' +
                "total_fee=" + total_fee + '&' +
                "trade_type=" + trade_type + '&';
    }

}
