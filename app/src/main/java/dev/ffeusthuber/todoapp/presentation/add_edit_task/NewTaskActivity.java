package dev.ffeusthuber.todoapp.presentation.add_edit_task;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.Objects;

import dev.ffeusthuber.todoapp.R;
import dev.ffeusthuber.todoapp.model.TaskHandler;
import dev.ffeusthuber.todoapp.presentation.ActivityStarter;


public class NewTaskActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private EditText edtTxtTaskTitle;
    private EditText edtTxtTaskDescription;
    private EditText edtTxtTaskCompletionDate;
    private EditText edtTxtTaskKeyword;

    private final TaskHandler taskHandler = new TaskHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        edtTxtTaskTitle = findViewById(R.id.edtTxtTaskTitle);
        edtTxtTaskDescription = findViewById(R.id.edtTxtDescription);
        edtTxtTaskCompletionDate = findViewById(R.id.edtTxtTaskFinishDate);
        edtTxtTaskKeyword = findViewById(R.id.edtTxtTaskKeyword);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar_newTask);
        setItemClickListener(toolbar);
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

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnCreateTask) {
            Log.d(TAG, "onClick: User clicked btnCreateTask");
            //TODO: add functionality to save tasks for other users
            if (validData()) {
                saveNewTask();
                ActivityStarter.openActivityToDoList(NewTaskActivity.this);
                this.finish();
            }

        }
        else if (id == R.id.edtTxtTaskFinishDate || id == R.id.ibtnCalendar) {
            final Calendar calendar = Calendar.getInstance();
            int finishDateDay = calendar.get(Calendar.DATE);
            int finishDateMonth = calendar.get(Calendar.MONTH);
            int finishDateYear = calendar.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(NewTaskActivity.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    edtTxtTaskCompletionDate.setText(getString(R.string.task_display_completionDate, day, month+1, year));
                }
            }, finishDateYear, finishDateMonth, finishDateDay);
            datePickerDialog.show();
        }
    }

    private boolean validData(){
        if(edtTxtTaskCompletionDate.getText().toString().isEmpty()){
            displayMissingCompletionDateDialog();
            return false;
        }
        //TODO: add check for username
        return true;
    }

    private void displayMissingCompletionDateDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(NewTaskActivity.this);
        builder.setMessage(R.string.missing_completionDate)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        builder.create().show();
    }

    private void saveNewTask(){
        taskHandler.handleSaveNewTask(
                edtTxtTaskTitle.getText().toString(),
                edtTxtTaskKeyword.getText().toString(),
                Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid(),
                edtTxtTaskDescription.getText().toString(),
                edtTxtTaskCompletionDate.getText().toString());
    }
}