package com.ncit.finder.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DB {
	@Autowired
	private DataSource dataSource;
	
	public Connection makeConnection() {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			System.out.println("Connecteddddddddddd");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public void closeConnection(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
