package dev.ffeusthuber.todoapp.data;

import com.google.firebase.firestore.Query;

import dev.ffeusthuber.todoapp.model.Task;
import dev.ffeusthuber.todoapp.util.FirestoreCallback;

public interface DBConnection {
    void saveTask(Task task);

    Query getQuery(String userId, String orderOption);

    void checkIfUserIsNew(String userId, FirestoreCallback<Boolean> callback);

    void checkIfUsernameIsTaken(String username, FirestoreCallback<Boolean> callback);

    void saveUser(String userId, String username);

    void setUsername(String userId, String newUsername);

    String getUserId(String username);

    void getUsername(String userId, FirestoreCallback<String> callback);
}
