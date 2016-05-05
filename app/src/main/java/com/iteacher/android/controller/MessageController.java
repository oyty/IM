package com.iteacher.android.controller;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.iteacher.android.entities.Message;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oyty on 5/5/16.
 */
public class MessageController {

    public static Dao<Message, String> getDao() throws SQLException {
        return DatabaseHelper.getInstance().getDao(Message.class);
    }

    /**
     * 回话中message改变，message本身的数据库要改变
     * 和回话中得消息也要进行同步
     */
    public static void addOrUpdate(Message msg) {
        try {
            getDao().createOrUpdate(msg);
            ConversationController.syncMessage(msg);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Message> queryAllByTimeAsc(String id1, String id2) {
        ArrayList<Message> messages = new ArrayList<Message>();
        try {
            QueryBuilder<Message, String> queryBuilder = getDao().queryBuilder();
            queryBuilder.orderBy(Message.TIMESTAMP, false);
            Where<Message, String> where = queryBuilder.where();
            where.in(Message.SENDERID, id1, id2);
            where.and();
            where.in(Message.RECEIVERID, id1, id2);
//            if (state == Constants.LOADMORE) {
//                where.and();
//                where.lt(Message.TIMESTAMP, timestamp);
//            }
//            queryBuilder.limit(Constants.PAGECOUNT);
            // (senderid=id1 and receiverid=id2) or (senderid=id2 and
            // receiverid=id1)
            // "where senderid in(id1,id2) and receiverid in(id1,id2) order by timestamp desc";
            List<Message> tmp =  queryBuilder.query();
            if (tmp == null || tmp.size() == 0) {
                return messages;
            }
            for (int i = tmp.size() - 1; i >= 0; i--) {
                messages.add(tmp.get(i));
            }
            return messages;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }
}
