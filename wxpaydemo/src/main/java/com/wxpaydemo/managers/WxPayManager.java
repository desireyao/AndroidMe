package com.wxpaydemo.managers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.wxpaydemo.Constants;
import com.wxpaydemo.model.Order;
import com.wxpaydemo.model.PrepayInfo;
import com.wxpaydemo.utils.MD5;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * Package com.wxpaydemo.utils.
 * Created by yaoh on 2016/12/15.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public class WxPayManager {
    private static final String TAG = "WxPayManager";

    private Context mContext;
    private IWXAPI iwxapi;

    public WxPayCallback wxPayCallback;

    public  interface WxPayCallback{
        void getOrderInfoSuccess(PrepayInfo data);
        void onFailure(String message);
    }

    public WxPayManager(Context context,WxPayCallback wxPayCallback){
        mContext = context;
        this.wxPayCallback = wxPayCallback;
        getWXAPI();
    }

    public void unifiedOrder(){
        NetworkManager.UnfiedOrder(getOrder(), new NetworkManager.ResultListerner<PrepayInfo>() {

            @Override
            public void Success(PrepayInfo data) {
                wxPayCallback.getOrderInfoSuccess(data);
            }

            @Override
            public void Faiulre(String data) {
                wxPayCallback.onFailure(data);
            }
        });

    }

    public void pay(PrepayInfo prepayInfo) {
        if (isInstalledWx()) {
            iwxapi.sendReq(createPayReq(prepayInfo));
        }
    }

    private IWXAPI getWXAPI() {
        if (iwxapi == null) {
            iwxapi = WXAPIFactory.createWXAPI(mContext, Constants.APP_ID);  //通过WXAPIFactory创建IWAPI实例
            iwxapi.registerApp(Constants.APP_ID);   //将应用的appid注册到微信
        }
        return iwxapi;
    }

    //生成支付参数
    private PayReq createPayReq(PrepayInfo prepayInfo) {
        PayReq req = new PayReq();
        req.appId = Constants.APP_ID;
        req.partnerId = Constants.PARTNER_ID;
        req.prepayId = prepayInfo.getPrepay_id();
        req.packageValue = "Sign=" + "WXPay";
        req.nonceStr = prepayInfo.getNonce_str();
        req.timeStamp = String.valueOf(getTimeStamp());

        Log.e("TAG", "appId: " + req.appId
                + "\n partnerId: " + req.partnerId
                + "\n packageValue: " + req.packageValue
                + "\n nonceStr: " + req.nonceStr
                + "\n timeStamp:" + req.timeStamp);

        Map<String,Object> signParams = new HashMap<>();
        signParams.put("appid", req.appId);
        signParams.put("noncestr", req.nonceStr);
        signParams.put("package", req.packageValue);
        signParams.put("partnerid", req.partnerId);
        signParams.put("prepayid", req.prepayId);
        signParams.put("timestamp", req.timeStamp);

        req.sign = prepayInfo.getSign();
        Log.e(TAG," req.sign: " +  req.sign);
        return req;
    }

    /**
     * 判断是否安装了微信
     * @return
     */
    private boolean isInstalledWx() {
        if (!iwxapi.isWXAppInstalled()) {
            Toast.makeText(mContext, "请先安装微信客户端", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 随机字符串
     * @return
     */
    public static String getNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    public static long getTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    public static String getOutTradeNo(){
        String date_trade_no = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        return date_trade_no;
    }

    private String getAppSign(Map<String,Object> params) {
        StringBuilder sb = new StringBuilder();
        Iterator<String> iterator = params.keySet().iterator();
        while(iterator.hasNext()){
            String key =  iterator.next();
            sb.append(key);
            sb.append('=');
            sb.append(params.get(key));
            sb.append('&');
        }

        //拼接密钥
        sb.append("key=");
        sb.append(Constants.API_KEY);

        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return appSign;
    }

    //生成预支付随机签名
    public static String getSign(Order order) {
        if (Constants.API_KEY.equals("")) {
            return null;
        }

        StringBuffer sb = new StringBuffer(order.toString());
        //拼接密钥
        sb.append("key=");
        sb.append(Constants.API_KEY);

        String appSign = MD5.getMessageDigest(sb.toString().getBytes());
        return appSign;
    }

    private Order getOrder() {
        Order order = new Order(Constants.APP_ID,
                Constants.PARTNER_ID,
                getNonceStr(),
                "腾讯充值中心-QQ会员充值",
                getOutTradeNo(),
                "1",
                "127.0.0.1",
                "http://www.weixin.qq.com/wxpay/pay.php",
                "APP");
        return order;
    }

}
