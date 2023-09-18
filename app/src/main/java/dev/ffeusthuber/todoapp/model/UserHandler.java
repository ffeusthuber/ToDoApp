package dev.ffeusthuber.todoapp.model;

import dev.ffeusthuber.todoapp.data.DBConnection;
import dev.ffeusthuber.todoapp.data.DBConnectionImpl_Firestore;

public class UserHandler {
    private static final String TAG = "UserHandler";
    private final DBConnection con = new DBConnectionImpl_Firestore();

    public void handleNewUser(String userId) {
        if (con.isNewUser(userId)) {
            //do stuff
        }
    }


}
