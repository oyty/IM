package com.oyty.im.base;

import android.app.Application;

/**
 * Created by oyty on 5/5/16.
 */
public class BaseApplication extends Application {
    private static volatile BaseApplication mInstance;

    public static BaseApplication getInstance() {
        if(mInstance == null) {
            synchronized (BaseApplication.class) {
                if(mInstance == null) {
                    mInstance = new BaseApplication();
                }
            }
        }
        return mInstance;
    }

}
