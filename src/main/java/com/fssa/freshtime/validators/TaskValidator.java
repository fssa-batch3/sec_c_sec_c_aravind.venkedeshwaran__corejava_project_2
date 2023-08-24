package com.fssa.freshtime.validators;

import com.fssa.freshtime.validators.contants.ValidatorConstants;
import com.fssa.freshtime.validators.errors.TaskErrors;
import com.fssa.freshtime.exceptions.InvalidInputException;
import com.fssa.freshtime.models.Subtask;
import com.fssa.freshtime.models.Task;
import java.time.*;


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
        validateTaskDescription(task.getDescription());
        validateDueDate(task.getDueDate());
        validateTaskNotes(task.getNotes());
        validateReminder(task.getReminder());

        return true;
    }

    //TODO: ask if this ok or not
    public static boolean validateSubtask(Subtask subtask) throws InvalidInputException {

        if(subtask == null){
            throw new InvalidInputException(TaskErrors.TASK_NULL);
        }

        validateTaskName(subtask.getSubtaskName());
        validateTaskDescription(subtask.getDescription());
        validateDueDate(subtask.getDueDate());
        validateReminder(subtask.getReminder());

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

    /**
     * Validates the task description.
     *
     * @param taskDescription The task description to be validated.
     * @return true if the task description is valid, throws an InvalidInputException if invalid.
     */
    public static boolean validateTaskDescription(String taskDescription)  throws InvalidInputException {

        if (taskDescription == null) {
            throw new InvalidInputException(TaskErrors.INVALID_TASK_DESCRIPTION_NULL);
        }

        if(taskDescription.trim().length() < ValidatorConstants.SHORT_MIN_LEN){
            throw new InvalidInputException(TaskErrors.INVALID_TASK_DESCRIPTION_LESS_THAN_TEN_CHAR);
        }

        if(taskDescription.trim().length() > ValidatorConstants.VERY_LONG_MAX_LEN) {
            throw new InvalidInputException(TaskErrors.INVALID_TASK_NOTES_MORE_THAN_ONEFIFTY_CHAR);
        }

        return true;
    }

    /**
     * Validates the task due date.
     *
     * @param dueDate The task due date to be validated.
     * @return true if the due date is valid, throws an InvalidInputException if invalid.
     */
    public static boolean validateDueDate(LocalDate dueDate) throws InvalidInputException {
        LocalDate today = LocalDate.now();

        if(dueDate == null){
            throw new InvalidInputException(TaskErrors.INVALID_DUEDATE_NULL);
        }

        if (dueDate.isBefore(today)) {
            throw new InvalidInputException(TaskErrors.INVALID_DUEDATE_BEFORE_DATE);
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

    public static boolean validateTag(String taskTag) throws InvalidInputException {
        if(taskTag == null){
            throw new InvalidInputException(TaskErrors.TASKTAG_NULL);
        }
        if(taskTag.length() < ValidatorConstants.VERY_SHORT_MIN_LEN) {
            throw new InvalidInputException(TaskErrors.INVALID_TAG);
        }
        return true;
    }


}
