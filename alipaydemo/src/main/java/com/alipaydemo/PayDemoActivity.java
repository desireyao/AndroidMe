package com.alipaydemo;

import java.util.Map;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.alipaydemo.managers.AliPayManager;
import com.alipaydemo.model.PayResult;
import com.alipaydemo.utils.OrderInfoUtil2_0;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 *  重要说明:
 *  
 *  这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
 *  真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
 *  防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险； 
 */
public class PayDemoActivity extends AppCompatActivity {
	
	private AliPayManager aliPayManager;
	private TextView tv_result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_main);
		tv_result = (TextView) findViewById(R.id.tv_result);

		aliPayManager = new AliPayManager(this, new AliPayManager.AliPayListener() {

			@Override
			public void payResult(Map<String, String> result) {
				PayResult payResult = new PayResult(result);
				String resultInfo = payResult.getResult();           // 同步返回需要验证的信息
				String resultStatus = payResult.getResultStatus();
				tv_result.setText("支付宝返回结果如下: \n" + "resultInfo:" + resultInfo
						+ "\n resultStatus: " + resultStatus);
				// 判断resultStatus 为9000则代表支付成功
				if (TextUtils.equals(resultStatus, "9000")) {
					// 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
					Toast.makeText(PayDemoActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
				} else {
					// 该笔订单真实的支付结果，需要依赖服务端的异步通知。
					Toast.makeText(PayDemoActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void authResult(Map<String, String> result) {
				AuthResult authResult = new AuthResult(result, true);
				String resultInfo = authResult.getResult();           // 同步返回需要验证的信息
				String resultStatus = authResult.getResultStatus();
				tv_result.setText("支付宝返回结果如下: \n" + "resultInfo:" + resultInfo
						+ "\n resultStatus: " + resultStatus);

				// 判断resultStatus 为“9000”且result_code
				// 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
				if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
					// 获取alipay_open_id，调支付时作为参数extern_token 的value
					// 传入，则支付账户为该授权账户
					Toast.makeText(PayDemoActivity.this, "授权成功 \n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();
				} else {
					// 其他状态值则为授权失败
					Toast.makeText(PayDemoActivity.this,"授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();
				}

			}
		});
	}

	public void payV2(View view){
		aliPayManager.payV2();
	}

	public void authV2(View view){
		aliPayManager.authV2();
	}

	/**
	 * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
	 * 
	 * @param v
	 */
	public void h5Pay(View v) {
		Intent intent = new Intent(this, H5PayDemoActivity.class);
		Bundle extras = new Bundle();
		/**
		 * url是测试的网站，在app内部打开页面是基于webview打开的，demo中的webview是H5PayDemoActivity，
		 * demo中拦截url进行支付的逻辑是在H5PayDemoActivity中shouldOverrideUrlLoading方法实现，
		 * 商户可以根据自己的需求来实现
		 */
		String url = "http://m.taobao.com";
		// url可以是一号店或者淘宝等第三方的购物wap站点，在该网站的支付过程中，支付宝sdk完成拦截支付
		extras.putString("url", url);
		intent.putExtras(extras);
		startActivity(intent);
	}
}
