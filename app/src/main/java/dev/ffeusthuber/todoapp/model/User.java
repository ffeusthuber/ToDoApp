package dev.ffeusthuber.todoapp.model;

import java.util.ArrayList;

public class User {

    private final String username;
    private final ArrayList<Task> tasks;

    public User() {
        //get existing Data
       tasks = new ArrayList<>();
       username = "TESTUSER1";
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public void removeTask(Task task){
        tasks.remove(task);
    }
    public ArrayList<Task> getTasks(){
        return tasks;
    }

    public String getUsername() {
        return username;
    }

}


