package com.fssa.freshtime.validators;

import com.fssa.freshtime.validators.contants.ValidatorConstants;
import com.fssa.freshtime.validators.errors.TaskErrors;
import com.fssa.freshtime.exceptions.InvalidInputException;
import com.fssa.freshtime.models.Subtask;
import com.fssa.freshtime.models.Task;
import java.time.*;
import java.time.format.DateTimeFormatter;


/**
 * TaskValidator class contains static methods to validate various properties of a Task object.
 */
public class TaskValidator {
    /**
     * Validates a Task object.
     *
     * @param task The Task object to be validated.
     * @return true if the Task is valid, otherwise throw InvalidInputException.
     */
    public static boolean validate(Task task) throws InvalidInputException {

        if(task == null){
            throw new InvalidInputException(TaskErrors.TASK_NULL);
        }

        validateTaskName(task.getTaskName());
        validateStartDate(task.getStartDate());
        validateEndDate(task.getEndDate());
        validateTaskNotes(task.getNotes());
        validateReminder(task.getReminder());

        return true;
    }

    /**
     * Validates the task name.
     *
     * @param taskName The task name to be validated.
     * @return true if the task name is valid, throws an InvalidInputException if invalid.
     */
    public static boolean validateTaskName(String taskName) throws InvalidInputException {

        if(taskName == null ){
            throw new InvalidInputException(TaskErrors.INVALID_TASK_NAME_NULL);
        }

        if (taskName.trim().length() < ValidatorConstants.VERY_SHORT_MIN_LEN){
            throw new InvalidInputException(TaskErrors.INVALID_TASK_NAME_LESS_THAN_THREE_CHAR);
        }

        if(taskName.trim().length() > ValidatorConstants.SHORT_MAX_LEN) {
            throw new InvalidInputException(TaskErrors.INVALID_TASK_NAME_MORE_THAN_FIFTY_CHAR);
        }

        return true;
    }


    static LocalDateTime now = LocalDateTime.now();
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    static String formattedTime = now.format(formatter);
    static LocalDateTime currentTime = LocalDateTime.parse(formattedTime, formatter);

    
    /**
     * Validates the task startdate.
     *
     * @param start Date of the task to be validated.
     * @return true if the start date is valid, throws an InvalidInputException if invalid.
     */
    public static boolean validateStartDate(LocalDateTime startDate) throws InvalidInputException {
        
        if(startDate == null){
            throw new InvalidInputException(TaskErrors.INVALID_DATE_NULL);
        }

        if (startDate.isBefore(currentTime)) {
            throw new InvalidInputException(TaskErrors.INVALID_DATE_BEFORE_DATE);
        }

        return true;
    }
    
    /**
     * Validates the task enddate.
     *
     * @param end Date of the task to be validated.
     * @return true if the end date is valid, throws an InvalidInputException if invalid.
     */
    public static boolean validateEndDate(LocalDateTime endDate) throws InvalidInputException {
        
        if(endDate == null){
            throw new InvalidInputException(TaskErrors.INVALID_DATE_NULL);
        }

        if (endDate.isBefore(currentTime)) {
            throw new InvalidInputException(TaskErrors.INVALID_DATE_BEFORE_DATE);
        }

        return true;
    }


    /**
     * Validates the task notes.
     *
     * @param notes The task notes to be validated.
     * @return true if the notes are valid, throws an InvalidInputException if invalid.
     */
    public static boolean validateTaskNotes(String notes) throws InvalidInputException {

        if (notes == null){
            throw new InvalidInputException(TaskErrors.INVALID_TASK_NOTES_NULL);
        }
        if(notes.trim().length() < ValidatorConstants.SHORT_MIN_LEN){
            throw new InvalidInputException(TaskErrors.INVALID_TASK_NOTES_LESS_THAN_TEN_CHAR);
        }

        if(notes.trim().length() > ValidatorConstants.VERY_LONG_MAX_LEN) {
            throw new InvalidInputException(TaskErrors.INVALID_TASK_NOTES_MORE_THAN_ONEFIFTY_CHAR);
        }

        return true;
    }

    /**
     * Validates the task reminder date.
     *
     * @param reminder The task reminder date to be validated.
     * @return true if the reminder date is valid, throws an InvalidInputException if invalid.
     */
    public static boolean validateReminder(LocalDateTime reminder) throws InvalidInputException {
        LocalDateTime now = LocalDateTime.now();

        if(reminder == null){
            throw new InvalidInputException(TaskErrors.INVALID_REMINDER_NULL); 
        }

        if(reminder.isEqual(now)) {
            throw new InvalidInputException(TaskErrors.INVALID_REMINDER_CURRENT_TIME);
        }

        if (reminder.isBefore(now)){
            throw new InvalidInputException(TaskErrors.INVALID_REMINDER_BEFORE_DATE_TIME);
        }

        return true;
    }



}
