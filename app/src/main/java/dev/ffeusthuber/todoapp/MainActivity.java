package dev.ffeusthuber.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnLoginClick(View view){
        EditText edtTxtUsername = findViewById(R.id.edtTxtUsername);
        Toast.makeText(MainActivity.this,"Login von "+ edtTxtUsername.getText().toString() + " erfolgreich",Toast.LENGTH_LONG).show();
    }

}