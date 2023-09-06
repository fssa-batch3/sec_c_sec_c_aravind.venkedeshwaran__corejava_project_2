package com.fssa.freshtime.models;


import com.fssa.freshtime.models.enums.TaskPriority;
import com.fssa.freshtime.models.enums.TaskStatus;

import java.time.*;

/**
 * Task Object
 */
public class Task {

    private int taskId;
    private int userId;
    private String taskName;
    private String description;
    private LocalDate dueDate;
    private TaskPriority priority;
    private TaskStatus status;
    private String notes;
    private LocalDateTime reminder;
    private LocalDate createdDate;
    private LocalDateTime createdTime;
    private LocalDateTime statusUpdatedTime;

//    default constructor
    public Task(){}
    
    
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getReminder() {
        return reminder;
    }

    public void setReminder(LocalDateTime reminder) {
        this.reminder = reminder;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdLocalDate) {
        this.createdDate = createdLocalDate;
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


    @Override
    public String toString() {
        return "Task{" +
                "\n    taskId=" + taskId +
                "\n    userId=" + userId +
                ",\n    taskName='" + taskName + '\'' +
                ",\n    description='" + description + '\'' +
                ",\n    dueDate=" + dueDate +
                ",\n    priority='" + priority + '\'' +
                ",\n    status='" + status + '\'' +
                ",\n    notes='" + notes + '\'' +
                ",\n    reminder=" + reminder +
                ",\n    createdDate=" + createdDate +
                ",\n    createdTime=" + createdTime +
                '\n' + '}';
    }
    

}

