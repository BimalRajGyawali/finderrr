package com.ncit.finder.models;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class User {
	private int id;
	private String bio;
	private String firstName;
	private String middleName;
	private String lastName;
	private LocalDateTime joinedOn;
	private List<HashTag> hashtagsFollowed;
	private String email;
	private String pass;
	private String profilePic;

	public boolean isValid(){
		return this.getId() > 0 && this.firstName.length() > 0 && this.lastName.length() > 0;
	}

}
