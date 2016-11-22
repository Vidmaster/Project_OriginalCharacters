package edu.unomaha.oc.domain;

public class UserToStory {
	private int id;
	private int contributor;
	private int story;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getContributor() {
		return contributor;
	}
	public void setContributor(int contributor) {
		this.contributor = contributor;
	}
	public int getStory() {
		return story;
	}
	public void setStory(int story) {
		this.story = story;
	}
	
	public UserToStory(int id, int contributor, int story) {
		this.id = id;
		this.contributor = contributor;
		this.story = story;
	}
	
	public UserToStory() {
		
	}
	
}