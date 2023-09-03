package dev.ffeusthuber.todoapp.feature_todo.presentation.add_edit_task;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import dev.ffeusthuber.todoapp.R;
import dev.ffeusthuber.todoapp.feature_todo.domain.model.Task;
import dev.ffeusthuber.todoapp.feature_todo.presentation.todolist.ToDoListActivity;

public class NewTaskActivity extends AppCompatActivity {

    private EditText edtTxtTaskTitle;
    private EditText edtTxtTaskFinishDate;
    private int finishDateDay,finishDateMonth,finishDateYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        edtTxtTaskTitle = findViewById(R.id.edtTxtTaskTitle);
        edtTxtTaskFinishDate = findViewById(R.id.edtTxtTaskFinishDate);
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
            finishDateDay = calendar.get(Calendar.DATE);
            finishDateMonth = calendar.get(Calendar.MONTH);
            finishDateYear = calendar.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(NewTaskActivity.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    edtTxtTaskFinishDate.setText(getString(R.string.task_display_finishdate,day,month,year));
                }
            },finishDateYear,finishDateMonth,finishDateDay);
            datePickerDialog.show();
        }
    }

    private void addTaskToToDoList() {
        Task task = new Task(edtTxtTaskTitle.getText().toString());
    }

    private void openActivityToDoList(){
        Intent intent = new Intent(this, ToDoListActivity.class);
        startActivity(intent);
    }
}