package com.kamerlin.leon.todolist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kamerlin.leon.todolist.db.DatabaseContracts.CategoryColumns;
import com.kamerlin.leon.todolist.db.DatabaseContracts.TaskColumns;

import java.util.Random;

public class DbHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 15;
    public static final String DB_NAME = "tasks.db";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(String.format("CREATE TABLE %s (" +
                        "%s INTEGER PRIMARY KEY, " +
                        "%s TEXT UNIQUE NOT NULL, " +
                        "%s TEXT NOT NULL);",
                DatabaseContracts.TABLE_CATEGORIES,
                CategoryColumns._ID,
                CategoryColumns.NAME,
                CategoryColumns.THEME_NAME
        ));

        db.execSQL(String.format("CREATE TABLE %s (" +
                        "%s INTEGER PRIMARY KEY, " +
                        "%s INT NOT NULL, " +
                        "%s INT DEFAULT 0, " +
                        "%s TEXT NOT NULL, " +
                        "%s TEXT, " +
                        "%s INT NOT NULL, " +
                        "%s INT DEFAULT -1, " +
                        "%s INT DEFAULT 0, " +
                        "%s INT NOT NULL, " +
                        "FOREIGN KEY(%s) REFERENCES %s(%s));",
                DatabaseContracts.TABLE_TASKS,
                TaskColumns._ID,
                TaskColumns.DUE_DATE,
                TaskColumns.PRIORITY,
                TaskColumns.NAME,
                TaskColumns.DESCRIPTION,
                TaskColumns.CREATED_AT,
                TaskColumns.REMIND_ME_AT,
                TaskColumns.IS_COMPLETE,
                TaskColumns.CATEGORY_ID,
                TaskColumns.CATEGORY_ID,
                DatabaseContracts.TABLE_CATEGORIES,
                CategoryColumns._ID
        ));







        db.insert(DatabaseContracts.TABLE_CATEGORIES, null, new Category("All", "Red").toContentValues());
        db.insert(DatabaseContracts.TABLE_CATEGORIES, null, new Category("Personal", "Cyan").toContentValues());
        db.insert(DatabaseContracts.TABLE_CATEGORIES, null, new Category("Shopping", "Blue").toContentValues());
        db.insert(DatabaseContracts.TABLE_CATEGORIES, null, new Category("Work", "Green").toContentValues());
        db.insert(DatabaseContracts.TABLE_CATEGORIES, null, new Category("Errands", "Orange").toContentValues());
        db.insert(DatabaseContracts.TABLE_CATEGORIES, null, new Category("Sport", "Blue Grey").toContentValues());
        db.insert(DatabaseContracts.TABLE_CATEGORIES, null, new Category("Movies to watch", "Brown").toContentValues());


        for (int i = 0; i < 50; i++) {
            Task task = new Task.TaskBuilder(String.format("Title %s", i), rangeRandom(2, 7))
                    .setComplete(false)
                    .setCreatedAt(System.currentTimeMillis())
                    .setDescription(String.format("Some description %s", i))
                    .setPriority(rangeRandom(0, 3))
                    .setDueDate(System.currentTimeMillis() + 1000 * 86400 *  rangeRandom(1, 50)).build();
            db.insert(DatabaseContracts.TABLE_TASKS, null, task.toContentValues());
        }




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", DatabaseContracts.TABLE_CATEGORIES));
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", DatabaseContracts.TABLE_TASKS));
        onCreate(db);
    }




    private static int rangeRandom(int min, int max) {
        Random randomGenerator = new Random();
        return randomGenerator.nextInt(max - min + 1);
    }
}
