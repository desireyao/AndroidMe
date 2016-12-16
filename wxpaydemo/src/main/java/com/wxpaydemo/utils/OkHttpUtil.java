package com.wxpaydemo.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class OKHttpUtil {
    private static final String TAG = "OKHttpUtil";

    private static OKHttpUtil mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;
    private Gson mGson;

    private OKHttpUtil() {
        mOkHttpClient = new OkHttpClient
                .Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();

        mGson = new Gson();
        mHandler = new Handler(Looper.getMainLooper());
    }

    private synchronized static OKHttpUtil get() {
        if (mInstance == null) {
            mInstance = new OKHttpUtil();
        }
        return mInstance;
    }

    private void getRequest(String url, final ResultCallback callback) {
        final Request request = new Request.Builder().url(url).build();
        deliveryResult(callback, request);
    }

    private void postRequest(String url, final ResultCallback callback, List<Param> params) {
        Request request = buildPostRequest(url, params);
        deliveryResult(callback, request);
    }

    private void postRequest(String url, final ResultCallback callback, String xml) {
        Request request = buildPostRequest(url, xml);
        deliveryResult(callback, request);
    }

    private void postRequest(String url,final ResultCallback callback,JSONObject json){
        Request request = buildPostRequest(url,json);
        deliveryResult(callback,request);
    }


    /**
     * 处理结果
     *
     * @param callback
     * @param request
     */
    private void deliveryResult(final ResultCallback callback, Request request) {

        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                  sendFailCallback(callback, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String str = response.body().string();
                    if (callback.mType == String.class) {
                        sendSuccessCallBack(callback, str);                  // 返回字符串
                    } else {
                        Object object = mGson.fromJson(str, callback.mType); // 这里处理解析返回对象
                        sendSuccessCallBack(callback, object);
                    }
                } catch (final Exception e) {
//                      LogUtils.e(TAG, "convert json failure", e);
                    sendFailCallback(callback, e);
                }
            }
        });
    }

    private void sendFailCallback(final ResultCallback callback, final Exception e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onFailure(e);
                }
            }
        });
    }

    private void sendSuccessCallBack(final ResultCallback callback, final Object obj) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onSuccess(obj);
                }
            }
        });
    }

    private Request buildPostRequest(String url, List<Param> params) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Param param : params) {
            builder.add(param.key, param.value);
        }

        RequestBody requestBody = builder.build();
        return new Request.Builder()
                          .url(url)
                          .post(requestBody)
                          .build();
    }

    private Request buildPostRequest(String url, String xml) {
        MediaType MEDIA_TYPE_TEXT = MediaType.parse("text/xml");
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_TEXT, xml);
        return new Request.Builder().url(url).post(requestBody).build();
    }

    private Request buildPostRequest(String url,JSONObject json){
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,json.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return request;
    }

    /**********************对外接口************************/
    public static void get(String url, ResultCallback callback) {
        get().getRequest(url, callback);
    }


    public static void post(String url, final ResultCallback callback, List<Param> params) {
        get().postRequest(url, callback, params);
    }

    public static void post(String url, final ResultCallback callback, String xml) {
        get().postRequest(url, callback, xml);
    }

    public static void post(String url, final ResultCallback callback, JSONObject json) {
        get().postRequest(url, callback, json);
    }

    /**
     * http请求回调类,回调方法在UI线程中执行
     *
     * @param
     */
    public static abstract class ResultCallback<T> {
        Type mType;

        public ResultCallback() {
            mType = getSuperclassTypeParameter(getClass());
        }

        static Type getSuperclassTypeParameter(Class<?> subclass) {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        public abstract void onSuccess(T response);  // 请求成功回调

        public abstract void onFailure(Exception e); // 请求失败回调
    }

    /**
     * post请求参数类   这里可以根据项目抽取成泛型
     */
    public static class Param {
        String key;
        String value;

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}