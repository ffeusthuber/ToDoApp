package dev.ffeusthuber.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ActivityNewTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
    }

    public void onClick(View view){
        int id = view.getId();
        if (id == R.id.btnCreateTask) {
            openActivityToDoList();
        } else if (id == R.id.btnCancel1) {
            openActivityToDoList();
        }
    }

    private void openActivityToDoList(){
        Intent intent = new Intent(this, ActivityToDoList.class);
        startActivity(intent);
    }
}