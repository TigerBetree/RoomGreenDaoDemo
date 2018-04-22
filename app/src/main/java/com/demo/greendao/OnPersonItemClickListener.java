package com.demo.greendao;

import android.view.View;

import com.demo.greendao.model.Person;

public interface OnPersonItemClickListener {

    void onItemClicked(Person person);

    boolean onItemLongClicked(View view, Person person);
}
