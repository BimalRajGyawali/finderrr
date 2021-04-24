package com.ncit.finder.models;

import java.time.LocalDateTime;
import java.util.List;



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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Comment> getComments() {
		return comments;
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
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public int getCommentsCount() {
		return commentsCount;
	}
	public void setCommentsCount(int commentsCount) {
		this.commentsCount = commentsCount;
	}
	public List<User> getUsersRequestingToJoin() {
		return usersRequestingToJoin;
	}
	public void setUsersRequestingToJoin(List<User> usersRequestingToJoin) {
		this.usersRequestingToJoin = usersRequestingToJoin;
	}
	public int getJoinRequestsCount() {
		return joinRequestsCount;
	}
	public List<HashTag> getHashTags() {
		return hashTags;
	}
	public void setHashTags(List<HashTag> hashTags) {
		this.hashTags = hashTags;
	}
	public void setJoinRequestsCount(int joinRequestsCount) {
		this.joinRequestsCount = joinRequestsCount;
	}
	public LocalDateTime getPostedDateTime() {
		return postedDateTime;
	}
	public void setPostedDateTime(LocalDateTime postedDateTime) {
		this.postedDateTime = postedDateTime;
	}
	
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Post [comments=" + comments + ", commentsCount=" + commentsCount + ", content=" + content
				+ ", daysTillNow=" + daysTillNow + ", hashTags=" + hashTags + ", hoursTillNow=" + hoursTillNow + ", id="
				+ id + ", joinRequestsCount=" + joinRequestsCount + ", minutesTillNow=" + minutesTillNow
				+ ", monthsTillNow=" + monthsTillNow + ", postedDateTime=" + postedDateTime + ", secondsTillNow="
				+ secondsTillNow + ", status=" + status + ", user=" + user + ", usersRequestingToJoin="
				+ usersRequestingToJoin + ", yearsTillNow=" + yearsTillNow + "]";
	}
	
	
	
}
