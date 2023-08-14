package com.fssa.freshtime.errors;

public interface TaskErrors {
    public static final String INVALID_TASK_NAME = "Invalid Task Name: Task Name Can Not Be Empty or less than 3 Character";
    public static final String INVALID_TASK_ID = "Invalid Task Id: Task Id Can Not Be Zero Or Negative";
    public static final String INVALID_TASK_DESCRIPTION = "Invalid Task Description: Task Description CanNot Be Empty or less than 10 Character";
    public static final String INVALID_DUEDATE = "Invalid DueDate: Due date can't be before todays date";
    public static final String INVALID_TASK_NOTES= "Invalid Task Notes: Task Notes CanNot Be Empty or less than 10 Character";
    public static final String INVALID_REMINDER= "Invalid Remainder: Remainder can't be current time or minute before or day before";
    public static final String INVALID_PRIORITY = "Invalid Priority: Priority can only be HIGH, MEDIUM or LOW";
    public static final String INVALID_STATUS  ="Invalid Status: Status can only be NOTSTARTED, INPROGRESS, COMPLETED, CANCELLED, OVERDUE, or SCHEDULE";
    public static final String  INVALID_CREATEDDATE = "Invalid Created Date: Created date neither the future date nor the before date";
    public static final String  INVALID_CREATEDTIME = "Invalid Created Time: Created Time neither the future time nor the before time";
    public static final String INVALID_TAG = "Invalid Task Tag: tags can't be empty or less than 3 characters";
}
