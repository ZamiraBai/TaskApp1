package com.geektech.taskapp1.ui.home;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geektech.taskapp1.App;
import com.geektech.taskapp1.FormActivity;
import com.geektech.taskapp1.OnItemClickListener;
import com.geektech.taskapp1.R;
import com.geektech.taskapp1.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    private TaskAdapter adapter;
    private List<Task> list;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        list = new ArrayList<>();
        list = App.getDatabase().taskDao().getAll();
        App.getDatabase().taskDao().getAllLive().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
            list.clear();
            list.addAll(tasks);
                Collections.reverse(list);
            adapter.notifyDataSetChanged();
            }
        });
        adapter = new TaskAdapter(list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Task task = list.get(position);
                Intent intent = new Intent(getContext(), FormActivity.class);
                intent.putExtra("Task", task);
                startActivity(intent);
                Toast.makeText(getContext(), "pos = " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Подтвердите Ваши действия");
                builder.setMessage("Удалить выбранный элемент?");
                builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int item) {
                        App.getDatabase().taskDao().delete(list.get(position));
                    }
                });
                builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int item) {
                        dialogInterface.cancel();
                    }
                });
                builder.create().show();
            }
        });
        return root;
    }

    public void sortList() {
        list.clear();
        list.addAll(App.getDatabase().taskDao().sort());
        adapter.notifyDataSetChanged();
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            String title = data.getStringExtra("title");
            String desc = data.getStringExtra("description");

            list.add(0, new Task(title, desc));
            adapter.notifyDataSetChanged();
        }
    }*/
}