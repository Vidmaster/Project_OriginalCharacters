package edu.unomaha.oc.domain;

public class Contribution {
	private int id;
	private int owner;
	private int story;
	private int order;
	private String title;
	private String body;
	private String status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOwner() {
		return owner;
	}
	public void setOwner(int owner) {
		this.owner = owner;
	}
	public int getStory() {
		return story;
	}
	public void setStory(int story) {
		this.story = story;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Contribution(int id, int owner, int story, int order, String title, String body, String status) {
		this.id = id;
		this.owner = owner;
		this.story = story;
		this.order = order;
		this.title = title;
		this.body = body;
		this.status = status;
	}
	
	public Contribution() {
		
	}
	
}