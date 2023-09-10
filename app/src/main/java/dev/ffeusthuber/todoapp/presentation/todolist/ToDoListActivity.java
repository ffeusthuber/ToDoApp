package dev.ffeusthuber.todoapp.presentation.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import dev.ffeusthuber.todoapp.R;
import dev.ffeusthuber.todoapp.presentation.login.LoginActivity;
import dev.ffeusthuber.todoapp.model.Task;
import dev.ffeusthuber.todoapp.model.User;
import dev.ffeusthuber.todoapp.presentation.add_edit_task.NewTaskActivity;

public class ToDoListActivity extends AppCompatActivity {
    private static final String TAG = "ToDoListActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            Log.d(TAG, "onCreate: No user signed in. Starting LoginActivity");
            Intent intent =new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        RecyclerView toDoListRecView = findViewById(R.id.recViewToDoList);
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);

        User currentUser = new User();

        //TODO: Load users Todolist
        //currentUser.addTask(new Task("Add database"));
        //currentUser.addTask(new Task("Rework UI"));
        //currentUser.addTask(new Task("Implement Login"));

        Query query = FirebaseFirestore.getInstance().collection("Tasks");
        FirestoreRecyclerOptions<Task> options = new FirestoreRecyclerOptions.Builder<Task>().setQuery(query, Task.class).build();
        ToDoListRecViewAdapter adapter = new ToDoListRecViewAdapter(options);
        adapter.setToDoList(currentUser.getTasks());
        toDoListRecView.setAdapter(adapter);
        toDoListRecView.setLayoutManager(new LinearLayoutManager(ToDoListActivity.this));

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

    public void onClick(View view){
        int id = view.getId();
        if (id == R.id.btnNewTask) {
            openActivityNewTask();
        }
    }


    private void openActivityNewTask(){
        Intent intent = new Intent(this, NewTaskActivity.class);
        startActivity(intent);
    }

    private void openActivityLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void logOutUser(){
        Log.d(TAG, "logOutUser: Logging out");
        FirebaseAuth.getInstance().signOut();
        openActivityLogin();
        Toast.makeText(ToDoListActivity.this, "Logout", Toast.LENGTH_SHORT).show();
    }
}