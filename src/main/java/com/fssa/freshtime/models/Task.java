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
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private TaskPriority priority;
    private TaskStatus status;
    private String notes;
    private LocalDateTime reminder;
    private LocalDateTime createdDateTime;
    
    
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
	public LocalDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	public LocalDateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
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
	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}
	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", userId=" + userId + ", taskName=" + taskName + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", priority=" + priority + ", status=" + status + ", notes=" + notes
				+ ", reminder=" + reminder + ", createdDateTime=" + createdDateTime + "]";
	}

    
    

}

