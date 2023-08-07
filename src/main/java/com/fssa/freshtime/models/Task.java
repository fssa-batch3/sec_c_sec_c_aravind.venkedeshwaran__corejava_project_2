package com.fssa.freshtime.models;


import java.util.ArrayList;
import java.util.HashMap;
import java.time.*;

/**
 * Task Object
 */
public class Task {

    private int taskId;
    private String taskName;
    private String taskDescription;
    private LocalDate dueDate;
    private String priority;
    private String taskStatus;
    private String taskNotes;
    private LocalDateTime reminder;
    private String taskTag;
    private static HashMap<String, ArrayList<String> > taskTagsMap;
    private LocalDate createdLocalDate;
    private LocalDateTime createdTime;

//    default constructor
    public Task(){
        LocalDate today = LocalDate.now();
        this.createdLocalDate = today;

        // Format the LocalTime object to display only hours and minutes
        LocalDateTime currentTime = LocalDateTime.now();

        this.createdTime = currentTime;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueLocalDate) {
        this.dueDate = dueLocalDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskNotes() {
        return taskNotes;
    }

    public void setTaskNotes(String taskNotes) {
        this.taskNotes = taskNotes;
    }

    public LocalDateTime getReminder() {
        return reminder;
    }

    public void setReminder(LocalDateTime reminder) {
        this.reminder = reminder;
    }

    public void setTag(String taskTag){
        this.taskTag = taskTag;
    }

    public String getTaskTag(){
        return taskTag;
    }

    public HashMap<String, ArrayList<String>> getTaskTagsMap(){
        return taskTagsMap;
    }

    public LocalDate getCreatedDate() {
        return createdLocalDate;
    }

    public void setCreatedDate(LocalDate createdLocalDate) {
        this.createdLocalDate = createdLocalDate;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", dueDate=" + dueDate +
                ", priority='" + priority + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                ", taskNotes='" + taskNotes + '\'' +
                ", reminder=" + reminder +
                ", taskTag='" + taskTag + '\'' +
                ", createdLocalDate=" + createdLocalDate +
                ", createdTime=" + createdTime +
                '}';
    }
}

