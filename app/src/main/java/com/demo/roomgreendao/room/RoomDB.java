package com.demo.roomgreendao.room;

public class RoomDB {

    private static volatile RoomDB mInstance = null;

    private AppDataBase mDB;

    private RoomDB() {
        mDB = AppDataBase.getDB();
    }

    public static RoomDB getInstance() {
        if (null == mInstance) {
            synchronized (RoomDB.class) {
                if (null == mInstance) {
                    mInstance = new RoomDB();
                }
            }
        }

        return mInstance;
    }

    public static AppDataBase getDB() {
        return getInstance().mDB;
    }
}
