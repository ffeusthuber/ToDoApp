package dev.ffeusthuber.todoapp.model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

import java.util.Date;

public class Task {
    private String title;
    private String description;
    private String userId;
    private String creatorId;
    private boolean isCompleted;
    private Date dateCreation;
    private Date dateCompletion;
    private boolean isCardviewExpanded;


    public Task() {
        //needed for firestore
    }

    public Task(String title, String description, String userId, String creatorId, boolean isCompleted, Date dateCreation, Date dateCompletion, boolean isCardviewExpanded) {
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.creatorId = creatorId;
        this.isCompleted = isCompleted;
        this.dateCreation = dateCreation;
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

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
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

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Exclude
    public boolean getIsCardviewExpanded() {
        return isCardviewExpanded;
    }

    @Exclude
    public void setCardviewExpanded(boolean cardviewExpanded) {
        isCardviewExpanded = cardviewExpanded;
    }

    @NonNull
    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", uid='" + userId + '\'' +
                ", isCompleted=" + isCompleted +
                ", dateCreated=" + dateCreation +
                ", dateCompletion=" + dateCompletion +
                ", isCardviewExpanded=" + isCardviewExpanded +
                '}';
    }
}

