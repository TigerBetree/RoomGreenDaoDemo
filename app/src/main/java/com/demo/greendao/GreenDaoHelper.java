package com.demo.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.demo.greendao.db.DaoMaster;
import com.demo.greendao.db.DaoSession;

public class GreenDaoHelper {

    private static DaoSession daoSession;

    public static void initGreenDao(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "greendao.db");

        SQLiteDatabase db = helper.getWritableDatabase();

        DaoMaster daoMaster = new DaoMaster(db);

        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
