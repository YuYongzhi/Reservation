package com.reservation.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	private static ThreadLocal<Connection> connHolder = new ThreadLocal<Connection>();
	public static Connection getConnon(){
		Connection conn = connHolder.get();
		if(conn==null){
			conn = getConnection();
			connHolder.set(conn);
		}
		return conn;
	}
	public static Connection getConnection(){
		Connection conn = null;
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		String username = "admin";
		String pwd = "admin";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url,username,pwd);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	public static void CloseConnection(){
		Connection conn = connHolder.get();
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
