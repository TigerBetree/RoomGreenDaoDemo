package com.demo.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.demo.MyApp;
import com.demo.room.dao.UserDao;
import com.demo.room.entity.User;

@Database(version = 1, entities = {User.class})
public abstract class AppDataBase extends RoomDatabase {

    public abstract UserDao userDao();

    public static AppDataBase getDB() {
        return Room.databaseBuilder(MyApp.getInstance(), AppDataBase.class, "mRoomDB")
                // 版本升级
                // .addMigrations(MIGRATION_1_2, MIGRATION_2_3)

                // Room不允许在主线程中访问数据库，除非在buid的时候使用allowMainThreadQueries()方法
                // .allowMainThreadQueries()
                .build();
    }

//    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, "
//                    + "`name` TEXT, PRIMARY KEY(`id`))");
//        }
//    };
//
//    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE Book "
//                    + " ADD COLUMN pub_year INTEGER");
//        }
//    };
}
