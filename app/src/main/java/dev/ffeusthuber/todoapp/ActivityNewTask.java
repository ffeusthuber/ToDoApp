package dev.ffeusthuber.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityNewTask extends AppCompatActivity {

    private EditText edtTaskName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        edtTaskName = findViewById(R.id.edtTxtTaskName);
    }


    protected void onStart() {
        super.onStart();
        //refresh todolist
    }

    public void onClick(View view){
        int id = view.getId();
        if (id == R.id.btnCreateTask) {
            // save new Task
            openActivityToDoList();
        } else if (id == R.id.btnCancel) {
            openActivityToDoList();
        }
    }

    private void openActivityToDoList(){
        Intent intent = new Intent(this, ActivityToDoList.class);
        startActivity(intent);
    }
}