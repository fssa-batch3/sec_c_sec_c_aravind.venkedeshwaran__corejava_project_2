package com.fssa.freshtime.services;

import com.fssa.freshtime.dao.TaskDAO;
import com.fssa.freshtime.exceptions.DAOException;
import com.fssa.freshtime.exceptions.InvalidInputException;
import com.fssa.freshtime.models.Task;
import com.fssa.freshtime.validations.TaskValidator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TaskService {


    public boolean addTaskToDB(Task task) throws DAOException, InvalidInputException {
        if(TaskValidator.validate(task)) return TaskDAO.createTask(task);
        else return false;
    }

    public ArrayList<Task> readAllTask() throws DAOException{
        return TaskDAO.readTask();
    }

    public boolean updateTaskAttribute(int taskId, String attributeName, Object attributeValue) throws DAOException, InvalidInputException {
        if (TaskDAO.getAllIds().contains(taskId)) {
            boolean isValid = switch (attributeName) {
                case "taskName" -> TaskValidator.validateTaskName((String) attributeValue);
                case "taskDescription" -> TaskValidator.validateTaskDescription((String) attributeValue);
                case "dueDate" -> TaskValidator.validateDueDate((LocalDate) attributeValue);
                case "priority" -> TaskValidator.validatePriority((String) attributeValue);
                case "taskStatus" -> TaskValidator.validateStatus((String) attributeValue);
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

    public boolean deleteTask(int taskId) throws DAOException, InvalidInputException {
        if(TaskDAO.getAllIds().contains(taskId)) return TaskDAO.deleteTask(taskId);
        return false;
    }


    public boolean createTaskTag(int taskId, String tag)  throws DAOException, InvalidInputException {
        if(TaskValidator.validateTag(tag)) return TaskDAO.createTags(taskId, tag);
        return false;
    }

    public ArrayList<ArrayList<String>> readTaskWithTags() throws DAOException{
        return TaskDAO.readTaskTags();
    }

    public boolean createSubTask(int taskId, String subTask)  throws DAOException, InvalidInputException {
        if(TaskValidator.validateTaskName(subTask)) return TaskDAO.createSubtask(taskId, subTask);
        return false;
    }

    public ArrayList<ArrayList<String>> readTaskWithSubTask() throws DAOException{
        return TaskDAO.readSubTask();
    }

}
