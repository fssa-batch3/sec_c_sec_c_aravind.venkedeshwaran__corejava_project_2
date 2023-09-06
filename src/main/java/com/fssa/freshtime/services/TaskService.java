package com.fssa.freshtime.services;

import com.fssa.freshtime.dao.TaskDAO;
import com.fssa.freshtime.models.enums.TaskStatus;
import com.fssa.freshtime.exceptions.DAOException;
import com.fssa.freshtime.exceptions.InvalidInputException;
import com.fssa.freshtime.exceptions.ServiceException;
import com.fssa.freshtime.models.Subtask;
import com.fssa.freshtime.models.Task;
import com.fssa.freshtime.models.Tasktags;
import com.fssa.freshtime.validators.TaskValidator;
import com.fssa.freshtime.validators.UserValidator;

import java.util.List;

/**
 * Service class for managing tasks in the FreshTime application.
 */
public class TaskService {

    public static final String INVALID_TASK_ID = "Invalid Task Id";
    /**
     * Adds a task to the database.
     *
     * @param task The task to be added.
     * @return True if the task is valid and successfully added, false otherwise.
     * @throws DAOException If there's an issue with database operations.
     * @throws InvalidInputException If the task is invalid.
     */

    public boolean addTask(Task task) throws ServiceException {
        try {
            if (TaskValidator.validate(task)) {
                return TaskDAO.createTask(task);
            } else {
                return false;
            }
        } catch (InvalidInputException | DAOException e) {
            throw new ServiceException("Error while adding task: " + e.getMessage());
        }
    }




    /**
     * Retrieves a list of all tasks from the database.
     *
     * @return An ArrayList containing all tasks.
     * @throws DAOException If there's an issue with database operations.
     */
    public List<Task> readAllTask() throws ServiceException {
        try {
            return TaskDAO.readTask();
        }
        catch (DAOException e) {
            throw new ServiceException("Error while reading task: " + e.getMessage());
        }
    }
    
    public int getUserIdByEmail(String emailId) throws ServiceException {
        try {
            
            if(UserValidator.validateEmailId(emailId)) {
            	return TaskDAO.getUserIdByEmail(emailId);
            }
        } catch (DAOException | InvalidInputException e) {
            throw new ServiceException("Error while getting user id: " + e.getMessage(), e);
        }
		return -1;
    }
    
    
    
    public List<Task> readAllTaskByUser(String emailId) throws ServiceException, InvalidInputException {
        try {
        	if(UserValidator.validateEmailId(emailId)) {
        		return TaskDAO.readTaskByUser(emailId);
        	}
        }
        catch (InvalidInputException| DAOException e) {
            throw new ServiceException("Error while reading task: " + e.getMessage());
        }
		return null;
    }

    
    public Task readAllTaskByTaskId(int taskId) throws ServiceException, InvalidInputException {
        try {
        	if(TaskDAO.getAllIds().contains(taskId)) {
        		return TaskDAO.readTaskByTaskId(taskId);
        	}
        }
        catch (DAOException e) {
            throw new ServiceException("Error while reading task: " + e.getMessage());
        }
		return null;
    }


    public boolean updateTask(Task task) throws ServiceException {
        try {
            if(TaskDAO.getAllIds().contains(task.getTaskId())) {
                if( TaskValidator.validateTaskName(task.getTaskName()) &&
            		TaskValidator.validateTaskDescription(task.getDescription()) &&
            		TaskValidator.validateTaskNotes(task.getNotes())) {
                    return TaskDAO.updateTask(task);
                }
            }
            else{
                throw new ServiceException(INVALID_TASK_ID);
            }
        } catch (InvalidInputException | DAOException e) {
            throw new ServiceException("Error while updating task: " + e.getMessage());
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
    public boolean deleteTask(int taskId) throws ServiceException {
        try {
            if (TaskDAO.getAllIds().contains(taskId)) {
                return TaskDAO.deleteTask(taskId);
            }
            else{
                throw new ServiceException(INVALID_TASK_ID);
            }
        }
        catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
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
    public boolean createTaskTag(int taskId, String tag) throws ServiceException {
        try {
            if (TaskDAO.getAllIds().contains(taskId)) {
                if (TaskValidator.validateTag(tag)) {
                    return TaskDAO.createTags(taskId, tag);
                }
                return false;
            } else {
                throw new ServiceException(INVALID_TASK_ID);
            }
        }
        catch(DAOException| InvalidInputException e){
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Retrieves a list of tasks along with their associated tags from the database.
     *
     * @return An ArrayList of ArrayLists, where each inner ArrayList contains task ID and its tags.
     * @throws DAOException If there's an issue with database operations.
     */
    public List<Tasktags> readTaskTags() throws DAOException {
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
    public boolean updateTaskTag(String tagName, int taskId) throws ServiceException {
        try{
            if(TaskDAO.getAllIds().contains(taskId)) {
                if (TaskValidator.validateTag(tagName)) {
                    return TaskDAO.updateTagName(tagName, taskId);
                } else {
                    return false;
                }
            }
            else{
                throw new ServiceException(INVALID_TASK_ID);
            }
        }
        catch (InvalidInputException | DAOException e){
            throw new ServiceException(e.getMessage());
        }

    }


    public boolean createSubtask(Subtask subtask) throws ServiceException {
        try {
            if (TaskValidator.validateSubtask(subtask)) {
                return TaskDAO.createSubTask(subtask);
            } else {
                return false;
            }
        } catch (InvalidInputException | DAOException e) {
            throw new ServiceException("Error while creating subtask: " + e.getMessage());
        }
    }

    /**
     * Retrieves a list of tasks along with their associated subtasks from the database.
     *
     * @return An ArrayList of ArrayLists, where each inner ArrayList contains task ID and its subtasks.
     * @throws DAOException If there's an issue with database operations.
     */
    public List<Subtask> readSubtask() throws ServiceException {
        try {
            return TaskDAO.readSubTask();
        }
        catch (DAOException e) {
            throw new ServiceException("Error while reading subtask: " + e.getMessage());
        }
    }

    /**
     * Updates a specific subtask in the database.
     *
     * @param subtask   The new subtask value.
     * @return True if the subtask value is valid and successfully updated, false otherwise.
     * @throws DAOException If there's an issue with database operations.
     * @throws InvalidInputException If the subtask value is invalid.
     */
    public boolean updateSubtask(Subtask subtask) throws ServiceException {
        try {
            if(TaskDAO.getAllSubtaskIds().contains(subtask.getSubtaskId())) {
                if(TaskValidator.validateSubtask(subtask)) {
                    return TaskDAO.updatesubtask(subtask);
                }
            }
            else{
                throw new ServiceException(INVALID_TASK_ID);
            }
        } catch (InvalidInputException | DAOException e) {
            throw new ServiceException("Error while updating subtask: " + e.getMessage());
        }
        return false;
    }

    /**
     * Changes the status of a task in the FreshTime application.
     *
     * @param taskStatus The new status of the task.
     * @param taskId     The ID of the task whose status is to be changed.
     * @return True if the task status change is successful, false otherwise.
     * @throws DAOException If an error occurs while changing the task status.
     */
//    public boolean changeTaskStatus(TaskStatus taskStatus, int taskId) throws DAOException {
//        return ProgressDAO.changeTaskStatus(taskStatus, taskId);
//    }

}