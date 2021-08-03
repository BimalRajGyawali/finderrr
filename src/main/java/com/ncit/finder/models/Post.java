package com.ncit.finder.models;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Post {
	private int id;
	private String content;
	private User user;
	private List<Comment> comments;
	private int commentsCount;
	private List<User> usersRequestingToJoin;
	private List<HashTag> hashTags;
	private int joinRequestsCount;
	private LocalDateTime postedDateTime;
	private Status status;

	private long yearsTillNow;
	private long monthsTillNow;
	private long daysTillNow;
	private long hoursTillNow;
	private long minutesTillNow;
	private long secondsTillNow;

}
