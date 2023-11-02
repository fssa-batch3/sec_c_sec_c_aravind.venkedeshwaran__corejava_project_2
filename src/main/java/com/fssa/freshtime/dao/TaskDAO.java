package com.fssa.freshtime.dao;

import com.fssa.freshtime.models.enums.TaskPriority;
import com.fssa.freshtime.models.enums.TaskStatus;
import com.fssa.freshtime.exceptions.DAOException;
import com.fssa.freshtime.models.Subtask;
import com.fssa.freshtime.models.Task;
import com.fssa.freshtime.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) class for managing tasks and related data in the
 * database.
 */
public class TaskDAO { 

	public static final String TASKID = "task_id";
	public static final String TASKNAME = "taskname";

	/**
	 * Creates a new task in the database.
	 *
	 * @param task The task object to be created.
	 * @return True if the task creation is successful, false otherwise.
	 * @throws DAOException If an error occurs while creating the task.
	 **/

	public static boolean addTask(Task task) throws DAOException {
	    try (Connection connection = ConnectionUtil.getConnection()) {
	        String insertQuery = "INSERT INTO tasks (user_id, taskname, startdate, enddate, priority, status, reminder, notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	        
	        try (PreparedStatement psmt = connection.prepareStatement(insertQuery)) {
	            psmt.setInt(1, task.getUserId());
	            psmt.setString(2, task.getTaskName());
	            psmt.setTimestamp(3, java.sql.Timestamp.valueOf(task.getStartDate()));
	            psmt.setTimestamp(4, java.sql.Timestamp.valueOf(task.getEndDate()));
	            
	            psmt.setString(5, task.getPriority() != null ? task.getPriority().toString() : null);
	            psmt.setString(6, task.getStatus() != null ? task.getStatus().toString() : null);
	            psmt.setTimestamp(7, task.getReminder() != null ? java.sql.Timestamp.valueOf(task.getReminder()) : null);
	            psmt.setString(8, task.getNotes() != null ? task.getNotes() : null);

	            int rowAffected = psmt.executeUpdate();
	            return rowAffected > 0;
	        }
	    } catch (SQLException e) {
	        throw new DAOException("Error while adding task: " + e.getMessage());
	    }
	}



