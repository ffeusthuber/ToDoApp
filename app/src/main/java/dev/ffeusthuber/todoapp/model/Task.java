package dev.ffeusthuber.todoapp.model;

import com.google.firebase.firestore.Exclude;

import java.util.Date;

public class Task {
    private String title;
    private String keyword;
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

    public Task(String title,String keyword, String description, String userId, String creatorId, boolean isCompleted, Date dateCreation, Date dateCompletion, boolean isCardviewExpanded) {
        this.title = title;
        this.keyword = keyword;
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

    public String getKeyword() {
        return keyword;
    }

    public String getDescription() {
        return description;
    }

    public String getUserId() {
        //needed for firestore
        return userId;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public Date getDateCompletion() {
        return dateCompletion;
    }

    public Date getDateCreation() {
        return dateCreation;
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
                ", keyword='" + keyword + '\'' +
                ", description='" + description + '\'' +
                ", userId='" + userId + '\'' +
                ", creatorId='" + creatorId + '\'' +
                ", isCompleted=" + isCompleted +
                ", dateCreation=" + dateCreation +
                ", dateCompletion=" + dateCompletion +
                ", isCardviewExpanded=" + isCardviewExpanded +
                '}';
    }
}

