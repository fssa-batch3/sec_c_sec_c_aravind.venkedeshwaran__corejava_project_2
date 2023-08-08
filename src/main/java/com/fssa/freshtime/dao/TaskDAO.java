package com.fssa.freshtime.dao;

import com.fssa.freshtime.exceptions.DAOException;
import com.fssa.freshtime.exceptions.InvalidInputException;
import com.fssa.freshtime.models.Task;
import com.fssa.freshtime.services.TaskService;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;


public class TaskDAO {

    public static boolean createTask(Task task) throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String insertQuery = "INSERT INTO tasks (taskId, taskName, taskDescription, dueDate, priority, " +
                    "taskStatus, taskNotes, reminder, createdDate, createdTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement psmt = connection.prepareStatement(insertQuery)) {
                psmt.setInt(1, task.getTaskId());
                psmt.setString(2, task.getTaskName());
                psmt.setString(3, task.getTaskDescription());
                psmt.setDate(4, java.sql.Date.valueOf(task.getDueDate()));
                psmt.setString(5, task.getPriority());
                psmt.setString(6, task.getTaskStatus());
                psmt.setString(7, task.getTaskNotes());
                psmt.setTimestamp(8, java.sql.Timestamp.valueOf(task.getReminder()));
                psmt.setDate(9, java.sql.Date.valueOf(task.getCreatedDate()));
                psmt.setTimestamp(10, java.sql.Timestamp.valueOf(task.getCreatedTime()));

