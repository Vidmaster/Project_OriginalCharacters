package edu.unomaha.oc.domain;

public class Comment {
	private int id;
	private int story;
	private int commenter;
	private String date;
	private String comment;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStory() {
		return story;
	}
	public void setStory(int story) {
		this.story = story;
	}
	public int getCommenter() {
		return commenter;
	}
	public void setCommenter(int commenter) {
		this.commenter = commenter;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public Comment(int id, int story, int commenter, String date, String comment) {
		this.id = id;
		this.story = story;
		this.commenter = commenter;
		this.date = date;
		this.comment = comment;
	}
	
	public Comment() {
		
	}
	
}