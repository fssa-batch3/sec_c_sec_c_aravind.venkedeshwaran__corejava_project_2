package com.fssa.freshtime.dao;

import com.fssa.freshtime.exceptions.DAOException;
import com.fssa.freshtime.models.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;


public class TaskDAO {

    public static boolean createTask(Task task) throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String insertQuery = "INSERT INTO tasks (taskId, taskName, taskDescription, dueDate, priority, taskStatus, taskNotes, reminder, createdDate, createdTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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

                psmt.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            throw new DAOException("Error while creating task: " + e.getMessage());
        }
    }

    public static Task readTask(int taskId) throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String selectQuery = "SELECT * FROM tasks WHERE taskId=?";
            try (PreparedStatement psmt = connection.prepareStatement(selectQuery)) {
                psmt.setInt(1, taskId);
                try (ResultSet rs = psmt.executeQuery()) {
                    if (rs.next()) {
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
                        return task;
                    } else {
                        return null; // Task not found
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error while reading task: " + e.getMessage());
        }
    }

    public static boolean updateTaskName(Task task) throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String updateQuery = "UPDATE tasks SET taskName=? WHERE taskId=?";
            try (PreparedStatement psmt = connection.prepareStatement(updateQuery)) {
                psmt.setString(1, task.getTaskName());
                psmt.setInt(2, task.getTaskId());
                psmt.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            throw new DAOException("Error while updating task: " + e.getMessage());
        }
    }

    public static boolean updateTaskDescription(Task task) throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String updateQuery = "UPDATE tasks SET taskDescription=? WHERE taskId=?";
            try (PreparedStatement psmt = connection.prepareStatement(updateQuery)) {
                psmt.setString(1, task.getTaskDescription());
                psmt.setInt(2, task.getTaskId());
                psmt.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            throw new DAOException("Error while updating task: " + e.getMessage());
        }

    }

    public static boolean updateTaskDueDate(Task task) throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String updateQuery = "UPDATE tasks SET dueDate=? WHERE taskId=?";
            try (PreparedStatement psmt = connection.prepareStatement(updateQuery)) {
                psmt.setDate(1, java.sql.Date.valueOf(task.getDueDate()));
                psmt.setInt(2, task.getTaskId());
                psmt.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            throw new DAOException("Error while updating task: " + e.getMessage());
        }
    }

    public static boolean updateTaskPriority(Task task) throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String updateQuery = "UPDATE tasks SET priority=? WHERE taskId=?";
            try (PreparedStatement psmt = connection.prepareStatement(updateQuery)) {
                psmt.setString(1, task.getPriority());
                psmt.setInt(2, task.getTaskId());
                psmt.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            throw new DAOException("Error while updating task: " + e.getMessage());
        }
    }

    public static boolean updateTaskStatus(Task task) throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String updateQuery = "UPDATE tasks SET taskStatus=? WHERE taskId=?";
            try (PreparedStatement psmt = connection.prepareStatement(updateQuery)) {
                psmt.setString(1, task.getTaskStatus());
                psmt.setInt(2, task.getTaskId());
                psmt.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            throw new DAOException("Error while updating task: " + e.getMessage());
        }
    }

    public static boolean updateTaskNotes(Task task) throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String updateQuery = "UPDATE tasks SET taskStatus=? WHERE taskId=?";
            try (PreparedStatement psmt = connection.prepareStatement(updateQuery)) {
                psmt.setString(1, task.getTaskNotes());
                psmt.setInt(2, task.getTaskId());
                psmt.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            throw new DAOException("Error while updating task: " + e.getMessage());
        }
    }

    public static boolean updateTaskReminder(Task task) throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String updateQuery = "UPDATE tasks SET taskStatus=? WHERE taskId=?";
            try (PreparedStatement psmt = connection.prepareStatement(updateQuery)) {
                psmt.setTimestamp(1, java.sql.Timestamp.valueOf(task.getReminder()));
                psmt.setInt(2, task.getTaskId());
                psmt.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            throw new DAOException("Error while updating task: " + e.getMessage());
        }
    }

    //
    public static boolean deleteTask(int taskId) throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String deleteQuery = "DELETE FROM tasks WHERE taskId=?";
            try (PreparedStatement psmt = connection.prepareStatement(deleteQuery)) {
                psmt.setInt(1, taskId);
                psmt.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            throw new DAOException("Error while deleting task: " + e.getMessage());
        }
    }


    public static boolean createTaskTags(Task task) throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            try {
                for (String taskName : task.getTaskTagsMap().keySet()) {
                    int taskId = getTaskIdFromDatabase(connection, taskName);
                    if (taskId != -1) {
                        ArrayList<String> tags = task.getTaskTagsMap().get(taskName);
                        insertTags(connection, taskId, tags);
                    }
                }
                return true;
            } catch (SQLException e) {
                throw new DAOException("Error while creating task tags: " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new DAOException("Error while creating task tags: " + e.getMessage());
        }
    }

    private static int getTaskIdFromDatabase(Connection connection, String taskName) throws SQLException {
        String selectIdQuery = "SELECT taskId FROM tasks WHERE taskName=?";
        try (PreparedStatement psmt = connection.prepareStatement(selectIdQuery)) {
            psmt.setString(1, taskName);
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("taskId");
                }
            }
        }
        return -1;
    }

    private static void insertTags(Connection connection, int taskId, ArrayList<String> tags) throws SQLException {
        String insertQuery = "INSERT INTO tags (taskId, tagName) VALUES (?, ?)";
        try (PreparedStatement psmt = connection.prepareStatement(insertQuery)) {
            for (String tag : tags) {
                psmt.setInt(1, taskId);
                psmt.setString(2, tag);
                psmt.executeUpdate();
            }
        }
    }
}


