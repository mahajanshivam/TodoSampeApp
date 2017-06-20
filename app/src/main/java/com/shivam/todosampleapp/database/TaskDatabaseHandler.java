package com.shivam.todosampleapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.shivam.todosampleapp.model.Task;

import java.util.ArrayList;

public class TaskDatabaseHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "TodoSampleDB";
    public static final int DATABASE_VERSION = 4;

    private static final String TABLE_TASK = "tasks";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESC = "description";
    private static final String KEY_DATE_CREATED = "dateCreated";
    private static final String KEY_DATE_UPDATED = "dateUpdated";
    private Context context;

    public TaskDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("task", "inside database oncreate");
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASK + "("
                + KEY_ID + " INTEGER PRIMARY KEY ," + KEY_NAME + " TEXT,"
                + KEY_DESC + " TEXT," + KEY_DATE_CREATED + " TEXT," + KEY_DATE_UPDATED + " TEXT" + ");";
        db.execSQL(CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("task", "inside onUpgrade db");
    }

    public void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, task.getTaskName());
        contentValues.put(KEY_DESC, task.getTaskDescription());
        contentValues.put(KEY_DATE_CREATED, task.getDateCreated());
        contentValues.put(KEY_DATE_UPDATED, task.getDateUpdated());

        db.insert(TABLE_TASK, null, contentValues);
        db.close();
        Toast.makeText(context, "task added successfully", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> taskList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_TASK;        // Select All Query

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {                     // looping through all rows and adding to list
            do {
                Task task = new Task();
                task.setTaskID(cursor.getString(0));
                task.setTaskName(cursor.getString(1));
                task.setTaskDescription(cursor.getString(2));
                task.setDateCreated(cursor.getString(3));
                task.setDateUpdated(cursor.getString(4));

                taskList.add(task);           // Adding contact to list
            } while (cursor.moveToNext());
        }

        return taskList;                         // return task list
    }

    public boolean deleteTask(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_TASK, KEY_ID + "=" + id, null) > 0;
    }

    public void editTask(String taskId, String newName, String newDesc, String dateCreated, String dateModified) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(KEY_ID, taskId);
        args.put(KEY_NAME, newName);
        args.put(KEY_DESC, newDesc);
        args.put(KEY_DATE_CREATED, dateCreated);
        args.put(KEY_DATE_UPDATED, dateModified);

        db.update(TABLE_TASK, args, "id=" + taskId, null);
    }

}
