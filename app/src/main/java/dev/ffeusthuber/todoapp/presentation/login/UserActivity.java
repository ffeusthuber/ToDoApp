package dev.ffeusthuber.todoapp.presentation.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import dev.ffeusthuber.todoapp.R;
import dev.ffeusthuber.todoapp.model.UserHandler;
import dev.ffeusthuber.todoapp.presentation.ActivityStarter;
import dev.ffeusthuber.todoapp.util.IsUsernameTakenCallback;

public class UserActivity extends AppCompatActivity {

    private EditText edtUsername;
    private final UserHandler userHandler = new UserHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        edtUsername = findViewById(R.id.edtUsername);
    }

    public void onClick(View view){
        int id = view.getId();
        if (id == R.id.btnSetUsername) {
            validateUsername(edtUsername.getText().toString());
        }
    }
    //add callback
    private void validateUsername(String username){
        if(username.isEmpty()){
            displayUsernameEmptyDialog();
            return;
        }
        userHandler.checkIfUsernameIsTaken(username, new IsUsernameTakenCallback() {
            @Override
            public void onResult(boolean isUsernameInUse) {
                if(!isUsernameInUse){
                    saveUsername();
                    startToDoListActivity();
                }else {
                    displayUsernameTakenDialog();
                }
            }
        });

    }


    private void saveUsername() {
        userHandler.saveNewUser(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid(),edtUsername.getText().toString());
    }

    private void startToDoListActivity() {
        ActivityStarter.openActivityToDoList(UserActivity.this);
        UserActivity.this.finish();
    }

    private void displayUsernameTakenDialog() {
        System.out.println("USERNAME TAKEN");
    }

    private void displayUsernameEmptyDialog() {
        System.out.println("USERNAME EMPTY");
    }

}