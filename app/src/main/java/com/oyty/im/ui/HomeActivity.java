package com.oyty.im.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.oyty.im.controller.MsgManager;
import com.oyty.im.R;
import com.oyty.im.controller.ReceiveCallback;
import com.oyty.im.entities.Message;


/**
 * Created by oyty on 5/5/16.
 */
public class HomeActivity extends Activity implements View.OnClickListener {
    private static final String SELF_ID = "oyty";
    private static final String TARGET_ID = "stay";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViewById(R.id.button).setOnClickListener(this);
        MsgManager.getDefault().register(new ReceiveCallback() {

            @Override
            public Message addOrUpdateOnIOThread(Message msg) {
                return super.addOrUpdateOnIOThread(msg);
            }

            @Override
            public void onReceiveOnUIThread(Message msg) {
                super.onReceiveOnUIThread(msg);
            }
        });

    }

    @Override
    public void onClick(View view) {
        Message message = Message.test("00001", SELF_ID, TARGET_ID);
        MsgManager.getDefault().sendMessage(message);
    }
}
