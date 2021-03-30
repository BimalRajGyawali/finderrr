package com.ncit.finder.models;

import java.time.LocalDateTime;
import java.util.List;

public class User {
	private int id;
	private String bio;
	private String firstName;
	private String middleName;
	private String lastName;
	private LocalDateTime joinedOn;
	private List<HashTag> hashtagsFollowed;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public LocalDateTime getJoinedOn() {
		return joinedOn;
	}
	public void setJoinedOn(LocalDateTime joinedOn) {
		this.joinedOn = joinedOn;
	}
	public List<HashTag> getHashtagsFollowed() {
		return hashtagsFollowed;
	}
	public void setHashtagsFollowed(List<HashTag> hashtagsFollowed) {
		this.hashtagsFollowed = hashtagsFollowed;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName
				+ ", joinedOn=" + joinedOn + "]";
	}

	public boolean isValid(){
		return this.getId() > 0 && this.firstName.length() > 0 && this.lastName.length() > 0;
	}
	
	
}
