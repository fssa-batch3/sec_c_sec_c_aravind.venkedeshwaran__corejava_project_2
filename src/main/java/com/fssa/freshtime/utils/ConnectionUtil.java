package com.fssa.freshtime.utils;

import com.fssa.freshtime.exceptions.DAOException;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;


public class ConnectionUtil {
    private static final String DATABASE_HOST_ENV_VAR = "DATABASE_HOST";
    private static final String DATABASE_USERNAME_ENV_VAR = "DATABASE_USERNAME";
    private static final String DATABASE_PASSWORD_ENV_VAR = "DATABASE_PASSWORD";
    private static final String CI_ENV_VAR = "CI";

    public static Connection getConnection() throws DAOException {
        String url = getEnvVariable(DATABASE_HOST_ENV_VAR);
        String userName = getEnvVariable(DATABASE_USERNAME_ENV_VAR);
        String passWord = getEnvVariable(DATABASE_PASSWORD_ENV_VAR);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, userName, passWord);
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException("Unable to connect to the database");
        }
    }

    private static String getEnvVariable(String variableName) {
        if (isRunningInCI()) {
            return System.getenv(variableName);
        } else {
            Dotenv env = Dotenv.load();
            return env.get(variableName);
        }
    }

    private static boolean isRunningInCI() {
        return System.getenv(CI_ENV_VAR) != null;
    }

    private static final String DB_URL = "jdbc:mysql://:3306/freshtime";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";

    public static Connection getMyConnection() throws SQLException {

        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
}
