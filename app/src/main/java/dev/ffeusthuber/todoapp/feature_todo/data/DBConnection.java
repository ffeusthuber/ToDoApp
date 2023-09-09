package dev.ffeusthuber.todoapp.feature_todo.data;

import java.util.ArrayList;

import dev.ffeusthuber.todoapp.feature_todo.domain.model.Task;

public interface DBConnection {
    public void saveTask(Task task, String username);
    //TODO: Implement Firebase Authentification and remove username from getTasks method
    public ArrayList<Task> getTasks(String username);

}
