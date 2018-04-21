package com.demo.roomgreendao.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.demo.roomgreendao.room.entity.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("select * from user")
    List<User> getAllUsers();

    @Insert
    void insertUsers(User... users);

    @Delete
    void deleteUser(User user);
}
