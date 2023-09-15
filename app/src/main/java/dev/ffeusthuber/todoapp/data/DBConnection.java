package dev.ffeusthuber.todoapp.data;

import dev.ffeusthuber.todoapp.model.Task;
import dev.ffeusthuber.todoapp.presentation.todolist.ToDoListActivity;
import dev.ffeusthuber.todoapp.presentation.todolist.ToDoListRecyclerAdapter;

public interface DBConnection {
    void saveTask(Task task);
    Task getTask(String documentId);
    ToDoListRecyclerAdapter getToDoListRecyclerAdapter(String userId, ToDoListActivity activity);
    String getUserId(String username);

}
