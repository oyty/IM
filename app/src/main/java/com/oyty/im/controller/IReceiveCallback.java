package com.oyty.im.controller;

import com.oyty.im.entities.Message;

/**
 * Created by oyty on 5/5/16.
 */
public interface IReceiveCallback {
    void onReceiveOnUIThread(Message msg);
    Message addOrUpdateOnIOThread(Message msg);
}
