package edu.unomaha.oc.domain;

public class UserToStory {
	private int contributor;
	private int story;
	
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
	
	public UserToStory(int contributor, int story) {
		this.contributor = contributor;
		this.story = story;
	}
	
	public UserToStory() {
		
	}
	
}