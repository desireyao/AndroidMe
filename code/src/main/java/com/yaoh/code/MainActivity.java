package com.yaoh.code;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.blankj.utilcode.utils.LogUtils;
import com.blankj.utilcode.utils.SPUtils;
import com.blankj.utilcode.utils.StringUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test();
    }

    /**
     * 测试
     */
    private void test(){

        //测试中文转拼音
        LogUtils.e(StringUtils.cn2PY("中国"));

        // 测试 shareprefence
        SPUtils spUtils = new SPUtils(this,"sp");
    }
}