                int rowAffected = psmt.executeUpdate();
                System.out.println("No.Of Rows Affected: " + rowAffected);
                return rowAffected > 0;
            }
        } catch (SQLException e) {
            throw new DAOException("Error while creating task: " + e.getMessage());
        }
    }

    public static ArrayList<Task> readTask() throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String selectQuery = "SELECT * FROM tasks";
            try (PreparedStatement psmt = connection.prepareStatement(selectQuery)) {
                try (ResultSet rs = psmt.executeQuery()) {
                    ArrayList<Task> taskList = new ArrayList<Task>();
                    while (rs.next()) {
                        Task task = new Task();
                        task.setTaskId(rs.getInt("taskId"));
                        task.setTaskName(rs.getString("taskName"));
                        task.setTaskDescription(rs.getString("taskDescription"));
                        task.setDueDate(rs.getDate("dueDate").toLocalDate());
                        task.setPriority(rs.getString("priority"));
                        task.setTaskStatus(rs.getString("taskStatus"));
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

    public static ArrayList<Integer> getAllIds() throws DAOException{
        try (Connection connection = ConnectionUtil.getConnection()) {
            String selectQuery = "SELECT taskId FROM tasks";
            try (PreparedStatement psmt = connection.prepareStatement(selectQuery)) {
                try (ResultSet rs = psmt.executeQuery()) {
                    ArrayList<Integer> idList = new ArrayList<Integer>();
                    while (rs.next()) {
                        idList.add(rs.getInt("taskId"));
                    }
                    return idList;
                }
            }
        }
        catch (SQLException e) {
            throw new DAOException("Error while reading task: " + e.getMessage());
        }
    }


    public static boolean updateTaskAttribute(int taskId, String attributeName, Object attributeValue) throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String updateQuery = "UPDATE tasks SET " + attributeName + "=? WHERE taskId=?";
            try (PreparedStatement psmt = connection.prepareStatement(updateQuery)) {
                if (attributeValue instanceof String) {
                    psmt.setString(1, (String) attributeValue);
                }
                else if (attributeValue instanceof LocalDate) {
                    psmt.setDate(1, java.sql.Date.valueOf((LocalDate) attributeValue));
                }
                else if (attributeValue instanceof LocalDateTime) {
                    psmt.setTimestamp(1, java.sql.Timestamp.valueOf((LocalDateTime) attributeValue));
                }
                else {
                    throw new IllegalArgumentException("Unsupported attribute value type");
                }

                psmt.setInt(2, taskId);

                int rowAffected = psmt.executeUpdate();
                System.out.println("No.Of Rows Affected: " + rowAffected);
                return rowAffected > 0;
            }
        } catch (SQLException e) {
            throw new DAOException("Error while updating task: " + e.getMessage());
        }
    }


    public static boolean deleteTask(int taskId) throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String deleteQuery = "DELETE FROM tasks WHERE taskId=?";
            try (PreparedStatement psmt = connection.prepareStatement(deleteQuery)) {
                psmt.setInt(1, taskId);

                int rowAffected = psmt.executeUpdate();
                return rowAffected > 0;
            }
        } catch (SQLException e) {
            throw new DAOException("Error while deleting task: " + e.getMessage());
        }
    }

    public static boolean createTags(int taskId, String tag) throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String insertQuery = "INSERT INTO taskTags (taskId, tagName) VALUES (?, ?)";
            try (PreparedStatement psmt = connection.prepareStatement(insertQuery)) {
                psmt.setInt(1, taskId);
                psmt.setString(2, tag.toLowerCase());

                int rowAffected = psmt.executeUpdate();
                System.out.println(rowAffected);
                return rowAffected > 0;
            }
        } catch (SQLException e) {
            throw new DAOException("Error while creating task tags: " + e.getMessage());
        }
    }

    public static ArrayList<ArrayList<String>> readTaskTags() throws DAOException {
        ArrayList<ArrayList<String>> taskTagList = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection()) {
            String selectQuery = "SELECT tasks.taskId, tasks.taskName, taskTags.tagName " +
                    "FROM tasks " +
                    "LEFT JOIN taskTags ON tasks.taskId = taskTags.taskId";
            try (PreparedStatement psmt = connection.prepareStatement(selectQuery);
                 ResultSet rs = psmt.executeQuery()) {

                while (rs.next()) {
                    ArrayList<String> row = new ArrayList<>();
                    row.add(Integer.toString(rs.getInt("taskId")) + " ");
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

    public static boolean updateTagName(String tagName, int taskId) throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String updateQuery = "UPDATE taskTags SET tagName =? WHERE taskId=?";
            try (PreparedStatement psmt = connection.prepareStatement(updateQuery)) {
                psmt.setString(1, tagName);
                psmt.setInt(2, taskId);

                int rowAffected = psmt.executeUpdate();
                System.out.println("No.Of Rows Affected: " + rowAffected);
                return rowAffected > 0;
            }
        }
        catch (SQLException e) {
            throw new DAOException("Error while updating task tag: " + e.getMessage());
        }
    }

    public static boolean createSubtask(int taskId, String subTaskName) throws DAOException{
        try (Connection connection = ConnectionUtil.getConnection()) {
            String insertQuery = "INSERT INTO subTasks (taskId, subtask) VALUES (?, ?)";
            try (PreparedStatement psmt = connection.prepareStatement(insertQuery)) {
                psmt.setInt(1, taskId);
                psmt.setString(2, subTaskName);

                int rowAffected = psmt.executeUpdate();
                System.out.println(rowAffected);
                return rowAffected > 0;
            }
        } catch (SQLException e) {
            throw new DAOException("Error while creating subtask: " + e.getMessage());
        }
    }
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
                    row.add(Integer.toString(rs.getInt("taskId")) + " ");
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

    public static boolean updateSubtask(String subtask, int taskId) throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String updateQuery = "UPDATE subTasks SET subtask = ? WHERE taskId = ?";
            try (PreparedStatement psmt = connection.prepareStatement(updateQuery)) {
                psmt.setString(1, subtask);
                psmt.setInt(2, taskId);

                int rowAffected = psmt.executeUpdate();
                System.out.println("No.Of Rows Affected: " + rowAffected);
                return rowAffected > 0;
            }
        }
        catch (SQLException e) {
            throw new DAOException("Error while updating subtask: " + e.getMessage());
        }
    }

    //TODO Write one Delete Method for all main task, subtask and tag

    //TODO Write DAO Layer for the progress table

}


