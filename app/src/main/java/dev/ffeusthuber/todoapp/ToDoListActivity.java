package dev.ffeusthuber.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class ToDoListActivity extends AppCompatActivity {
    private RecyclerView toDoListRecView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        toDoListRecView = findViewById(R.id.recViewToDoList);

        //load user todolist (temp solution till db)
        ArrayList<Task> todoList = new ArrayList<>();
        todoList.add(new Task("Add database"));
        todoList.add(new Task("Rework UI"));
        todoList.add(new Task("Implement Login"));

        ToDoListRecViewAdapter adapter = new ToDoListRecViewAdapter();
        adapter.setToDoList(todoList);
        toDoListRecView.setAdapter(adapter);
        toDoListRecView.setLayoutManager(new LinearLayoutManager(ToDoListActivity.this));
    }

    protected void onStart() {
        super.onStart();
        //refresh todolist
    }

    public void onClick(View view){
        int id = view.getId();
        if (id == R.id.btnNewTask) {
            openActivityNewTask();
        }
    }

    private void openActivityNewTask(){
        Intent intent = new Intent(this, NewTaskActivity.class);
        startActivity(intent);
    }
}