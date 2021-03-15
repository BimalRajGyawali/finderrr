package com.ncit.finder.models;

import java.time.LocalDateTime;

public class Comment {
	private int id;
	private String content;
	private LocalDateTime commentedOn;
	private User user;
	private Post post;
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
	
	
}
