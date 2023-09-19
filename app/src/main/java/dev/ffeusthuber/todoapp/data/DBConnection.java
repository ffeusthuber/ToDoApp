package dev.ffeusthuber.todoapp.data;

import com.google.firebase.firestore.Query;

import dev.ffeusthuber.todoapp.model.Task;
import dev.ffeusthuber.todoapp.util.IsNewUserCallback;
import dev.ffeusthuber.todoapp.util.IsUsernameTakenCallback;

public interface DBConnection {
    void saveTask(Task task);

    Query getQuery(String userId, String orderOption);

    void checkIfUserIsNew(String userId, IsNewUserCallback isNewUserCallback);

    void checkIfUsernameIsInUse(String username, IsUsernameTakenCallback isUsernameTakenCallback);

    void saveUser(String userId, String username);

    void setUsername(String userId, String newUsername);

    String getUserId(String username);

}
