package com.pay.model;

/**
 * Package com.pay.model.
 * Created by yaoh on 2016/12/16.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public class WxPrepayInfo {
    private String appid;
    private String partnerid;
    private String prepayid;
    private String mpackage;
    private String noncestr;
    private String timestamp;
    private String sign;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getMpackage() {
        return mpackage;
    }

    public void setMpackage(String mpackage) {
        this.mpackage = mpackage;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
