package dev.ffeusthuber.todoapp.presentation.todolist;

import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;

import dev.ffeusthuber.todoapp.R;
import dev.ffeusthuber.todoapp.model.NewUserCallback;
import dev.ffeusthuber.todoapp.model.TaskHandler;
import dev.ffeusthuber.todoapp.model.UserHandler;
import dev.ffeusthuber.todoapp.presentation.ActivityStarter;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ToDoListActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener{
    private static final String TAG = "ToDoListActivity";
    private final TaskHandler taskHandler = new TaskHandler();
    private final UserHandler userHandler = new UserHandler();
    private RecyclerView taskRecyclerView;
    private TaskRecyclerAdapter taskRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        taskRecyclerView = findViewById(R.id.recViewToDoList);
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);

        topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.logout){
                    logOutUser();
                    return true;
                } else if (id == R.id.sort_by_title) {
                    Log.d(TAG, "onMenuItemClick: Sort by title");
                    connectNewRecyclerAdapter(FirebaseAuth.getInstance().getCurrentUser().getUid(),"title");
                    return true;
                } else if (id == R.id.sort_by_keyword) {
                    Log.d(TAG, "onMenuItemClick: Sort by keyword");
                    connectNewRecyclerAdapter(FirebaseAuth.getInstance().getCurrentUser().getUid(), "keyword");
                    return true;
                } else if (id == R.id.sort_by_creationdate) {
                    Log.d(TAG, "onMenuItemClick: Sort by creation date");
                    connectNewRecyclerAdapter(FirebaseAuth.getInstance().getCurrentUser().getUid(), "creationDate");
                    return true;
                } else if (id == R.id.sort_by_completiondate) {
                    Log.d(TAG, "onMenuItemClick: Sort by completion date");
                    connectNewRecyclerAdapter(FirebaseAuth.getInstance().getCurrentUser().getUid(), "completionDate");
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
        if (taskRecyclerAdapter != null){
            taskRecyclerAdapter.stopListening();
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
        if (firebaseAuth.getCurrentUser() == null) {
            Log.d(TAG, "onCreate: No user signed in. Starting LoginActivity");
            ActivityStarter.openActivityLogin(ToDoListActivity.this);
            this.finish();
            return;
        }
        Log.d(TAG, "onAuthStateChanged: Username = " + firebaseAuth.getCurrentUser().getDisplayName());
        userHandler.checkIfNewUser(firebaseAuth.getCurrentUser().getUid(), new NewUserCallback() {
            @Override
            public void onResult(boolean isNewUser) {
                String username = "NEW USER";
                // show Username dialog
                userHandler.saveNewUser(firebaseAuth.getCurrentUser().getUid(), username);
            }
        });
        initRecyclerView(firebaseAuth.getCurrentUser().getUid());
    }

    private void initRecyclerView(String userId){
        connectNewRecyclerAdapter(userId,"default");
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(taskRecyclerView);
    }

    private void connectNewRecyclerAdapter(String userId, String sortingOption){
        taskRecyclerAdapter = taskHandler.getTaskRecyclerAdapter(userId, sortingOption);
        taskRecyclerView.setAdapter(taskRecyclerAdapter);
        taskRecyclerAdapter.startListening();
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if(direction == ItemTouchHelper.RIGHT){
                TaskRecyclerAdapter.TaskViewHolder taskViewHolder = (TaskRecyclerAdapter.TaskViewHolder) viewHolder;
                taskViewHolder.deleteTask(taskRecyclerView);
            }
        }

        @Override
        public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(ToDoListActivity.this, R.color.gold))
                    .addActionIcon(R.drawable.ic_delete)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
}