package com.ncit.finder.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.ncit.finder.db.DB;
import com.ncit.finder.models.Following;
import com.ncit.finder.models.HashTag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class FollowingRepository {
    private DB db;

    @Autowired
    public FollowingRepository(DB db) {
        this.db = db;
    }

    public boolean follow(Following following) {
        Connection connection = db.makeConnection();
        String sql = "INSERT INTO followings(user_id, hashtag, followed_on)\n" + "VALUES(?, ?, ?);";
        PreparedStatement preparedStatement;
        try {
            System.out.println(following);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, following.getUserId());
            preparedStatement.setString(2, following.getHashTag());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(following.getFollowedDateTime()));
            System.out.println(sql);
            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection(connection);
        }
        return false;
    }

    public boolean unfollow(Following following) {
        Connection connection = db.makeConnection();
        String sql = "DELETE FROM followings WHERE user_id = ? AND hashtag = ?;\n";

        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, following.getUserId());
            preparedStatement.setString(2, following.getHashTag());
            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection(connection);
        }
        return false;
    }

    public List<HashTag> recommendedHashTags(int userId, int n) {
        // Pass n = -1 to view all recommended hashtags
        Connection connection = db.makeConnection();
        PreparedStatement preparedStatement;
        List<HashTag> hashTags = new ArrayList<>();
        String sql = "";
        if (n == -1) {
            sql = "SELECT title FROM hashtags\n"
                    + "WHERE title NOT IN (SELECT hashtag FROM followings WHERE user_id = ?) \n"
                    + "ORDER BY total_followers\n";
        } else {
            sql = "SELECT title FROM hashtags\n"
                    + "WHERE title NOT IN (SELECT hashtag FROM followings WHERE user_id = ?) \n"
                    + "ORDER BY total_followers\n" + "LIMIT " + n;
        }

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                hashTags.add(new HashTag(rs.getString("title")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection(connection);
        }

        return hashTags;
    }
    public List<HashTag> followedHashTags(int userId) {
        
        Connection connection = db.makeConnection();
        PreparedStatement preparedStatement;
        List<HashTag> hashTags = new ArrayList<>();
        String sql = "SELECT hashtag FROM followings WHERE user_id = ? ;";
              
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                hashTags.add(new HashTag(rs.getString("hashtag")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection(connection);
        }

        return hashTags;
    }

    public boolean hasFollowed(int userId, String hashTag){
        Connection connection = db.makeConnection();
        PreparedStatement preparedStatement;
        String sql = "SELECT * FROM followings WHERE user_id = ? AND hashtag = ?;";
              
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, hashTag);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            db.closeConnection(connection);
        }
        return false;
    }
    public boolean isHashTagPresent(String title){
        Connection connection = db.makeConnection();
        PreparedStatement preparedStatement;
        String sql = "SELECT * FROM hashtags WHERE title = ?;";
              
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, title);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            db.closeConnection(connection);
        }
        return false;
    }
}
