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
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostMapper {
    private String idFieldName;
    private String contentFieldName;
    private String statusFieldName;
    private String postedDateTimeFieldName;
    private String commentsCountFieldName;
    private String joinRequestsCountFieldName;


    public List<Post> mapResultSetIntoPosts(ResultSet resultSet) {
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
                    User user = UserMapper.ofDefaultFieldNames().map(resultSet);
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


    public Post map(ResultSet resultSet) throws SQLException {
        return Post.builder()
                .id(resultSet.getInt(idFieldName))
                .content(resultSet.getString(contentFieldName))
                .status(Status.valueOf(resultSet.getString(statusFieldName)))
                .commentsCount(resultSet.getInt(commentsCountFieldName))
                .joinRequestsCount(resultSet.getInt(joinRequestsCountFieldName))
                .postedDateTime(resultSet.getTimestamp(postedDateTimeFieldName).toLocalDateTime())
                .build();
    }


    public static PostMapper ofDefaultFieldNames() {
        return PostMapper.builder()
                .idFieldName("p_id")
                .contentFieldName("content")
                .statusFieldName("status")
                .commentsCountFieldName("comments_count")
                .joinRequestsCountFieldName("join_requests_count")
                .postedDateTimeFieldName("posted_on")
                .build();
    }

}
