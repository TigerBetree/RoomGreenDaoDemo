package com.demo.room;

import android.view.View;

import com.demo.room.entity.User;

public interface OnUserItemClickListener {

    void onItemClicked(User user);

    boolean onItemLongClicked(View view, User user);
}
