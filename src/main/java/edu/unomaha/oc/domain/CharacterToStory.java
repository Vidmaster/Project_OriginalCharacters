package edu.unomaha.oc.domain;

public class CharacterToStory {
	private int id;
	private int character;
	private int story;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCharacter() {
		return character;
	}
	public void setCharacter(int character) {
		this.character = character;
	}
	public int getStory() {
		return story;
	}
	public void setStory(int story) {
		this.story = story;
	}

	public CharacterToStory(int id, int character, int story) {
		this.id = id;
		this.character = character;
		this.story = story;
	}
	
	public CharacterToStory() {
		
	}
	
}