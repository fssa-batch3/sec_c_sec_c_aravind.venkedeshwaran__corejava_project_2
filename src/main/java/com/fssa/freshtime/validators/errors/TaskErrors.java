package com.fssa.freshtime.validators.errors;

public class TaskErrors {

    public static final String TASK_NULL = "Invalid Task: Task can not be null.";

    public static final String INVALID_TASK_NAME_NULL = "Invalid Task Name: Task Name Can Not Be Null";
    public static final String INVALID_TASK_NAME_LESS_THAN_THREE_CHAR = "Invalid Task Name: Task Name Can Not Be Empty or less than 3 Character";
    public static final String INVALID_TASK_NAME_MORE_THAN_FIFTY_CHAR = "Invalid Task Name: Task Name Can Not Be More Than 50 Character";


    public static final String INVALID_TASK_DESCRIPTION_NULL = "Invalid Task Description: Task Description Can Not Be Null";
    public static final String INVALID_TASK_DESCRIPTION_LESS_THAN_TEN_CHAR = "Invalid Task Description: Task Description Can Not Be Empty or less than 10 Character";
    public static final String INVALID_TASK_DESCRIPTION_MORE_THAN_ONEFIFTY_CHAR = "Invalid Task Description: Task Description Can Not Be More Than 150 Character";


    public static final String INVALID_DUEDATE_NULL = "Invalid DueDate: Due date can't be null";
    public static final String INVALID_DUEDATE_BEFORE_DATE = "Invalid DueDate: Due date can't be before todays date";



    public static final String INVALID_TASK_NOTES_NULL = "Invalid Task Notes: Task Notes Can Not Be Null";
    public static final String INVALID_TASK_NOTES_LESS_THAN_TEN_CHAR = "Invalid Task Notes: Task Notes Can Not Be Empty or less than 10 Character";
    public static final String INVALID_TASK_NOTES_MORE_THAN_ONEFIFTY_CHAR = "Invalid Task Notes: Task Notes Can Not Be More Than 150 Character";


    public static final String INVALID_REMINDER_NULL = "Invalid Reminder: Reminder can't be null";
    public static final String INVALID_REMINDER_CURRENT_TIME = "Invalid Remainder: Remainder can't be current time.";
    public static final String INVALID_REMINDER_BEFORE_DATE_TIME = "Invalid Remainder: Remainder can't be before time.";

    public static final String TASKTAG_NULL = "Invalid Task Tag: Task Tag can not be null";
    public static final String INVALID_TAG = "Invalid Task Tag: tags can't be empty or less than 3 characters";



}
