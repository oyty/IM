package com.oyty.im.db;

import com.j256.ormlite.dao.Dao;
import com.oyty.im.entities.Message;

import java.sql.SQLException;

/**
 * Created by oyty on 5/5/16.
 */
public class MsgHelper {

    public static Dao<Message, Integer> getDao() throws SQLException {
        return DatabaseHelper.getInstance().getDao(Message.class);
    }

}
