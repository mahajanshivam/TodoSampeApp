package com.shivam.todosampleapp.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.shivam.todosampleapp.R;
import com.shivam.todosampleapp.database.TaskDatabaseHandler;

public class BaseActivity extends AppCompatActivity {

    protected TaskDatabaseHandler taskDatabaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        taskDatabaseHandler = new TaskDatabaseHandler(getApplicationContext());
    }

    public void hideKeyboard() {
//        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(et_comment.getWindowToken(), 0);

        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
