package com.ncit.finder.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.ncit.finder.db.DB;
import com.ncit.finder.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRepository {
	private DB db;

	@Autowired
	public UserRepository(DB db) {
		this.db = db;
	}
	public boolean createUser(User user){
		Connection connection =  db.makeConnection();
		String sql = "INSERT INTO users(firstname,middlename,lastname,joined_on, email, pass) VALUES (?,?,?,?,?,?)";
		
		PreparedStatement preparedStatement;
		
		try {
			//prevents resubmission of verification code to again insert a row in user_details .
			if(!testEmail(user.getEmail())) {
				return false;
			}
			
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1,user.getFirstName());
			preparedStatement.setString(2,user.getMiddleName());
			preparedStatement.setString(3,user.getLastName());
			preparedStatement.setTimestamp(4, Timestamp.valueOf(user.getJoinedOn()));
			preparedStatement.setString(5,user.getEmail());
			preparedStatement.setString(6,user.getPass());
			preparedStatement.executeUpdate();
		
			return true;
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			db.closeConnection(connection);
		}
		
		
		return false;
	}
	public User getUserDetail(String email,String pass) {
		
		Connection connection =  db.makeConnection();
		PreparedStatement preparedStatement;
		String sql="SELECT u.id,u.firstname,u.middlename,u.lastname,u.bio,u.profile_pic FROM users u where email=? and pass=?";
		User user=new User();
		 try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1,email);
			preparedStatement.setString(2,pass);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				
				user.setId(resultSet.getInt("id"));
				user.setFirstName(resultSet.getString("firstname"));
				user.setMiddleName(resultSet.getString("middlename"));
				user.setLastName(resultSet.getString("lastname"));
				user.setBio(resultSet.getString("bio"));
				user.setProfilePic(resultSet.getString("profile_pic"));
				
			}
			
		}catch(SQLException e) {
			e.printStackTrace();	
		}finally {
			db.closeConnection(connection);
		}
		 return user;
		
	}
	
	public boolean testEmail(String email) {
		Connection connection =  db.makeConnection();
		PreparedStatement preparedStatement;
		String sqlTest="select * from users where email=?"; 
		try {
			preparedStatement = connection.prepareStatement(sqlTest);
			preparedStatement.setString(1,email);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(!resultSet.isBeforeFirst()) {
				return true;//here true meaning email doenst exist in the database
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			db.closeConnection(connection);
		}
		
		
		return false;
	}
	public boolean insertImage(String name,String email) {
		Connection connection =  db.makeConnection();
		PreparedStatement preparedStatement;
		String sql="UPDATE users SET profile_pic = ? WHERE email=?"; 
		
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1,name);
			preparedStatement.setString(2,email);
			preparedStatement.executeUpdate();
			return true;
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			db.closeConnection(connection);
		}
		return false;
	}
	
	public boolean insertBio(String bio,int id) {
		Connection connection =  db.makeConnection();
		PreparedStatement preparedStatement;
		String sql="UPDATE users SET bio=? WHERE id=?"; 
		System.out.println(id);
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1,bio);
			preparedStatement.setInt(2,id);
			preparedStatement.executeUpdate();
			return true;
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			db.closeConnection(connection);
		}
		return false;
	}
	
	
//	public boolean testProfilePicName(String name) {
//		Connection connection =  DB.makeConnection();
//		PreparedStatement preparedStatement;
//		String sqlTest="SELECT * FROM user_details WHERE profile_pic=?"; 
//		try {
//			preparedStatement = connection.prepareStatement(sqlTest);
//			preparedStatement.setString(1,name);
//			ResultSet resultSet = preparedStatement.executeQuery();
//			if(!resultSet.isBeforeFirst()) {
//				return true;//here true meaning profilepic with same name doenst exist in the database
//			}
//		}catch(SQLException e){
//			e.printStackTrace();
//		}finally {
//			DB.closeConnection(connection);
//		}
//		
//		
//		return false;
//	}
	
	

public User getById(int id) {
		
	Connection connection =  db.makeConnection();
	PreparedStatement preparedStatement;
	String sql="SELECT u.id,u.firstname,u.middlename,u.lastname,u.bio,u.profile_pic FROM users u where id = ?";
	User user=new User();
	 try {
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1,id);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			
			user.setId(resultSet.getInt("id"));
			user.setFirstName(resultSet.getString("firstname"));
			user.setMiddleName(resultSet.getString("middlename"));
			user.setLastName(resultSet.getString("lastname"));
			user.setBio(resultSet.getString("bio"));
			user.setProfilePic(resultSet.getString("profile_pic"));
			
		}
		
	}catch(SQLException e) {
		e.printStackTrace();	
	}finally {
		db.closeConnection(connection);
	}
	 return user;
	
 }
}
