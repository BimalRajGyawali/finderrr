package com.ncit.finder.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ncit.finder.models.HashTag;
import com.ncit.finder.models.Post;
import com.ncit.finder.models.Status;
import com.ncit.finder.models.User;
import com.ncit.finder.functionality.LocalDateTimeParser;

public class PostMapper {

    public static List<Post> mapResultSetIntoPosts(ResultSet resultSet) {
        Map<Integer, Post> postMap = new LinkedHashMap<>();
        try {
            while (resultSet.next()) {

                int postId = resultSet.getInt("p_id");

                if (postMap.containsKey(postId)) {
                    HashTag h = new HashTag();
                    h.setTitle(resultSet.getString("hashtag"));
                    postMap.get(postId).getHashTags().add(h);
                } else {
                    User user = new User();
                    user.setId(resultSet.getInt("user_id"));
                    user.setFirstName(resultSet.getString("firstname"));
                    user.setMiddleName(resultSet.getString("middlename"));
                    user.setLastName(resultSet.getString("lastname"));
                    user.setBio(resultSet.getString("bio"));
                    if (resultSet.getTimestamp("joined_on") != null) {
                        user.setJoinedOn(resultSet.getTimestamp("joined_on").toLocalDateTime());
                    }
                
				
				user.setEmail(resultSet.getString("email"));
				user.setPass(resultSet.getString("pass"));
				user.setProfilePic(resultSet.getString("profile_pic"));	
		

                    Post post = new Post();
                    post.setUser(user);
                    post.setId(postId);
                  
                    post.setStatus(Status.valueOf(resultSet.getString("status")));
                    
                    
                    post.setContent(resultSet.getString("content"));
                    if (resultSet.getTimestamp("posted_on") != null) {
                        post.setPostedDateTime(resultSet.getTimestamp("posted_on").toLocalDateTime());
                    }
                    post.setCommentsCount(resultSet.getInt("comments_count"));
                    post.setJoinRequestsCount(resultSet.getInt("join_requests_count"));

                    HashTag h = new HashTag();
                    h.setTitle(resultSet.getString("hashtag"));
                    List<HashTag> hashTags = new ArrayList<>();
                    hashTags.add(h);

                    post.setHashTags(hashTags);
                    postMap.put(postId, post);

                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(postMap.values());
    }

}
