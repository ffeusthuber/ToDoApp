package dev.ffeusthuber.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ToDoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        //load user todolist
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