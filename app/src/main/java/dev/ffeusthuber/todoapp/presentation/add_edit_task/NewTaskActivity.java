package dev.ffeusthuber.todoapp.presentation.add_edit_task;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

import dev.ffeusthuber.todoapp.R;
import dev.ffeusthuber.todoapp.data.DBConnectionImpl_Firestore;
import dev.ffeusthuber.todoapp.model.DateParser;
import dev.ffeusthuber.todoapp.model.Task;
import dev.ffeusthuber.todoapp.presentation.todolist.ToDoListActivity;

public class NewTaskActivity extends AppCompatActivity {

    private EditText edtTxtTaskTitle;
    private EditText edtTxtTaskDescription;
    private EditText edtTxtTaskCompletionDate;
    private final DateParser dateParser = new DateParser();
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
            addTaskToToDoList();
            openActivityToDoList();
        } else if (id == R.id.btnCancel) {
            openActivityToDoList();
        } else if (id == R.id.edtTxtTaskFinishDate || id == R.id.ibtnCalendar) {
            final Calendar calendar =Calendar.getInstance();
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

    private void addTaskToToDoList() {
        Task task = buildTaskObject();
        DBConnectionImpl_Firestore con = new DBConnectionImpl_Firestore();
        con.saveTask(task, "TESTUSER1");
    }

    private Task buildTaskObject() {
        return new Task(
                edtTxtTaskTitle.getText().toString(),
                edtTxtTaskDescription.getText().toString(),
                false,
                new Date(),
                dateParser.parseStringtoDate(edtTxtTaskCompletionDate.getText().toString()),
                false);
    }

    //TODO: seperate Logic from view

    private void openActivityToDoList() {
        Intent intent = new Intent(this, ToDoListActivity.class);
        startActivity(intent);
    }
}