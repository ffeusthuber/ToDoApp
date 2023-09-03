package dev.ffeusthuber.todoapp.feature_todo.presentation.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import java.util.ArrayList;

import dev.ffeusthuber.todoapp.R;
import dev.ffeusthuber.todoapp.feature_todo.domain.model.Task;

public class ToDoListRecViewAdapter extends RecyclerView.Adapter<ToDoListRecViewAdapter.ViewHolder>{

    private ArrayList<Task> toDoList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_task,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTaskTitle.setText(toDoList.get(position).getTitle());
        holder.cbTaskFinished.setChecked(toDoList.get(position).isFinished());
        holder.txtTaskFinishDate.setText(toDoList.get(position).getFinishDate().toString());

        handleExpandedLayoutVisibility(holder, position);
    }

    private void handleExpandedLayoutVisibility(ViewHolder holder, int position){
        if(toDoList.get(position).isCardviewExpanded()){
            TransitionManager.beginDelayedTransition(holder.cvItemTask);
            holder.expandedTaskLayout.setVisibility(View.VISIBLE);
            holder.ibtnTaskDropdown.setVisibility(View.GONE);
        } else{
            TransitionManager.beginDelayedTransition(holder.cvItemTask);
            holder.expandedTaskLayout.setVisibility(View.GONE);
            holder.ibtnTaskDropdown.setVisibility(View.VISIBLE);
        }
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
        private final CardView cvItemTask;
        private final TextView txtTaskTitle;
        private final CheckBox cbTaskFinished;
        private final ImageButton ibtnTaskDropdown, ibtnTaskDropup;
        private final ConstraintLayout expandedTaskLayout;
        private final TextView txtTaskFinishDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvItemTask = itemView.findViewById(R.id.cvItemTask);
            txtTaskTitle = itemView.findViewById(R.id.txtTaskTitle);
            cbTaskFinished = itemView.findViewById(R.id.cbTaskFinished);
            ibtnTaskDropdown = itemView.findViewById(R.id.ibtnTaskDropdown);

            expandedTaskLayout = itemView.findViewById(R.id.expandedTaskLayout);
            ibtnTaskDropup = itemView.findViewById(R.id.ibtnTaskDropup);
            txtTaskFinishDate = itemView.findViewById(R.id.txtTaskFinishDate);

            ibtnTaskDropdown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   switchExpandView(toDoList.get(getAdapterPosition()));
                }
            });

            ibtnTaskDropup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switchExpandView(toDoList.get(getAdapterPosition()));
                }
            });
        }

        private void switchExpandView(Task task){
            task.setCardviewExpanded(!task.isCardviewExpanded());
            notifyItemChanged(getAdapterPosition());
        }
    }
}
