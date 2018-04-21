package com.demo.roomgreendao.room.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "User")
public class User {

    @PrimaryKey(autoGenerate = true) // 自增
    public int uid;

    @ColumnInfo(name = "userName")
    public String name;

    public int age;
}
