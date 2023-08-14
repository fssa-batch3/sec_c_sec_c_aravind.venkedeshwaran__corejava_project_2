package com.fssa.freshtime.services;

import com.fssa.freshtime.dao.ProgressDAO;
import com.fssa.freshtime.dao.TaskDAO;
import com.fssa.freshtime.enums.TaskStatus;
import com.fssa.freshtime.exceptions.DAOException;
import com.fssa.freshtime.exceptions.InvalidInputException;
import com.fssa.freshtime.models.Task;
import com.fssa.freshtime.validations.TaskValidator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Service class for managing tasks in the FreshTime application.
 */
public class TaskService {

    /**
     * Adds a task to the database.
     *
     * @param task The task to be added.
     * @return True if the task is valid and successfully added, false otherwise.
     * @throws DAOException If there's an issue with database operations.
     * @throws InvalidInputException If the task is invalid.
     */
    public boolean addTaskToDB(Task task) throws DAOException, InvalidInputException {
        if (TaskValidator.validate(task)) {
            return TaskDAO.createTask(task);
        } else {
            return false;
        }
    }

    /**
     * Retrieves a list of all tasks from the database.
     *
     * @return An ArrayList containing all tasks.
     * @throws DAOException If there's an issue with database operations.
     */
    public ArrayList<Task> readAllTask() throws DAOException {
        return TaskDAO.readTask();
    }

    /**
     * Updates a specific attribute of a task.
     *
     * @param taskId         The ID of the task to be updated.
     * @param attributeName The name of the attribute to be updated.
     * @param attributeValue The new value for the attribute.
     * @return True if the attribute is valid and successfully updated, false otherwise.
     * @throws DAOException If there's an issue with database operations.
     * @throws InvalidInputException If the attribute name or value is invalid.
     */
    public boolean updateTaskAttribute(int taskId, String attributeName, Object attributeValue) throws DAOException, InvalidInputException {
        if (TaskDAO.getAllIds().contains(taskId)) {
            boolean isValid = switch (attributeName) {
                case "taskName" -> TaskValidator.validateTaskName((String) attributeValue);
                case "taskDescription" -> TaskValidator.validateTaskDescription((String) attributeValue);
                case "dueDate" -> TaskValidator.validateDueDate((LocalDate) attributeValue);
                case "taskNotes" -> TaskValidator.validateTaskNotes((String) attributeValue);
                case "reminder" -> TaskValidator.validateReminder((LocalDateTime) attributeValue);
                default -> throw new InvalidInputException("Invalid attribute name");
            };

            if (isValid) {
                return TaskDAO.updateTaskAttribute(taskId, attributeName, attributeValue);
            }
        }
        return false;
    }

    /**
     * Deletes a task from the database.
     *
     * @param taskId The ID of the task to be deleted.
     * @return True if the task is successfully deleted, false otherwise.
     * @throws DAOException If there's an issue with database operations.
     * @throws InvalidInputException If the task ID is invalid.
     */
    public boolean deleteTask(int taskId) throws DAOException, InvalidInputException {
        if (TaskDAO.getAllIds().contains(taskId)) {
            return TaskDAO.deleteTask(taskId);
        }
        return false;
    }




    /**
     * Creates a new tag for a specific task in the database.
     *
     * @param taskId The ID of the task to associate with the new tag.
     * @param tag    The tag to be created.
     * @return True if the tag is valid and successfully created, false otherwise.
     * @throws DAOException If there's an issue with database operations.
     * @throws InvalidInputException If the tag is invalid.
     */
    public boolean createTaskTag(int taskId, String tag) throws DAOException, InvalidInputException {
        if (TaskValidator.validateTag(tag)) {
            return TaskDAO.createTags(taskId, tag);
        } else {
            return false;
        }
    }

    /**
     * Retrieves a list of tasks along with their associated tags from the database.
     *
     * @return An ArrayList of ArrayLists, where each inner ArrayList contains task ID and its tags.
     * @throws DAOException If there's an issue with database operations.
     */
    public ArrayList<ArrayList<String>> readTaskWithTags() throws DAOException {
        return TaskDAO.readTaskTags();
    }

    /**
     * Updates the tag for a specific task in the database.
     *
     * @param tagName The new name for the tag.
     * @param taskId  The ID of the task to be associated with the updated tag.
     * @return True if the tag name is valid and successfully updated, false otherwise.
     * @throws DAOException If there's an issue with database operations.
     * @throws InvalidInputException If the tag name is invalid.
     */
    public boolean updateTaskTag(String tagName, int taskId) throws DAOException, InvalidInputException {
        if (TaskValidator.validateTag(tagName)) {
            return TaskDAO.updateTagName(tagName, taskId);
        } else {
            return false;
        }
    }

    /**
     * Creates a new subtask for a specific task in the database.
     *
     * @param taskId   The ID of the task to associate with the new subtask.
     * @param subTask The subtask to be created.
     * @return True if the subtask is valid and successfully created, false otherwise.
     * @throws DAOException If there's an issue with database operations.
     * @throws InvalidInputException If the subtask is invalid.
     */
    public boolean createSubTask(int taskId, String subTask) throws DAOException, InvalidInputException {
        if (TaskValidator.validateTaskName(subTask)) {
            return TaskDAO.createSubtask(taskId, subTask);
        } else {
            return false;
        }
    }

    /**
     * Retrieves a list of tasks along with their associated subtasks from the database.
     *
     * @return An ArrayList of ArrayLists, where each inner ArrayList contains task ID and its subtasks.
     * @throws DAOException If there's an issue with database operations.
     */
    public ArrayList<ArrayList<String>> readTaskWithSubTask() throws DAOException {
        return TaskDAO.readSubTask();
    }

    /**
     * Updates a specific subtask in the database.
     *
     * @param subtask   The new subtask value.
     * @param subtaskId The ID of the subtask to be updated.
     * @return True if the subtask value is valid and successfully updated, false otherwise.
     * @throws DAOException If there's an issue with database operations.
     * @throws InvalidInputException If the subtask value is invalid.
     */
    public boolean updateSubTask(String subtask, int subtaskId) throws DAOException, InvalidInputException {
        if (TaskValidator.validateTaskName(subtask)) {
            return TaskDAO.updateSubtask(subtask, subtaskId);
        } else {
            return false;
        }
    }

    /**
     * Changes the status of a task in the FreshTime application.
     *
     * @param taskStatus The new status of the task.
     * @param taskId     The ID of the task whose status is to be changed.
     * @return True if the task status change is successful, false otherwise.
     * @throws DAOException If an error occurs while changing the task status.
     */
    public boolean changeTaskStatus(TaskStatus taskStatus, int taskId) throws DAOException {
        return ProgressDAO.changeTaskStatus(taskStatus, taskId);
    }
}