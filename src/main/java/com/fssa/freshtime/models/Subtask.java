package com.fssa.freshtime.models;

import java.time.LocalDateTime;

import com.fssa.freshtime.models.enums.TaskPriority;
import com.fssa.freshtime.models.enums.TaskStatus;

public class Subtask {
	private int subtaskId;
	private int taskId;
	private String subtaskName;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
    private TaskStatus status;
    private TaskPriority priority;
    private String notes;
    private LocalDateTime reminder;
    private LocalDateTime createdDateTime;
    
    
	public int getSubtaskId() {
		return subtaskId;
	}
	public void setSubtaskId(int subtaskId) {
		this.subtaskId = subtaskId;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public String getSubtaskName() {
		return subtaskName;
	}
	public void setSubtaskName(String subtaskName) {
		this.subtaskName = subtaskName;
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
		return "Subtask [subtaskId=" + subtaskId + ", taskId=" + taskId + ", subtaskName=" + subtaskName
				+ ", startdate=" + startDate + ", enddate=" + endDate + ", status=" + status + ", priority=" + priority + ", notes=" + notes
				+ ", reminder=" + reminder + ", createdDateTime=" + createdDateTime + "]";
	}
    
    
}
