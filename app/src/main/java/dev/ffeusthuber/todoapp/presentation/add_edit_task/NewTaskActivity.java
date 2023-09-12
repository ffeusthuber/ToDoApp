package dev.ffeusthuber.todoapp.presentation.add_edit_task;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.Date;

import dev.ffeusthuber.todoapp.R;
import dev.ffeusthuber.todoapp.data.DBConnection;
import dev.ffeusthuber.todoapp.data.DBConnectionImpl_Firestore;
import dev.ffeusthuber.todoapp.model.Task;
import dev.ffeusthuber.todoapp.presentation.ActivityStarter;
import dev.ffeusthuber.todoapp.util.DateParser;

public class NewTaskActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private EditText edtTxtTaskTitle;
    private EditText edtTxtTaskDescription;
    private EditText edtTxtTaskCompletionDate;
    private final DateParser dateParser = new DateParser();
    private final DBConnection con = new DBConnectionImpl_Firestore();
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
            con.saveTask(buildTaskObject());
            ActivityStarter.openActivityToDoList(NewTaskActivity.this);
        } else if (id == R.id.btnCancel) {
            Log.d(TAG, "onClick: User clicked btnCancel");
            ActivityStarter.openActivityToDoList(NewTaskActivity.this);
        } else if (id == R.id.edtTxtTaskFinishDate || id == R.id.ibtnCalendar) {
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

    private Task buildTaskObject() {
        return new Task(
                edtTxtTaskTitle.getText().toString(),
                edtTxtTaskDescription.getText().toString(),
                FirebaseAuth.getInstance().getCurrentUser().getUid(),
                false,
                new Date(),
                dateParser.parseStringtoDate(edtTxtTaskCompletionDate.getText().toString()),
                false);
    }
}