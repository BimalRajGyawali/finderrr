package com.ncit.finder.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JoinRequest {
    private int id;
    private Post post;
    private User user;
    private LocalDateTime requestedOn;
}
