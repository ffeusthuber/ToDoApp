package dev.ffeusthuber.todoapp.model;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;
import java.util.Objects;

import dev.ffeusthuber.todoapp.data.DBConnection;
import dev.ffeusthuber.todoapp.data.DBConnectionImpl_Firestore;
import dev.ffeusthuber.todoapp.util.DateParser;

public class TaskHandler {
    private final DBConnection con = new DBConnectionImpl_Firestore();


    public void saveNewTask(String taskTitle, String taskCreator, String taskDescription, String dateString){
        con.saveTask(new Task(
                taskTitle,
                taskDescription,
                Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid(),
                taskCreator,
                false,
                new Date(),
                DateParser.parseStringtoDate(dateString),
                false));
    }
    public void saveNewTask(String taskTitle,String taskCreator, String taskDescription, String dateString, String userId){
        con.saveTask(new Task(
                taskTitle,
                taskDescription,
                taskCreator,
                userId,
                false,
                new Date(),
                DateParser.parseStringtoDate(dateString),
                false));
    }

}
