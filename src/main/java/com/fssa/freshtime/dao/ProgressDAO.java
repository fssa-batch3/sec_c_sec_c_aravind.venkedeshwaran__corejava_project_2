package com.fssa.freshtime.dao;

import com.fssa.freshtime.enums.TaskStatus;
import com.fssa.freshtime.exceptions.DAOException;
import com.fssa.freshtime.utils.ConnectionUtil;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;

import java.util.Locale;

public class ProgressDAO {
    /**
     * Changes the status of a task and updates its progress in the database.
     *
     * @param taskStatus The new status of the task.
     * @param taskId     The ID of the task whose status is to be changed.
     * @return True if the task status change is successful, false otherwise.
     * @throws DAOException If an error occurs while changing the task status.
     */

    public static boolean changeTaskStatus(TaskStatus taskStatus, int taskId) throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String updateQuery = "UPDATE tasks SET taskStatus = ?, taskStatusUpdatedTime = CURRENT_TIMESTAMP WHERE taskId = ?";
            try (PreparedStatement psmt = connection.prepareStatement(updateQuery)) {
                psmt.setString(1, String.valueOf(taskStatus));
                psmt.setInt(2, taskId);

                int rowAffected = psmt.executeUpdate();

                insertDailyProgress(connection, taskId);
                insertWeeklyProgress(connection, taskId);
                updateOverallProgress(connection);

                return rowAffected > 0;
            }
        } catch (SQLException e) {
            throw new DAOException("Error while changing task status: " + e.getMessage());
        }
    }

    /**
     * Inserts or updates daily progress data in the database based on the task's due date.
     *
     * @param connection The database connection.
     * @param taskId     The ID of the task for which progress data is to be inserted/updated.
     * @throws DAOException If an error occurs while inserting or updating progress data.
     * @throws SQLException If a database access error occurs.
     */

    public static void insertDailyProgress(Connection connection, int taskId) throws DAOException, SQLException {
        Date dueDate = getDueDateByTaskId(connection, taskId);
        int totalTasks = getNoOfTasksWithDueDate(connection, dueDate);
        int completedTasks = getCompletedTaskWithDueDate(connection, dueDate);

        if (progressExist(connection, dueDate)) {
            updateDailyProgressData(connection, dueDate, completedTasks);
        } else {
            insertDailyProgressData(connection, dueDate, totalTasks, completedTasks);
        }
    }


    /**
     * Retrieves the due date of a task based on its ID.
     *
     * @param connection The database connection.
     * @param taskId     The ID of the task.
     * @return The due date of the task, or null if not found.
     * @throws DAOException If an error occurs while retrieving the due date.
     */

    public static Date getDueDateByTaskId(Connection connection, int taskId) throws DAOException {
        String selectQuery = "SELECT dueDate FROM tasks WHERE taskId = ?";
        try (PreparedStatement psmt = connection.prepareStatement(selectQuery)) {
            psmt.setInt(1, taskId);
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDate("dueDate");
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error while getting dueDate by taskId: " + e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves the number of tasks with a specific due date.
     *
     * @param connection The database connection.
     * @param dueDate    The due date for which to count tasks.
     * @return The number of tasks with the specified due date.
     * @throws DAOException If an error occurs while retrieving the task count.
     */

    public static int getNoOfTasksWithDueDate(Connection connection, Date dueDate) throws DAOException {
        int noOfTasks = 0;

        String selectQuery = "SELECT COUNT(*) FROM tasks WHERE dueDate = ?";
        try (PreparedStatement psmt = connection.prepareStatement(selectQuery)) {
            psmt.setDate(1, dueDate);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    noOfTasks = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error while getting number of tasks with due date: " + e.getMessage());
        }

        return noOfTasks;
    }


    /**
     * Retrieves the number of completed tasks with a specific due date.
     *
     * @param connection The database connection.
     * @param dueDate    The due date for which to count completed tasks.
     * @return The number of completed tasks with the specified due date.
     * @throws DAOException If an error occurs while retrieving the completed task count.
     */

    public static int getCompletedTaskWithDueDate(Connection connection, Date dueDate) throws DAOException {
        int completedTasks = 0;

        String selectQuery = "SELECT COUNT(*) FROM tasks WHERE dueDate = ? AND taskStatus = 'COMPLETED'";
        try (PreparedStatement psmt = connection.prepareStatement(selectQuery)) {
            psmt.setDate(1, dueDate);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    completedTasks = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error while getting number of completed tasks with due date: " + e.getMessage());
        }

        return completedTasks;
    }



    /**
     * Checks if progress data already exists for a specific due date.
     *
     * @param connection The database connection.
     * @param dueDate    The due date to check for existing progress data.
     * @return True if progress data exists for the given date, false otherwise.
     * @throws SQLException If a database access error occurs.
     */

    private static boolean progressExist(Connection connection, Date dueDate) throws SQLException {
        String selectQuery = "SELECT COUNT(*) FROM dailyProgress WHERE date = ?";
        try (PreparedStatement psmt = connection.prepareStatement(selectQuery)) {
            psmt.setDate(1, dueDate);

            try (ResultSet rs = psmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    /**
     * Inserts new daily progress data into the database for a specific due date.
     *
     * @param connection    The database connection.
     * @param dueDate       The due date of the task.
     * @param totalTasks    The total number of tasks on the due date.
     * @param completedTasks The number of completed tasks on the due date.
     * @throws DAOException If an error occurs while inserting progress data.
     */

    private static void insertDailyProgressData(Connection connection, Date dueDate, int totalTasks, int completedTasks) throws DAOException {
        String insertQuery = "INSERT INTO dailyProgress (date, totalNoOfTask, completedTask, progress)" +
                "VALUES (?, ?, ?, ?)";
        try (PreparedStatement psmt = connection.prepareStatement(insertQuery)) {
            psmt.setDate(1, dueDate);
            psmt.setInt(2, totalTasks);
            psmt.setInt(3, completedTasks);
            psmt.setInt(4, calculateProgress(totalTasks, completedTasks));

            psmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while inserting daily progress data: " + e.getMessage());
        }
    }


    /**
     * Updates the daily progress data in the database for a specific task's due date.
     *
     * @param connection     The database connection.
     * @param dueDate        The due date of the task.
     * @param completedTasks The number of completed tasks on the due date.
     * @throws DAOException If an error occurs while updating progress data.
     */

    private static void updateDailyProgressData(Connection connection, Date dueDate, int completedTasks) throws DAOException {
        String updateQuery = "UPDATE dailyProgress SET completedTask = ?, progress = ? WHERE date = ?";
        try (PreparedStatement psmt = connection.prepareStatement(updateQuery)) {
            psmt.setInt(1, completedTasks);
            psmt.setInt(2, calculateProgress(getNoOfTasksWithDueDate(connection, dueDate), completedTasks));
            psmt.setDate(3, dueDate);

            psmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while updating daily progress data: " + e.getMessage());
        }
    }


    /**
     * Calculates the progress percentage based on the total and completed tasks.
     *
     * @param totalTasks      The total number of tasks.
     * @param completedTasks  The number of completed tasks.
     * @return The progress percentage (0-100), or 0 if there are no total tasks.
     */

    private static int calculateProgress(int totalTasks, int completedTasks) {
        if (totalTasks == 0) {
            return 0;
        }
        return (completedTasks * 100) / totalTasks;
    }



    /**
     * Inserts or updates the weekly progress data for a specific task.
     *
     * @param connection The database connection.
     * @param taskId The ID of the task for which to insert/update the progress data.
     * @throws DAOException If there's an error with the database operation.
     * @throws SQLException If a SQL-related error occurs.
     */


    public static void insertWeeklyProgress(Connection connection, int taskId) throws DAOException, SQLException {

        LocalDate dueDate = getDueDateByTaskId(connection, taskId).toLocalDate();

        LocalDate startOfWeek = dueDate.minusDays(dueDate.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue());
        LocalDate endOfWeek = dueDate.plusDays(7 - dueDate.getDayOfWeek().getValue());

        // Define the first day of the week as Monday
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        // Get the week number using the week fields
        int weekNumber = dueDate.get(weekFields.weekOfWeekBasedYear());
        
        int totalTasks = getNoOfTasksInCurWeek(connection, startOfWeek, endOfWeek);
        int completedTasks = getCompletedTasksInCurWeek(connection, startOfWeek, endOfWeek);

        int progress = calculateProgress(totalTasks, completedTasks);

        if (progressExist(connection, weekNumber)) {
            updateWeeklyProgressData(connection, weekNumber, completedTasks, progress);
        } else {
            insertWeeklyProgressData(connection, weekNumber, startOfWeek, endOfWeek, totalTasks, completedTasks, progress);
        }
    }


    /**
     * Retrieves the number of tasks within the current week.
     *
     * @param connection The database connection.
     * @param startOfWeek The start date of the week.
     * @param endOfWeek The end date of the week.
     * @return The number of tasks within the current week.
     * @throws SQLException If a SQL-related error occurs.
     */


    private static int getNoOfTasksInCurWeek(Connection connection, LocalDate startOfWeek, LocalDate endOfWeek) throws SQLException {
        String selectQuery = "SELECT COUNT(*) FROM tasks WHERE dueDate >= ? AND dueDate <= ?";
        try (PreparedStatement psmt = connection.prepareStatement(selectQuery)) {
            psmt.setDate(1, java.sql.Date.valueOf(startOfWeek));
            psmt.setDate(2, java.sql.Date.valueOf(endOfWeek));

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }


    /**
     * Retrieves the number of completed tasks within the current week.
     *
     * @param connection The database connection.
     * @param startOfWeek The start date of the week.
     * @param endOfWeek The end date of the week.
     * @return The number of completed tasks within the current week.
     * @throws SQLException If a SQL-related error occurs.
     */

    private static int getCompletedTasksInCurWeek(Connection connection, LocalDate startOfWeek, LocalDate endOfWeek) throws SQLException{
    String selectQuery = "SELECT COUNT(*) FROM tasks WHERE dueDate >= ? AND dueDate <= ? AND taskStatus = 'COMPLETED'";
        try (PreparedStatement psmt = connection.prepareStatement(selectQuery)) {
            psmt.setDate(1, java.sql.Date.valueOf(startOfWeek));
            psmt.setDate(2, java.sql.Date.valueOf(endOfWeek));

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    /**
     * Checks if progress data already exists for a specific week.
     *
     * @param connection The database connection.
     * @param weekNumber The week number.
     * @return True if progress data exists for the week, otherwise false.
     * @throws SQLException If a SQL-related error occurs.
     */

    private static boolean progressExist(Connection connection,int weekNumber) throws SQLException {
        String selectQuery = "SELECT COUNT(*) FROM weeklyProgress WHERE weekNo = ?";
        try (PreparedStatement psmt = connection.prepareStatement(selectQuery)) {
            psmt.setInt(1, weekNumber);

            try (ResultSet rs = psmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    /**
     * Inserts new weekly progress data into the database.
     *
     * @param connection The database connection.
     * @param weekNumber The week number.
     * @param startOfWeek The start date of the week.
     * @param endOfWeek The end date of the week.
     * @param totalTasks The total number of tasks in the week.
     * @param completedTasks The number of completed tasks in the week.
     * @param progress The progress percentage.
     * @throws DAOException If there's an error with the database operation.
     */


    private static void insertWeeklyProgressData(Connection connection, int weekNumber, LocalDate startOfWeek, LocalDate endOfWeek, int totalTasks, int completedTasks, int progress) throws DAOException {
        String insertQuery = "INSERT INTO weeklyProgress (weekNo, startOfWeek, endOfWeek, totalNoOfTask, completedTask, progress)" +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement psmt = connection.prepareStatement(insertQuery)) {
            psmt.setInt(1, weekNumber);
            psmt.setDate(2, Date.valueOf(startOfWeek));
            psmt.setDate(3, Date.valueOf(endOfWeek));
            psmt.setInt(4, totalTasks);
            psmt.setInt(5, completedTasks);
            psmt.setInt(6, progress);

            psmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while inserting weekly progress data: " + e.getMessage());
        }
    }

    /**
     * Updates existing weekly progress data in the database.
     *
     * @param connection The database connection.
     * @param weekNumber The week number.
     * @param completedTasks The number of completed tasks in the week.
     * @param progress The progress percentage.
     * @throws DAOException If there's an error with the database operation.
     */

    private static void updateWeeklyProgressData(Connection connection, int weekNumber, int completedTasks, int progress) throws DAOException {
        String updateQuery = "UPDATE weeklyProgress SET completedTask = ?, progress = ? WHERE weekNo = ?";
        try (PreparedStatement psmt = connection.prepareStatement(updateQuery)) {
            psmt.setInt(1, completedTasks);
            psmt.setInt(2, progress);
            psmt.setInt(3, weekNumber);

            psmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while updating weekly progress data: " + e.getMessage());
        }
    }

    /**
     * Updates the overall progress data based on the completion status of tasks.
     *
     * @param connection The database connection.
     * @throws DAOException If a DAO-related error occurs.
     * @throws SQLException If a SQL-related error occurs.
     */


    public static void updateOverallProgress(Connection connection) throws DAOException, SQLException {
        int totalTaskCount = getOverallTaskCount(connection);
        int totalCompletedTaskCount = getOverallTaskCompletedCount(connection);
        int progress = calculateProgress(totalTaskCount, totalCompletedTaskCount);

        String updateQuery = "UPDATE overallProgress SET totalTaskCount = ? , totalCompletedTaskCount = ? , progress = ? WHERE progressId = 1";
        try (PreparedStatement psmt = connection.prepareStatement(updateQuery)) {

            psmt.setInt(1, totalTaskCount);
            psmt.setInt(2, totalCompletedTaskCount);
            psmt.setInt(3, progress);

            psmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while updating weekly progress data: " + e.getMessage());
        }
    }

    /**
     * Retrieves the total count of tasks in the database.
     *
     * @param connection The database connection.
     * @return The total count of tasks.
     * @throws SQLException If a SQL-related error occurs.
     */
    public static int getOverallTaskCount(Connection connection) throws SQLException {
        String selectQuery = "SELECT COUNT(*) FROM tasks";
        try (PreparedStatement psmt = connection.prepareStatement(selectQuery)) {
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    /**
     * Retrieves the total count of completed tasks in the database.
     *
     * @param connection The database connection.
     * @return The total count of completed tasks.
     * @throws SQLException If a SQL-related error occurs.
     */
    public static int getOverallTaskCompletedCount(Connection connection) throws SQLException {
        String selectQuery = "SELECT COUNT(*) FROM tasks WHERE taskStatus = 'COMPLETED'";
        try (PreparedStatement psmt = connection.prepareStatement(selectQuery)) {
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }


}
