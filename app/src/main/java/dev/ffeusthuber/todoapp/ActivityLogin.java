package dev.ffeusthuber.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityLogin extends AppCompatActivity {
    private EditText edtTxtUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtTxtUsername = findViewById(R.id.edtTxtUsername);
    }

    public void onBtnLoginClick(View view){
        Toast.makeText(ActivityLogin.this,"Login von "+ edtTxtUsername.getText().toString() + " erfolgreich",Toast.LENGTH_SHORT).show();

        //To implement: Save Login
        openActivityToDoList();
    }

    private void openActivityToDoList(){
        Intent intent = new Intent(this, ActivityToDoList.class);
        startActivity(intent);
    }

}