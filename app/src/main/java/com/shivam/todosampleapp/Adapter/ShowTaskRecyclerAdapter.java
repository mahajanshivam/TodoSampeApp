package com.shivam.todosampleapp.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shivam.todosampleapp.Activity.ShowTasksActivity;
import com.shivam.todosampleapp.R;
import com.shivam.todosampleapp.database.TaskDatabaseHandler;
import com.shivam.todosampleapp.model.Task;

import java.util.ArrayList;
import java.util.Calendar;

public class ShowTaskRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context ctx;
    private ArrayList<Task> taskArrayList;
    private int type;
    private TaskDatabaseHandler taskDatabaseHandler;
    private RecyclerView recyclerView;
    private Dialog editDialog;
    private String editedName, editedDesc;

    public ShowTaskRecyclerAdapter(Context ctx, ArrayList<Task> taskArrayList, int type, RecyclerView recyclerView) {
        this.ctx = ctx;
        this.taskArrayList = taskArrayList;
        this.type = type;
        taskDatabaseHandler = new TaskDatabaseHandler(ctx);
        this.recyclerView = recyclerView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_single_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (type == 1) {
            holder.iv_delete_task.setVisibility(View.VISIBLE);
        } else if (type == 2) {
            holder.iv_edit_task.setVisibility(View.VISIBLE);
        }
        holder.tv_name.setText("" + taskArrayList.get(position).getTaskName());
        holder.tv_desc.setText("" + taskArrayList.get(position).getTaskDescription());
        holder.tv_date_created.setText("" + taskArrayList.get(position).getDateCreated());
        holder.tv_date_modified.setText("" + taskArrayList.get(position).getDateUpdated());

        holder.iv_delete_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("task", "clicked pos id = " + taskArrayList.get(position).getTaskID());
                boolean isDeletedSuccessfully = taskDatabaseHandler.deleteTask(taskArrayList.get(position).getTaskID());
                if (isDeletedSuccessfully) {
//                    taskArrayList = taskDatabaseHandler.getAllTasks();
                    taskArrayList.remove(position);
                    recyclerView.removeView(recyclerView.getChildAt(position));
                    notifyDataSetChanged();
                }
            }
        });

        holder.iv_edit_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editedName = taskArrayList.get(position).getTaskName();
                editedDesc = taskArrayList.get(position).getTaskDescription();
                showEditDialog(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskArrayList.size();
    }

    private void showEditDialog(final int position) {
        editDialog = new Dialog(ctx);
        editDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        editDialog.setContentView(R.layout.edit_dialog);
        editDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        editDialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.copyFrom(editDialog.getWindow().getAttributes());
        editDialog.getWindow().setAttributes(lp);
        editDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
            }
        });

        final EditText et_task_name = (EditText) editDialog.findViewById(R.id.et_task_name);
        final EditText et_task_desc = (EditText) editDialog.findViewById(R.id.et_task_desc);
        CardView bt_edit_task = (CardView) editDialog.findViewById(R.id.bt_edit_task);

        et_task_name.setText(editedName);
        et_task_desc.setText(editedDesc);
 
        bt_edit_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ShowTasksActivity) ctx).hideKeyboard();
                editedName = "" + et_task_name.getText();
                editedDesc = "" + et_task_desc.getText();

                Calendar cl = Calendar.getInstance();
                cl.setTimeInMillis(System.currentTimeMillis());
                String date = "" + cl.get(Calendar.DAY_OF_MONTH) + "-" + cl.get(Calendar.MONTH) + "-" + cl.get(Calendar.YEAR);
                taskDatabaseHandler.editTask(taskArrayList.get(position).getTaskID(), editedName, editedDesc, taskArrayList.get(position).getDateCreated(), date);

                taskArrayList = taskDatabaseHandler.getAllTasks();
                notifyDataSetChanged();
                editDialog.dismiss();
            }
        });
    }
}

class ViewHolder extends RecyclerView.ViewHolder {

    TextView tv_name, tv_date_created, tv_desc, tv_date_modified;
    ImageView iv_delete_task, iv_edit_task;

    public ViewHolder(View itemView) {
        super(itemView);

        tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        tv_date_created = (TextView) itemView.findViewById(R.id.tv_date_created);
        tv_desc = (TextView) itemView.findViewById(R.id.tv_desc);
        tv_date_modified = (TextView) itemView.findViewById(R.id.tv_date_modified);
        iv_delete_task = (ImageView) itemView.findViewById(R.id.iv_delete_task);
        iv_edit_task = (ImageView) itemView.findViewById(R.id.iv_edit_task);
    }
}