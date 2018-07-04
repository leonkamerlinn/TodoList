package com.kamerlin.leon.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.kamerlin.leon.todolist.db.Task;

public class TaskActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(MainActivity.TASK_EXTRA)) {
            Task task = intent.getParcelableExtra(MainActivity.TASK_EXTRA);
            Toast.makeText(this, task.getName(), Toast.LENGTH_LONG).show();
        }

    }
}
