package com.ncit.finder.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ncit.finder.db.DB;
import com.ncit.finder.db.DBResponse;
import com.ncit.finder.models.HashTag;
import com.ncit.finder.models.JoinRequest;
import com.ncit.finder.models.Post;
import com.ncit.finder.models.User;
import com.ncit.finder.utils.LocalDateTimeParser;

public class PostRepository {

	public Post getPostById(int postId) {
		Connection connection = DB.makeConnection();
		PreparedStatement preparedStatement;
		Post post = null;

		String sql = "SELECT * \n" + "FROM posts_hashtags ph\n" + "INNER JOIN \n"
				+ "( SELECT p.id p_id , p.content, p.posted_on, p.comments_count, p.join_requests_count,p.status, "
				+ "u.id user_id, u.firstname, u.lastname, u.middlename, u.joined_on, u.bio\n" + "FROM posts p \n"
				+ "INNER JOIN users u ON p.user_id = u.id WHERE p.id = ?) sp \n" + "ON ph.post_id = sp.p_id\n";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, postId);
			ResultSet resultSet = preparedStatement.executeQuery();
			List<Post> posts = PostMapper.mapResultSetIntoPosts(resultSet);
			post = posts.size() > 0 ? posts.get(0) : null;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeConnection(connection);
		}

		return post;
	}

	public List<Post> getDetailedPosts(int n, LocalDateTime dateTime) {

		Timestamp before = Timestamp.valueOf(dateTime);
		Connection connection = DB.makeConnection();
		PreparedStatement preparedStatement;
		List<Post> posts = new ArrayList<>();

		String sql = "SELECT * \n" + "FROM posts_hashtags ph\n" + "INNER JOIN \n"
				+ "( SELECT p.id p_id , p.content, p.posted_on, p.comments_count, p.join_requests_count, p.status,"
				+ "u.id user_id, u.firstname, u.lastname, u.middlename, u.joined_on, u.bio\n" + "FROM posts p\n"
				+ "INNER JOIN users u WHERE p.user_id = u.id AND p.posted_on < ' " + before + "'\n"
				+ "ORDER BY p.posted_on DESC LIMIT " + n + " )sp \n" + "ON ph.post_id = sp.p_id\n";

		try {
			preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			posts = PostMapper.mapResultSetIntoPosts(resultSet);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeConnection(connection);
		}

		return posts;
	}

	public List<Post> getPostsFromHashTag(String hashTag, int n, LocalDateTime dateTime) {

		Timestamp before = Timestamp.valueOf(dateTime);
		Connection connection = DB.makeConnection();
		PreparedStatement preparedStatement;
		List<Post> posts = new ArrayList<>();

		String sql = "SELECT * FROM\n"
				+ "( SELECT p.id p_id, p.content, p.posted_on, p.comments_count, p.join_requests_count,p.status,"
				+ "u.id user_id, u.firstname, u.middlename, u.lastname, u.bio, u.joined_on\n"
				+ "FROM posts p INNER JOIN users u ON p.user_id = u.id\n"
				+ "WHERE p.id IN (SELECT ph.post_id FROM posts_hashtags ph WHERE ph.hashtag LIKE ? )\n"
				+ "AND p.posted_on < ?\n" + "ORDER BY p.posted_on DESC LIMIT " + n + ") sp \n"
				+ "INNER JOIN posts_hashtags phh on phh.post_id = sp.p_id;";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, "%" + hashTag + "%");
			preparedStatement.setString(2, before.toString());
			ResultSet resultSet = preparedStatement.executeQuery();
			posts = PostMapper.mapResultSetIntoPosts(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
			;
		}
		return posts;
	}

	public boolean createPost(Post post) {
		Connection connection = DB.makeConnection();
		String postSql = "INSERT INTO posts(content, posted_on, user_id, status) VALUES(?, ?, ?, ?);";
		String hashTagSql = "INSERT INTO hashtags(title) VALUES(?);";
		String postHashTagSql = "INSERT INTO posts_hashtags(post_id, hashtag) VALUES(?, ?);";

		PreparedStatement preparedStatement;
		int generatedPostId = 0;

		try {

			// 1. Insert post into posts table
			preparedStatement = connection.prepareStatement(postSql, new String[] { "id" });
			preparedStatement.setString(1, post.getContent());
			preparedStatement.setTimestamp(2, Timestamp.valueOf(post.getPostedDateTime()));
			preparedStatement.setInt(3, post.getUser().getId());
			preparedStatement.setString(4, post.getStatus().toString());

			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				generatedPostId = rs.getInt(1);
			}

			// 2. Get all hashtags of post and insert into hashtags table if not exist
			// already

			List<HashTag> hashTags = post.getHashTags();

			for (HashTag hashTag : hashTags) {

				preparedStatement = connection.prepareStatement(hashTagSql);
				preparedStatement.setString(1, hashTag.getTitle());
				try {
					preparedStatement.executeUpdate();
					// If no exception occurs, hashtag doesnot exist already.

				} catch (SQLIntegrityConstraintViolationException ex) {
					ex.printStackTrace();
				}
				// 3. Also link the post and hashtag in posts_hashtags table
				preparedStatement = connection.prepareStatement(postHashTagSql);
				preparedStatement.setInt(1, generatedPostId);
				preparedStatement.setString(2, hashTag.getTitle());
				preparedStatement.executeUpdate();

			}
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeConnection(connection);
		}

		return false;
	}

	public boolean updatePost(Post post) {
		String deletePostHashTagSql = "DELETE FROM posts_hashtags WHERE post_id = ?";
		String postSql = "UPDATE posts\n" + "SET content = ?, status = ? WHERE id = ?;";
		String hashTagSql = "INSERT INTO hashtags(title) VALUES(?);";
		String postHashTagSql = "INSERT INTO posts_hashtags(post_id, hashtag) VALUES(?, ?);";

		Connection connection = DB.makeConnection();
		PreparedStatement preparedStatement;

		try {
			preparedStatement = connection.prepareStatement(deletePostHashTagSql);
			preparedStatement.setInt(1, post.getId());
			preparedStatement.executeUpdate();

			preparedStatement = connection.prepareStatement(postSql);
			preparedStatement.setString(1, post.getContent());
			preparedStatement.setString(2, post.getStatus().toString());
			preparedStatement.setInt(3, post.getId());
			preparedStatement.executeUpdate();

			List<HashTag> hashTags = post.getHashTags();
			System.out.println(hashTags);

			for (HashTag hashTag : hashTags) {

				preparedStatement = connection.prepareStatement(hashTagSql);
				preparedStatement.setString(1, hashTag.getTitle());
				try {
					preparedStatement.executeUpdate();
					// If no exception occurs, hashtag doesnot exist already.

				} catch (SQLIntegrityConstraintViolationException ex) {
					ex.printStackTrace();
				}
				// 3. Also link the post and hashtag in posts_hashtags table
				preparedStatement = connection.prepareStatement(postHashTagSql);
				preparedStatement.setInt(1, post.getId());
				preparedStatement.setString(2, hashTag.getTitle());
				preparedStatement.executeUpdate();

			
			}
			return true;

		}

		catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public DBResponse addJoinRequest(JoinRequest joinRequest) {
		Connection connection = DB.makeConnection();
		PreparedStatement statement;

		String sql = "SELECT join_requests_count\n" + "FROM posts WHERE id=?";

		DBResponse response = new DBResponse();
		try {

			statement = connection.prepareStatement(sql);
			statement.setInt(1, joinRequest.getPost().getId());
			ResultSet rs = statement.executeQuery();
			int joinRequestCount = 0;
			while (rs.next()) {
				joinRequestCount = rs.getInt(1);
			}
			sql = "INSERT INTO join_requests(post_id, user_id, requested_on)\n" + "VALUES(?,?,?)\n";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, joinRequest.getPost().getId());
			statement.setInt(2, joinRequest.getUser().getId());
			statement.setTimestamp(3, Timestamp.valueOf(joinRequest.getRequestedOn()));
			statement.executeUpdate();

			joinRequestCount += 1;
			sql = "UPDATE posts\n" + "SET join_requests_count = ?\n" + "WHERE id = ?\n";

			statement = connection.prepareStatement(sql);
			statement.setInt(1, joinRequestCount);
			statement.setInt(2, joinRequest.getPost().getId());
			statement.executeUpdate();

			response.setResponseMessage("Join Request sent Succesfully");
			response.setSuccessStatus(true);
			return response;

		} catch (SQLIntegrityConstraintViolationException e) {
			response.setResponseMessage("Join Request to this post has been already sent");
			response.setSuccessStatus(true);
			return response;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeConnection(connection);
		}
		response.setResponseMessage("Error in sending join request");
		response.setSuccessStatus(false);

		return response;
	}

	public Post getPostWithJoinRequests(int postId) {
		String sql = "SELECT sp.post_id, sp.post_content, sp.post_posted_on, sp.post_comments_count, sp.post_join_requests_count,"
				+ "sp.post_user_id, sp.post_user_firstname , sp.post_user_middlename, sp.post_user_lastname, sp.post_user_joined_on, sp.post_user_bio"
				+ ", u.id join_requests_user_id, u.firstname join_requests_user_firstname, u.middlename join_requests_user_middlename, u.lastname join_requests_user_lastname, u.joined_on join_requests_user_joined_on, u.bio join_requests_user_bio\n"
				+ "FROM join_requests j\n" + "INNER JOIN users u on j.user_id = u.id\n" + "RIGHT JOIN\n" + "(SELECT\n"
				+ "p.id post_id, p.content post_content,p.status, p.posted_on post_posted_on, p.comments_count post_comments_count, p.join_requests_count post_join_requests_count,"
				+ "u.id post_user_id, u.firstname post_user_firstname , u.middlename post_user_middlename, u.lastname post_user_lastname, u.joined_on post_user_joined_on, u.bio post_user_bio\n"
				+ "FROM posts p INNER JOIN users u on p.user_id = u.id WHERE p.id = ?) sp ON j.post_id = sp.post_id;";

		Connection connection = DB.makeConnection();
		PreparedStatement statement;
		Post post = new Post();

		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, postId);
			ResultSet resultSet = statement.executeQuery();

			List<User> usersMakingJoinRequests = new ArrayList<>();

			while (resultSet.next()) {
				User userMakingJoinRequest = new User();
				userMakingJoinRequest.setId(resultSet.getInt("join_requests_user_id"));
				userMakingJoinRequest.setFirstName(resultSet.getString("join_requests_user_firstname"));
				userMakingJoinRequest.setMiddleName(resultSet.getString("join_requests_user_middlename"));
				userMakingJoinRequest.setLastName(resultSet.getString("join_requests_user_lastname"));
				if (resultSet.getTimestamp("join_requests_user_joined_on") != null) {
					userMakingJoinRequest
							.setJoinedOn(resultSet.getTimestamp("join_requests_user_joined_on").toLocalDateTime());
				}
				userMakingJoinRequest.setBio(resultSet.getString("join_requests_user_bio"));
				if (userMakingJoinRequest.isValid()) {
					usersMakingJoinRequests.add(userMakingJoinRequest);
				}

				post.setId(resultSet.getInt("post_id"));
				post.setContent(resultSet.getString("post_content"));
				post.setCommentsCount(resultSet.getInt("post_comments_count"));
				post.setJoinRequestsCount(resultSet.getInt("post_join_requests_count"));

				if (resultSet.getTimestamp("post_posted_on") != null) {
					post.setPostedDateTime(resultSet.getTimestamp("post_posted_on").toLocalDateTime());

					LocalDateTime fromTemp = post.getPostedDateTime();
					long[] parsedDateTime = LocalDateTimeParser.parse(fromTemp);
					long years = parsedDateTime[0];
					long months = parsedDateTime[1];
					long days = parsedDateTime[2];
					long hours = parsedDateTime[3];
					long minutes = parsedDateTime[4];
					long seconds = parsedDateTime[5];

					post.setYearsTillNow(years);
					post.setMonthsTillNow(months);
					post.setDaysTillNow(days);
					post.setHoursTillNow(hours);
					post.setMinutesTillNow(minutes);
					post.setSecondsTillNow(seconds);

				}

				User user = new User();
				user.setId(resultSet.getInt("post_user_id"));
				user.setFirstName(resultSet.getString("post_user_firstname"));
				user.setMiddleName(resultSet.getString("post_user_middlename"));
				user.setLastName(resultSet.getString("post_user_lastname"));
				if (resultSet.getTimestamp("post_user_joined_on") != null) {
					user.setJoinedOn(resultSet.getTimestamp("post_user_joined_on").toLocalDateTime());
				}
				user.setBio(resultSet.getString("post_user_bio"));

				post.setUser(user);
			}
			post.setUsersRequestingToJoin(usersMakingJoinRequests);

			String hashTagSql = "SELECT hashtag FROM posts_hashtags WHERE post_id = ?;";
			List<HashTag> hashTags = new ArrayList<>();
			statement = connection.prepareStatement(hashTagSql);
			statement.setInt(1, postId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				HashTag hashTag = new HashTag();
				hashTag.setTitle(rs.getString("hashtag"));
				hashTags.add(hashTag);
			}
			post.setHashTags(hashTags);

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DB.closeConnection(connection);
		}

		return post;
	}


	
	public boolean deletePost(int postId){
		String commentSql = "DELETE FROM comments WHERE post_id = ?;";
		String joinRequestSql = "DELETE FROM join_requests WHERE post_id = ?;";
		String postHashTagSql = "DELETE FROM posts_hashtags WHERE post_id = ?;";
		String postSql = "DELETE FROM posts WHERE id = ?;";
		

		Connection connection = DB.makeConnection();
		PreparedStatement preparedStatement;

		try {
			preparedStatement = connection.prepareStatement(commentSql);
			preparedStatement.setInt(1, postId);
			preparedStatement.executeUpdate();

			preparedStatement = connection.prepareStatement(joinRequestSql);
			preparedStatement.setInt(1, postId);
			preparedStatement.executeUpdate();

			preparedStatement = connection.prepareStatement(postHashTagSql);
			preparedStatement.setInt(1, postId);
			preparedStatement.executeUpdate();

			preparedStatement = connection.prepareStatement(postSql);
			preparedStatement.setInt(1, postId);
			preparedStatement.executeUpdate();

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DB.closeConnection(connection);
		}

		return false;
	}
}
