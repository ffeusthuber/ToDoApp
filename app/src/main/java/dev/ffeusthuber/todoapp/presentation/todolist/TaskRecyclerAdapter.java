package dev.ffeusthuber.todoapp.presentation.todolist;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import dev.ffeusthuber.todoapp.R;
import dev.ffeusthuber.todoapp.model.Task;

public class TaskRecyclerAdapter extends FirestoreRecyclerAdapter<Task, TaskRecyclerAdapter.TaskViewHolder>{
    private static final String TAG = "TaskRecyclerAdapter";
    TaskListener taskListener;
    public TaskRecyclerAdapter(@NonNull FirestoreRecyclerOptions<Task> options, TaskListener taskListener) {
        super(options);
        this.taskListener = taskListener;
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
        holder.cbTaskCompleted.setChecked(task.getIsCompleted());
        CharSequence dateCharSeqCreation = DateFormat.format("EEEE, MMM, d, yyyy", task.getDateCreation());
        holder.txtTaskDateCreation.setText(dateCharSeqCreation);
        holder.txtTaskCreator.setText(task.getCreatorId());
        CharSequence dateCharSeqCompletion = DateFormat.format("EEEE, MMM, d, yyyy", task.getDateCompletion());
        holder.txtTaskDateCompletion.setText(dateCharSeqCompletion);
        handleExpandedLayoutVisibility(holder, task);
    }

    private void handleExpandedLayoutVisibility(TaskViewHolder holder, Task task){
        if (task.getIsCardviewExpanded()) {
            TransitionManager.beginDelayedTransition(holder.cvItemTask);
            holder.expandedTaskLayout.setVisibility(View.VISIBLE);
            holder.ibtnTaskDropdown.setVisibility(View.INVISIBLE);
            holder.ibtnTaskDropdown.setClickable(false);
            holder.ibtnTaskDropup.setVisibility(View.VISIBLE);
        } else {
            TransitionManager.beginDelayedTransition(holder.cvItemTask);
            holder.expandedTaskLayout.setVisibility(View.GONE);
            holder.ibtnTaskDropdown.setVisibility(View.VISIBLE);
            holder.ibtnTaskDropdown.setClickable(true);
            holder.ibtnTaskDropup.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        Log.d(TAG, "onDataChanged: CHANGEEEEED!");
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder{
        private final CardView cvItemTask;
        private final TextView txtTaskTitle;
        private final TextView txtTaskDescription;
        private final TextView txtTaskDateCreation;
        private final TextView txtTaskCreator;
        private final TextView txtTaskDateCompletion;
        private final CheckBox cbTaskCompleted;
        private final ImageButton ibtnTaskDropdown;
        private final ImageButton ibtnTaskDropup;
        private final ConstraintLayout expandedTaskLayout;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            cvItemTask = itemView.findViewById(R.id.cvItemTask);
            txtTaskTitle = itemView.findViewById(R.id.txtTaskTitle);
            txtTaskDescription = itemView.findViewById(R.id.txtTaskDescription);
            txtTaskDateCreation = itemView.findViewById(R.id.txtTaskDateCreation);
            txtTaskCreator = itemView.findViewById(R.id.txtTaskCreator);
            txtTaskDateCompletion = itemView.findViewById(R.id.txtTaskDateCompletion);
            cbTaskCompleted = itemView.findViewById(R.id.cbTaskCompleted);
            ibtnTaskDropdown = itemView.findViewById(R.id.ibtnTaskDropdown);
            ibtnTaskDropup = itemView.findViewById(R.id.ibtnTaskDropup);
            expandedTaskLayout = itemView.findViewById(R.id.expandedTaskLayout);

            cbTaskCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    DocumentSnapshot ds = getSnapshots().getSnapshot(getBindingAdapterPosition());
                    Task task = getItem(getBindingAdapterPosition());
                    if (task.getIsCompleted() != isChecked){
                        taskListener.handleCheckChanged(ds,isChecked);
                    }
                }
            });

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

        public void deleteTask(RecyclerView toDoListRecView){
            taskListener.handleDeleteTask(getSnapshots().getSnapshot(getBindingAdapterPosition()),toDoListRecView);
        }
    }

    public interface TaskListener{
        void handleCheckChanged(DocumentSnapshot ds, boolean isChecked);
        void handleDeleteTask(DocumentSnapshot ds, RecyclerView toDoListRecView);
    }
}
