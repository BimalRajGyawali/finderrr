package com.ncit.finder.models;

import com.ncit.finder.functionality.LocalDateTimeParser;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {
	private int id;
	private String content;
	private LocalDateTime commentedOn;
	private User user;
	private Post post;
	private DurationTillNow durationTillNow;

	public void setCommentedOn(LocalDateTime commentedOn) {
		this.commentedOn = commentedOn;
		this.durationTillNow = LocalDateTimeParser.parse(commentedOn);
	}
}
