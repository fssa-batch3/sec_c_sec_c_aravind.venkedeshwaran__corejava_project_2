package com.fssa.freshtime.services;

import com.fssa.freshtime.dao.TaskDAO;
import com.fssa.freshtime.exceptions.DAOException;
import com.fssa.freshtime.exceptions.InvalidInputException;
import com.fssa.freshtime.models.Task;
import com.fssa.freshtime.validations.TaskValidator;

public class TaskService {
    public boolean addTaskToDB(Task task) throws DAOException, InvalidInputException {
        if(TaskValidator.validate(task)){
            return TaskDAO.createTask(task);
        }
        return false;
    }

    public boolean updateTaskName(Task  task) throws DAOException, InvalidInputException {
        if(TaskValidator.validate(task)){
            return TaskDAO.updateTaskName(task);
        }
        return false;
    }

    public boolean updateTaskDescription(Task task) throws DAOException, InvalidInputException {
        if(TaskValidator.validate(task)){
            return TaskDAO.updateTaskDescription(task);
        }
        return false;
    }

    public boolean updateTaskDueDate(Task task) throws DAOException, InvalidInputException {
        if(TaskValidator.validate(task)){
            return TaskDAO.updateTaskDueDate(task);
        }
        return false;
    }

    public boolean updateTaskPriority(Task task) throws DAOException, InvalidInputException {
        if(TaskValidator.validate(task)){
            return TaskDAO.updateTaskPriority(task);
        }
        return false;
    }

    public boolean updateTaskStatus(Task task) throws DAOException, InvalidInputException {
        if(TaskValidator.validate(task)){
            return TaskDAO.updateTaskStatus(task);
        }
        return false;
    }

    public boolean updateTaskNotes(Task task) throws DAOException, InvalidInputException {
        if(TaskValidator.validate(task)){
            return TaskDAO.updateTaskNotes(task);
        }
        return false;
    }

    public boolean updateTaskReminder(Task task) throws DAOException, InvalidInputException {
        if(TaskValidator.validate(task)){
            return TaskDAO.updateTaskReminder(task);
        }
        return false;
    }


    public boolean deleteTask(Task task) throws DAOException, InvalidInputException {
        if(TaskValidator.validate(task)) return TaskDAO.deleteTask(task.getTaskId());
        return false;
    }

    public Task readTask(Task task) throws DAOException, InvalidInputException{
        if(TaskValidator.validate(task)) return TaskDAO.readTask(task.getTaskId());
        return task;
    }

    public boolean createTaskTag(Task task) throws InvalidInputException {
        try {
            if (TaskValidator.validate(task)) {
                return TaskDAO.createTaskTags(task);
            }
            return false;
        } catch (DAOException e) {
            throw new InvalidInputException("Error while creating task tag: " + e.getMessage());
        }
    }

}
