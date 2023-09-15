package dev.ffeusthuber.todoapp.data;

import com.google.firebase.firestore.Query;

import dev.ffeusthuber.todoapp.model.Task;

public interface DBConnection {
    void saveTask(Task task);
    Task getTask(String documentId);
    Query getQuery(String userId);
    String getUserId(String username);

}
