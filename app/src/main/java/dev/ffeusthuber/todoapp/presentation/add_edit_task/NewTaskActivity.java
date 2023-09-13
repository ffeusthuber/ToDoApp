package dev.ffeusthuber.todoapp.presentation.add_edit_task;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import dev.ffeusthuber.todoapp.R;

import dev.ffeusthuber.todoapp.model.TaskHandler;
import dev.ffeusthuber.todoapp.presentation.ActivityStarter;


public class NewTaskActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private EditText edtTxtTaskTitle;
    private EditText edtTxtTaskDescription;
    private EditText edtTxtTaskCompletionDate;

    private final TaskHandler taskHandler = new TaskHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        edtTxtTaskTitle = findViewById(R.id.edtTxtTaskTitle);
        edtTxtTaskDescription = findViewById(R.id.edtTxtDescription);
        edtTxtTaskCompletionDate = findViewById(R.id.edtTxtTaskFinishDate);
    }


    public void onClick(View view){
        int id = view.getId();
        if (id == R.id.btnCreateTask) {
            Log.d(TAG, "onClick: User clicked btnCreateTask");
            //TODO: add functionality to save tasks for other users
            taskHandler.saveNewTask(
                    edtTxtTaskTitle.getText().toString(),
                    edtTxtTaskDescription.getText().toString(),
                    edtTxtTaskCompletionDate.getText().toString());
            ActivityStarter.openActivityToDoList(NewTaskActivity.this);
        }
        else if (id == R.id.btnCancel) {
            Log.d(TAG, "onClick: User clicked btnCancel");
            ActivityStarter.openActivityToDoList(NewTaskActivity.this);
        }
        else if (id == R.id.edtTxtTaskFinishDate || id == R.id.ibtnCalendar) {
            final Calendar calendar = Calendar.getInstance();
            int finishDateDay = calendar.get(Calendar.DATE);
            int finishDateMonth = calendar.get(Calendar.MONTH);
            int finishDateYear = calendar.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(NewTaskActivity.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    edtTxtTaskCompletionDate.setText(getString(R.string.task_display_finishdate, day, month, year));
                }
            }, finishDateYear, finishDateMonth, finishDateDay);
            datePickerDialog.show();
        }
    }
}