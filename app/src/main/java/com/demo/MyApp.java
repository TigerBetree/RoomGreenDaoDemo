package com.demo;

import android.app.Application;

import com.demo.greendao.GreenDaoHelper;

public class MyApp extends Application {

    private static MyApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        GreenDaoHelper.initGreenDao(this);
    }

    public static MyApp getInstance() {
        return mInstance;
    }
}
