package com.kamerlin.leon.todolist;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.kamerlin.leon.todolist.db.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {
    private final List<Task> mTasks;
    private OnItemClickListener mOnItemClickListener;

    public Task getTask(int position) {
        if (mTasks.size() >= position) {
            return mTasks.get(position);
        }

        return null;
    }

    public TaskAdapter() {
        mTasks = new ArrayList<>();
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);

        void onItemToggled(boolean active, int position);
    }



    private void completionToggled(TaskHolder holder) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemToggled(holder.checkBox.isChecked(), holder.getAdapterPosition());
        }
    }

    private void postItemClick(TaskHolder holder) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(holder.itemView, holder.getAdapterPosition());
        }
    }


    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        holder.bindView(mTasks.get(position));
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public void addTasks(List<Task> tasks) {
        mTasks.addAll(tasks);
        notifyItemRangeInserted(mTasks.size() + 1, tasks.size());
    }

    public void clear() {
        mTasks.clear();
        notifyDataSetChanged();
    }

    public class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView name;
        private final TextView description;
        private final CheckBox checkBox;

        public TaskHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            name = itemView.findViewById(R.id.nameText);
            description = itemView.findViewById(R.id.descriptionText);

            AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
            if (activity instanceof OnItemClickListener) {
                mOnItemClickListener = (OnItemClickListener)activity;
            }

            itemView.setOnClickListener(this);
            checkBox.setOnClickListener(this);
        }

        public void bindView(Task task) {
            checkBox.setChecked(task.isComplete());
            name.setText(task.getName());
            description.setText(task.getDescription());
        }

        @Override
        public void onClick(View v) {
            if (v == checkBox) {
                completionToggled(this);
            } else {
                postItemClick(this);
            }
        }
    }
}
