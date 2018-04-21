package com.demo.roomgreendao;

import android.view.View;

import com.demo.roomgreendao.room.entity.User;

public interface OnUserItemClickListener {

    void onItemClicked(User user);

    boolean onItemLongClicked(View view, User user);
}
