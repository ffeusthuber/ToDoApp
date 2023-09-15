package dev.ffeusthuber.todoapp.model;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;
import java.util.Objects;

import dev.ffeusthuber.todoapp.R;
import dev.ffeusthuber.todoapp.data.DBConnection;
import dev.ffeusthuber.todoapp.data.DBConnectionImpl_Firestore;
import dev.ffeusthuber.todoapp.presentation.todolist.ToDoListRecyclerAdapter;
import dev.ffeusthuber.todoapp.util.DateParser;

public class TaskHandler implements ToDoListRecyclerAdapter.TaskListener{
    private static final String TAG = "TaskHandler";
    private final DBConnection con = new DBConnectionImpl_Firestore();


    public void saveNewTask(String taskTitle, String taskCreator, String taskDescription, String dateString){
        con.saveTask(new Task(
                taskTitle,
                taskDescription,
                Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid(),
                taskCreator,
                false,
                new Date(),
                DateParser.parseStringtoDate(dateString),
                false));
    }
    public void saveNewTask(String taskTitle,String taskCreator, String taskDescription, String dateString, String userId){
        con.saveTask(new Task(
                taskTitle,
                taskDescription,
                taskCreator,
                userId,
                false,
                new Date(),
                DateParser.parseStringtoDate(dateString),
                false));
    }


    @Override
    public void handleCheckChanged(DocumentSnapshot ds, boolean isChecked) {
        ds.getReference().update("isCompleted", isChecked)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "onSuccess: Changed isCompleted on "+ds.getString("title"));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: "+e.getLocalizedMessage());
                    }
                });

    }

    @Override
    public void handleDeleteTask(DocumentSnapshot ds, RecyclerView toDoListRecView) {
        DocumentReference documentReference = ds.getReference();
        Task task = ds.toObject(Task.class);
        documentReference.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "onSuccess: Task deleted ");
                        Snackbar.make(toDoListRecView,R.string.task_deleted, Snackbar.LENGTH_LONG)
                                .setAction(R.string.undo, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        assert task != null;
                                        documentReference.set(task);
                                    }
                                }).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ", e);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ",e );
                    }
                });

    }

}
