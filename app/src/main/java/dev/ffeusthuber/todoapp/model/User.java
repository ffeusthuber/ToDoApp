package dev.ffeusthuber.todoapp.model;

public class User {
    private  String username;


    public User(){
        //needed for Firebase
    }

    public User(String uId, String username) {
        this.username = username;
    }


    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "User{" +
                ", username='" + username + '\'' +
                '}';
    }
}


