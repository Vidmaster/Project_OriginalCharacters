package edu.unomaha.oc.domain;

public class Invitation {
	public static final String STATUS_APPROVED = "Approved";
	public static final String STATUS_PENDING = "Pending";
	public static final String STATUS_REJECTED = "Rejected";
	public static final String TYPE_INVITATION = "Invitation";
	public static final String TYPE_APPROVAL = "Approval";
	
	private int id;
	private int sender;
	private int recipient;
	private int story;
	private String status;
	private String type;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSender() {
		return sender;
	}
	public void setSender(int sender) {
		this.sender = sender;
	}
	public int getRecipient() {
		return recipient;
	}
	public void setRecipient(int recipient) {
		this.recipient = recipient;
	}
	public int getStory() {
		return story;
	}
	public void setStory(int story) {
		this.story = story;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public Invitation(int id, int sender, int recipient, int story, String status, String type) {
		this.id = id;
		this.sender = sender;
		this.recipient = recipient;
		this.story = story;
		this.status = status;
		this.type = type;
	}
	
	public Invitation() {
		
	}
	
}