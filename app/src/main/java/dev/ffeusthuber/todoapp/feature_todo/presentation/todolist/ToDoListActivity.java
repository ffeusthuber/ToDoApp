package dev.ffeusthuber.todoapp.feature_todo.presentation.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import dev.ffeusthuber.todoapp.R;
import dev.ffeusthuber.todoapp.feature_todo.domain.model.Task;
import dev.ffeusthuber.todoapp.feature_todo.domain.model.User;
import dev.ffeusthuber.todoapp.feature_todo.presentation.add_edit_task.NewTaskActivity;

public class ToDoListActivity extends AppCompatActivity {
    private RecyclerView toDoListRecView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        toDoListRecView = findViewById(R.id.recViewToDoList);


        User currentUser = new User();
        //TODO: Load users Todolist
        currentUser.addTask(new Task("Add database"));
        currentUser.addTask(new Task("Rework UI"));
        currentUser.addTask(new Task("Implement Login"));


        ToDoListRecViewAdapter adapter = new ToDoListRecViewAdapter();
        adapter.setToDoList(currentUser.getTasks());
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