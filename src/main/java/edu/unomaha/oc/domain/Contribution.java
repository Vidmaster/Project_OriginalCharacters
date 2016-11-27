package edu.unomaha.oc.domain;

import java.util.List;

public class Contribution {
	public static final String STATUS_APPROVED = "Approved";
	public static final String STATUS_PENDING_APPROVAL = "Pending Approvals";
	public static final String STATUS_REJECTED = "Rejected";
	
	private int id;
	private int owner;
	private int story;
	private int order;
	private String title;
	private String body;
	private String status;
	private List<OriginalCharacter> characters;
	
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
	
	public void setCharacters(List<OriginalCharacter> characters) {
		this.characters = characters;
	}
	
	public List<OriginalCharacter> getCharacters() {
		return characters;
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
	
	@Override
	public String toString() {
		return "Contribution [id=" + id + ", owner=" + owner + ", story=" + story + ", order=" + order + ", title="
				+ title + ", body=" + body + ", status=" + status + ", characters=" + characters + "]";
	}
	
}