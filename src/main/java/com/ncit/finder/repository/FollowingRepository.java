package com.ncit.finder.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.ncit.finder.db.DB;
import com.ncit.finder.models.Following;

public class FollowingRepository {

    public boolean follow(Following following){
        Connection connection = DB.makeConnection();
        String sql = "INSERT INTO followings(user_id, hashtag, followed_on)\n"
                     +"VALUES(?, ?, ?);";
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
        }finally{
            DB.closeConnection(connection);
        }
        return false;
    }

    public boolean unfollow(Following following){
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
        }finally{
            DB.closeConnection(connection);
        }
        return false;
    }
}
