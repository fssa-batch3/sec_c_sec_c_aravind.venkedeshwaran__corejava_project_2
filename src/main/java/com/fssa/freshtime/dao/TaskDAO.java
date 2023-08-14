package com.fssa.freshtime.dao;

import com.fssa.freshtime.enums.TaskPriority;
import com.fssa.freshtime.enums.TaskStatus;
import com.fssa.freshtime.exceptions.DAOException;
import com.fssa.freshtime.models.Task;
import com.fssa.freshtime.utils.ConnectionUtil;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) class for managing tasks and related data in the database.
 */
public class TaskDAO {

    public static final String taskId = "taskId";

    /**
     * Creates a new task in the database.
     *
     * @param task The task object to be created.
     * @return True if the task creation is successful, false otherwise.
     * @throws DAOException If an error occurs while creating the task.
     **/

    public static boolean createTask(Task task) throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String insertQuery = "INSERT INTO tasks (taskId, taskName, taskDescription, dueDate, priority, " +
                    "taskStatus, taskNotes, reminder, createdDate, createdTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement psmt = connection.prepareStatement(insertQuery)) {
                psmt.setInt(1, task.getTaskId());
                psmt.setString(2, task.getTaskName());
                psmt.setString(3, task.getTaskDescription());
                psmt.setDate(4, java.sql.Date.valueOf(task.getDueDate()));
                psmt.setObject(5, task.getPriority());
                psmt.setObject(6, task.getTaskStatus());
                psmt.setString(7, task.getTaskNotes());
                psmt.setTimestamp(8, java.sql.Timestamp.valueOf(task.getReminder()));
                psmt.setDate(9, java.sql.Date.valueOf(task.getCreatedDate()));
                psmt.setTimestamp(10, java.sql.Timestamp.valueOf(task.getCreatedTime()));

                int rowAffected = psmt.executeUpdate();
                
