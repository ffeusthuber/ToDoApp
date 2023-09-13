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

import dev.ffeusthuber.todoapp.R;
import dev.ffeusthuber.todoapp.model.Task;

public class ToDoListRecyclerAdapter extends FirestoreRecyclerAdapter<Task, ToDoListRecyclerAdapter.TaskViewHolder>{

    public ToDoListRecyclerAdapter(@NonNull FirestoreRecyclerOptions<Task> options) {
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
        holder.txtTaskDescription.setText(task.getDescription());
        holder.cbTaskFinished.setChecked(task.getIsCompleted());
        CharSequence dateCharSeq = DateFormat.format("EEEE, MMM, d, yyyy", task.getDateCreated());
        holder.txtTaskFinishDate.setText(dateCharSeq);

        handleExpandedLayoutVisibility(holder, task);
    }

    private void handleExpandedLayoutVisibility(TaskViewHolder holder, Task task){
        if (task.getIsCardviewExpanded()) {
            TransitionManager.beginDelayedTransition(holder.cvItemTask);
            holder.expandedTaskLayout.setVisibility(View.VISIBLE);
            holder.ibtnTaskDropdown.setVisibility(View.GONE);
        } else {
            TransitionManager.beginDelayedTransition(holder.cvItemTask);
            holder.expandedTaskLayout.setVisibility(View.GONE);
            holder.ibtnTaskDropdown.setVisibility(View.VISIBLE);
        }
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder{
        private final CardView cvItemTask;
        private final TextView txtTaskTitle;
        private final TextView txtTaskDescription;
        private final TextView txtTaskFinishDate;
        private final CheckBox cbTaskFinished;
        private final ImageButton ibtnTaskDropdown;
        private final ImageButton ibtnTaskDropup;
        private final ConstraintLayout expandedTaskLayout;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            cvItemTask = itemView.findViewById(R.id.cvItemTask);
            txtTaskTitle = itemView.findViewById(R.id.txtTaskTitle);
            txtTaskDescription = itemView.findViewById(R.id.txtTaskDescription);
            txtTaskFinishDate = itemView.findViewById(R.id.txtTaskFinishDate);
            cbTaskFinished = itemView.findViewById(R.id.cbTaskFinished);
            ibtnTaskDropdown = itemView.findViewById(R.id.ibtnTaskDropdown);
            ibtnTaskDropup = itemView.findViewById(R.id.ibtnTaskDropup);
            expandedTaskLayout = itemView.findViewById(R.id.expandedTaskLayout);

            ibtnTaskDropdown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switchExpandView(getItem(getBindingAdapterPosition()));
                }
            });

            ibtnTaskDropup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switchExpandView(getItem(getBindingAdapterPosition()));
                }
            });
        }

        private void switchExpandView(Task task){
            task.setCardviewExpanded(!task.getIsCardviewExpanded());
            notifyItemChanged(getBindingAdapterPosition());
        }
    }
}
