package dev.ffeusthuber.todoapp.presentation.todolist;

import android.text.format.DateFormat;
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

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;

import dev.ffeusthuber.todoapp.R;
import dev.ffeusthuber.todoapp.model.Task;

public class ToDoListRecViewAdapter extends FirestoreRecyclerAdapter<Task, ToDoListRecViewAdapter.TaskViewHolder>{

    private ArrayList<Task> toDoList = new ArrayList<>();

    public ToDoListRecViewAdapter(@NonNull FirestoreRecyclerOptions<Task> options) {
        super(options);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_task,parent,false);
        return new TaskViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull TaskViewHolder holder, int position, @NonNull Task task) {
        holder.txtTaskTitle.setText(task.getTitle());
        holder.cbTaskFinished.setChecked(task.getIsfinished());
        CharSequence dateCharSeq = DateFormat.format("EEE, MMM, d, yyy",task.getDateCreated().toDate());
        //holder.txtTaskFinishDate.setText(toDoList.get(position).getFinishDate().toString());

        handleExpandedLayoutVisibility(holder, position);
    }

    private void handleExpandedLayoutVisibility(TaskViewHolder holder, int position){
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

    public class TaskViewHolder extends RecyclerView.ViewHolder{
        private final CardView cvItemTask;
        private final TextView txtTaskTitle;
        private final CheckBox cbTaskFinished;
        private final ImageButton ibtnTaskDropdown, ibtnTaskDropup;
        private final ConstraintLayout expandedTaskLayout;
        private final TextView txtTaskFinishDate;
        public TaskViewHolder(@NonNull View itemView) {
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
                   switchExpandView(toDoList.get(getBindingAdapterPosition()));
                }
            });

            ibtnTaskDropup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switchExpandView(toDoList.get(getBindingAdapterPosition()));
                }
            });
        }

        private void switchExpandView(Task task){
            task.setCardviewExpanded(!task.isCardviewExpanded());
            notifyItemChanged(getBindingAdapterPosition());
        }
    }
}
