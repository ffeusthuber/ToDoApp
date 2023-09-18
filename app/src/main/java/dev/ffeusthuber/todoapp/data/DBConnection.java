package dev.ffeusthuber.todoapp.data;

import com.google.firebase.firestore.Query;

import dev.ffeusthuber.todoapp.model.NewUserCallback;
import dev.ffeusthuber.todoapp.model.Task;

public interface DBConnection {
    void saveTask(Task task);
    Query getQuery(String userId, String orderOption);
    void checkIfUserIsNew(String userId, NewUserCallback newUserCallback);
    void saveUser(String userId, String username);
    void setUsername(String userId, String newUsername);
    String getUserId(String username);

}
