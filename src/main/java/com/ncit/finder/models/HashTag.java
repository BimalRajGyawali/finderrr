package com.ncit.finder.models;

public class HashTag {
	private String title;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	/** Do not change this format. This is in JSON Format */
	@Override
	public String toString() {
		return "{\"title\":\"" + title + "\"}";
	}
	
	
}
