package dev.ffeusthuber.todoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ToDoListRecViewAdapter extends RecyclerView.Adapter<ToDoListRecViewAdapter.ViewHolder>{
    public ToDoListRecViewAdapter() {
    }

    private ArrayList<Task> toDoList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_task,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTaskTitle.setText(toDoList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return toDoList.size();
    }

    public void setToDoList(ArrayList<Task> toDoList) {
        this.toDoList = toDoList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTaskTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTaskTitle = itemView.findViewById(R.id.txtTaskTitle);
        }
    }
}
