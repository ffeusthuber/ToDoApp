package dev.ffeusthuber.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onBtnLoginClick(View view){
        EditText edtTxtUsername = findViewById(R.id.edtTxtUsername);
        Toast.makeText(ActivityLogin.this,"Login von "+ edtTxtUsername.getText().toString() + " erfolgreich",Toast.LENGTH_LONG).show();

        //To implement: Save Login
        openToDoListActivity();
    }

    private void openToDoListActivity(){
        Intent intent = new Intent(this, ActivityToDoList.class);
        startActivity(intent);
    }

}