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
import dev.ffeusthuber.todoapp.data.DBConnection;
import dev.ffeusthuber.todoapp.data.DBConnectionImpl_Firestore;
import dev.ffeusthuber.todoapp.model.TaskHandler;
import dev.ffeusthuber.todoapp.presentation.ActivityStarter;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ToDoListActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener{
    private static final String TAG = "ToDoListActivity";
    private final TaskHandler taskHandler = new TaskHandler();
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
        toDoListRecyclerAdapter = db.getToDoListRecyclerAdapter(userID,taskHandler);
        toDoListRecView.setAdapter(toDoListRecyclerAdapter);

        toDoListRecyclerAdapter.startListening();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(toDoListRecView);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if(direction == ItemTouchHelper.RIGHT){
                ToDoListRecyclerAdapter.TaskViewHolder taskViewHolder = (ToDoListRecyclerAdapter.TaskViewHolder) viewHolder;
                taskViewHolder.deleteTask(toDoListRecView);
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