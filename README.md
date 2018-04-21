# Room

- [参考文档](https://www.jianshu.com/p/ef7cbf7c12b1)
- 依赖

    ```
    // model build.gradle

    // Room dependencies
    implementation "android.arch.persistence.room:runtime:1.0.0"
    annotationProcessor "android.arch.persistence.room:compiler:1.0.0"

    defaultConfig {
        ...

        // Room Schema
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = ["room.schemaLocation":
                                         "$projectDir/schemas".toString()]
            }
        }
    }
    ```

- demo

    ```
    @Entity(tableName = "User")
    public class User {

        @PrimaryKey(autoGenerate = true) // 自增
        public int uid;

        @ColumnInfo(name = "userName")
        public String name;

        public int age;
    }

    @Dao
    public interface UserDao {

        @Query("select * from user")
        List<User> getAllUsers();

        @Insert
        void insertUsers(User... users);

        @Delete
        void deleteUser(User user);
    }

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

    ```

# GreenDao

- [Github](https://github.com/greenrobot/greenDAO)