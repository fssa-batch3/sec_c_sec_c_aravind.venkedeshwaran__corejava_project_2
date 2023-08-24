package com.fssa.freshtime.models;

public class Tasktags {
    private int taskId;
    private int tagId;
    private String taskName;
    private String tagName;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return "Tasktags{" +
                "taskId=" + taskId +
                ", tagId=" + tagId +
                ", tagName='" + tagName + '\'' +
                '}';
    }
}
