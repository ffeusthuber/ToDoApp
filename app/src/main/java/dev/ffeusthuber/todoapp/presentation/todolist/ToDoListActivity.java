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
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import dev.ffeusthuber.todoapp.R;
import dev.ffeusthuber.todoapp.model.Task;
import dev.ffeusthuber.todoapp.presentation.ActivityStarter;

public class ToDoListActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener {
    private static final String TAG = "ToDoListActivity";
    private RecyclerView toDoListRecView;
    private ToDoListRecyclerAdapter toDoListRecyclerAdapter;

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
        }
        else{
            initRecyclerView(firebaseAuth.getCurrentUser().getUid());
        }

    }

    private void initRecyclerView(String userID){
        Query query = FirebaseFirestore.getInstance()
                .collection("tasks")
                .whereEqualTo("userId", userID);
        FirestoreRecyclerOptions<Task> options = new FirestoreRecyclerOptions.Builder<Task>()
                .setQuery(query, Task.class)
                .build();
        toDoListRecyclerAdapter = new ToDoListRecyclerAdapter(options);
        toDoListRecView.setAdapter(toDoListRecyclerAdapter);

        toDoListRecyclerAdapter.startListening();
    }


}