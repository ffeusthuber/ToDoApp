package dev.ffeusthuber.todoapp.util;

public interface FirestoreCallback<T> {
    void onCallback(T data);
}
