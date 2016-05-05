package com.iteacher.android.controller;

import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.iteacher.android.base.BaseApplication;
import com.iteacher.android.entities.Conversation;
import com.iteacher.android.entities.Message;
import com.iteacher.android.util.LogUtil;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by oyty on 5/5/16.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String LOG_TAG = DatabaseHelper.class.getSimpleName();

    private static volatile DatabaseHelper mInstance;
    private static final String DB_NAME = "ormlite_im";

    private static int DB_VERSION = 1;

    /**
     * 对dao进行缓存
     */
    private Map<String, Dao> daoMaps = new HashMap<>();

    public static DatabaseHelper getInstance() {
        if(mInstance == null) {
            synchronized (DatabaseHelper.class) {
                if(mInstance == null) {
                    mInstance = new DatabaseHelper();
                }
            }
        }
        return mInstance;
    }

    private DatabaseHelper() {
        super(BaseApplication.getInstance(), DB_NAME, null, DB_VERSION);
    }

    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao;
        String className = clazz.getSimpleName();
        if(daoMaps.containsKey(className)) {
            dao = daoMaps.get(className);
        } else {
            dao = super.getDao(clazz);
            daoMaps.put(className, dao);
        }
        return dao;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Message.class);
            TableUtils.createTableIfNotExists(connectionSource, Conversation.class);
        } catch (SQLException e) {
            e.printStackTrace();
            LogUtil.getLogger().e(LOG_TAG, "Unable to create databases ");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    @Override
    public void close() {
        super.close();
        for(String key : daoMaps.keySet()) {
            Dao dao = daoMaps.get(key);
            dao = null;
        }
    }
}
