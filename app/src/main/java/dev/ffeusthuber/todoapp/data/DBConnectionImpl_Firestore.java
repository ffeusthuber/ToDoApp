package dev.ffeusthuber.todoapp.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

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
    public boolean isNewUser(String userId) {
        if (usersCollRef.document(userId) != null) {
            Log.d(TAG, "new User: No user with that id in db");
        }
        return false;
    }

    @Override
    public void saveUser(String userId, String username) {
        Map<String, Object> user = new HashMap<>();
        user.put("username", username);
        usersCollRef.document(userId)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG,"User saved successfully");
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
    public void setUsername(String userId, String newUsername) {
        DocumentReference userRef = usersCollRef.document(userId);
        userRef.update("username",newUsername)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "onSuccess: Username changed");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ",e );
                    }
                });
    }

    @Override
    public String getUserId(String username) {
        //implement query for UID
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
                Log.d(TAG, "getQuery: title query");
                break;
            case("keyword"):
                query = tasksCollRef
                        .whereEqualTo("userId", userId)
                        .orderBy("isCompleted", Query.Direction.ASCENDING)
                        .orderBy("keyword", Query.Direction.ASCENDING);
                Log.d(TAG, "getQuery: keyword query");
                break;
            case("creationDate"):
                query = tasksCollRef
                        .whereEqualTo("userId", userId)
                        .orderBy("isCompleted", Query.Direction.ASCENDING)
                        .orderBy("dateCreation", Query.Direction.ASCENDING);
                Log.d(TAG, "getQuery: creation date query");
                break;
            case("completionDate"):
                query = tasksCollRef
                        .whereEqualTo("userId", userId)
                        .orderBy("isCompleted", Query.Direction.ASCENDING)
                        .orderBy("dateCompletion", Query.Direction.ASCENDING);
                Log.d(TAG, "getQuery: completion date query");
                break;
            default:
                query = tasksCollRef
                        .whereEqualTo("userId", userId)
                        .orderBy("isCompleted", Query.Direction.ASCENDING)
                        .orderBy("dateCompletion", Query.Direction.ASCENDING);
                Log.d(TAG, "getQuery: default query");
        }
        return query;
    }
}
