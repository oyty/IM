package com.iteacher.android.controller;

import com.iteacher.android.entities.Message;

/**
 * Created by oyty on 5/5/16.
 */
public class ReceiveCallback implements IReceiveCallback {
    @Override
    public void onReceiveOnUIThread(Message msg) {
    }

    @Override
    public Message addOrUpdateOnIOThread(Message msg) {
        return msg;
    }
}
