package com.kamerlin.leon.todolist.db;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.kamerlin.leon.todolist.MainActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.ReplaySubject;

public class DbService extends IntentService {
    private static final String EXTRA_CATEGORY_NAME = "extra_category_name";
    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();

    private static final String TAG = DbService.class.getSimpleName();
    private static final String ACTION_INSERT = TAG + ".INSERT";
    private static final String ACTION_UPDATE = TAG + ".UPDATE";
    private static final String ACTION_DELETE = TAG + ".DELETE";
    private static final String ACTION_SELECT_ALL_CATEGORIES = TAG + ".SELECT_ALL_CATEGORIES";
    private static final String ACTION_SELECT_ALL_TASKS = TAG + ".SELECT_ALL_TASKS";
    private static final String ACTION_SELECT_TASKS_BY_CATEGORY = TAG + ".SELECT_TASKS_BY_CATEGORY";
    private SQLiteDatabase mDatabase;

    public static void selectAllCategories(Context context) {
        Intent intent = new Intent(context, DbService.class);
        intent.setAction(ACTION_SELECT_ALL_CATEGORIES);
        context.startService(intent);
    }

    public static void selectAllTasks(Context context) {
        Intent intent = new Intent(context, DbService.class);
        intent.setAction(ACTION_SELECT_ALL_TASKS);
        context.startService(intent);
    }

    private ReplaySubject<List<Task>> taskSubjects = ReplaySubject.create();
    private ReplaySubject<List<Category>> categorySubjects = ReplaySubject.create();

    public static void selectTasksByCategory(Context context, CharSequence title) {
        Intent intent = new Intent(context, DbService.class);
        intent.setAction(ACTION_SELECT_TASKS_BY_CATEGORY);
        intent.putExtra(EXTRA_CATEGORY_NAME, title);
        context.startService(intent);
    }

    public Observable<List<Task>> getTasksObservable() {
        return taskSubjects;
    }

    public Observable<List<Category>> getCategorysObservable() {
        return categorySubjects;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DbHelper dbHelper = new DbHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

    }

    private Category getCategoryByName(String name) {
        Cursor c = mDatabase.query(DatabaseContracts.TABLE_CATEGORIES, null, String.format("%s=?", DatabaseContracts.CategoryColumns.NAME), new String[] {name}, null, null, null);
        if (c.moveToFirst()) {
            return new Category(c);
        }

        return null;
    }

    private List<Task> getTasksByCategoryName(String categoryName) {
        List<Task> tasks = new ArrayList<>();
        Category category = getCategoryByName(categoryName);
        if (category == null) {
            return null;
        }

        Cursor c = mDatabase.query(DatabaseContracts.TABLE_TASKS, null, String.format("%s=?", DatabaseContracts.TaskColumns.CATEGORY_ID), new String[] {String.valueOf(category.getId())}, null, null, null);
        while (c.moveToNext()) {
            tasks.add(new Task(c));
        }
        return tasks;
    }

    private List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        Cursor c = mDatabase.query(DatabaseContracts.TABLE_CATEGORIES, null, null, null, null, null, null);
        while (c.moveToNext()) {
            categories.add(new Category(c));
        }
        return categories;
    }

    private List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        Cursor c = mDatabase.query(DatabaseContracts.TABLE_TASKS, null, null, null, null, null, null);
        while (c.moveToNext()) {
            tasks.add(new Task(c));
        }

        return tasks;
    }


    public DbService() {
        super(TAG);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }



    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (ACTION_SELECT_ALL_CATEGORIES.equals(intent.getAction())) {
            categorySubjects.onNext(getAllCategories());
        } else if (ACTION_SELECT_ALL_TASKS.equals(intent.getAction())) {
            taskSubjects.onNext(getAllTasks());
        } else if (ACTION_SELECT_TASKS_BY_CATEGORY.equals(intent.getAction())) {
            String category = intent.getStringExtra(EXTRA_CATEGORY_NAME);
            taskSubjects.onNext(getTasksByCategoryName(category));
        }
    }

    public class LocalBinder extends Binder {
        public DbService getService() {
            // Return this instance of LocalService so clients can call public methods
            return DbService.this;
        }
    }
}
