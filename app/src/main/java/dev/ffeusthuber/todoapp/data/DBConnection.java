package dev.ffeusthuber.todoapp.data;

import java.util.ArrayList;

import dev.ffeusthuber.todoapp.model.Task;

public interface DBConnection {
    void saveTask(Task task);
    Task getTask(String documentId);

    ArrayList<Task> getTasks(String userId);

    String getUserId(String username);

}
