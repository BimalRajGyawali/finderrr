package com.ncit.finder.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Following {
    private int userId;
    private String hashTag;
    private LocalDateTime followedDateTime;

    public Following(int userId, String hashTag, LocalDateTime followedDateTime) {
        this.userId = userId;
        this.hashTag = hashTag;
        this.followedDateTime = followedDateTime;
    }

    public Following(int userId, String hashTag) {
       this(userId, hashTag, LocalDateTime.now());
    }
}
