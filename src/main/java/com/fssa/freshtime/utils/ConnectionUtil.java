package com.fssa.freshtime.utils;

import java.sql.*;

public class ConnectionUtil {

	public static Connection getConnection() {
		Connection con = null;

		String url, userName, passWord;

		url = System.getenv("DATABASE_HOST");
		userName = System.getenv("DATABASE_USERNAME");
		passWord = System.getenv("DATABASE_PASSWORD");

//		url = System.getenv("CLOUD_DATABASE_HOST");
//		userName = System.getenv("CLOUD_DATABASE_USERNAME");
//		passWord = System.getenv("CLOUD_DATABASE_PASSWORD");
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, userName, passWord);
			Logger.info("Connected to db");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to connect to the database");
		}
		return con;
	}
	
	public static void main(String[] args) {
		System.out.println(ConnectionUtil.getConnection());
	}

}

