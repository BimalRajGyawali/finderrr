package com.ncit.finder.models;

import java.time.LocalDateTime;

public class Following {
    private int userId;
    private String hashTag;
    private LocalDateTime followeDateTime;

    
    public Following(int userId, String hashTag, LocalDateTime followeDateTime) {
        this.userId = userId;
        this.hashTag = hashTag;
        this.followeDateTime = followeDateTime;
    }
    

    public Following(int userId, String hashTag) {
        this.userId = userId;
        this.hashTag = hashTag;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getHashTag() {
        return hashTag;
    }

    public void setHashTag(String hashTag) {
        this.hashTag = hashTag;
    }

    public LocalDateTime getFolloweDateTime() {
        return followeDateTime;
    }

    public void setFolloweDateTime(LocalDateTime followeDateTime) {
        this.followeDateTime = followeDateTime;
    }

    @Override
    public String toString() {
        return "Following [followeDateTime=" + followeDateTime + ", hashTag=" + hashTag + ", userId=" + userId + "]";
    }

}
