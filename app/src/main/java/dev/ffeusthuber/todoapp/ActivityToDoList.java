package dev.ffeusthuber.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ActivityToDoList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        //load user todolist
    }


    public void onClick(View view){
        int id = view.getId();
        if (id == R.id.btnEditTask) {
            openActivityEditTask();
        } else if (id == R.id.btnNewTask) {
            openActivityNewTask();
        }
    }

    private void openActivityNewTask(){
        Intent intent = new Intent(this, ActivityNewTask.class);
        startActivity(intent);
    }

    private void openActivityEditTask(){
        Intent intent = new Intent(this, ActivityEditTask.class);
        startActivity(intent);
    }
}