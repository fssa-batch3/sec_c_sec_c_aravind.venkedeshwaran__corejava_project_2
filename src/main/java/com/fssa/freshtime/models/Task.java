package com.fssa.freshtime.models;


import com.fssa.freshtime.enums.TaskPriority;
import com.fssa.freshtime.enums.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.time.*;

/**
 * Task Object
 */
public class Task {

    private int taskId;
    private int userId;
    private String taskName;
    private String taskDescription;
    private LocalDate dueDate;
    private TaskPriority priority;
    private TaskStatus taskStatus;
    private String taskNotes;
    private LocalDateTime reminder;
    private LocalDate createdLocalDate;
    private LocalDateTime createdTime;
    private LocalDateTime statusUpdatedTime;

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

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
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

    public LocalDateTime getStatusUpdatedTime() {
        return statusUpdatedTime;
    }

    public void setStatusUpdatedTime(LocalDateTime statusUpdatedTime) {
        this.statusUpdatedTime = statusUpdatedTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "\n    taskId=" + taskId +
                ",\n    taskName='" + taskName + '\'' +
                ",\n    taskDescription='" + taskDescription + '\'' +
                ",\n    dueDate=" + dueDate +
                ",\n    priority='" + priority + '\'' +
                ",\n    taskStatus='" + taskStatus + '\'' +
                ",\n    taskNotes='" + taskNotes + '\'' +
                ",\n    reminder=" + reminder +
                ",\n    createdLocalDate=" + createdLocalDate +
                ",\n    createdTime=" + createdTime +
                '\n' + '}';
    }

}

