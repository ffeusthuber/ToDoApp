package dev.ffeusthuber.todoapp.model;

import com.google.firebase.firestore.Exclude;

import java.util.Date;

public class Task {
    private String title;
    private String description;
    private String userId;
    private boolean isCompleted;
    private Date dateCreated;
    private Date dateCompletion;
    private boolean isCardviewExpanded;


    public Task() {
        //needed for firestore
    }

    public Task(String title, String description, String userId, boolean isCompleted, Date dateCreated, Date dateCompletion, boolean isCardviewExpanded) {
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.isCompleted = isCompleted;
        this.dateCreated = dateCreated;
        this.dateCompletion = dateCompletion;
        this.isCardviewExpanded = isCardviewExpanded;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Date getDateCompletion() {
        return dateCompletion;
    }

    public void setDateCompletion(Date dateCompletion) {
        this.dateCompletion = dateCompletion;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Exclude
    public boolean getIsCardviewExpanded() {
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
                ", description='" + description + '\'' +
                ", uid='" + userId + '\'' +
                ", isCompleted=" + isCompleted +
                ", dateCreated=" + dateCreated +
                ", dateCompletion=" + dateCompletion +
                ", isCardviewExpanded=" + isCardviewExpanded +
                '}';
    }
}

