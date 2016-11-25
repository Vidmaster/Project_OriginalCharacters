package edu.unomaha.oc.domain;

public class CharacterToStory {
	private int character;
	private int story;
	
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

	public CharacterToStory(int character, int story) {
		this.character = character;
		this.story = story;
	}
	
	public CharacterToStory() {
		
	}
	
}