package dev.ffeusthuber.todoapp.util;

public interface IsUsernameTakenCallback {
    void onResult(boolean isUsernameInUse);
}
