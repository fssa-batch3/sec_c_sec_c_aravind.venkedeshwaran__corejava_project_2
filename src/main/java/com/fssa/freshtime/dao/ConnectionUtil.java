package com.fssa.freshtime.dao;

import java.sql.*;


public class ConnectionUtil {
         private static final String DB_URL = "jdbc:mysql://:3306/freshtime";
         private static final String DB_USER = "root";
         private static final String DB_PASSWORD = "root";

    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
