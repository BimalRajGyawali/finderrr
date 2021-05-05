package com.ncit.finder.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import com.ncit.finder.db.DB;
import com.ncit.finder.models.User;
import com.ncit.finder.models.UserDetail;

public class UserRepository {
	public boolean createUser(UserDetail userdetail){
		Connection connection =  DB.makeConnection();
		String sql = "INSERT INTO users(firstname,middlename,lastname,joined_on) VALUES (?,?,?,?)";
		String sql2="INSERT INTO user_details(email,pass,user_id) VALUES (?,?,?)";
		
		PreparedStatement preparedStatement,preparedStatement2,pInsertOid;
		
		try {
			//prevents resubmission of verification code to again insert a row in user_details .
			if(!testEmail(userdetail.getEmail())) {
				return false;
			}
			
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement2=connection.prepareStatement(sql2);
			preparedStatement.setString(1,userdetail.getUser().getFirstName());
			preparedStatement.setString(2,userdetail.getUser().getMiddleName());
			preparedStatement.setString(3,userdetail.getUser().getLastName());
			preparedStatement.setTimestamp(4, Timestamp.valueOf(userdetail.getUser().getJoinedOn()));
			preparedStatement2.setString(1,userdetail.getEmail());
			preparedStatement2.setString(2,userdetail.getPass());
		
			
			
			pInsertOid = connection.prepareStatement(preparedStatement.toString().split(": ")[1], Statement.RETURN_GENERATED_KEYS);
			pInsertOid.executeUpdate();
			ResultSet rs = pInsertOid.getGeneratedKeys();
			if (rs.next()) {
			 
			  preparedStatement2.setInt(3,rs.getInt(1));
			  
			}
			preparedStatement2.executeUpdate();
			return true;
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			DB.closeConnection(connection);
		}
		
		
		return false;
	}
	public User getUser(String email,String pass) {
		
		Connection connection =  DB.makeConnection();
		PreparedStatement preparedStatement;
		String sql="SELECT u.id,u.firstname,u.middlename,u.lastname FROM users u INNER JOIN user_details ud ON u.id = ud.user_id where email=? and pass=?";
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
				return user;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();	
		}finally {
			DB.closeConnection(connection);
		}
		 return user;
		
	}
	
	public boolean testEmail(String email) {
		Connection connection =  DB.makeConnection();
		PreparedStatement preparedStatement;
		String sqlTest="select * from user_details where email=?"; 
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
			DB.closeConnection(connection);
		}
		
		
		return false;
	}
	
	
	
	public boolean insertImageWithBio(String name,String email,String bio) {
		Connection connection =  DB.makeConnection();
		PreparedStatement preparedStatement;
		String sql="UPDATE user_details SET profile_pic = ? WHERE email=?"; 
		
		boolean bioChanged=bio.isEmpty();
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1,name);
			preparedStatement.setString(2,email);
			preparedStatement.executeUpdate();
			return true;
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			DB.closeConnection(connection);
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
	
	
}
