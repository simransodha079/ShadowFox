package com.example.todo;

public class Task {
    private String title;
    private int priority;
    private boolean isComplete;

    public Task(String title, int priority) {
        this.title = title;
        this.priority = priority;
        this.isComplete = false;
    }

    public String getTitle() {
        return title;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}
