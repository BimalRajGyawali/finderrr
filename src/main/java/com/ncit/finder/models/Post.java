package com.ncit.finder.models;

import com.ncit.finder.functionality.LocalDateTimeParser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
	private DurationTillNow durationTillNow;

	public Post setPostedDateTime(LocalDateTime postedDateTime) {
		this.postedDateTime = postedDateTime;
		this.durationTillNow = LocalDateTimeParser.parse(postedDateTime);
		return this;
	}
}
