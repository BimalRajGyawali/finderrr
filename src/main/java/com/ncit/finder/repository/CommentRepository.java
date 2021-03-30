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
import com.ncit.finder.models.Comment;
import com.ncit.finder.models.Post;
import com.ncit.finder.models.User;

public class CommentRepository {
	
	public Post getPost(int temp_id){
		Post post = new Post();
		List<Comment> post_comments = new ArrayList<>();
		int total_comments;
		boolean flag=true;
		User post_user=new User();
		Connection connection = DB.makeConnection();
		PreparedStatement preparedStatement;
		String sql = "SELECT sp.post_id, sp.post_content, sp.post_posted_on, sp.post_comments_count, sp.post_join_requests_count,\r\n"
				+ "        sp.post_user_id, sp.post_user_firstname , sp.post_user_middlename, sp.post_user_lastname, sp.post_user_joined_on, sp.post_user_bio,c.id comment_id,c.content comment_content, c.commented_on\r\n"
				+ "        ,u.id comments_user_id, u.firstname comments_user_firstname, u.middlename comments_user_middlename, u.lastname comments_user_lastname, u.joined_on comments_user_joined_on, u.bio comments_user_bio\r\n"
				+ "        FROM comments c\r\n"
				+ "        INNER JOIN users u on c.user_id = u.id\r\n"
				+ "        RIGHT JOIN \r\n"
				+ "        (SELECT\r\n"
				+ "        p.id post_id, p.content post_content, p.posted_on post_posted_on, p.comments_count post_comments_count, p.join_requests_count post_join_requests_count,\r\n"
				+ "        u.id post_user_id, u.firstname post_user_firstname , u.middlename post_user_middlename, u.lastname post_user_lastname, u.joined_on post_user_joined_on, u.bio post_user_bio\r\n"
				+ "        FROM posts p INNER JOIN users u on p.user_id = u.id where p.id=?) sp ON c.post_id = sp.post_id ORDER BY c.commented_on DESC";
				
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1,temp_id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				if(flag) {
					flag=false;
					post.setId(resultSet.getInt("post_id"));
					post.setContent(resultSet.getString("post_content"));
					if(resultSet.getTimestamp("post_posted_on") != null) {
						post.setPostedDateTime(resultSet.getTimestamp("post_posted_on").toLocalDateTime());	
						
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
					total_comments=resultSet.getInt("post_comments_count");
					post.setCommentsCount(total_comments);
//					System.out.println(resultSet.getInt("post_comments_count"));
					post.setJoinRequestsCount(resultSet.getInt("post_join_requests_count"));
					
					post_user.setId(resultSet.getInt("post_user_id"));
					post_user.setFirstName(resultSet.getString("post_user_firstname"));
					post_user.setMiddleName(resultSet.getString("post_user_middlename"));
					post_user.setLastName(resultSet.getString("post_user_lastname"));

					if (resultSet.getTimestamp("post_user_joined_on") != null) {
						post_user.setJoinedOn(resultSet.getTimestamp("post_user_joined_on").toLocalDateTime());
					}

					
					
					post.setUser(post_user);
//					System.out.println(post+"|"+post_user);
					
				}
				
				if(resultSet.getInt("post_comments_count")!=0) {
					Comment temp_comment=new Comment();
					temp_comment.setId(resultSet.getInt("comment_id"));
					temp_comment.setContent(resultSet.getString("comment_content"));
				
					User comment_user=new User();
				
					comment_user.setId(resultSet.getInt("comments_user_id"));
					comment_user.setFirstName(resultSet.getString("comments_user_firstname"));
					comment_user.setMiddleName(resultSet.getString("comments_user_middlename"));
					comment_user.setLastName(resultSet.getString("comments_user_lastname"));
					if(resultSet.getTimestamp("comments_user_joined_on") != null){
						comment_user.setJoinedOn(resultSet.getTimestamp("comments_user_joined_on").toLocalDateTime());
					}
				
					temp_comment.setUser(comment_user);
					if(resultSet.getTimestamp("commented_on") != null){
					temp_comment.setCommentedOn(resultSet.getTimestamp("commented_on").toLocalDateTime());
						
						LocalDateTime fromTemp = temp_comment.getCommentedOn();
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
					        
					        temp_comment.setYearsTillNow(years);
					        temp_comment.setMonthsTillNow(months);
					        temp_comment.setHoursTillNow(hours);
					        temp_comment.setMinutesTillNow(minutes);
					        temp_comment.setSecondsTillNow(seconds);
				
				
				}
				
					post_comments.add(temp_comment);
				}
			
			}
			post.setComments(post_comments);
//			System.out.println(post+"|"+post_user);
//			System.out.println(post_comments+"|");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DB.closeConnection(connection);
		}
		
		return post;
	}
	
	
	public boolean createComment(Comment comment,int post_id,int comments_count) {
		Connection connection =  DB.makeConnection();

		String sql = "INSERT INTO comments( content,commented_on,user_id,post_id) VALUES (?,?,?,?)";
		String sql_comment_count_update="UPDATE posts SET comments_count=? WHERE id=?";
				
		PreparedStatement preparedStatement,preparedStatement2;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, comment.getContent());
			preparedStatement.setTimestamp(2, Timestamp.valueOf(comment.getCommentedOn()));
			preparedStatement.setInt(3,comment.getUser().getId());
			preparedStatement.setInt(4,post_id);
			
			preparedStatement2=connection.prepareStatement(sql_comment_count_update);
			
			preparedStatement2.setInt(1,++comments_count);
			preparedStatement2.setInt(2,post_id);
			System.err.println(comment.getCommentedOn());
			
			preparedStatement.executeUpdate();
			preparedStatement2.executeUpdate();
		
			return true;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DB.closeConnection(connection);
		}
		return false;	
	
	}
	


}
