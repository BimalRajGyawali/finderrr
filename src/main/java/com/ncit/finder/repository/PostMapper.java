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
import com.ncit.finder.utils.LocalDateTimeParser;

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

                    Post post = new Post();
                    post.setUser(user);
                    post.setId(postId);
                  
                    post.setStatus(Status.valueOf(resultSet.getString("status")));
                    
                    
                    post.setContent(resultSet.getString("content"));
                    if (resultSet.getTimestamp("posted_on") != null) {
                        post.setPostedDateTime(resultSet.getTimestamp("posted_on").toLocalDateTime());

                        LocalDateTime fromTemp = post.getPostedDateTime();
                        long[] parsedDateTime = LocalDateTimeParser.parse(fromTemp);
                        long years = parsedDateTime[0];
                        long months = parsedDateTime[1];
                        long days = parsedDateTime[2];
                        long hours = parsedDateTime[3];
                        long minutes = parsedDateTime[4];
                        long seconds = parsedDateTime[5];

                        post.setYearsTillNow(years);
                        post.setMonthsTillNow(months);
                        post.setDaysTillNow(days);
                        post.setHoursTillNow(hours);
                        post.setMinutesTillNow(minutes);
                        post.setSecondsTillNow(seconds);

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
