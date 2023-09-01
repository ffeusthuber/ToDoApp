package dev.ffeusthuber.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NewTaskActivity extends AppCompatActivity {

    private EditText edtTxtTaskName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        edtTxtTaskName = findViewById(R.id.edtTxtTaskName);
    }


    public void onClick(View view){
        int id = view.getId();
        if (id == R.id.btnCreateTask) {
            saveNewTask();
            openActivityToDoList();
        } else if (id == R.id.btnCancel) {
            openActivityToDoList();
        }
    }

    private void saveNewTask() {
        Task task = new Task(edtTxtTaskName.getText().toString());
    }

    private void openActivityToDoList(){
        Intent intent = new Intent(this, ToDoListActivity.class);
        startActivity(intent);
    }
}