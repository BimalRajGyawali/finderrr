package com.ncit.finder.models;

public class HashTag {
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = sanitize(title);
	}

	/** Do not change this format. This is in JSON Format */
	@Override
	public String toString() {
		return "{\"title\":\"" + title + "\"}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HashTag other = (HashTag) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	public static String sanitize(String title){
		String[] hashtagParts = title.split("#");
		String sanitizedHashtag = "";
		if (hashtagParts.length == 0) {
			sanitizedHashtag ="empty";
		} else {
			String hashtag = hashtagParts[hashtagParts.length - 1];
			sanitizedHashtag = hashtag;

		}
		return sanitizedHashtag;
	}

}
