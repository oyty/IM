package com.oyty.im.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by oyty on 5/5/16.
 */
public class BaseApplication extends Application {
    private static volatile Context mInstance;

    public static final String SELF_ID = "oyty";

    public static Context getInstance() {
        if(mInstance == null) {
            synchronized (BaseApplication.class) {
                if(mInstance == null) {
                    mInstance = new BaseApplication();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = getApplicationContext();
    }
}
