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
    private final CollectionReference userCollRef;
    ArrayList<Task> tasks = new ArrayList<>();

    public DBConnectionImpl_Firestore(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        userCollRef = db.collection("Users");
    }

    public void saveTask(Task task, String username){
        userCollRef.document(username).collection("Tasks").document()
                .set(task)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "Task saved!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, e.toString());
                    }
                });
        getTasks("TESTUSER1");
    }

    public ArrayList<Task> getTasks(String username) {
        updateTaskList(username);
        return  tasks;
    }

    private void updateTaskList(String username){
        userCollRef.document(username)
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
