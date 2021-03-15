package com.ncit.finder.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.ncit.finder.db.DB;
import com.ncit.finder.models.Post;
import com.ncit.finder.models.User;

public class PostRepository {
	
	
	public List<Post> getPosts(){
		List<Post> posts = new ArrayList<>();
		Connection connection = DB.makeConnection();
		PreparedStatement preparedStatement;
		String sql = "SELECT p.id post_id, p.content, p.posted_on, p.comments_count, p.join_requests_count,\n"
				+ "u.id user_id, u.firstname, u.middlename, u.lastname, u.joined_on, u.bio\n"
				+ "FROM posts p\n"
				+ "INNER JOIN users u\n"
				+ "WHERE p.user_id = u.id\n"
				+ "ORDER BY p.posted_on DESC";
				
		try {
			preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getInt("user_id"));
				user.setFirstName(resultSet.getString("firstname"));
				user.setMiddleName(resultSet.getString("middlename"));
				user.setLastName(resultSet.getString("lastname"));
				user.setBio(resultSet.getString("bio"));
				if(resultSet.getTimestamp("joined_on") != null) {
					user.setJoinedOn(resultSet.getTimestamp("joined_on").toLocalDateTime());
				}
				
				
				
				Post post = new Post();
				post.setUser(user);
				post.setId(resultSet.getInt("post_id"));
				post.setContent(resultSet.getString("content"));
				if(resultSet.getTimestamp("posted_on") != null) {
					post.setPostedDateTime(resultSet.getTimestamp("posted_on").toLocalDateTime());	
					
					LocalDateTime fromTemp = post.getPostedDateTime();
					LocalDateTime to = LocalDateTime.now();
					
				     	long years = fromTemp.until(to, ChronoUnit.YEARS);
				        fromTemp = fromTemp.plusYears(years);

				        long months = fromTemp.until(to, ChronoUnit.MONTHS);
				        fromTemp = fromTemp.plusMonths(months);
				        
				        long days = fromTemp.until(to, ChronoUnit.DAYS);
				        fromTemp = fromTemp.plusDays(days);

				        long hours = fromTemp.until(to, ChronoUnit.HOURS);
				        fromTemp = fromTemp.plusHours(hours);

				        long minutes = fromTemp.until(to, ChronoUnit.MINUTES);
				        fromTemp = fromTemp.plusMinutes(minutes);

				        long seconds = fromTemp.until(to, ChronoUnit.SECONDS);
				        
				        post.setYearsTillNow(years);
				        post.setMonthsTillNow(months);
				        post.setHoursTillNow(hours);
				        post.setMinutesTillNow(minutes);
				        post.setSecondsTillNow(seconds);
				
				}
				post.setCommentsCount(resultSet.getInt("comments_count"));
				post.setJoinRequestsCount(resultSet.getInt("join_requests_count"));
				
				posts.add(post);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DB.closeConnection(connection);
		}
		
		return posts;
	}
	
	
	public boolean createPost(Post post) {
		Connection connection =  DB.makeConnection();

		String sql = "INSERT INTO posts(content, posted_on, user_id) "
				+ "VALUES(?, ?, ?)";
		
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, post.getContent());
			preparedStatement.setTimestamp(2, Timestamp.valueOf(post.getPostedDateTime()));
			preparedStatement.setInt(3, post.getUser().getId());
			
			preparedStatement.executeUpdate();
		
			return true;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DB.closeConnection(connection);
		}
		return false;
	}
}
