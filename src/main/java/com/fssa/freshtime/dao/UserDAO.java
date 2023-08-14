package com.fssa.freshtime.dao;

import com.fssa.freshtime.exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public boolean userRegistration(String emailId, String userName, String password) throws DAOException{
        try (Connection connection = ConnectionUtil.getConnection()) {

            String insertQuery = "INSERT INTO users (emailId, userName, password) VALUES (?, ?, ?)";
            try (PreparedStatement psmt = connection.prepareStatement(insertQuery)) {

                psmt.setString(1, emailId);
                psmt.setString(2, userName);
                psmt.setString(3, password);

                int rowAffected = psmt.executeUpdate();
                return rowAffected > 0;
            }
        } catch (SQLException e) {
            throw new DAOException("Error while registering user: " + e.getMessage());
        }
    }

    public boolean userLogin(String emailId, String password) throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {

            String selectQuery = "SELECT COUNT(*) FROM users WHERE emailId = ? AND password = ?";
            try (PreparedStatement psmt = connection.prepareStatement(selectQuery)) {

                psmt.setString(1, emailId);
                psmt.setString(2, password);

                try (ResultSet rs = psmt.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt(1);
                        return count > 0;
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new DAOException("Error while validating user credentials: " + e.getMessage());
        }
        return false;
    }

    public boolean forgotPassword(String emailId, String password) throws DAOException{
        try (Connection connection = ConnectionUtil.getConnection()) {

            String updateQuery = "UPDATE users SET password = ? WHERE emailId = ?";
            try (PreparedStatement psmt = connection.prepareStatement(updateQuery)) {

                psmt.setString(1, emailId);
                psmt.setString(2, password);

                int rowAffected = psmt.executeUpdate();
                return rowAffected > 0;

            }
        } catch (SQLException e) {
            throw new DAOException("Error while validating user credentials: " + e.getMessage());
        }
    }

    public boolean deleteUser(String emailId, String password) throws DAOException {
        try (Connection connection = ConnectionUtil.getConnection()) {

            String deleteQuery = "DELETE FROM users WHERE emailId = ? AND password = ?";
            try (PreparedStatement psmt = connection.prepareStatement(deleteQuery)) {

                psmt.setString(1, emailId);
                psmt.setString(2, password);

                int rowAffected = psmt.executeUpdate();
                return rowAffected > 0;
            }
        }
        catch (SQLException e) {
            throw new DAOException("Error while deleting user: " + e.getMessage());
        }
    }

}
