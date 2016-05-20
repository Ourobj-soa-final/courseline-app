package com.wangjin.courseline;

import android.app.Application;

/**
 * Created by wangjin on 16/5/20.
 */
public class BaseApplication extends Application {

    private static BaseApplication app;

    public static BaseApplication getInstance(){
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
}
