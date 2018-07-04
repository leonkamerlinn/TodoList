package com.kamerlin.leon.todolist.db;

import android.content.ContentValues;
import android.database.Cursor;


import static com.kamerlin.leon.todolist.db.DatabaseContracts.*;

public class Category {
    private static final long NO_ID = -1;

    private long mId;
    private String mName;
    private String mThemeName;

    public Category(String categoryName, String themeName) {
        mId = NO_ID;
        mName = categoryName;
        mThemeName = themeName;
    }

    public Category(Cursor cursor) {
        setId(getColumnLong(cursor, CategoryColumns._ID));
        setName(getColumnString(cursor, CategoryColumns.NAME));
        setThemeName(getColumnString(cursor, CategoryColumns.THEME_NAME));
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CategoryColumns.NAME, getName());
        contentValues.put(CategoryColumns.THEME_NAME, getThemeName());
        return contentValues;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getThemeName() {
        return mThemeName;
    }

    public void setThemeName(String themeName) {
        mThemeName = themeName;
    }

    public boolean hasId() {
        return getId() != NO_ID;
    }

    @Override
    public String toString() {
        return String.format("ID: %s, NAME: %s, THEME_NAME %s", getId(), getName(), getThemeName());
    }
}
