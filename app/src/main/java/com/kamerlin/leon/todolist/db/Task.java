package com.kamerlin.leon.todolist.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static com.kamerlin.leon.todolist.db.DatabaseContracts.*;

public class Task implements Parcelable {
    /* Constants representing missing data */
    private static final long NO_DATE = Long.MAX_VALUE;
    private static final long NO_ID = -1;

    private long mId;
    private long mDueDate;
    private int mPriority;
    private String mName;
    private String mDescription;
    private long mCreatedAt;
    private long mRemindMe;
    private boolean mIsComplete;
    private long mCategoryId;




    public Task(TaskBuilder builder) {
        setId(builder.mId);
        setDueDate(builder.mDueDate);
        setPriority(builder.mPriority);
        setName(builder.mName);
        setDescription(builder.mDescription);
        setCreatedAt(builder.mCreatedAt);
        setRemindMe(builder.mRemindMe);
        setComplete(builder.mIsComplete);
        setCategoryId(builder.mCategoryId);
    }


    public Task(Cursor cursor) {
        setId(getColumnLong(cursor, TaskColumns._ID));
        setDueDate(getColumnLong(cursor, TaskColumns.DUE_DATE));
        setPriority(getColumnInt(cursor, TaskColumns.PRIORITY));
        setName(getColumnString(cursor, TaskColumns.NAME));
        setDescription(getColumnString(cursor, TaskColumns.DESCRIPTION));
        setCreatedAt(getColumnLong(cursor, TaskColumns.CREATED_AT));
        setRemindMe(getColumnLong(cursor, TaskColumns.REMIND_ME_AT));
        setComplete(getColumnInt(cursor, TaskColumns.IS_COMPLETE) == 1);
        setCategoryId(getColumnLong(cursor, CategoryColumns._ID));
    }

    protected Task(Parcel in) {
        mId = in.readLong();
        mDueDate = in.readLong();
        mPriority = in.readInt();
        mName = in.readString();
        mDescription = in.readString();
        mCreatedAt = in.readLong();
        mRemindMe = in.readLong();
        mIsComplete = in.readByte() != 0;
        mCategoryId = in.readLong();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskColumns.DUE_DATE, getDueDate());
        contentValues.put(TaskColumns.PRIORITY, getPriority());
        contentValues.put(TaskColumns.NAME, getName());
        contentValues.put(TaskColumns.DESCRIPTION, getDescription());
        contentValues.put(TaskColumns.CREATED_AT, getCreatedAt());
        contentValues.put(TaskColumns.REMIND_ME_AT, getRemindMe());
        contentValues.put(TaskColumns.IS_COMPLETE, isComplete());
        contentValues.put(TaskColumns.CATEGORY_ID, getCategoryId());


        return contentValues;
    }

    @Override
    public String toString() {
        return String.format("ID: %s, Name: %s", getId(), getName());
    }

    public boolean hasDueDate() {
        return getDueDate() != Long.MAX_VALUE;
    }


    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public long getDueDate() {
        return mDueDate;
    }

    public void setDueDate(long dueDate) {
        mDueDate = dueDate;
    }

    public int getPriority() {
        return mPriority;
    }

    public void setPriority(int priority) {
        mPriority = priority;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public long getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(long createdAt) {
        mCreatedAt = createdAt;
    }

    public long getRemindMe() {
        return mRemindMe;
    }

    public void setRemindMe(long remindMe) {
        mRemindMe = remindMe;
    }

    public boolean isComplete() {
        return mIsComplete;
    }

    public void setComplete(boolean complete) {
        mIsComplete = complete;
    }

    public long getCategoryId() {
        return mCategoryId;
    }

    public void setCategoryId(long categoryId) {
        mCategoryId = categoryId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeLong(mDueDate);
        dest.writeInt(mPriority);
        dest.writeString(mName);
        dest.writeString(mDescription);
        dest.writeLong(mCreatedAt);
        dest.writeLong(mRemindMe);
        dest.writeByte((byte) (mIsComplete ? 1 : 0));
        dest.writeLong(mCategoryId);
    }

    public static class TaskBuilder {
        private long mId;
        private long mDueDate;
        private int mPriority;
        private String mName;
        private String mDescription;
        private long mCreatedAt;
        private long mRemindMe;
        private boolean mIsComplete;
        private long mCategoryId;

        public TaskBuilder(String name, long categoryId) {
            mName = name;
            mCategoryId = categoryId;
        }

        public TaskBuilder setId(long id) {
            mId = id;
            return this;
        }

        public TaskBuilder setDueDate(long dueDate) {
            mDueDate = dueDate;
            return this;
        }

        public TaskBuilder setPriority(int priority) {
            mPriority = priority;
            return this;
        }

        public TaskBuilder setDescription(String description) {
            mDescription = description;
            return this;
        }

        public TaskBuilder setCreatedAt(long createdAt) {
            mCreatedAt = createdAt;
            return this;
        }

        public TaskBuilder setRemindMe(long remindMe) {
            mRemindMe = remindMe;
            return this;
        }

        public TaskBuilder setComplete(boolean complete) {
            mIsComplete = complete;
            return this;
        }

        public Task build() {
            return new Task(this);
        }
    }
}
