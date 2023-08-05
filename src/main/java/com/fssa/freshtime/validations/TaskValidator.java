package com.fssa.freshtime.validations;

import com.fssa.freshtime.enums.TaskPriority;
import com.fssa.freshtime.enums.TaskStatus;
import com.fssa.freshtime.errors.TaskErrors;
import com.fssa.freshtime.exceptions.InvalidInputException;
import com.fssa.freshtime.models.Task;

import java.time.*;
import java.time.chrono.ChronoLocalDate;

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
        validateTaskName(task.getTaskName());
        validateTaskDescription(task.getTaskDescription());
        validateDueDate(task.getDueDate());
        validatePriority(task.getPriority());
        validateStatus(task.getTaskStatus());
        validateTaskNotes(task.getTaskNotes());
        validateReminder(task.getReminder());
        validateCreatedDate(task.getCreatedDate());
        validateCreatedTime(task.getCreatedTime());
//        validateTag(task.getTaskTag()) ;
        return true; // Placeholder method; actual validation should be implemented here.
    }

    /**
     * Validates the task name.
     *
     * @param taskName The task name to be validated.
     * @return true if the task name is valid, throws an InvalidInputException if invalid.
     */
    public static boolean validateTaskName(String taskName) throws InvalidInputException {
        if (taskName.equals(null) || taskName.trim().equals("") || taskName.trim().length() < 3 || taskName.trim().length() > 50) {
            throw new InvalidInputException(TaskErrors.INVALID_TASK_NAME);
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
        if (taskDescription.equals(null) || taskDescription.trim().equals("") || taskDescription.trim().length() < 10 || taskDescription.trim().length() > 150) {
            throw new InvalidInputException(TaskErrors.INVALID_TASK_DESCRIPTION);
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
        if (dueDate.isBefore(today)) {
            throw new InvalidInputException(TaskErrors.INVALID_DUEDATE);
        }
        return true;
    }

    /**
     * Validates the task priority.
     *
     * @param taskpriority The task priority to be validated.
     * @return true if the priority is valid, throws an InvalidInputException if invalid.
     */
    public static boolean validatePriority(String taskpriority) throws InvalidInputException {
        taskpriority = taskpriority.trim().toUpperCase();

        boolean isValid = false;
        for (TaskPriority priority : TaskPriority.values()) {
            if (taskpriority.equals(priority.name())) {
                isValid = true;
                break;
            }
        }

        if (!isValid) throw new InvalidInputException(TaskErrors.INVALID_PRIORITY);
        else return true;
    }

    /**
     * Validates the task status.
     *
     * @param taskStatus The task status to be validated.
     * @return true if the status is valid, throws an InvalidInputException if invalid.
     */
    public static boolean validateStatus(String taskStatus) throws InvalidInputException {
        // Trim the input and replace all white space with empty and making it uppercase to match with the enums
        taskStatus = taskStatus.trim().toUpperCase().replaceAll("\\s+", "");

        boolean isValid = false;
        for (TaskStatus status : TaskStatus.values()) {
            if (taskStatus.equals(status.name())) {
                isValid = true;
                break;
            }
        }

        if (!isValid) {
            throw new InvalidInputException(TaskErrors.INVALID_STATUS);
        } else {
            return true;
        }
    }


    /**
     * Validates the task notes.
     *
     * @param notes The task notes to be validated.
     * @return true if the notes are valid, throws an InvalidInputException if invalid.
     */
    public static boolean validateTaskNotes(String notes) throws InvalidInputException {
        if (notes.equals(null) || notes.trim().equals("") || notes.trim().length() < 10 || notes.trim().length() > 150) {
            throw new InvalidInputException(TaskErrors.INVALID_TASK_NOTES);
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
        if (reminder.isBefore(now) || reminder.isEqual(now)) {
            throw new InvalidInputException(TaskErrors.INVALID_REMINDER);
        }
        return true;
    }


    /**
     * Validates the task created date.
     *
     * @param createdDate The task created date to be validated.
     * @return true if the created date is valid, throws an InvalidInputException if invalid.
     */
    public static boolean validateCreatedDate(LocalDate createdDate) throws InvalidInputException {
        LocalDate today = LocalDate.now();
        if (createdDate.isBefore(today) || createdDate.isAfter(today)) {
            throw new InvalidInputException(TaskErrors.INVALID_CREATEDDATE);
        }
        return true;
    }

    /**
     * Validates the task created time.
     *
     * @param createdTime The task created time to be validated.
     * @return true if the created time is valid, throws an InvalidInputException if invalid.
     */
    public static boolean validateCreatedTime(LocalDateTime createdTime) throws InvalidInputException {
        LocalDateTime currentTime = LocalDateTime.now();
        // Define the minimum allowed time (1 minute ago from the current time)
        LocalDateTime minimumAllowedTime = currentTime.minusMinutes(1);

        if (createdTime.isAfter(currentTime)) {
            throw new InvalidInputException("Created time cannot be in the future.");
        }
        else if (createdTime.isBefore(minimumAllowedTime)) {
            throw new InvalidInputException("Created time cannot be before 1 minute from now.");
        }
        else{
            return true;
        }
    }

    public static boolean validateTag(String taskTag) throws InvalidInputException {
        if(taskTag == null || ("".equals(taskTag)) || taskTag.length() < 3) throw new InvalidInputException(TaskErrors.INVALID_TAG);
        return true;
    }

}
