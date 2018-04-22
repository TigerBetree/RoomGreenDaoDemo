package com.demo.greendao;

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

import com.demo.R;
import com.demo.databinding.ActivityGreendaoBinding;
import com.demo.databinding.ItemPersonBinding;
import com.demo.greendao.db.PersonDao;
import com.demo.greendao.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GreenDaoActivity extends Activity {

    private ActivityGreendaoBinding activityGreendaoBinding;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGreendaoBinding = DataBindingUtil.setContentView(this, R.layout.activity_greendao);

        activityGreendaoBinding.btAddPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPerson();
            }
        });

        initRecyclerView();

        loadPersonsFromDb();
    }

    private void loadPersonsFromDb() {
        new AsyncTask<Void, Void, List<Person>>() {

            @Override
            protected List<Person> doInBackground(Void... voids) {
                return GreenDaoHelper.getDaoSession().getPersonDao().loadAll();
            }

            @Override
            protected void onPostExecute(List<Person> persons) {
                myAdapter.setPersonListAndNotify(persons);

            }
        }.execute();
    }

    private void addPerson() {
        Person person = new Person();
        person.setId(System.currentTimeMillis());
        person.setName("Person " + (new Random().nextInt(20)));
        person.setAge(10 + new Random().nextInt(50));

        PersonDao personDao = GreenDaoHelper.getDaoSession().getPersonDao();
        personDao.insert(person);

        myAdapter.addPersonAndNotify(person);
    }

    private void deletePerson(final Person person) {
        PersonDao personDao = GreenDaoHelper.getDaoSession().getPersonDao();
        personDao.delete(person);
    }

    private void initRecyclerView() {
        activityGreendaoBinding.recyclerview.setHasFixedSize(true);
        activityGreendaoBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new MyAdapter();
        activityGreendaoBinding.recyclerview.setAdapter(myAdapter);
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements OnPersonItemClickListener {

        private List<Person> personList = new ArrayList<>();

        public void setPersonListAndNotify(@NonNull List<Person> persons) {
            this.personList = persons;
            notifyDataSetChanged();
        }

        public void addPersonAndNotify(@NonNull Person person) {
            personList.add(0, person);
            notifyDataSetChanged();
        }

        public void deletePersonAndNotify(@NonNull Person person) {
            personList.remove(person);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemPersonBinding userItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_person, parent, false);
            return new ViewHolder(userItemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
            holder.itemBinding.setPerson(personList.get(position));
            holder.itemBinding.setClickCallback(this);
            holder.itemBinding.executePendingBindings();
        }

        @Override
        public int getItemCount() {
            if (personList != null) {
                return personList.size();
            }
            return 0;
        }

        @Override
        public void onItemClicked(Person person) {
            Toast.makeText(GreenDaoActivity.this, "click : " + person.getName(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onItemLongClicked(View view, final Person person) {
            new AlertDialog.Builder(GreenDaoActivity.this).setMessage("删除？")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deletePersonAndNotify(person);
                            deletePerson(person);

                            dialog.dismiss();
                        }
                    }).show();

            return true;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            final ItemPersonBinding itemBinding;

            public ViewHolder(ItemPersonBinding binding) {
                super(binding.getRoot());
                itemBinding = binding;
            }
        }
    }
}
