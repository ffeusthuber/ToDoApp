package dev.ffeusthuber.todoapp.presentation.todolist;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

import dev.ffeusthuber.todoapp.R;
import dev.ffeusthuber.todoapp.data.DBConnection;
import dev.ffeusthuber.todoapp.data.DBConnectionImpl_Firestore;
import dev.ffeusthuber.todoapp.presentation.ActivityStarter;

public class ToDoListActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener, ToDoListRecyclerAdapter.TaskListener{
    private static final String TAG = "ToDoListActivity";
    private RecyclerView toDoListRecView;
    private ToDoListRecyclerAdapter toDoListRecyclerAdapter;
    private final DBConnection db = new DBConnectionImpl_Firestore();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        toDoListRecView = findViewById(R.id.recViewToDoList);
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);

        topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.logout){
                    logOutUser();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(this);
        if (toDoListRecyclerAdapter != null){
            toDoListRecyclerAdapter.stopListening();
        }
    }

    public void onClick(View view){
        int id = view.getId();
        if (id == R.id.btnNewTask) {
            ActivityStarter.openActivityNewTask(ToDoListActivity.this);
        }
    }

    private void logOutUser(){
        Log.d(TAG, "logOutUser: Logging out");
        AuthUI.getInstance().signOut(this);
        Toast.makeText(ToDoListActivity.this, "Logout", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if(firebaseAuth.getCurrentUser() == null){
            Log.d(TAG, "onCreate: No user signed in. Starting LoginActivity");
            ActivityStarter.openActivityLogin(ToDoListActivity.this);
            return;
        }
            initRecyclerView(firebaseAuth.getCurrentUser().getUid());
    }

    private void initRecyclerView(String userID){
        toDoListRecyclerAdapter = db.getToDoListRecyclerAdapter(userID,this);
        toDoListRecView.setAdapter(toDoListRecyclerAdapter);

        toDoListRecyclerAdapter.startListening();
    }


    @Override
    public void handleCheckChanged(DocumentSnapshot ds, boolean isChecked) {
        ds.getReference().update("isCompleted", isChecked)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "onSuccess: Changed isCompleted on "+ds.getString("title"));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: "+e.getLocalizedMessage());
                    }
                });

    }
}