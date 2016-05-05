package com.oyty.im.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.oyty.im.controller.ConversationController;
import com.oyty.im.controller.MessageController;
import com.oyty.im.controller.MsgManager;
import com.oyty.im.R;
import com.oyty.im.controller.ReceiveCallback;
import com.oyty.im.entities.Conversation;
import com.oyty.im.entities.Message;
import com.oyty.im.entities.MsgStatus;
import com.oyty.im.util.LogUtil;

import java.util.ArrayList;


/**
 * Created by oyty on 5/5/16.
 */
public class HomeActivity extends Activity implements View.OnClickListener {
    private Context context;
    private static final String SELF_ID = "oyty";
    private static final String TARGET_ID = "stay";
    private static final String OTHER_ID = "will";
    public int message_id = 0x0001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = this;
        findViewById(R.id.button).setOnClickListener(this);
        MsgManager.getDefault().register(new ReceiveCallback() {

            @Override
            public Message addOrUpdateOnIOThread(Message msg) {
                MessageController.addOrUpdate(msg);
                return super.addOrUpdateOnIOThread(msg);
            }

            @Override
            public void onReceiveOnUIThread(Message msg) {
                if(msg.getSenderId().equals(SELF_ID)) {
                    LogUtil.getLogger().d("test", "i send a message to " + msg.getReceiverId());
                } else {
                    if(msg.getSenderId().equals(TARGET_ID)) {
                        LogUtil.getLogger().d("test", "new message send by " + msg.getSenderId());
                        Toast.makeText(context, "new message send by " + msg.getSenderId(), Toast.LENGTH_SHORT).show();
                    } else {
                        LogUtil.getLogger().d("test", "new message send by others");
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        updateMessages();
        Message message = Message.test(String.valueOf(message_id), TARGET_ID, SELF_ID, MsgStatus.ING);
        MsgManager.getDefault().sendMessage(message);
        updateMessages();
        message.setStatus(MsgStatus.DONE);
        MsgManager.getDefault().sendMessage(message);
        updateMessages();


//        updateConversation();
    }

    private void updateMessages() {
        ArrayList<Message> messages = MessageController.queryAllByTimeAsc(SELF_ID, TARGET_ID);
        if (messages != null && !messages.isEmpty()) {
            for(Message message : messages) {
                LogUtil.getLogger().d("test", "messages --- " + message.toString());
                Toast.makeText(this, message.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateConversation() {
        ArrayList<Conversation> conversations = ConversationController.queryAllByTimeDesc();
        if(conversations != null && !conversations.isEmpty()) {
            for(Conversation conversation : conversations) {
                LogUtil.getLogger().d("test", conversation.toString());
                Toast.makeText(this, conversation.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
