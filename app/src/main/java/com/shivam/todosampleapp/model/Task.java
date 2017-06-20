package com.shivam.todosampleapp.model;

public class Task {
    private String taskID;
    private String taskName;
    private String taskDescription;
    private String dateCreated;
    private String dateUpdated;

    public Task() {

    }

    public Task(String taskName, String taskDescription, String dateCreated, String dateUpdated) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    public String getTaskID() {
        return taskID;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}
