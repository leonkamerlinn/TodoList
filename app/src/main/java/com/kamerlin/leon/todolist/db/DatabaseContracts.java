package com.kamerlin.leon.todolist.db;

import android.database.Cursor;
import android.provider.BaseColumns;

public class DatabaseContracts {

    //Priority first, Completed last, the rest by date
    //public static final String DEFAULT_SORT = String.format("%s ASC, %s DESC, %s ASC", TaskColumns.IS_COMPLETE, TaskColumns.IS_PRIORITY, TaskColumns.DUE_DATE);

    //Completed last, then by date, followed by priority
    //public static final String DATE_SORT = String.format("%s ASC, %s ASC, %s DESC", TaskColumns.IS_COMPLETE, TaskColumns.DUE_DATE, TaskColumns.IS_PRIORITY);


    public static final String TABLE_TASKS = "tasks";
    public static final String TABLE_CATEGORIES = "categories";

    public static final class TaskColumns implements BaseColumns {
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String IS_COMPLETE = "is_complete";
        public static final String PRIORITY = "priority";
        public static final String DUE_DATE = "due_date";
        public static final String CREATED_AT = "created_at";
        public static final String REMIND_ME_AT = "remind_me_at";
        public static final String CATEGORY_ID = "category_id";
    }

    public static final class CategoryColumns implements BaseColumns {
        public static final String NAME = "name";
        public static final String THEME_NAME = "theme_name";
    }


    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong( cursor.getColumnIndex(columnName) );
    }
}
