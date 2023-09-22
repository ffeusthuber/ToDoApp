package dev.ffeusthuber.todoapp.model;

public class User {
    private String username;
    private String userId;


    public User(){
        //needed for Firebase
    }

    public User(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }


    public String getUsername() {
        return username;
    }

    public String getUserId() {
        return userId;
    }
}


