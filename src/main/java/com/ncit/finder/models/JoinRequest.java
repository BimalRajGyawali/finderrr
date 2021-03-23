package com.ncit.finder.models;

import java.time.LocalDateTime;

public class JoinRequest {
    private int id;
    private Post post;
    private User user;
    private LocalDateTime requestedOn;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Post getPost() {
        return post;
    }
    public void setPost(Post post) {
        this.post = post;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public LocalDateTime getRequestedOn() {
        return requestedOn;
    }
    public void setRequestedOn(LocalDateTime requestedOn) {
        this.requestedOn = requestedOn;
    }

    @Override
    public String toString() {
        return "JoinRequest [id=" + id + ", post=" + post + ", requestedOn=" + requestedOn + ", user=" + user + "]";
    }
}
