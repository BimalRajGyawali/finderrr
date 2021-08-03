package com.ncit.finder.mappers;

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
                Post post = map(resultSet);

                if (postMap.containsKey(post.getId())) {
                    postMap
                            .get(post.getId())
                            .getHashTags()
                            .add(new HashTag(resultSet.getString("hashtag")));
                } else {
                    User user = UserMapper.map(resultSet);
                    List<HashTag> hashTags = new ArrayList<>();
                    hashTags.add(new HashTag(resultSet.getString("hashtag")));

                    post.setUser(user);
                    post.setHashTags(hashTags);

                    postMap.put(post.getId(), post);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(postMap.values());
    }

    private static Post map(ResultSet resultSet) throws SQLException {
        Post post = new Post();

        int postId = resultSet.getInt("p_id");
        post.setId(postId);
        post.setStatus(Status.valueOf(resultSet.getString("status")));
        post.setContent(resultSet.getString("content"));
        post.setPostedDateTime(resultSet.getTimestamp("posted_on").toLocalDateTime());
        post.setCommentsCount(resultSet.getInt("comments_count"));
        post.setJoinRequestsCount(resultSet.getInt("join_requests_count"));

        return post;
    }

}
