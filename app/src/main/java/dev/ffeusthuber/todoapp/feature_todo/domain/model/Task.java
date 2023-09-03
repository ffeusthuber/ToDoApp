package dev.ffeusthuber.todoapp.feature_todo.domain.model;

import java.time.LocalDate;

public class Task {
    private String title;
    private boolean finished;
    private boolean isCardviewExpanded;
    private LocalDate finishDate;

    public Task(String title) {
        this.title = title;
        this.finished = false;
        this.isCardviewExpanded=false;
        this.finishDate = LocalDate.now();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public boolean isCardviewExpanded() {
        return isCardviewExpanded;
    }

    public void setCardviewExpanded(boolean cardviewExpanded) {
        isCardviewExpanded = cardviewExpanded;
    }
}
