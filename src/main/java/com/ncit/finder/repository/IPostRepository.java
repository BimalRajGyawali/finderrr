package com.ncit.finder.repository;

import com.ncit.finder.db.Response;
import com.ncit.finder.models.JoinRequest;
import com.ncit.finder.models.Post;
import com.ncit.finder.models.User;

import java.time.LocalDateTime;
import java.util.List;

public interface IPostRepository {
    User getAuthor(int postId);

    Post getPostById(int postId);

    // For non logged in users
    List<Post> getDetailedPosts(int n, LocalDateTime dateTime);

    List<Post> getRecommendedPosts(int userId, int n, LocalDateTime dateTime);

    List<Post> getExploringPosts(int userId, int n, LocalDateTime dateTime);

    List<Post> getPostsFromHashTag(String hashTag, int n, LocalDateTime dateTime);

    int createPost(Post post);

    boolean updatePost(Post post);

    Response addJoinRequest(JoinRequest joinRequest);

    Post getPostWithJoinRequests(int postId);

    boolean deletePost(int postId);

    List<Post> getUserIdSpecificPosts(String id, int n, LocalDateTime dateTime);
}
