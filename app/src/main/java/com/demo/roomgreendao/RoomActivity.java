package com.demo.roomgreendao;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.demo.roomgreendao.databinding.ActivityRoomBinding;
import com.demo.roomgreendao.databinding.ItemUserBinding;
import com.demo.roomgreendao.room.RoomDB;
import com.demo.roomgreendao.room.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class RoomActivity extends Activity {

    private ActivityRoomBinding mBinding;

    private MyAdapter myAdapter;

    private int index = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_room);

        mBinding.btAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.name = "Test_" + index;
                user.age = 20 + index;

                myAdapter.addUserAndNotify(user);
                inserToDb(user);

                index++;
            }
        });

        initRecyclerView();

        loadUsersFromDb();
    }

    private void loadUsersFromDb() {
        new AsyncTask<Void, Void, List<User>>() {

            @Override
            protected List<User> doInBackground(Void... voids) {
                return RoomDB.getDB().userDao().getAllUsers();
            }

            @Override
            protected void onPostExecute(List<User> users) {
                myAdapter.setUserListAndNotify(users);

            }
        }.execute();
    }

    private void inserToDb(final User user) {
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                RoomDB.getDB().userDao().insertUsers(user);
            }
        });
    }

    private void deleteUser(final User user) {
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                RoomDB.getDB().userDao().deleteUser(user);
            }
        });
    }

    private void initRecyclerView() {
        mBinding.recyclerview.setHasFixedSize(true);
        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new MyAdapter();
        mBinding.recyclerview.setAdapter(myAdapter);
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements OnUserItemClickListener {

        private List<User> userList = new ArrayList<>();

        public void setUserListAndNotify(@NonNull List<User> users) {
            this.userList = users;
            notifyDataSetChanged();
        }

        public void addUserAndNotify(@NonNull User user) {
            userList.add(0, user);
            notifyDataSetChanged();
        }

        public void deleteUserAndNotify(@NonNull User user) {
            userList.remove(user);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemUserBinding userItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_user, parent, false);
            return new ViewHolder(userItemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.itemBinding.setUser(userList.get(position));
            holder.itemBinding.setClickCallback(this);
            holder.itemBinding.executePendingBindings();
        }

        @Override
        public int getItemCount() {
            if (userList != null) {
                return userList.size();
            }
            return 0;
        }

        @Override
        public void onItemClicked(User user) {
            Toast.makeText(RoomActivity.this, "click : " + user.name, Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onItemLongClicked(View view, final User user) {
            new AlertDialog.Builder(RoomActivity.this).setMessage("删除？")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteUserAndNotify(user);
                            deleteUser(user);

                            dialog.dismiss();
                        }
                    }).show();

            return true;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            final ItemUserBinding itemBinding;

            public ViewHolder(ItemUserBinding binding) {
                super(binding.getRoot());
                itemBinding = binding;
            }
        }
    }
}
