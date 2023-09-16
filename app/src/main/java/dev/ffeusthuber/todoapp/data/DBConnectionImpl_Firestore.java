package dev.ffeusthuber.todoapp.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import dev.ffeusthuber.todoapp.model.Task;

public class DBConnectionImpl_Firestore implements DBConnection{
    private static final String TAG = "DBConnectionImpl_Firestore";
    static final CollectionReference tasksCollRef = FirebaseFirestore.getInstance().collection("tasks");
    static final CollectionReference usersCollRef = FirebaseFirestore.getInstance().collection("users");

    @Override
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


    public Query getQuery(String userId, String orderOption){
        Query query;
        switch (orderOption){
            case("title"):
                query = tasksCollRef
                        .whereEqualTo("userId", userId)
                        .orderBy("isCompleted", Query.Direction.ASCENDING)
                        .orderBy("title", Query.Direction.ASCENDING);
                break;
            default:
                query  = tasksCollRef
                    .whereEqualTo("userId", userId)
                    .orderBy("isCompleted", Query.Direction.ASCENDING)
                    .orderBy("dateCompletion", Query.Direction.ASCENDING);
        }

        return query;
    }

    @Override
    public String getUserId(String username) {
        //implement query for UID
        return null;
    }
}
