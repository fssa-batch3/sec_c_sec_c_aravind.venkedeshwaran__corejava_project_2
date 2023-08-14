package com.fssa.freshtime.utils;

import com.fssa.freshtime.exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CommonTry {
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
}
