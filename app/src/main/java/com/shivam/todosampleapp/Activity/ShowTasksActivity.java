package com.shivam.todosampleapp.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shivam.todosampleapp.Adapter.ShowTaskRecyclerAdapter;
import com.shivam.todosampleapp.R;
import com.shivam.todosampleapp.database.TaskDatabaseHandler;
import com.shivam.todosampleapp.model.Task;

import java.util.ArrayList;

public class ShowTasksActivity extends BaseActivity {

    private RecyclerView taskRecyclerView;
    private TextView tv_no_task;
    private TaskDatabaseHandler taskDatabaseHandler = new TaskDatabaseHandler(ShowTasksActivity.this);
    private ArrayList<Task> taskArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tasks);

        taskRecyclerView = (RecyclerView) findViewById(R.id.taskRecyclerView);
        tv_no_task = (TextView) findViewById(R.id.tv_no_task);

        taskArrayList = taskDatabaseHandler.getAllTasks();

        if (taskArrayList.size() == 0) {
            tv_no_task.setVisibility(View.VISIBLE);
        } else {
            taskRecyclerView.setVisibility(View.VISIBLE);

            taskRecyclerView.setLayoutManager(new LinearLayoutManager(ShowTasksActivity.this));
            taskRecyclerView.setAdapter(new ShowTaskRecyclerAdapter(ShowTasksActivity.this, taskArrayList, getIntent().getIntExtra("type", 0), taskRecyclerView));
        }

    }
}
