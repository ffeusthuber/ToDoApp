package dev.ffeusthuber.todoapp.data;

import java.util.ArrayList;

import dev.ffeusthuber.todoapp.model.Task;

public interface DBConnection {
    void saveTask(Task task, String username);
    //TODO: Implement Firebase Authentification and remove username from getTasks method
    ArrayList<Task> getTasks(String username);

}
