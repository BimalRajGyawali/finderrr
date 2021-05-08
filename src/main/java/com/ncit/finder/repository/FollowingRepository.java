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

public class FollowingRepository {

    public boolean follow(Following following) {
        Connection connection = DB.makeConnection();
        String sql = "INSERT INTO followings(user_id, hashtag, followed_on)\n" + "VALUES(?, ?, ?);";
        PreparedStatement preparedStatement;
        try {
            System.out.println(following);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, following.getUserId());
            preparedStatement.setString(2, following.getHashTag());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(following.getFolloweDateTime()));
            System.out.println(sql);
            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeConnection(connection);
        }
        return false;
    }

    public boolean unfollow(Following following) {
        Connection connection = DB.makeConnection();
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
            DB.closeConnection(connection);
        }
        return false;
    }

    public List<HashTag> recommendedHashTags(int userId, int n) {
        // Pass n = -1 to view all recommended hashtags
        Connection connection = DB.makeConnection();
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
                String title = rs.getString("title");
                HashTag hashTag = new HashTag();
                hashTag.setTitle(title);
                hashTags.add(hashTag);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeConnection(connection);
        }

        return hashTags;
    }
}
