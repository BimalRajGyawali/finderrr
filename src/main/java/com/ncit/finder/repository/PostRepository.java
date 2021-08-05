package com.ncit.finder.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ncit.finder.db.DB;
import com.ncit.finder.db.Response;
import com.ncit.finder.mappers.PostMapper;
import com.ncit.finder.mappers.UserMapper;
import com.ncit.finder.models.HashTag;
import com.ncit.finder.models.JoinRequest;
import com.ncit.finder.models.Post;
import com.ncit.finder.models.Status;
import com.ncit.finder.models.User;

import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostRepository implements IPostRepository {
    private DB db;

    @Autowired
    public PostRepository(DB db) {
        this.db = db;
    }

    @Override
    public User getAuthor(int postId) {
        String sql = "SELECT u.id user_id, u.*\n" +
                "FROM posts p\n" +
                "INNER JOIN\n" +
                "users u \n" +
                "ON p.user_id = u.id\n" +
                "where p.id = ?;";

        Connection connection = db.makeConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, postId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return UserMapper.ofDefaultFieldNames().map(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection(connection);
        }
        return null;

    }

    @Override
    public Post getPostById(int postId) {
        Connection connection = db.makeConnection();
        PreparedStatement preparedStatement;
        Post post = null;

        String sql = "SELECT * \n" + "FROM posts_hashtags ph\n" + "INNER JOIN \n"
                + "( SELECT p.id p_id , p.content, p.posted_on, p.comments_count, p.join_requests_count,p.status, "
                + "u.id user_id, u.firstname, u.lastname, u.middlename, u.joined_on, u.bio, u.email, u.pass, u.profile_pic\n" + "FROM posts p \n"
                + "INNER JOIN users u ON p.user_id = u.id WHERE p.id = ?) sp \n" + "ON ph.post_id = sp.p_id\n";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, postId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Post> posts = PostMapper.ofDefaultFieldNames().mapResultSetIntoPosts(resultSet);
            post = posts.size() > 0 ? posts.get(0) : null;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection(connection);
        }

        return post;
    }

    // For non logged in users
    @Override
    public List<Post> getDetailedPosts(int n, LocalDateTime dateTime) {

        Timestamp before = Timestamp.valueOf(dateTime);
        Connection connection = db.makeConnection();
        PreparedStatement preparedStatement;
        List<Post> posts = new ArrayList<>();

        String sql = "SELECT * \n" + "FROM posts_hashtags ph\n" + "INNER JOIN \n"
                + "( SELECT p.id p_id , p.content, p.posted_on, p.comments_count, p.join_requests_count, p.status,"
                + "u.id user_id, u.firstname, u.lastname, u.middlename, u.bio,u.email, u.pass, u.profile_pic\n" + "FROM posts p\n"
                + "INNER JOIN users u ON p.user_id = u.id WHERE p.posted_on < ' " + before + "'\n"
                + "ORDER BY p.posted_on DESC LIMIT\n" + n + ")sp \n"
                + "ON ph.post_id = sp.p_id ORDER BY sp.posted_on DESC; \n";

        try {
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            posts = PostMapper.ofDefaultFieldNames().mapResultSetIntoPosts(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection(connection);
        }

        return posts;
    }


    @Override
    public List<Post> getRecommendedPosts(int userId, int n, LocalDateTime dateTime) {

        Timestamp before = Timestamp.valueOf(dateTime);
        Connection connection = db.makeConnection();
        PreparedStatement preparedStatement;
        List<Post> recommendedPosts = new ArrayList<>();

        String followedPostsSql = "SELECT * \n" + "FROM posts_hashtags ph\n" + "INNER JOIN \n"
                + "( SELECT p.id p_id , p.content, p.posted_on, p.comments_count, p.join_requests_count, p.status,"
                + "u.id user_id, u.firstname, u.lastname, u.middlename, u.joined_on, u.bio, u.email, u.pass, u.profile_pic\n" + "FROM posts p\n"
                + "INNER JOIN users u ON p.user_id = u.id WHERE p.posted_on < ' " + before + "'\n"
                + "AND p.id IN\n"
                + "(SELECT DISTINCT ph1.post_id FROM posts_hashtags ph1 INNER JOIN followings f ON ph1.hashtag = f.hashtag WHERE f.user_id = ?\n)"
                + "ORDER BY p.posted_on DESC LIMIT\n" + n + ")sp \n" + "ON ph.post_id = sp.p_id\n"
                + "ORDER BY sp.posted_on DESC ;";

        try {
            preparedStatement = connection.prepareStatement(followedPostsSql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            recommendedPosts = PostMapper.ofDefaultFieldNames().mapResultSetIntoPosts(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection(connection);
        }
        return recommendedPosts;
    }

    @Override
    public List<Post> getExploringPosts(int userId, int n, LocalDateTime dateTime) {
        Timestamp before = Timestamp.valueOf(dateTime);
        Connection connection = db.makeConnection();
        PreparedStatement preparedStatement;
        List<Post> exploringPosts = new ArrayList<>();

        String exploringPostsSql = "SELECT * \n" + "FROM posts_hashtags ph\n" + "INNER JOIN \n"
                + "( SELECT p.id p_id , p.content, p.posted_on, p.comments_count, p.join_requests_count, p.status,"
                + "u.id user_id, u.firstname, u.lastname, u.middlename, u.joined_on, u.bio, u.email, u.pass, u.profile_pic\n" + "FROM posts p\n"
                + "INNER JOIN users u ON p.user_id = u.id WHERE p.posted_on < ' " + before + "'\n"
                + "AND p.id NOT IN\n"
                + "(SELECT DISTINCT ph1.post_id FROM posts_hashtags ph1 INNER JOIN followings f ON ph1.hashtag = f.hashtag WHERE f.user_id = ?\n)"
                + "ORDER BY p.posted_on DESC LIMIT\n" + n + ")sp \n" + "ON ph.post_id = sp.p_id\n"
                + "ORDER BY sp.posted_on DESC ;";

        try {
            preparedStatement = connection.prepareStatement(exploringPostsSql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            exploringPosts = PostMapper.ofDefaultFieldNames().mapResultSetIntoPosts(resultSet);


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection(connection);
        }
        return exploringPosts;

    }

    @Override
    public List<Post> getPostsFromHashTag(String hashTag, int n, LocalDateTime dateTime) {

        Timestamp before = Timestamp.valueOf(dateTime);
        Connection connection = db.makeConnection();
        PreparedStatement preparedStatement;
        List<Post> posts = new ArrayList<>();

        String sql = "SELECT * FROM\n"
                + "( SELECT p.id p_id, p.content, p.posted_on, p.comments_count, p.join_requests_count,p.status,"
                + "u.id user_id, u.firstname, u.middlename, u.lastname, u.bio, u.joined_on, u.email, u.pass, u.profile_pic\n"
                + "FROM posts p INNER JOIN users u ON p.user_id = u.id\n"
                + "WHERE p.id IN (SELECT ph.post_id FROM posts_hashtags ph WHERE LOWER(ph.hashtag) LIKE ? )\n"
                + "AND p.posted_on < ' " + before + "'\n"
                + "ORDER BY p.posted_on DESC LIMIT\n" + n + ") sp \n"
                + "INNER JOIN posts_hashtags phh on phh.post_id = sp.p_id\n"
                + "ORDER BY sp.posted_on DESC;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + hashTag.toLowerCase() + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            posts = PostMapper.ofDefaultFieldNames().mapResultSetIntoPosts(resultSet);
            System.out.println(posts.size());
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            db.closeConnection(connection);
        }
        return posts;
    }

    @Override
    public int createPost(Post post) {
        Connection connection = db.makeConnection();
        String postSql = "INSERT INTO posts(content, posted_on, user_id, status) VALUES(?, ?, ?, ?);";
        String hashTagSql = "INSERT INTO hashtags(title) VALUES(?);";
        String postHashTagSql = "INSERT INTO posts_hashtags(post_id, hashtag) VALUES(?, ?);";

        PreparedStatement preparedStatement;
        int generatedPostId = 0;

        try {
            // These 3 steps should be wrapped into a transaction.

            // Step 1. Insert post into posts table
            preparedStatement = connection.prepareStatement(postSql, new String[]{"id"});
            preparedStatement.setString(1, post.getContent());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(post.getPostedDateTime()));
            preparedStatement.setInt(3, post.getUser().getId());
            preparedStatement.setString(4, post.getStatus().toString());

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                generatedPostId = rs.getInt(1);
            }

            // Step 2. Get all hashtags of post and insert into hashtags table if not exist
            // already

            Set<HashTag> hashTags = new HashSet<>(post.getHashTags());

            for (HashTag hashTag : hashTags) {
                if (hashTag.getTitle().isEmpty()) {
                    continue;
                }
                preparedStatement = connection.prepareStatement(hashTagSql);
                preparedStatement.setString(1, hashTag.getTitle());
                try {
                    preparedStatement.executeUpdate();
                    // If no exception occurs, hashtag doesnot exist already.

                } catch (SQLIntegrityConstraintViolationException ex) {
                    System.out.println("Hashtag " + hashTag + "already exist");

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                // Step 3. Also link the post and hashtag in posts_hashtags table
                preparedStatement = connection.prepareStatement(postHashTagSql);
                preparedStatement.setInt(1, generatedPostId);
                preparedStatement.setString(2, hashTag.getTitle());
                try {
                    preparedStatement.executeUpdate();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            db.closeConnection(connection);
        }
        return generatedPostId;
    }

    @Override
    public boolean updatePost(Post post) {
        String deletePostHashTagSql = "DELETE FROM posts_hashtags WHERE post_id = ?";
        String postSql = "UPDATE posts\n" + "SET content = ?, status = ? WHERE id = ?;";
        String hashTagSql = "INSERT INTO hashtags(title) VALUES(?);";
        String postHashTagSql = "INSERT INTO posts_hashtags(post_id, hashtag) VALUES(?, ?);";

        Connection connection = db.makeConnection();
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

            Set<HashTag> hashTags = new HashSet<>(post.getHashTags());

            for (HashTag hashTag : hashTags) {
                if (hashTag.getTitle().isEmpty()) {
                    continue;
                }
                preparedStatement = connection.prepareStatement(hashTagSql);
                preparedStatement.setString(1, hashTag.getTitle());
                try {
                    preparedStatement.executeUpdate();
                    // If no exception occurs, hashtag doesnot exist already.

                } catch (SQLIntegrityConstraintViolationException ex) {
                    ex.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                preparedStatement = connection.prepareStatement(postHashTagSql);
                preparedStatement.setInt(1, post.getId());
                preparedStatement.setString(2, hashTag.getTitle());
                try {
                    preparedStatement.executeUpdate();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection(connection);
        }

        return false;
    }

    @Override
    public Response addJoinRequest(JoinRequest joinRequest) {
        Connection connection = db.makeConnection();
        PreparedStatement statement;

        String sql = "SELECT join_requests_count\n" + "FROM posts WHERE id=?";

        Response response = new Response();
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
            sql = "UPDATE posts\n" +
                    "SET join_requests_count = ?\n" +
                    "WHERE id = ?\n";

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
        } catch (PSQLException ex) {
            System.out.println("already sent");
            response.setResponseMessage("Join Request to this post has been already sent");
            response.setSuccessStatus(true);
        } catch (SQLException e) {
            response.setResponseMessage("Error in sending join request");
            response.setSuccessStatus(false);
            e.printStackTrace();
        } catch (Exception e) {
            response.setResponseMessage("Error in sending join request");
            response.setSuccessStatus(false);
            e.printStackTrace();
        } finally {
            db.closeConnection(connection);
        }


        return response;
    }

    @Override
    public Post getPostWithJoinRequests(int postId) {
        String sql = "SELECT sp.p_id, sp.content, sp.posted_on, sp.comments_count,sp.status, sp.join_requests_count,"
                + "sp.post_user_id, sp.post_user_firstname , sp.post_user_middlename, sp.post_user_lastname, sp.post_user_joined_on, sp.post_user_bio, sp.post_user_email, sp.post_user_pass, sp.post_user_pp"
                + ", u.id join_requests_user_id, u.firstname join_requests_user_firstname, u.middlename join_requests_user_middlename, u.lastname join_requests_user_lastname, u.joined_on join_requests_user_joined_on, u.bio join_requests_user_bio, u.email join_requests_user_email, u.pass join_requests_user_pass, u.profile_pic join_requests_user_pp\n"
                + "FROM join_requests j\n" + "INNER JOIN users u on j.user_id = u.id\n" + "RIGHT JOIN\n" + "(SELECT\n"
                + "p.id p_id, p.content ,p.status, p.posted_on , p.comments_count , p.join_requests_count ,"
                + "u.id post_user_id, u.firstname post_user_firstname , u.middlename post_user_middlename, u.lastname post_user_lastname, u.joined_on post_user_joined_on, u.bio post_user_bio, u.email post_user_email, u.pass post_user_pass, u.profile_pic post_user_pp\n"
                + "FROM posts p INNER JOIN users u on p.user_id = u.id WHERE p.id = ?) sp ON j.post_id = sp.p_id;";

        Connection connection = db.makeConnection();
        PreparedStatement statement;

        List<User> usersMakingJoinRequests = new ArrayList<>();
        Post post = null;

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, postId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                if(post == null){
                    post = PostMapper.ofDefaultFieldNames().map(resultSet);

                    User author = UserMapper.builder()
                            .idFieldName("post_user_id")
                            .firstNameFieldName("post_user_firstname")
                            .middleNameFieldName("post_user_middlename")
                            .lastNameFieldName("post_user_lastname")
                            .bioFieldName("post_user_bio")
                            .emailFieldName("post_user_email")
                            .passwordFieldName("post_user_pass")
                            .profilePicFieldName("post_user_pp")
                            .joinedOnFieldName("post_user_joined_on")
                            .build()
                            .map(resultSet);

                    post.setUser(author);

                }
                User userMakingJoinRequest = UserMapper.builder()
                        .idFieldName("join_requests_user_id")
                        .firstNameFieldName("join_requests_user_firstname")
                        .middleNameFieldName("join_requests_user_middlename")
                        .lastNameFieldName("join_requests_user_lastname")
                        .bioFieldName("join_requests_user_bio")
                        .emailFieldName("join_requests_user_email")
                        .passwordFieldName("join_requests_user_pass")
                        .profilePicFieldName("join_requests_user_pp")
                        .joinedOnFieldName("join_requests_user_joined_on")
                        .build()
                        .map(resultSet);

                if (userMakingJoinRequest.isValid()) {
                    usersMakingJoinRequests.add(userMakingJoinRequest);
                }

            }
            post.setUsersRequestingToJoin(usersMakingJoinRequests);

            String hashTagSql = "SELECT hashtag FROM posts_hashtags WHERE post_id = ?;";
            List<HashTag> hashTags = new ArrayList<>();
            statement = connection.prepareStatement(hashTagSql);
            statement.setInt(1, postId);
            ResultSet rs = statement.executeQuery();
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


    @Override
    public boolean deletePost(int postId) {
        String commentSql = "DELETE FROM comments WHERE post_id = ?;";
        String joinRequestSql = "DELETE FROM join_requests WHERE post_id = ?;";
        String postHashTagSql = "DELETE FROM posts_hashtags WHERE post_id = ?;";
        String postSql = "DELETE FROM posts WHERE id = ?;";


        Connection connection = db.makeConnection();
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
        } finally {
            db.closeConnection(connection);
        }

        return false;
    }

    @Override
    public List<Post> getUserIdSpecificPosts(String id, int n, LocalDateTime dateTime) {

        Timestamp before = Timestamp.valueOf(dateTime);
        Connection connection = db.makeConnection();
        PreparedStatement preparedStatement;
        List<Post> posts = new ArrayList<>();

        String sql = "SELECT * \n" + "FROM posts_hashtags ph\n" + "INNER JOIN \n"
                + "( SELECT p.id p_id , p.content, p.posted_on, p.comments_count, p.join_requests_count, p.status,"
                + "u.id user_id, u.firstname, u.lastname, u.middlename, u.joined_on, u.bio,u.email, u.pass, u.profile_pic\n" + "FROM posts p\n"
                + "RIGHT OUTER JOIN users u ON p.user_id = u.id WHERE user_id=? and p.posted_on < ? \n"
                + "ORDER BY p.posted_on DESC LIMIT ? )sp \n"
                + "ON ph.post_id = sp.p_id ORDER BY sp.posted_on DESC; \n";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(id));
            preparedStatement.setTimestamp(2, before);
            preparedStatement.setInt(3, n);
            ResultSet resultSet = preparedStatement.executeQuery();
            posts = PostMapper.ofDefaultFieldNames().mapResultSetIntoPosts(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection(connection);
        }

        return posts;
    }


}
