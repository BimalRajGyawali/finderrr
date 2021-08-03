package com.ncit.finder.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {
	private int id;
	private String content;
	private LocalDateTime commentedOn;
	private User user;
	private Post post;

	private long yearsTillNow;
	private long monthsTillNow;
	private long daysTillNow;
	private long hoursTillNow;
	private long minutesTillNow;
	private long secondsTillNow;

}
