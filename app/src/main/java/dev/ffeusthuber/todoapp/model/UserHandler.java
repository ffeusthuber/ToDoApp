package dev.ffeusthuber.todoapp.model;

import dev.ffeusthuber.todoapp.data.DBConnection;
import dev.ffeusthuber.todoapp.data.DBConnectionImpl_Firestore;

public class UserHandler {
    private final DBConnection con = new DBConnectionImpl_Firestore();

    public void checkIfNewUser(String userId, NewUserCallback callback){
        con.checkIfUserIsNew(userId, callback);
    }

    public void saveNewUser(String userId, String username) {
        con.saveUser(userId,username);
    }


}
