package edu.unomaha.oc.domain;

public class Character {
	private int id;
	private int owner;
	private String name;
	private String appearance;
	private String personality;
	private String notes;

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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAppearance() {
		return appearance;
	}
	public void setAppearance(String appearance) {
		this.appearance = appearance;
	}
	public String getNotest () {
		return notes;
	}
	public void setNotes (String notes) {
		this.notes = notes;
	}

	public Character(int id, int owner, String name, String appearance, String personality, String notes) {
		this.id = id;
		this.owner = owner;
		this.name = name;
		this.appearance = appearance;
		this.personality = personality;
		this.notes = notes;
	}

	public Character() {

	}

}
