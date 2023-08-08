package com.fssa.freshtime.dao;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;


public class ConnectionUtil {
    public static Connection getConnection() {
        Connection con = null;

        String url, userName, passWord;

        if (System.getenv("CI") != null) {
            url = System.getenv("DATABASE_HOST");
            userName = System.getenv("DATABASE_USERNAME");
            passWord = System.getenv("DATABASE_PASSWORD");
        } else {
            Dotenv env = Dotenv.load();
            url = env.get("DATABASE_HOST");
            userName = env.get("DATABASE_USERNAME");
            passWord = env.get("DATABASE_PASSWORD");
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, userName, passWord);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to connect to the database");
        }
        return con;
    }

    private static final String DB_URL = "jdbc:mysql://:3306/freshtime";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    public static Connection getMyConnection() throws SQLException {

        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
