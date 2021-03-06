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
import com.ncit.finder.functionality.LocalDateTimeParser;
import com.ncit.finder.models.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentRepository {
	private DB db;

	@Autowired
	public CommentRepository(DB db) {
		this.db = db;
	}

	public Post getPost(int temp_id) {
		Post post = new Post();
		List<Comment> post_comments = new ArrayList<>();
		int total_comments;
		boolean flag = true;
		User post_user = new User();
		Connection connection = db.makeConnection();
		PreparedStatement preparedStatement;
		String sql = "SELECT sp.post_id, sp.post_content, sp.post_posted_on, sp.post_comments_count,sp.status, sp.post_join_requests_count,\r\n"
				+ "        sp.post_user_id, sp.post_user_firstname , sp.post_user_middlename, sp.post_user_lastname, sp.post_user_joined_on, sp.post_user_bio,sp.post_user_email, sp.post_user_pass, sp.post_user_pp, c.id comment_id,c.content comment_content, c.commented_on\r\n"
				+ "        ,u.id comments_user_id, u.firstname comments_user_firstname, u.middlename comments_user_middlename, u.lastname comments_user_lastname, u.joined_on comments_user_joined_on, u.bio comments_user_bio, u.email comments_user_email, u.pass comments_user_pass, u.profile_pic comments_user_pp\r\n"
				+ "        FROM comments c\r\n" + "        INNER JOIN users u on c.user_id = u.id\r\n"
				+ "        RIGHT JOIN \r\n" + "        (SELECT\r\n"
				+ "        p.id post_id, p.content post_content,p.status, p.posted_on post_posted_on, p.comments_count post_comments_count, p.join_requests_count post_join_requests_count,\r\n"
				+ "        u.id post_user_id, u.firstname post_user_firstname , u.middlename post_user_middlename, u.lastname post_user_lastname, u.joined_on post_user_joined_on, u.bio post_user_bio,u.email post_user_email, u.pass post_user_pass, u.profile_pic post_user_pp\r\n"
				+ "        FROM posts p INNER JOIN users u on p.user_id = u.id where p.id=?) sp ON c.post_id = sp.post_id ORDER BY c.commented_on DESC";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, temp_id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				if (flag) {
					flag = false;
					post.setId(resultSet.getInt("post_id"));
					post.setContent(resultSet.getString("post_content"));
					post.setStatus(Status.valueOf(resultSet.getString("status")));
					if (resultSet.getTimestamp("post_posted_on") != null) {
						post.setPostedDateTime(resultSet.getTimestamp("post_posted_on").toLocalDateTime());
					}
					total_comments = resultSet.getInt("post_comments_count");
					post.setCommentsCount(total_comments);
					// System.out.println(resultSet.getInt("post_comments_count"));
					post.setJoinRequestsCount(resultSet.getInt("post_join_requests_count"));

					post_user.setId(resultSet.getInt("post_user_id"));
					post_user.setFirstName(resultSet.getString("post_user_firstname"));
					post_user.setMiddleName(resultSet.getString("post_user_middlename"));
					post_user.setLastName(resultSet.getString("post_user_lastname"));
					post_user.setEmail(resultSet.getString("post_user_email"));
					post_user.setPass(resultSet.getString("post_user_pass"));
					post_user.setProfilePic(resultSet.getString("post_user_pp"));
					post_user.setBio(resultSet.getString("post_user_bio"));

					if (resultSet.getTimestamp("post_user_joined_on") != null) {
						post_user.setJoinedOn(resultSet.getTimestamp("post_user_joined_on").toLocalDateTime());
					}

					post.setUser(post_user);
					// System.out.println(post+"|"+post_user);

				}

				if (resultSet.getInt("post_comments_count") != 0) {
					Comment temp_comment = new Comment();
					temp_comment.setId(resultSet.getInt("comment_id"));
					temp_comment.setContent(resultSet.getString("comment_content"));

					User comment_user = new User();

					comment_user.setId(resultSet.getInt("comments_user_id"));
					comment_user.setFirstName(resultSet.getString("comments_user_firstname"));
					comment_user.setMiddleName(resultSet.getString("comments_user_middlename"));
					comment_user.setLastName(resultSet.getString("comments_user_lastname"));
					if (resultSet.getTimestamp("comments_user_joined_on") != null) {
						comment_user.setJoinedOn(resultSet.getTimestamp("comments_user_joined_on").toLocalDateTime());
					}
					comment_user.setEmail(resultSet.getString("comments_user_email"));
					comment_user.setPass(resultSet.getString("comments_user_pass"));
					comment_user.setProfilePic(resultSet.getString("comments_user_pp"));

					temp_comment.setUser(comment_user);
					if (resultSet.getTimestamp("commented_on") != null) {
						temp_comment.setCommentedOn(resultSet.getTimestamp("commented_on").toLocalDateTime());

					}

					post_comments.add(temp_comment);
				}

			}
			post.setComments(post_comments);
			// System.out.println(post+"|"+post_user);
			// System.out.println(post_comments+"|");
			String hashTagSql = "SELECT hashtag FROM posts_hashtags WHERE post_id = ?;";
			List<HashTag> hashTags = new ArrayList<>();
			preparedStatement = connection.prepareStatement(hashTagSql);
			preparedStatement.setInt(1, temp_id);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				hashTags.add(new HashTag(rs.getString("hashtag")));
			}
			post.setHashTags(hashTags);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeConnection(connection);
		}

		return post;
	}

	public boolean createComment(Comment comment, int post_id, int comments_count) {
		Connection connection = db.makeConnection();

		String sql = "INSERT INTO comments( content,commented_on,user_id,post_id) VALUES (?,?,?,?)";
		String sql_comment_count_update = "UPDATE posts SET comments_count=? WHERE id=?";

		PreparedStatement preparedStatement, preparedStatement2;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, comment.getContent());
			preparedStatement.setTimestamp(2, Timestamp.valueOf(comment.getCommentedOn()));
			preparedStatement.setInt(3, comment.getUser().getId());
			preparedStatement.setInt(4, post_id);

			preparedStatement2 = connection.prepareStatement(sql_comment_count_update);

			preparedStatement2.setInt(1, ++comments_count);
			preparedStatement2.setInt(2, post_id);
			System.err.println(comment.getCommentedOn());

			preparedStatement.executeUpdate();
			preparedStatement2.executeUpdate();

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeConnection(connection);
		}
		return false;

	}

}
