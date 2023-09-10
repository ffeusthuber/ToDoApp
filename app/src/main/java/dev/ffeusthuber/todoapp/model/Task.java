package dev.ffeusthuber.todoapp.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;

public class Task {
    private String title;
    private boolean isCompleted;
    private Timestamp dateCreated;
    private boolean isCardviewExpanded;


    public Task(){
        //needed for firestore
    }

    public Task(String title, boolean isCompleted, Timestamp dateCreated, boolean isCardviewExpanded) {
        this.title = title;
        this.isCompleted = isCompleted;
        this.dateCreated = dateCreated;
        this.isCardviewExpanded = isCardviewExpanded;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getIsfinished() {
        return isCompleted;
    }

    public void setIsfinished(boolean isfinished) {
        this.isCompleted = isfinished;
    }


    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Exclude
    public boolean isCardviewExpanded() {
        return isCardviewExpanded;
    }
    @Exclude
    public void setCardviewExpanded(boolean cardviewExpanded) {
        isCardviewExpanded = cardviewExpanded;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", isCompleted=" + isCompleted +
                ", dateCreated=" + dateCreated +
                ", isCardviewExpanded=" + isCardviewExpanded +
                '}';
    }
}

