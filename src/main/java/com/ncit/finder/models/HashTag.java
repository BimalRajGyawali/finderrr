package com.ncit.finder.models;

import lombok.Data;

@Data
public class HashTag {
	private final String title;

	public HashTag(String title){
		this.title = sanitize(title);
	}
	public static String sanitize(String title){
		String[] hashtagParts = title.split("#");
		String sanitizedHashtag = "";
		if (hashtagParts.length != 0) {
			sanitizedHashtag = hashtagParts[hashtagParts.length - 1];
		}
		return sanitizedHashtag;
	}
	/** Do not change this format. This is in JSON Format */
	@Override
	public String toString() {
		return "{\"title\":\"" + title + "\"}";
	}


}
