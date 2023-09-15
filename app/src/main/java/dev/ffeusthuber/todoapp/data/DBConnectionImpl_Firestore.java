package dev.ffeusthuber.todoapp.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import java.util.ArrayList;

import dev.ffeusthuber.todoapp.model.Task;
import dev.ffeusthuber.todoapp.presentation.todolist.ToDoListActivity;
import dev.ffeusthuber.todoapp.presentation.todolist.ToDoListRecyclerAdapter;

public class DBConnectionImpl_Firestore implements DBConnection{
    private static final String TAG = "DBConnectionImpl_Firestore";
    private final CollectionReference tasksCollRef;
    private final CollectionReference usersCollRef;
    ArrayList<Task> tasks = new ArrayList<>();

    public DBConnectionImpl_Firestore(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        tasksCollRef = db.collection("tasks");
        usersCollRef = db.collection("users");
    }

    public void saveTask(Task task){
        tasksCollRef.document()
                .set(task)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "Task saved successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, e.toString());
                    }
                });
    }

    @Override
    public Task getTask(String documentId) {
        Task test = new Task();
        usersCollRef
                .document(documentId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        //test = documentSnapshot.toObject(Task.class);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ", e);
                    }
                });
        return null;
    }

    @Override
    public ToDoListRecyclerAdapter getToDoListRecyclerAdapter(String userId, ToDoListActivity activity) {
        Query query = tasksCollRef
                .whereEqualTo("userId", userId);
        FirestoreRecyclerOptions<Task> options = new FirestoreRecyclerOptions.Builder<Task>()
                .setQuery(query, Task.class)
                .build();
        return new ToDoListRecyclerAdapter(options, activity);
    }

    @Override
    public String getUserId(String username) {
        //implement query for UID
        return null;
    }
}
