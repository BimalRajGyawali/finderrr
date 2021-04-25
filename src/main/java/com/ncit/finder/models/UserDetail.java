package com.ncit.finder.models;

import java.time.LocalDateTime;
import java.util.List;

public class UserDetail {
	private int id;
	private String email;
	private String pass;
	private User user;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "UserDetail [id=" + id + ", email=" + email + ", pass=" + pass + ", user=" + user + "]";
	}



	
	

	
	

}
