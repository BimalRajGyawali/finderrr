package com.ncit.finder.mappers;

import com.ncit.finder.models.User;
import lombok.Builder;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@Builder
public class UserMapper {
    private String idFieldName;
    private String firstNameFieldName;
    private String middleNameFieldName;
    private String lastNameFieldName;
    private String bioFieldName;
    private String joinedOnFieldName;
    private String emailFieldName;
    private String passwordFieldName;
    private String profilePicFieldName;


    public User map(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getInt(idFieldName))
                .firstName(resultSet.getString(firstNameFieldName))
                .middleName(resultSet.getString(middleNameFieldName))
                .lastName(resultSet.getString(lastNameFieldName))
                .bio(resultSet.getString(bioFieldName))
                .email(resultSet.getString(emailFieldName))
                .pass(resultSet.getString(passwordFieldName))
                .profilePic(resultSet.getString(profilePicFieldName))
                .build();


    }

    public static UserMapper ofDefaultFieldNames() {
        return UserMapper.builder()
                .idFieldName("user_id")
                .firstNameFieldName("firstname")
                .middleNameFieldName("middlename")
                .lastNameFieldName("lastname")
                .emailFieldName("email")
                .passwordFieldName("pass")
                .bioFieldName("bio")
                .profilePicFieldName("profile_pic")
                .build();
    }
}
