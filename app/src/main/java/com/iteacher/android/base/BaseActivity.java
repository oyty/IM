package com.iteacher.android.base;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by oyty on 5/5/16.
 */
public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayoutId());
        initView();
        initViewListener();
        process();
    }

    protected abstract int initLayoutId();

    protected abstract void initView();

    protected abstract void initViewListener();

    protected abstract void process();

}
