package edu.unomaha.oc.domain;

import java.util.List;

public class Story {
	private int id;
	private int owner;
	private String title;
	private String description;
	private String genre;
	private boolean visible;
	private boolean inviteOnly;
	private List<Contribution> contributions;
	private List<Integer> contributors;
	private List<OriginalCharacter> characters;
	
	public List<Integer> getContributors() {
		return contributors;
	}
	
	public void setContributors(List<Integer> contributors) {
		this.contributors = contributors;
	}
	
	public List<OriginalCharacter> getCharacters() {
		return characters;
	}
	
	public void setCharacters(List<OriginalCharacter> characters) {
		this.characters = characters;
	}
	
	public List<Contribution> getContributions() {
		return contributions;
	}
	
	public void setContributions(List<Contribution> contributions) {
		this.contributions = contributions;
	}
	
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
	
	@Override
	public String toString() {
		return "Story [id=" + id + ", owner=" + owner + ", title=" + title + "]";
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
