package dev.ffeusthuber.todoapp.model;

public class User {
    private String uId;
    private  String username;


    public User(String username){
        //needed for Firebase
    }

    public User(String uId, String username) {
        this.uId = uId;
        this.username = username;
    }


    public String getUsername() {
        return username;
    }

}


