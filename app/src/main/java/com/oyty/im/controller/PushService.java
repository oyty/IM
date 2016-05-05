package com.oyty.im.controller;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.oyty.im.base.Constants;
import com.oyty.im.entities.Message;


/**
 * Created by oyty on 5/5/16.
 */
public class PushService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Message message = (Message) intent.getParcelableExtra(Constants.KEY_MESSAGE);
        switch (message.getType()) {
            case PLAIN:
                sendPlainMsg(message);
                break;
            default:
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void sendPlainMsg(Message message) {

    }
}