	/**
	 * Retrieves a list of tasks from the database.
	 *
	 * @return An ArrayList of Task objects representing the tasks retrieved.
	 * @throws DAOException If an error occurs while reading tasks.
	 */
	public static List<Task> readTaskByUser(int userId) throws DAOException {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String selectQuery = "SELECT task_id, user_id, taskname,startdate, enddate, priority, status, "
					+ "notes, reminder, created_date_time FROM tasks WHERE user_id = ?";

			try (PreparedStatement psmt = connection.prepareStatement(selectQuery)) {
				psmt.setInt(1, userId);

				try (ResultSet rs = psmt.executeQuery()) {
					List<Task> taskList = new ArrayList<>();
					while (rs.next()) {
						Task task = new Task();

						task.setTaskId(rs.getInt("task_id"));
						task.setUserId(rs.getInt("user_id"));
						task.setTaskName(rs.getString("taskname"));
						
						task.setStartDate(rs.getTimestamp("startdate") != null ? rs.getTimestamp("startdate").toLocalDateTime() : null);
						task.setEndDate(rs.getTimestamp("enddate") != null ? rs.getTimestamp("enddate").toLocalDateTime() : null);
						task.setPriority(rs.getString("priority") != null ? TaskPriority.valueOf(rs.getString("priority")) : null);
						task.setStatus(rs.getString("status") != null ? TaskStatus.valueOf(rs.getString("status")) : null);
						task.setNotes(rs.getString("notes") != null ? rs.getString("notes") : null);
						task.setReminder(rs.getTimestamp("reminder") != null ? rs.getTimestamp("reminder").toLocalDateTime() : null);
						task.setCreatedDateTime(rs.getTimestamp("created_date_time") != null ? rs.getTimestamp("created_date_time").toLocalDateTime() : null);

						taskList.add(task);
					}
					return taskList;
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Error while reading tasks: " + e.getMessage());
		}
	}


	public static Task readTaskByTaskId(int taskId) throws DAOException {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String selectQuery = "SELECT task_id, user_id, taskname, startdate, enddate, "
					+ "priority, status, reminder, notes, created_date_time FROM tasks WHERE task_id = ?";

			try (PreparedStatement psmt = connection.prepareStatement(selectQuery)) {

				psmt.setInt(1, taskId);

				try (ResultSet rs = psmt.executeQuery()) {

					if (rs.next()) {
						Task task = new Task();

						task.setTaskId(rs.getInt("task_id"));
						task.setUserId(rs.getInt("user_id"));
						task.setTaskName(rs.getString("taskname"));
						
						task.setStartDate(rs.getTimestamp("startdate") != null ? rs.getTimestamp("startdate").toLocalDateTime() : null);
						task.setEndDate(rs.getTimestamp("enddate") != null ? rs.getTimestamp("enddate").toLocalDateTime() : null);
						task.setPriority(rs.getString("priority") != null ? TaskPriority.valueOf(rs.getString("priority")) : null);
						task.setStatus(rs.getString("status") != null ? TaskStatus.valueOf(rs.getString("status")) : null);
						task.setNotes(rs.getString("notes") != null ? rs.getString("notes") : null);
						task.setReminder(rs.getTimestamp("reminder") != null ? rs.getTimestamp("reminder").toLocalDateTime() : null);
						task.setCreatedDateTime(rs.getTimestamp("created_date_time") != null ? rs.getTimestamp("created_date_time").toLocalDateTime() : null);

						return task;
					}
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Error while reading tasks: " + e.getMessage());
		}
		return null;
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
			throw new DAOException("Error while reading task id's: " + e.getMessage());
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
			String updateQuery = "UPDATE tasks SET taskname=?, startdate=?, enddate=?, priority=?, status=?, reminder=?, notes=? WHERE task_id = ?";
			
			try (PreparedStatement psmt = connection.prepareStatement(updateQuery)) {
				
				psmt.setString(1, task.getTaskName());
				
				psmt.setTimestamp(2, task.getStartDate() != null ? java.sql.Timestamp.valueOf(task.getStartDate()) : null);
			    psmt.setTimestamp(3, task.getEndDate() != null ? java.sql.Timestamp.valueOf(task.getEndDate()) : null);
			    psmt.setString(4, task.getPriority() != null ? task.getPriority().toString() : null);
			    psmt.setString(5, task.getStatus() != null ? task.getStatus().toString() : null);
			    psmt.setTimestamp(6, task.getReminder() != null ? java.sql.Timestamp.valueOf(task.getReminder()) : null);
			    psmt.setString(7, task.getNotes() != null ? task.getNotes() : null);
				
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

				int rowAffected = taskPsmt.executeUpdate();

				return rowAffected > 0;
			}
		} catch (SQLException e) {
			throw new DAOException("Error while deleting task: " + e.getMessage());
		}
	}


	public static boolean createSubTask(Subtask subtask) throws DAOException {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String insertQuery = "INSERT INTO subtasks (task_id, subtask, status) VALUES (?, ?, ?)";
			
			try (PreparedStatement psmt = connection.prepareStatement(insertQuery)) {
				
				psmt.setInt(1, subtask.getTaskId());
				psmt.setString(2, subtask.getSubtaskName());
				psmt.setString(3, TaskStatus.TODO.toString());

				int rowAffected = psmt.executeUpdate();

				return rowAffected > 0;
			}
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	public static List<Subtask> readAllSubTaskByTaskId(int taskId) throws DAOException {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String selectQuery = "SELECT subtask_id, task_id, subtask, startdate, enddate, priority, status, reminder, notes, created_date_time "
					+ "FROM subtasks WHERE task_id = ?";

			try (PreparedStatement psmt = connection.prepareStatement(selectQuery)) {

				psmt.setInt(1, taskId);

				try (ResultSet rs = psmt.executeQuery()) {

					List<Subtask> subtaskList = new ArrayList<Subtask>();

					while (rs.next()) {
						Subtask subtask = new Subtask();

						subtask.setSubtaskId(rs.getInt("subtask_id"));
						subtask.setTaskId(rs.getInt("task_id"));
						subtask.setSubtaskName(rs.getString("subtask"));  

						subtask.setStartDate(rs.getTimestamp("startdate") != null ? rs.getTimestamp("startdate").toLocalDateTime() : null);
						subtask.setEndDate(rs.getTimestamp("enddate") != null ? rs.getTimestamp("enddate").toLocalDateTime() : null);
						subtask.setPriority(rs.getString("priority") != null ? TaskPriority.valueOf(rs.getString("priority")) : null);
						subtask.setStatus(rs.getString("status") != null ? TaskStatus.valueOf(rs.getString("status")) : null);
						subtask.setNotes(rs.getString("notes") != null ? rs.getString("notes") : null);
						subtask.setReminder(rs.getTimestamp("reminder") != null ? rs.getTimestamp("reminder").toLocalDateTime() : null);
						subtask.setCreatedDateTime(rs.getTimestamp("created_date_time") != null ? rs.getTimestamp("created_date_time").toLocalDateTime() : null);

						subtaskList.add(subtask);
					}
					return subtaskList;
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Error while reading Subtasks: " + e.getMessage());
		}
	}

	public static Subtask readSubTaskById(int subtaskId) throws DAOException {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String selectSubtaskQuery = "SELECT task_id, subtask, startdate, enddate, priority, status, reminder, notes, created_date_time FROM subtasks WHERE subtask_id = ?";

			try (PreparedStatement psmt = connection.prepareStatement(selectSubtaskQuery)) {

				psmt.setInt(1, subtaskId);

				try (ResultSet rs = psmt.executeQuery()) {

					Subtask subtask = new Subtask();

					while (rs.next()) {

						subtask.setSubtaskId(subtaskId);
						subtask.setTaskId(rs.getInt("task_id"));
						subtask.setSubtaskName(rs.getString("subtask"));  

						subtask.setStartDate(rs.getTimestamp("startdate") != null ? rs.getTimestamp("startdate").toLocalDateTime() : null);
						subtask.setEndDate(rs.getTimestamp("enddate") != null ? rs.getTimestamp("enddate").toLocalDateTime() : null);
						subtask.setPriority(rs.getString("priority") != null ? TaskPriority.valueOf(rs.getString("priority")) : null);
						subtask.setStatus(rs.getString("status") != null ? TaskStatus.valueOf(rs.getString("status")) : null);
						subtask.setNotes(rs.getString("notes") != null ? rs.getString("notes") : null);
						subtask.setReminder(rs.getTimestamp("reminder") != null ? rs.getTimestamp("reminder").toLocalDateTime() : null);
						subtask.setCreatedDateTime(rs.getTimestamp("created_date_time") != null ? rs.getTimestamp("created_date_time").toLocalDateTime() : null);
					}
					return subtask;
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Error while getting Subtasks: " + e.getMessage());
		}
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
			String updateQuery = "UPDATE subtasks SET subtask=?, startdate=?, enddate=?, priority=?, status=?, reminder=?, notes=? WHERE subtask_id = ? ";
			
			try (PreparedStatement psmt = connection.prepareStatement(updateQuery)) {
				
					psmt.setString(1, subtask.getSubtaskName());
					
					psmt.setTimestamp(2, subtask.getStartDate() != null ? java.sql.Timestamp.valueOf(subtask.getStartDate()) : null);
				    psmt.setTimestamp(3, subtask.getEndDate() != null ? java.sql.Timestamp.valueOf(subtask.getEndDate()) : null);
				    psmt.setString(4, subtask.getPriority() != null ? subtask.getPriority().toString() : null);
				    psmt.setString(5, subtask.getStatus() != null ? subtask.getStatus().toString() : null);
				    psmt.setTimestamp(6, subtask.getReminder() != null ? java.sql.Timestamp.valueOf(subtask.getReminder()) : null);
				    psmt.setString(7, subtask.getNotes() != null ? subtask.getNotes() : null);
				    
				    psmt.setInt(8, subtask.getSubtaskId());

				int rowAffected = psmt.executeUpdate();
				return rowAffected > 0;
			}
		} catch (SQLException e) {
			throw new DAOException("Error while updating subtask: " + e.getMessage());
		}
	}

	public static boolean deleteSubTask(int subtaskId) throws DAOException {
		try (Connection connection = ConnectionUtil.getConnection()) {

			String deleteSubtasksQuery = "DELETE FROM subTasks WHERE subtask_id=?";

			try (PreparedStatement subtasksPsmt = connection.prepareStatement(deleteSubtasksQuery)) {
				subtasksPsmt.setInt(1, subtaskId);

				int rowAffected = subtasksPsmt.executeUpdate();
				return rowAffected > 0;
			}
		} catch (SQLException e) {
			throw new DAOException("Error while deleting subtask: " + e.getMessage());
		}
	}

}
