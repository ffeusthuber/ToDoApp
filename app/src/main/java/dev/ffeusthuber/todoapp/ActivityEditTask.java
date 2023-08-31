package dev.ffeusthuber.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ActivityEditTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
    }


    public void onClick(View view){
        int id = view.getId();
        if (id == R.id.btnEditTask) {
            openActivityToDoList();
        } else if (id == R.id.btnCancel2) {
            openActivityToDoList();
        }
    }

    private void openActivityToDoList(){
        Intent intent = new Intent(this, ActivityToDoList.class);
        startActivity(intent);
    }
}