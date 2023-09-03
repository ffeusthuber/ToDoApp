package dev.ffeusthuber.todoapp.feature_todo.domain.model;

import java.util.ArrayList;

public class ToDoList {
    private ArrayList<Task> tasks;

    public ToDoList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public void populateListFromDB(){
    }

    public void saveListToDB(){
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
}
