package com.ncit.finder.models;

import java.time.LocalDateTime;

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
	


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public LocalDateTime getCommentedOn() {
		return commentedOn;
	}
	public void setCommentedOn(LocalDateTime commentedOn) {
		this.commentedOn = commentedOn;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	@Override
	public String toString() {
		return "Comment [id=" + id + ", content=" + content + ", commentedOn=" + commentedOn + ", user=" + user
				+ ", post=" + post + "]";
	}
	public long getYearsTillNow() {
		return yearsTillNow;
	}
	public void setYearsTillNow(long yearsTillNow) {
		this.yearsTillNow = yearsTillNow;
	}
	public long getMonthsTillNow() {
		return monthsTillNow;
	}
	public void setMonthsTillNow(long monthsTillNow) {
		this.monthsTillNow = monthsTillNow;
	}
	public long getDaysTillNow() {
		return daysTillNow;
	}
	public void setDaysTillNow(long daysTillNow) {
		this.daysTillNow = daysTillNow;
	}
	public long getHoursTillNow() {
		return hoursTillNow;
	}
	public void setHoursTillNow(long hoursTillNow) {
		this.hoursTillNow = hoursTillNow;
	}
	public long getMinutesTillNow() {
		return minutesTillNow;
	}
	public void setMinutesTillNow(long minutesTillNow) {
		this.minutesTillNow = minutesTillNow;
	}
	public long getSecondsTillNow() {
		return secondsTillNow;
	}
	public void setSecondsTillNow(long secondsTillNow) {
		this.secondsTillNow = secondsTillNow;
	}
	
	
}
