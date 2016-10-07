package edu.unomaha.oc.domain;

public class Story {
	private int id;
	private int owner;
	private String title;
	private String description;
	private String genre;
	private boolean visible;
	private boolean inviteOnly;
	
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public boolean isInviteOnly() {
		return inviteOnly;
	}
	public void setInviteOnly(boolean inviteOnly) {
		this.inviteOnly = inviteOnly;
	}
	public Story(int id, int owner, String title, String description, String genre, boolean visible,
			boolean inviteOnly) {
		this.id = id;
		this.owner = owner;
		this.title = title;
		this.description = description;
		this.genre = genre;
		this.visible = visible;
		this.inviteOnly = inviteOnly;
	}

	public Story() {
		
	}


}
