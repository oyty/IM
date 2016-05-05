package com.oyty.im.controller;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.oyty.im.db.DatabaseHelper;
import com.oyty.im.entities.Conversation;
import com.oyty.im.entities.Message;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by oyty on 5/5/16.
 */
public class ConversationController {

    public static Dao<Conversation, String> getDao() throws SQLException {
        return DatabaseHelper.getInstance().getDao(Conversation.class);
    }

    public static void addOrUpdate(Conversation conversation) {
        try {
            getDao().createOrUpdate(conversation);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Conversation queryById(String id) {
        try {
            return getDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void syncMessage(Message msg) {
        addOrUpdate(msg.copyTo());
    }

    public static ArrayList<Conversation> queryAllByTimeDesc() {
        ArrayList<Conversation> conversations = new ArrayList<Conversation>();
        try {
            QueryBuilder<Conversation, String> queryBuilder = getDao().queryBuilder();
            queryBuilder.orderBy(Conversation.TIMESTAMP, false);
            conversations = (ArrayList<Conversation>) getDao().query(queryBuilder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conversations;
    }

    public static void markAsRead(String targetId) {
        try {
            getDao().updateRaw("UPDATE conversation SET " + Conversation.UNREADNUM + "=0 WHERE " + Conversation.TARGETID + "=?", targetId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
