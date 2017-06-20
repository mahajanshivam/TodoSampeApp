package com.shivam.todosampleapp.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.shivam.todosampleapp.R;
import com.shivam.todosampleapp.model.Task;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends BaseActivity {

    private CardView bt_create_task;
    private CardView bt_list_tasks;
    private CardView bt_edit_task;
    private CardView bt_remove_task;
    private Dialog addTaskDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_create_task = (CardView) findViewById(R.id.bt_create_task);
        bt_list_tasks = (CardView) findViewById(R.id.bt_list_tasks);
        bt_edit_task = (CardView) findViewById(R.id.bt_edit_task);
        bt_remove_task = (CardView) findViewById(R.id.bt_remove_task);

        bt_create_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddtaskDialog();
            }
        });

        bt_list_tasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowTasksActivity.class);
                intent.putExtra("type", 0);
                startActivity(intent);
            }
        });

        bt_edit_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowTasksActivity.class);
                intent.putExtra("type", 2);
                startActivity(intent);
            }
        });

        bt_remove_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowTasksActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
            }
        });
    }

    private void showAddtaskDialog() {
        addTaskDialog = new Dialog(MainActivity.this);
        addTaskDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        addTaskDialog.setContentView(R.layout.addtask_dialog_layout);
        addTaskDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        addTaskDialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.copyFrom(addTaskDialog.getWindow().getAttributes());
        addTaskDialog.getWindow().setAttributes(lp);
        addTaskDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
            }
        });

        final EditText et_task_name = (EditText) addTaskDialog.findViewById(R.id.et_task_name);
        final EditText et_task_desc = (EditText) addTaskDialog.findViewById(R.id.et_task_desc);
        CardView bt_add_task = (CardView) addTaskDialog.findViewById(R.id.bt_add_task);

        bt_add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "" + et_task_name.getText();
                if (str.equalsIgnoreCase("")) {
                    Toast.makeText(MainActivity.this, "Please Enter a name", Toast.LENGTH_SHORT).show();
                } else {
                    hideKeyboard();
                    Calendar cl = Calendar.getInstance();
                    cl.setTimeInMillis(System.currentTimeMillis());
                    String date = "" + cl.get(Calendar.DAY_OF_MONTH) + "-" + cl.get(Calendar.MONTH) + "-" + cl.get(Calendar.YEAR);

                    taskDatabaseHandler.addTask(new Task("" + et_task_name.getText(), "" + et_task_desc.getText(), date, date));
                    addTaskDialog.dismiss();

                    List<Task> taskList = taskDatabaseHandler.getAllTasks();
                    for (int i = 0; i < taskList.size(); i++) {
                        Log.d("task", "getting Data from database " + taskList.get(i).getTaskName());
                    }
                }
            }
        });
    }
}
