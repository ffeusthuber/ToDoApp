package dev.ffeusthuber.todoapp.presentation.login;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

import dev.ffeusthuber.todoapp.R;
import dev.ffeusthuber.todoapp.presentation.todolist.ToDoListActivity;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            this::onSignInResult
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            Log.d(TAG, "onCreate: User already signed in. Starting ToDoListActivity");
            Intent intent =new Intent(this, ToDoListActivity.class);
            startActivity(intent);
            this.finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClick(View view){
        int id = view.getId();
        if (id == R.id.btnLogin) {
            createSignInIntent();
        }
    }

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result){
        if(result.getResultCode() == RESULT_OK){
            Log.d(TAG, "onSignInResult: Sign in Success");
            openActivityToDoList();
        } else{
            Log.d(TAG, "onSignInResult: Sign in Failed");
        }
    }



    public void createSignInIntent(){
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build()
        );

        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers).build();
        //TODO: add logo
        signInLauncher.launch(signInIntent);
    }



    private void openActivityToDoList(){
        Log.d(TAG, "openActivityToDoList: starting ActivityToDoList");
        Intent intent = new Intent(this, ToDoListActivity.class);
        startActivity(intent);
        finish();
    }


}