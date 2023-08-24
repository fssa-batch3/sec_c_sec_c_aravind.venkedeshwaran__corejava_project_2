package com.fssa.freshtime.dao;

import com.fssa.freshtime.models.enums.TaskPriority;
import com.fssa.freshtime.models.enums.TaskStatus;
import com.fssa.freshtime.exceptions.DAOException;
import com.fssa.freshtime.models.Subtask;
import com.fssa.freshtime.models.Task;
import com.fssa.freshtime.models.Tasktags;
import com.fssa.freshtime.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) class for managing tasks and related data in the database.
 */
public class TaskDAO {

    public static final String TASKID = "task_id";
    public static final String TASKNAME = "task_name";


    /**
     * Creates a new task in the database.
     *
     * @param task The task object to be created.
     * @return True if the task creation is successful, false otherwise.
     * @throws DAOException If an error occurs while creating the task.
     **/

    public static boolean createTask(Task task) throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String insertQuery = "INSERT INTO tasks (task_id, task_name, task_description, due_date, priority, " +
                    "task_status, task_notes, reminder) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement psmt = connection.prepareStatement(insertQuery)) {
                psmt.setInt(1, task.getTaskId());
                psmt.setString(2, task.getTaskName());
                psmt.setString(3, task.getDescription());
                psmt.setDate(4, java.sql.Date.valueOf(task.getDueDate()));
                psmt.setString(5, task.getPriority().toString());
                psmt.setString(6, task.getStatus().toString());
                psmt.setString(7, task.getNotes());
                psmt.setTimestamp(8, java.sql.Timestamp.valueOf(task.getReminder()));

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
            String selectQuery = "SELECT task_id, task_name, task_description, due_date, priority, task_status, " +
                    "task_notes, reminder, created_date_time FROM tasks";

