package com.lib.use;

import android.app.Application;

import com.lib.android.base.tool.CrashHandler;

/**
 * Create by limin on 2021/4/30.
 **/
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(getApplicationContext(),"crash-test");
    }
}
