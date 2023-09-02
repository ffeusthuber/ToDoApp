package dev.ffeusthuber.todoapp;

public class Task {
    private String title;
    private boolean finished;

    public Task(String title) {
        this.title = title;
        this.finished = false;
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
}

