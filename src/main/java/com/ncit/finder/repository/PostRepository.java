package com.ncit.finder.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.ncit.finder.db.DB;
import com.ncit.finder.db.DBResponse;
import com.ncit.finder.models.JoinRequest;
import com.ncit.finder.models.Post;
import com.ncit.finder.models.User;

public class PostRepository {

	public List<Post> getPosts(int n, LocalDateTime dateTime) {
		Timestamp before = Timestamp.valueOf(dateTime);
		List<Post> posts = new ArrayList<>();
		Connection connection = DB.makeConnection();
		PreparedStatement preparedStatement;
		String sql = "SELECT p.id post_id, p.content, p.posted_on, p.comments_count, p.join_requests_count,\n"
				+ "u.id user_id, u.firstname, u.middlename, u.lastname, u.joined_on, u.bio\n" 
				+ "FROM posts p\n"
				+ "INNER JOIN users u\n"
				+ "WHERE p.user_id = u.id and p.posted_on < \"" + before + "\"\n"
				+ "ORDER BY p.posted_on DESC\n" + "LIMIT " + n;

		try {
			preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getInt("user_id"));
				user.setFirstName(resultSet.getString("firstname"));
				user.setMiddleName(resultSet.getString("middlename"));
				user.setLastName(resultSet.getString("lastname"));
				user.setBio(resultSet.getString("bio"));
				if (resultSet.getTimestamp("joined_on") != null) {
					user.setJoinedOn(resultSet.getTimestamp("joined_on").toLocalDateTime());
				}

				Post post = new Post();
				post.setUser(user);
				post.setId(resultSet.getInt("post_id"));
				post.setContent(resultSet.getString("content"));
				if (resultSet.getTimestamp("posted_on") != null) {
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
					post.setDaysTillNow(days);
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
		} finally {
			DB.closeConnection(connection);
		}

		return posts;
	}

	public boolean createPost(Post post) {
		Connection connection = DB.makeConnection();

		String sql = "INSERT INTO posts(content, posted_on, user_id) " + "VALUES(?, ?, ?)";

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
		} finally {
			DB.closeConnection(connection);
		}
		return false;
	}

	public DBResponse addJoinRequest(JoinRequest joinRequest) {
		Connection connection = DB.makeConnection();
		PreparedStatement statement;
		
		String sql = "SELECT join_requests_count\n"
		+"FROM posts WHERE id=?";

		DBResponse response = new DBResponse();
		try {

			statement = connection.prepareStatement(sql);
			statement.setInt(1, joinRequest.getPost().getId());
			ResultSet rs =  statement.executeQuery();
			int joinRequestCount = 0;
			while(rs.next()){
				joinRequestCount = rs.getInt(1);
			}
			sql = "INSERT INTO join_requests(post_id, user_id, requested_on)\n" + "VALUES(?,?,?)\n";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, joinRequest.getPost().getId());
			statement.setInt(2, joinRequest.getUser().getId());
			statement.setTimestamp(3, Timestamp.valueOf(joinRequest.getRequestedOn()));
			statement.executeUpdate();

			System.out.println("Join Req. "+joinRequestCount);
			joinRequestCount += 1;
			System.out.println("Join Req. "+joinRequestCount);
			sql = "UPDATE posts\n"
			+"SET join_requests_count = ?\n"
			+"WHERE id = ?\n";

			statement = connection.prepareStatement(sql);
			statement.setInt(1, joinRequestCount);
			statement.setInt(2, joinRequest.getPost().getId());
			statement.executeUpdate();

			response.setResponseMessage("Join Request sent Succesfully");
			response.setSuccessStatus(true);
			return response;

		}catch(SQLIntegrityConstraintViolationException e){
			response.setResponseMessage("Join Request to this post has been already sent");
			response.setSuccessStatus(true);
			return response;
		}  
		catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DB.closeConnection(connection);
		}
		response.setResponseMessage("Error in sending join request");
		response.setSuccessStatus(false);
		
		return response;
	}

	public Post getPostWithJoinRequests(int postId){
		String sql = "SELECT sp.post_id, sp.post_content, sp.post_posted_on, sp.post_comments_count, sp.post_join_requests_count,"
		+"sp.post_user_id, sp.post_user_firstname , sp.post_user_middlename, sp.post_user_lastname, sp.post_user_joined_on, sp.post_user_bio"
		+", u.id join_requests_user_id, u.firstname join_requests_user_firstname, u.middlename join_requests_user_middlename, u.lastname join_requests_user_lastname, u.joined_on join_requests_user_joined_on, u.bio join_requests_user_bio\n"
		+"FROM join_requests j\n"
		+"INNER JOIN users u on j.user_id = u.id\n"
		+"RIGHT JOIN\n" 
		+"(SELECT\n"
		+"p.id post_id, p.content post_content, p.posted_on post_posted_on, p.comments_count post_comments_count, p.join_requests_count post_join_requests_count,"
		+"u.id post_user_id, u.firstname post_user_firstname , u.middlename post_user_middlename, u.lastname post_user_lastname, u.joined_on post_user_joined_on, u.bio post_user_bio\n"
		+"FROM posts p INNER JOIN users u on p.user_id = u.id WHERE p.id = ?) sp ON j.post_id = sp.post_id;";

		Connection connection = DB.makeConnection();
		PreparedStatement statement;
		Post post = new Post();


		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, postId);
			ResultSet resultSet = statement.executeQuery();


			List<User> usersMakingJoinRequests = new ArrayList<>();

			while(resultSet.next()){
				User userMakingJoinRequest = new User();
				userMakingJoinRequest.setId(resultSet.getInt("join_requests_user_id"));
				userMakingJoinRequest.setFirstName(resultSet.getString("join_requests_user_firstname"));
				userMakingJoinRequest.setMiddleName(resultSet.getString("join_requests_user_middlename"));
				userMakingJoinRequest.setLastName(resultSet.getString("join_requests_user_lastname"));
				if(resultSet.getTimestamp("join_requests_user_joined_on") != null){
					userMakingJoinRequest.setJoinedOn(resultSet.getTimestamp("join_requests_user_joined_on").toLocalDateTime());
				}
				userMakingJoinRequest.setBio(resultSet.getString("join_requests_user_bio"));

				usersMakingJoinRequests.add(userMakingJoinRequest);

				post.setId(resultSet.getInt("post_id"));
				post.setContent(resultSet.getString("post_content"));	
				post.setCommentsCount(resultSet.getInt("post_comments_count"));
				post.setJoinRequestsCount(resultSet.getInt("post_join_requests_count"));

				User user = new User();
				user.setId(resultSet.getInt("post_user_id"));
				user.setFirstName(resultSet.getString("post_user_firstname"));
				user.setMiddleName(resultSet.getString("post_user_middlename"));
				user.setLastName(resultSet.getString("post_user_lastname"));
				if(resultSet.getTimestamp("post_user_joined_on") != null){
					user.setJoinedOn(resultSet.getTimestamp("post_user_joined_on").toLocalDateTime());
				}
				user.setBio(resultSet.getString("post_user_bio"));

				post.setUser(user);
			}
			System.out.println("users "+usersMakingJoinRequests);
			post.setUsersRequestingToJoin(usersMakingJoinRequests);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return post;
	}
}