                return rowAffected > 0;
            }
        } catch (SQLException e) {
            throw new DAOException("Error while creating task: " + e.getMessage());
        }
    }

    /**
     * Retrieves a list of tasks from the database.
     *
     * @return An ArrayList of Task objects representing the tasks retrieved.
     * @throws DAOException If an error occurs while reading tasks.
     */
    public static List<Task> readTask() throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String selectQuery = "SELECT * FROM tasks";
            try (PreparedStatement psmt = connection.prepareStatement(selectQuery)) {
                try (ResultSet rs = psmt.executeQuery()) {
                    ArrayList<Task> taskList = new ArrayList<>();
                    while (rs.next()) {
                        Task task = new Task();
                        task.setTaskId(rs.getInt(taskId));
                        task.setTaskName(rs.getString("taskName"));
                        task.setTaskDescription(rs.getString("taskDescription"));
                        task.setDueDate(rs.getDate("dueDate").toLocalDate());
                        task.setPriority(TaskPriority.valueOf(rs.getString("priority")));
                        task.setTaskStatus(TaskStatus.valueOf(rs.getString("taskStatus")));
                        task.setTaskNotes(rs.getString("taskNotes"));
                        task.setReminder(rs.getTimestamp("Reminder").toLocalDateTime());
                        task.setCreatedDate(rs.getDate("createdDate").toLocalDate());
                        task.setCreatedTime(rs.getTimestamp("createdTime").toLocalDateTime());
                        taskList.add(task);
                    }
                    return taskList;
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error while reading task: " + e.getMessage());
        }
    }

    /**
     * Retrieves a list of all task IDs from the database.
     *
     * @return An ArrayList of integers representing task IDs.
     * @throws DAOException If an error occurs while reading task IDs.
     */

    public static ArrayList<Integer> getAllIds() throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String selectQuery = "SELECT taskId FROM tasks";
            try (PreparedStatement psmt = connection.prepareStatement(selectQuery)) {
                try (ResultSet rs = psmt.executeQuery()) {
                    ArrayList<Integer> idList = new ArrayList<>();
                    while (rs.next()) {
                        idList.add(rs.getInt(taskId));
                    }
                    return idList;
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error while reading task: " + e.getMessage());
        }
    }


    /**
     * Updates a specific attribute of a task in the database.
     *
     * @param taskId         The ID of the task to update.
     * @param attributeName  The name of the attribute to update.
     * @param attributeValue The new value of the attribute.
     * @return True if the attribute update is successful, false otherwise.
     * @throws DAOException If an error occurs while updating the task attribute.
     */

    public static boolean updateTaskAttribute(int taskId, String attributeName, Object attributeValue) throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String updateQuery = "UPDATE tasks SET " + attributeName + "=? WHERE taskId=?";
            try (PreparedStatement psmt = connection.prepareStatement(updateQuery)) {
                if (attributeValue instanceof String) {
                    psmt.setString(1, (String) attributeValue);
                } else if (attributeValue instanceof LocalDate) {
                    psmt.setDate(1, java.sql.Date.valueOf((LocalDate) attributeValue));
                } else if (attributeValue instanceof LocalDateTime) {
                    psmt.setTimestamp(1, java.sql.Timestamp.valueOf((LocalDateTime) attributeValue));
                } else {
                    throw new IllegalArgumentException("Unsupported attribute value type");
                }

                psmt.setInt(2, taskId);

                int rowAffected = psmt.executeUpdate();
                return rowAffected > 0;
            }
        } catch (SQLException e) {
            throw new DAOException("Error while updating task: " + e.getMessage());
        }
    }


    /**
     * Deletes a task and its associated subtasks and tags from the database.
     *
     * @param taskId The ID of the task to delete.
     * @return True if the task deletion is successful, false otherwise.
     * @throws DAOException If an error occurs while deleting the task.
     */

    public static boolean deleteTask(int taskId) throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            // Delete subtasks first to avoid foreign key constraints
            String deleteSubtasksQuery = "DELETE FROM subTasks WHERE taskId=?";
            try (PreparedStatement subtasksPsmt = connection.prepareStatement(deleteSubtasksQuery)) {
                subtasksPsmt.setInt(1, taskId);
                subtasksPsmt.executeUpdate();
            }

            // Delete task tags next
            String deleteTagsQuery = "DELETE FROM taskTags WHERE taskId=?";
            try (PreparedStatement tagsPsmt = connection.prepareStatement(deleteTagsQuery)) {
                tagsPsmt.setInt(1, taskId);
                tagsPsmt.executeUpdate();
            }

            // Delete the main task
            String deleteTaskQuery = "DELETE FROM tasks WHERE taskId=?";
            try (PreparedStatement taskPsmt = connection.prepareStatement(deleteTaskQuery)) {
                taskPsmt.setInt(1, taskId);
                int rowAffected = taskPsmt.executeUpdate();
                return rowAffected > 0;
            }
        } catch (SQLException e) {
            throw new DAOException("Error while deleting task: " + e.getMessage());
        }
    }


    /**
     * Creates a new task tag in the database for a specified task.
     *
     * @param taskId The ID of the task for which the tag is to be created.
     * @param tag    The tag to be associated with the task.
     * @return True if the task tag creation is successful, false otherwise.
     * @throws DAOException If an error occurs while creating the task tag.
     */

    public static boolean createTags(int taskId, String tag) throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String insertQuery = "INSERT INTO taskTags (taskId, tagName) VALUES (?, ?)";
            try (PreparedStatement psmt = connection.prepareStatement(insertQuery)) {
                psmt.setInt(1, taskId);
                psmt.setString(2, tag.toLowerCase());

                int rowAffected = psmt.executeUpdate();
                return rowAffected > 0;
            }
        } catch (SQLException e) {
            throw new DAOException("Error while creating task tags: " + e.getMessage());
        }
    }


    /**
     * Retrieves a list of task tags along with corresponding task information from the database.
     *
     * @return An ArrayList of ArrayLists, where each inner ArrayList contains task tag information.
     * @throws DAOException If an error occurs while reading task tags.
     */

    public static List<ArrayList<String>> readTaskTags() throws DAOException {
        ArrayList<ArrayList<String>> taskTagList = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection()) {
            String selectQuery = "SELECT tasks.taskId, tasks.taskName, taskTags.tagName " +
                    "FROM tasks " +
                    "LEFT JOIN taskTags ON tasks.taskId = taskTags.taskId";
            try (PreparedStatement psmt = connection.prepareStatement(selectQuery);
                 ResultSet rs = psmt.executeQuery()) {

                while (rs.next()) {
                    ArrayList<String> row = new ArrayList<>();
                    row.add(rs.getInt(taskId) + " ");
                    row.add(rs.getString("taskName") + " ");
                    row.add(rs.getString("tagName") + " ");
                    taskTagList.add(row);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error while reading task tags: " + e.getMessage());
        }

        return taskTagList;
    }


    /**
     * Updates the name of a task tag in the database.
     *
     * @param tagName The new name for the task tag.
     * @param taskId  The ID of the task whose tag is to be updated.
     * @return True if the task tag name update is successful, false otherwise.
     * @throws DAOException If an error occurs while updating the task tag name.
     */

    public static boolean updateTagName(String tagName, int taskId) throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String updateQuery = "UPDATE taskTags SET tagName =? WHERE taskId=?";
            try (PreparedStatement psmt = connection.prepareStatement(updateQuery)) {
                psmt.setString(1, tagName);
                psmt.setInt(2, taskId);

                int rowAffected = psmt.executeUpdate();
                
                return rowAffected > 0;
            }
        } catch (SQLException e) {
            throw new DAOException("Error while updating task tag: " + e.getMessage());
        }
    }


    /**
     * Creates a new subtask for a specified task in the database.
     *
     * @param taskId      The ID of the task for which the subtask is to be created.
     * @param subTaskName The name of the subtask.
     * @return True if the subtask creation is successful, false otherwise.
     * @throws DAOException If an error occurs while creating the subtask.
     */

    public static boolean createSubtask(int taskId, String subTaskName) throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String insertQuery = "INSERT INTO subTasks (taskId, subtask) VALUES (?, ?)";
            try (PreparedStatement psmt = connection.prepareStatement(insertQuery)) {
                psmt.setInt(1, taskId);
                psmt.setString(2, subTaskName);

                int rowAffected = psmt.executeUpdate();
                
                return rowAffected > 0;
            }
        } catch (SQLException e) {
            throw new DAOException("Error while creating subtask: " + e.getMessage());
        }
    }

    /**
     * Retrieves a list of tasks along with corresponding subtask information from the database.
     *
     * @return An ArrayList of ArrayLists, where each inner ArrayList contains task and subtask information.
     * @throws DAOException If an error occurs while reading tasks and subtasks.
     */

    public static ArrayList<ArrayList<String>> readSubTask() throws DAOException {
        ArrayList<ArrayList<String>> taskWithSubTaskList = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection()) {
            String selectQuery = "SELECT tasks.taskId, tasks.taskName, subTasks.subtask " +
                    "FROM tasks " +
                    "LEFT JOIN subTasks ON tasks.taskId = subTasks.taskId";
            try (PreparedStatement psmt = connection.prepareStatement(selectQuery);
                 ResultSet rs = psmt.executeQuery()) {

                while (rs.next()) {
                    ArrayList<String> row = new ArrayList<>();
                    row.add(rs.getInt(taskId) + " ");
                    row.add(rs.getString("taskName") + " ");
                    row.add(rs.getString("subtask") + " ");
                    taskWithSubTaskList.add(row);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error while reading task tags: " + e.getMessage());
        }

        return taskWithSubTaskList;
    }


    /**
     * Updates the content of a subtask in the database.
     *
     * @param subtask    The updated content of the subtask.
     * @param subTaskId  The ID of the subtask to be updated.
     * @return True if the subtask update is successful, false otherwise.
     * @throws DAOException If an error occurs while updating the subtask content.
     */

    public static boolean updateSubtask(String subtask, int subTaskId) throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String updateQuery = "UPDATE subTasks SET subtask = ? WHERE taskId = ?";
            try (PreparedStatement psmt = connection.prepareStatement(updateQuery)) {
                psmt.setString(1, subtask);
                psmt.setInt(2, subTaskId);

                int rowAffected = psmt.executeUpdate();
                
                return rowAffected > 0;
            }
        } catch (SQLException e) {
            throw new DAOException("Error while updating subtask: " + e.getMessage());
        }
    }
}