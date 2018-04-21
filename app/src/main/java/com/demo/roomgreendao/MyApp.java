package com.demo.roomgreendao;

import android.app.Application;

public class MyApp extends Application {

    private static MyApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static MyApp getInstance() {
        return mInstance;
    }
}
