package dev.ffeusthuber.todoapp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Task {
    private String title;
    private boolean finished;
    private LocalDate finishDate;
    public Task(String title) {
        this.title = title;
        this.finished = false;
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
}

