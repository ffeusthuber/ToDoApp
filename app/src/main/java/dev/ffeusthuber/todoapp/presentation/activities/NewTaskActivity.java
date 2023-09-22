package dev.ffeusthuber.todoapp.presentation.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.Objects;

import dev.ffeusthuber.todoapp.R;
import dev.ffeusthuber.todoapp.model.TaskHandler;
import dev.ffeusthuber.todoapp.model.User;
import dev.ffeusthuber.todoapp.model.UserHandler;
import dev.ffeusthuber.todoapp.presentation.ActivityStarter;
import dev.ffeusthuber.todoapp.util.FirestoreCallback;


public class NewTaskActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private EditText edtTaskTitle;
    private EditText edtTaskDescription;
    private EditText edtTaskCompletionDate;
    private EditText edtTaskKeyword;
    private EditText edtOtherUser;

    private final TaskHandler taskHandler = new TaskHandler();
    private final UserHandler userHandler = new UserHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        edtTaskTitle = findViewById(R.id.edtTxtTaskTitle);
        edtTaskDescription = findViewById(R.id.edtTxtDescription);
        edtTaskCompletionDate = findViewById(R.id.edtTxtTaskFinishDate);
        edtTaskKeyword = findViewById(R.id.edtTxtTaskKeyword);
        edtOtherUser = findViewById(R.id.edtOtherUser);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar_newTask);
        setItemClickListener(toolbar);

        Switch switchAssignToOtherUser = findViewById(R.id.switchAssignToOtherUser);
        setOnCheckedChangeListener(switchAssignToOtherUser);
    }

    private void setItemClickListener(MaterialToolbar toolbar) {
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menuItem_back) {
                    ActivityStarter.openActivityToDoList(NewTaskActivity.this);
                    NewTaskActivity.this.finish();
                    return true;
                }
                return false;
            }
        });
    }

    private void setOnCheckedChangeListener(Switch switchAssignToOtherUser) {
        switchAssignToOtherUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    edtOtherUser.setVisibility(View.VISIBLE);
                    edtOtherUser.setClickable(true);
                    return;
                }
                edtOtherUser.setVisibility(View.INVISIBLE);
                edtOtherUser.setClickable(false);
                edtOtherUser.setText("");
            }
        });
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnCreateTask) {
            Log.d(TAG, "onClick: User clicked btnCreateTask");
            validateData(new FirestoreCallback<Boolean>() {
                @Override
                public void onCallback(Boolean isValid) {
                    if (isValid){
                        saveNewTask();
                        ActivityStarter.openActivityToDoList(NewTaskActivity.this);
                        NewTaskActivity.this.finish();
                    }
                }
            });
        }
        else if (id == R.id.edtTxtTaskFinishDate || id == R.id.ibtnCalendar) {
            showDatePickerDialog();
        }
    }

    private void validateData(final FirestoreCallback<Boolean> callback){
        if(edtTaskCompletionDate.getText().toString().isEmpty()){
            Toast.makeText(NewTaskActivity.this, R.string.missing_completionDate, Toast.LENGTH_LONG).show();
            callback.onCallback(false);
            return;
        }

        if(!edtOtherUser.getText().toString().isEmpty()){
            userHandler.checkIfUsernameIsTaken(edtOtherUser.getText().toString(), new FirestoreCallback<Boolean>() {
                @Override
                public void onCallback(Boolean isTaken) {
                    if(!isTaken){
                        Toast.makeText(NewTaskActivity.this,R.string.user_not_found,Toast.LENGTH_SHORT).show();
                        callback.onCallback(false);
                    } else {
                        callback.onCallback(true);
                    }
                }
            });
        }else{callback.onCallback(true);}
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int finishDateDay = calendar.get(Calendar.DATE);
        int finishDateMonth = calendar.get(Calendar.MONTH);
        int finishDateYear = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(NewTaskActivity.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                edtTaskCompletionDate.setText(getString(R.string.task_display_completionDate, day, month+1, year));
            }
        }, finishDateYear, finishDateMonth, finishDateDay);
        datePickerDialog.show();
    }

    private void saveNewTask(){
        if(edtOtherUser.getText().toString().isEmpty()){
            taskHandler.handleSaveNewTask(
                    edtTaskTitle.getText().toString(),
                    edtTaskKeyword.getText().toString(),
                    Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid(),
                    edtTaskDescription.getText().toString(),
                    edtTaskCompletionDate.getText().toString());
        } else{
            userHandler.getUserId(edtOtherUser.getText().toString(), new FirestoreCallback<String>() {
                @Override
                public void onCallback(String otherUserId) {
                    taskHandler.handleSaveNewTask(
                            edtTaskTitle.getText().toString(),
                            edtTaskKeyword.getText().toString(),
                            Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid(),
                            otherUserId,
                            edtTaskDescription.getText().toString(),
                            edtTaskCompletionDate.getText().toString());
                }
            });

        }
    }
}