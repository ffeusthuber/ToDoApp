package dev.ffeusthuber.todoapp.model;

import dev.ffeusthuber.todoapp.data.DBConnection;
import dev.ffeusthuber.todoapp.data.DBConnectionImpl_Firestore;
import dev.ffeusthuber.todoapp.util.IsNewUserCallback;
import dev.ffeusthuber.todoapp.util.IsUsernameTakenCallback;

public class UserHandler {
    private final DBConnection con = new DBConnectionImpl_Firestore();

    public void checkIfNewUser(String userId, IsNewUserCallback callback) {
        con.checkIfUserIsNew(userId, callback);
    }

    public void checkIfUsernameIsTaken(String username, IsUsernameTakenCallback callback) {
        con.checkIfUsernameIsTaken(username,callback);
    }

    public void saveNewUser(String userId, String username) {
        con.saveUser(userId, username);
    }


}
