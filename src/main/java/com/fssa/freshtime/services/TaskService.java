package com.fssa.freshtime.services;

import com.fssa.freshtime.dao.TaskDAO;
import com.fssa.freshtime.dao.UserDAO;
import com.fssa.freshtime.models.enums.TaskStatus;
import com.fssa.freshtime.utils.Logger;
import com.fssa.freshtime.exceptions.DAOException;
import com.fssa.freshtime.exceptions.InvalidInputException;
import com.fssa.freshtime.exceptions.ServiceException;
import com.fssa.freshtime.models.Subtask;
import com.fssa.freshtime.models.Task;
import com.fssa.freshtime.validators.TaskValidator;
import com.fssa.freshtime.validators.UserValidator;

import java.util.List;

/**
 * Service class for managing tasks in the FreshTime application.
 */
public class TaskService {

	public static final String INVALID_TASK_ID = "Invalid Task Id";
	
	UserService userservice = new UserService();

	/**
	 * Adds a task to the database.
	 *
	 * @param task The task to be added.
	 * @return True if the task is valid and successfully added, false otherwise.
	 * @throws DAOException          If there's an issue with database operations.
	 * @throws InvalidInputException If the task is invalid.
	 */

	public boolean addTask(int userId, String taskName) throws ServiceException {
		Logger.info("Adding Task in db");
		try {
			if (TaskValidator.validateTaskName(taskName)) {
				return TaskDAO.addTask(userId, taskName);
			}
		} catch (InvalidInputException | DAOException e) {
			throw new ServiceException("Error while adding task: " + e.getMessage());
		}
		return false;
	}


	public List<Task> readAllTaskByUser(int userId) throws ServiceException, InvalidInputException {
		Logger.info("Reading Tasks by user id in db");
		try {
			if (TaskDAO.getAllIds().contains(userId)) {
				return TaskDAO.readTaskByUser(userId);
			}
		} catch (DAOException e) {
			throw new ServiceException("Error while reading task: " + e.getMessage());
		}
		return null;
	}

	public Task readTaskByTaskId(int taskId) throws ServiceException, InvalidInputException {
		Logger.info("Reading All Task by task id in db");
		try {
			if (TaskDAO.getAllIds().contains(taskId)) {
				return TaskDAO.readTaskByTaskId(taskId);
			}
		} catch (DAOException e) {
			throw new ServiceException("Error while reading task: " + e.getMessage());
		}
		return null;
	}

	public boolean updateTask(Task task) throws ServiceException {
		Logger.info("updating Task in db");
		try {
			if(task != null) {
				if (TaskDAO.getAllIds().contains(task.getTaskId())) {				    
				    if (TaskValidator.validateTaskName(task.getTaskName())) {
				        return TaskDAO.updateTask(task);
				    }
	
				} else {
					throw new ServiceException(INVALID_TASK_ID);
				}
			}
			else {
				throw new ServiceException("Task Can Not be null");
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
	 * @throws DAOException          If there's an issue with database operations.
	 * @throws InvalidInputException If the task ID is invalid.
	 */
	public boolean deleteTask(int taskId) throws ServiceException {
		Logger.info("Deleting Task in db");
		try {
			if (TaskDAO.getAllIds().contains(taskId)) {
				return TaskDAO.deleteTask(taskId);
			} else {
				throw new ServiceException(INVALID_TASK_ID);
			}
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
	}


	public boolean createSubtask(Subtask subtask) throws ServiceException {
		Logger.info("Inserting Sub Task in db");
		try {
			if (TaskDAO.getAllIds().contains(subtask.getTaskId())) {
				if (TaskValidator.validateTaskName(subtask.getSubtaskName())) {
					return TaskDAO.createSubTask(subtask);
				}
			} else {
				throw new ServiceException("Invalid Task Id: Task Id don't exist.");
			}
		} catch (DAOException | InvalidInputException e) {
			throw new ServiceException("Error while creating subtask: " + e.getMessage());
		}
		return false;
	}

	public List<Subtask> readAllSubTaskByTaskId(int taskId) throws ServiceException, InvalidInputException {
		Logger.info("Reading Sub Task by Task id in db");
		try {
			return TaskDAO.readAllSubTaskByTaskId(taskId);
		} catch (DAOException e) {
			throw new ServiceException("Error while reading Subtask: " + e.getMessage());
		}
	}

	public Subtask readSubTaskById(int subtaskId) throws ServiceException, InvalidInputException {
		Logger.info("Reading Sub Task by SubTask id in db");
		try {
			if (TaskDAO.getAllSubtaskIds().contains(subtaskId)) {
				return TaskDAO.readSubTaskById(subtaskId);
			} else {
				throw new ServiceException("Invalid Sub Task Id: Subtask Id doesn't exist,");
			}
		} catch (DAOException e) {
			throw new ServiceException("Error while reading Subtask: " + e.getMessage());
		}
	}

	/**
	 * Updates a specific subtask in the database.
	 *
	 * @param subtask The new subtask value.
	 * @return True if the subtask value is valid and successfully updated, false
	 *         otherwise.
	 * @throws DAOException          If there's an issue with database operations.
	 * @throws InvalidInputException If the subtask value is invalid.
	 */
	public boolean updateSubtask(Subtask subtask) throws ServiceException {
		Logger.info("Updating Sub Task in db");
		try {
			if (subtask != null) {
				if (TaskDAO.getAllSubtaskIds().contains(subtask.getSubtaskId())) {
				    if (TaskValidator.validateTaskName(subtask.getSubtaskName())) {
				    	return TaskDAO.updatesubtask(subtask);
				    }
					
				} else {
					throw new ServiceException("Invalid Subtask Id");
				}
			} else {
				throw new ServiceException("Subtask can not be null");
			}
		} catch (DAOException | InvalidInputException e) {
			throw new ServiceException("Error while updating subtask: " + e.getMessage());
		}
		return false;
	}

	public boolean deleteSubTask(int subtaskId) throws ServiceException {
		Logger.info("Deleting Subtask in db");
		try {
			if (TaskDAO.getAllSubtaskIds().contains(subtaskId)) {
				return TaskDAO.deleteSubTask(subtaskId);
			} else {
				throw new ServiceException("Invalid subtaskId: subtask doesn't exist");
			}
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
	}

}