package com.ncit.finder.mappers;

import com.ncit.finder.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    public static User map(ResultSet resultSet) throws SQLException {

        User user = new User();

        user.setId(resultSet.getInt("user_id"));
        user.setFirstName(resultSet.getString("firstname"));
        user.setMiddleName(resultSet.getString("middlename"));
        user.setLastName(resultSet.getString("lastname"));
        user.setBio(resultSet.getString("bio"));
        user.setJoinedOn(resultSet.getTimestamp("joined_on").toLocalDateTime());
        user.setEmail(resultSet.getString("email"));
        user.setPass(resultSet.getString("pass"));
        user.setProfilePic(resultSet.getString("profile_pic"));


        return user;
    }
}
