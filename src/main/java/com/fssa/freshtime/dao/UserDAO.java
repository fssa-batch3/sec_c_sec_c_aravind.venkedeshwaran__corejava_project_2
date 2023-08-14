package com.fssa.freshtime.dao;

import com.fssa.freshtime.exceptions.DAOException;
import com.fssa.freshtime.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public boolean userRegistration(String emailId, String userName, String password) throws DAOException {
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
        } catch (SQLException e) {
            throw new DAOException("Error while validating user credentials: " + e.getMessage());
        }
        return false;
    }

    public boolean forgotPasswordInDB(String emailId, String newPassword) throws DAOException, SQLException {
        try (Connection connection = ConnectionUtil.getConnection()) {

            if (emailExists(emailId, connection)) {
                String updateQuery = "UPDATE users SET password = ? WHERE emailId = ?";
                try (PreparedStatement psmt = connection.prepareStatement(updateQuery)) {
                    psmt.setString(1, newPassword);
                    psmt.setString(2, emailId);

                    int rowAffected = psmt.executeUpdate();
                    return rowAffected > 0;
                }
            } else {
                throw new DAOException("Error while changing password: Invalid Email Id");
            }
        }
    }

    private boolean emailExists(String emailId, Connection connection) throws DAOException {
        String selectQuery = "SELECT COUNT(*) FROM users WHERE emailId = ?";
        try (PreparedStatement psmt = connection.prepareStatement(selectQuery)) {
            psmt.setString(1, emailId);
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error while checking email existence: " + e.getMessage());
        }
        return false;
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
        } catch (SQLException e) {
            throw new DAOException("Error while deleting user: " + e.getMessage());
        }
    }
}

