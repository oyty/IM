package com.iteacher.android.controller;

import com.iteacher.android.entities.Message;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by oyty on 5/5/16.
 */
public class MessageManager {
    private static volatile MessageManager mInstance;

    private MessageManager() {
    }

    public static MessageManager getDefault() {
        if(mInstance == null) {
            synchronized (MessageManager.class) {
                if(mInstance == null) {
                    mInstance = new MessageManager();
                }
            }
        }
        return mInstance;
    }

    PublishSubject<Message> publishSubject = PublishSubject.create();
    private final Subject<Message, Message> bus = new SerializedSubject<>(publishSubject);

    public void sendMessage(final Message message) {
        bus.onNext(message);
    }

    public void register(final ReceiveCallback receiveCallback) {
        bus.observeOn(Schedulers.io())
                .map(new Func1<Message, Message>() {
                    @Override
                    public Message call(Message message) {
                        return receiveCallback.addOrUpdateOnIOThread(message);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Message>() {
                    @Override
                    public void call(Message msg) {
                        receiveCallback.onReceiveOnUIThread(msg);
                    }
                });
    }

}
