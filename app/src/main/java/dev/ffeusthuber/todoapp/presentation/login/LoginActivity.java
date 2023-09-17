package dev.ffeusthuber.todoapp.presentation.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

import dev.ffeusthuber.todoapp.R;
import dev.ffeusthuber.todoapp.presentation.ActivityStarter;

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
            ActivityStarter.openActivityToDoList(LoginActivity.this);
            this.finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClick(View view){
        int id = view.getId();
        if (id == R.id.btnLogin) {
            createSignInIntent();
            this.finish();
        }
    }

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result){
        if(result.getResultCode() == RESULT_OK){
            Log.d(TAG, "onSignInResult: Sign in Success");
            ActivityStarter.openActivityToDoList(LoginActivity.this);
        } else{
            IdpResponse response = result.getIdpResponse();
            if(response == null){
                Log.d(TAG, "onSignInResult: User canceled sign in request");
            } else {
                Log.e(TAG, "onSignInResult: ", response.getError());
            }
        }
    }

    public void createSignInIntent(){
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build()
        );

        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers).build();
        //TODO: add logo
        signInLauncher.launch(signInIntent);
    }
}