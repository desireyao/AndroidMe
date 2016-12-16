package com.alipaydemo.managers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.alipaydemo.Constants;
import com.alipaydemo.PayDemoActivity;
import com.alipaydemo.utils.OrderInfoUtil2_0;

import java.util.Map;

/**
 * Package com.alipaydemo.managers.
 * Created by yaoh on 2016/12/16.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public class AliPayManager {
    private Activity mActivity;
    private static final int PAY_CODE = 0;
    private static final int AUTH_CODE = 1;

    public AliPayManager(Activity activity,AliPayListener aliListener) {
        this.mActivity = activity;
        this.aliListener = aliListener;
    }

    private AliPayListener aliListener;

    public interface AliPayListener {
        void payResult(Map<String, String> result);
        void authResult(Map<String, String> result);
    }



    private Handler mHandler = new Handler(Looper.getMainLooper());

    /**
     * 支付宝支付业务
     */
    public void payV2() {
        if (TextUtils.isEmpty(Constants.APPID) || TextUtils.isEmpty(Constants.RSA_PRIVATE)) {
            new AlertDialog.Builder(mActivity)
                    .setTitle("警告")
                    .setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {

                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(Constants.APPID);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String sign = OrderInfoUtil2_0.getSign(params, Constants.RSA_PRIVATE);
        final String orderInfo = orderParam + "&" + sign;
        Log.e("TAG", "orderInfo: " + orderInfo);

        new Thread(new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(mActivity);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());
                sendResultCallback(result,PAY_CODE);
            }
        }).start();
    }

    /**
     * 支付宝账户授权业务
     */
    public void authV2() {
        if (TextUtils.isEmpty(Constants.PID)
                || TextUtils.isEmpty(Constants.APPID)
                || TextUtils.isEmpty(Constants.RSA_PRIVATE)
                || TextUtils.isEmpty(Constants.TARGET_ID)) {
            new AlertDialog.Builder(mActivity)
                    .setTitle("警告")
                    .setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * authInfo的获取必须来自服务端；
         */
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(Constants.PID, Constants.APPID, Constants.TARGET_ID);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, Constants.RSA_PRIVATE);
        final String authInfo = info + "&" + sign;

        // 必须异步调用
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(mActivity);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);
                sendResultCallback(result,AUTH_CODE);
            }
        }).start();
    }

    private void sendResultCallback(final Map<String, String> result, final int code) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if(aliListener == null)
                    return;
                if(code == PAY_CODE){
                    aliListener.payResult(result);
                }else if(code == AUTH_CODE){
                    aliListener.authResult(result);
                }
            }
        });
    }
}
