package dev.ffeusthuber.todoapp.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import dev.ffeusthuber.todoapp.model.Task;

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

    public ArrayList<Task> getTasks(String username) {
        updateTaskList(username);
        return  tasks;
    }

    @Override
    public String getUserId(String username) {
        //implement query for UID
        return null;
    }

    private void updateTaskList(String username){
        tasksCollRef.document(username)
                .collection("Tasks")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        tasks.clear();
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        System.out.println("................................");
                        for(DocumentSnapshot ds:snapshotList){
                            Task task = ds.toObject(Task.class);
                            tasks.add(task);
                        }
                        System.out.println("................................");
                        Log.d(TAG, "Retrieved all tasks!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, e.toString());
                    }
                });
    }
}
