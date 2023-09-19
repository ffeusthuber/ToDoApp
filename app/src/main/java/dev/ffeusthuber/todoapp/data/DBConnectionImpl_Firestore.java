package dev.ffeusthuber.todoapp.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import dev.ffeusthuber.todoapp.model.Task;
import dev.ffeusthuber.todoapp.util.FirestoreCallback;

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
    public void checkIfUserIsNew(String userId, FirestoreCallback<Boolean> callback) {
        usersCollRef.document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot ds = task.getResult();
                    if (ds.exists()) {
                        callback.onCallback(false);
                    } else {
                        callback.onCallback(true);
                        Log.d(TAG, "isNewUser - User not found in db");
                    }
                } else {
                    Log.e(TAG, "onComplete: ", task.getException());
                }
            }
        });
    }

    @Override
    public void checkIfUsernameIsTaken(String username, FirestoreCallback<Boolean> callback) {
        usersCollRef.whereEqualTo("username", username).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot qs = task.getResult();
                    callback.onCallback(!qs.isEmpty());
                }
            }
        });
    }

    @Override
    public void saveUser(String userId, String username) {
        Log.d(TAG, "SaveUser called");
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

    @Override
    public void getUsername(String userId, final FirestoreCallback<String> callback) {
        usersCollRef.document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot ds = task.getResult();
                    if(ds.exists()){
                        String username = ds.getString("username");
                        callback.onCallback(username);
                    }else {
                        callback.onCallback(null);
                    }
                }else {
                    callback.onCallback(null);
                }
            }
        });
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
        }
        return query;
    }
}
