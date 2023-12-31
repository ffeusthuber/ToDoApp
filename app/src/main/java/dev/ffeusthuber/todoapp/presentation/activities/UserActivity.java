package dev.ffeusthuber.todoapp.presentation.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import dev.ffeusthuber.todoapp.R;
import dev.ffeusthuber.todoapp.model.UserHandler;
import dev.ffeusthuber.todoapp.presentation.ActivityStarter;
import dev.ffeusthuber.todoapp.util.FirestoreCallback;

public class UserActivity extends AppCompatActivity {

    private EditText edtUsername;
    private final UserHandler userHandler = new UserHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        edtUsername = findViewById(R.id.edtUsername);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar_user);
        setItemClickListener(toolbar);
        fillInCurrentUsername();
    }

    private void setItemClickListener(MaterialToolbar toolbar) {
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menuItem_back) {
                    ActivityStarter.openActivityToDoList(UserActivity.this);
                    UserActivity.this.finish();
                    return true;
                }
                return false;
            }
        });
    }

    private void fillInCurrentUsername() {
        userHandler.checkIfNewUser(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid(), new FirestoreCallback<Boolean>() {
            @Override
            public void onCallback(Boolean isNewUser) {
                if (!isNewUser) {
                    userHandler.getUsername(FirebaseAuth.getInstance().getCurrentUser().getUid(), new FirestoreCallback<String>() {
                        @Override
                        public void onCallback(String username) {
                            if (username != null) {
                                edtUsername.setText(username);
                            }
                        }
                    });
                }
            }
        });
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
        userHandler.checkIfUsernameIsTaken(username, new FirestoreCallback<Boolean>() {
            @Override
            public void onCallback(Boolean isUsernameInUse) {
                if(!isUsernameInUse){
                    saveUser();
                    startToDoListActivity();
                }else {
                    displayUsernameTakenDialog();
                }
            }
        });

    }

    private void saveUser() {
        userHandler.saveUser(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid(),edtUsername.getText().toString());
    }

    private void startToDoListActivity() {
        ActivityStarter.openActivityToDoList(UserActivity.this);
        UserActivity.this.finish();
    }

    private void displayUsernameTakenDialog() {
        Toast.makeText(UserActivity.this, R.string.chosen_username_is_already_taken,Toast.LENGTH_SHORT).show();
    }

    private void displayUsernameEmptyDialog() {
        Toast.makeText(UserActivity.this, R.string.username_can_not_be_empty,Toast.LENGTH_SHORT).show();
    }

}