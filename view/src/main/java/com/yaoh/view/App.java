package com.yaoh.view;

import android.app.Application;

import com.yaoh.view.utils.ViewUtil;

/**
 * Created by yaoh on 2016/12/24.
 */

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        ViewUtil.init(getApplicationContext());
    }
}
