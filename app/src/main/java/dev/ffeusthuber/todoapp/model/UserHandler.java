package dev.ffeusthuber.todoapp.model;

import dev.ffeusthuber.todoapp.data.DBConnection;
import dev.ffeusthuber.todoapp.data.DBConnectionImpl_Firestore;
import dev.ffeusthuber.todoapp.util.FirestoreCallback;

public class UserHandler {
    private final DBConnection con = new DBConnectionImpl_Firestore();

    public void checkIfNewUser(String userId, FirestoreCallback<Boolean> callback) {
        con.checkIfUserIsNew(userId, callback);
    }

    public void checkIfUsernameIsTaken(String username, FirestoreCallback<Boolean> callback) {
        con.checkIfUsernameIsTaken(username,callback);
    }

    public void saveNewUser(String userId, String username) {
        con.saveUser(userId, username);
    }

    public void getUsername(String userId, FirestoreCallback<String> firestoreCallback){
        con.getUsername(userId,firestoreCallback);
    }


}