            try (PreparedStatement psmt = connection.prepareStatement(selectQuery)) {
                try (ResultSet rs = psmt.executeQuery()) {
                    ArrayList<Task> taskList = new ArrayList<>();
                    while (rs.next()) {
                        Task task = new Task();

                        task.setTaskId(rs.getInt(TASKID));
                        task.setTaskName(rs.getString(TASKNAME));
                        task.setDescription(rs.getString("task_description"));
                        task.setDueDate(rs.getDate("due_date").toLocalDate());
                        task.setPriority(TaskPriority.valueOf(rs.getString("priority")));
                        task.setStatus(TaskStatus.valueOf(rs.getString("task_status")));
                        task.setNotes(rs.getString("task_notes"));
                        task.setReminder(rs.getTimestamp("reminder").toLocalDateTime());
                        task.setCreatedDate(rs.getDate("created_date_time").toLocalDate());

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

    public static List<Integer> getAllIds() throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String selectQuery = "SELECT task_id FROM tasks";
            try (PreparedStatement psmt = connection.prepareStatement(selectQuery)) {
                try (ResultSet rs = psmt.executeQuery()) {
                    List<Integer> idList = new ArrayList<>();
                    while (rs.next()) {
                        idList.add(rs.getInt(TASKID));
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

    public static boolean updateTask(Task task) throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String updateQuery = "UPDATE tasks SET task_name = ?, task_description = ?, due_date = ?, priority = ?, task_status = ?, task_notes = ?, reminder = ? WHERE task_id = ?";
            try (PreparedStatement psmt = connection.prepareStatement(updateQuery)) {
                psmt.setString(1, task.getTaskName());
                psmt.setString(2, task.getDescription());
                psmt.setDate(3, java.sql.Date.valueOf(task.getDueDate()));
                psmt.setString(4, task.getPriority().toString()); // Priority is an Enum
                psmt.setString(5, task.getStatus().toString()); // TaskStatus is an Enum
                psmt.setString(6, task.getNotes());
                psmt.setTimestamp(7, java.sql.Timestamp.valueOf(task.getReminder()));
                psmt.setInt(8, task.getTaskId());

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

            String deleteTaskQuery = "DELETE FROM tasks WHERE task_id=?";
            try (PreparedStatement taskPsmt = connection.prepareStatement(deleteTaskQuery)) {
                taskPsmt.setInt(1, taskId);


                deleteSubTask(taskId);
                deleteTaskTag(taskId);
                int rowAffected = taskPsmt.executeUpdate();

                return rowAffected > 0;
            }
        } catch (SQLException e) {
            throw new DAOException("Error while deleting task: " + e.getMessage());
        }
    }

    public static boolean deleteSubTask(int taskId) throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {

            String deleteSubtasksQuery = "DELETE FROM subTasks WHERE task_id=?";

            try (PreparedStatement subtasksPsmt = connection.prepareStatement(deleteSubtasksQuery)) {
                subtasksPsmt.setInt(1, taskId);

                int rowAffected = subtasksPsmt.executeUpdate();
                return rowAffected > 0;
            }
        } catch (SQLException e) {
            throw new DAOException("Error while deleting subtask: " + e.getMessage());
        }
    }

    public static boolean deleteTaskTag(int taskId) throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {

            String deleteTagsQuery = "DELETE FROM taskTags WHERE task_id=?";
            try (PreparedStatement tagsPsmt = connection.prepareStatement(deleteTagsQuery)) {
                tagsPsmt.setInt(1, taskId);

                int rowAffected = tagsPsmt.executeUpdate();
                return rowAffected > 0;
            }
        }
        catch (SQLException e) {
            throw new DAOException("Error while deleting task tag: " + e.getMessage());
        }
    }


    //TODO: why use lowercase in tag
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
            String insertQuery = "INSERT INTO tasktags (task_id, tag_name) VALUES (?, ?)";
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

    public static List<Tasktags> readTaskTags() throws DAOException {
        List<Tasktags> taskTagList = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection()) {
            String selectQuery = "SELECT tasktags.tag_id, tasks.task_id, tasks.task_name, tasktags.tag_name " +
                    "FROM tasks " +
                    "LEFT JOIN tasktags ON tasks.task_id = tasktags.task_id";
            try (PreparedStatement psmt = connection.prepareStatement(selectQuery);
                 ResultSet rs = psmt.executeQuery()) {

                while (rs.next()) {
                    Tasktags tasktags = new Tasktags();
                    tasktags.setTagId(rs.getInt("tag_id"));
                    tasktags.setTaskId(rs.getInt(TASKID));
                    tasktags.setTaskName(rs.getString(TASKNAME));
                    tasktags.setTagName(rs.getString("tag_name"));
                    taskTagList.add(tasktags);
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
            String updateQuery = "UPDATE tasktags SET tag_name =? WHERE task_id=?";
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


    public static boolean createSubTask(Subtask subtask) throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String insertQuery = "INSERT INTO subtasks (task_id, subtask, description, due_date, priority, status, reminder) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement psmt = connection.prepareStatement(insertQuery)) {
                psmt.setInt(1, subtask.getTaskId());
                psmt.setString(2, subtask.getSubtaskName());
                psmt.setString(3, subtask.getDescription());
                psmt.setDate(4, java.sql.Date.valueOf(subtask.getDueDate()));
                psmt.setString(5, subtask.getPriority().toString());
                psmt.setString(6, subtask.getStatus().toString());
                psmt.setTimestamp(7, java.sql.Timestamp.valueOf(subtask.getReminder()));

                int rowAffected = psmt.executeUpdate();

                return rowAffected > 0;
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    //TODO: ask null issue to naresh sir

    /**
     * Retrieves a list of tasks along with corresponding subtask information from the database.
     *
     * @return An ArrayList of ArrayLists, where each inner ArrayList contains task and subtask information.
     * @throws DAOException If an error occurs while reading tasks and subtasks.
     */

    public static List<Subtask> readSubTask() throws DAOException {
        List<Subtask> taskWithSubTaskList = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection()) {
            String selectQuery = "SELECT \n" +
                    "tasks.task_id, \n" +
                    "subtasks.subtask_id, \n" +
                    "tasks.task_name, \n" +
                    "subtasks.subtask , \n" +
                    "subtasks.description, \n" +
                    "subtasks.due_date, \n" +
                    "subtasks.priority, \n" +
                    "subtasks.status, \n" +
                    "subtasks.reminder \n" +
                    "FROM tasks\n" +
                    "LEFT JOIN subtasks ON tasks.task_id = subtasks.task_id";
            try (PreparedStatement psmt = connection.prepareStatement(selectQuery);
                 ResultSet rs = psmt.executeQuery()) {

                while (rs.next()) {
                    Subtask subtask = new Subtask();
                    subtask.setTaskId(rs.getInt(TASKID));
                    subtask.setSubtaskId(rs.getInt("subtask_id"));
                    subtask.setTaskName(rs.getString(TASKNAME));

                    // Check for null values before setting optional properties
                    String subtaskName = rs.getString("subtask");
                    if (!rs.wasNull()) {
                        subtask.setSubtaskName(subtaskName);
                    }

                    String description = rs.getString("description");
                    if (!rs.wasNull()) {
                        subtask.setDescription(description);
                    }

                    String priorityValue = rs.getString("priority");
                    if (!rs.wasNull()) {
                        subtask.setPriority(TaskPriority.valueOf(priorityValue));
                    }

                    String statusValue = rs.getString("status");
                    if (!rs.wasNull()) {
                        subtask.setStatus(TaskStatus.valueOf(statusValue));
                    }


                    Timestamp reminderTimestamp = rs.getTimestamp("reminder");
                    if (!rs.wasNull()) {
                        subtask.setReminder(reminderTimestamp.toLocalDateTime());
                    }

                    taskWithSubTaskList.add(subtask);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }

        return taskWithSubTaskList;
    }

    public static List<Integer> getAllSubtaskIds() throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String selectQuery = "SELECT subtask_id FROM subtasks";
            try (PreparedStatement psmt = connection.prepareStatement(selectQuery)) {
                try (ResultSet rs = psmt.executeQuery()) {
                    List<Integer> idList = new ArrayList<>();
                    while (rs.next()) {
                        idList.add(rs.getInt("subtask_id"));
                    }
                    return idList;
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error while reading subtask id: " + e.getMessage());
        }
    }

    public static boolean updatesubtask(Subtask subtask) throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String updateQuery = "UPDATE subtasks SET subtask = ?, description = ?, due_date = ?, priority = ?, status = ?, reminder = ? WHERE subtask_id = ?";
            try (PreparedStatement psmt = connection.prepareStatement(updateQuery)) {
                psmt.setString(1, subtask.getSubtaskName());
                psmt.setString(2, subtask.getDescription());
                psmt.setDate(3, java.sql.Date.valueOf(subtask.getDueDate()));
                psmt.setString(4, subtask.getPriority().toString());
                psmt.setString(5, subtask.getStatus().toString());
                psmt.setTimestamp(6, java.sql.Timestamp.valueOf(subtask.getReminder()));
                psmt.setInt(7, subtask.getSubtaskId());

                int rowAffected = psmt.executeUpdate();
                return rowAffected > 0;
            }
        } catch (SQLException e) {
            throw new DAOException("Error while updating subtask: " + e.getMessage());
        }
    }



}



