package dev.ffeusthuber.todoapp.feature_todo.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import dev.ffeusthuber.todoapp.feature_todo.domain.model.Task;
import dev.ffeusthuber.todoapp.feature_todo.domain.model.User;

public class DBConnection {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void SaveUser(){
        Map<String, Object> user = new HashMap<>();
        user.put("User", "Testuser");
        System.out.println(db.toString());
        db.collection("Users").document("User1")
                        .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("TESTSUCCESS", "Hat funktioniert");
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("TESTFAILED", "Hat nicht funktioniert");
                            }
                        });
        System.out.println(db.toString());
    }

    public void SaveTaskForUser(User user, Task task){

    }
    //TODO: Add new user to db
}
