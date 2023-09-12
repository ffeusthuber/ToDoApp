package dev.ffeusthuber.todoapp.presentation;

import android.content.Context;
import android.content.Intent;

import dev.ffeusthuber.todoapp.presentation.add_edit_task.NewTaskActivity;
import dev.ffeusthuber.todoapp.presentation.login.LoginActivity;
import dev.ffeusthuber.todoapp.presentation.todolist.ToDoListActivity;

public class ActivityStarter {

    public static void openActivityNewTask(Context context) {
        Intent intent = new Intent(context, NewTaskActivity.class);
        context.startActivity(intent);
    }

    public static void openActivityLogin(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void openActivityToDoList(Context context) {
        Intent intent = new Intent(context, ToDoListActivity.class);
        context.startActivity(intent);
    }

}
