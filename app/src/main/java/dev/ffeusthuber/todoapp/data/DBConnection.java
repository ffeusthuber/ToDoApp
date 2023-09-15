package dev.ffeusthuber.todoapp.data;

import dev.ffeusthuber.todoapp.model.Task;
import dev.ffeusthuber.todoapp.model.TaskHandler;
import dev.ffeusthuber.todoapp.presentation.todolist.ToDoListRecyclerAdapter;

public interface DBConnection {
    void saveTask(Task task);
    Task getTask(String documentId);
    ToDoListRecyclerAdapter getToDoListRecyclerAdapter(String userId, TaskHandler taskHandler);
    String getUserId(String username);

}
