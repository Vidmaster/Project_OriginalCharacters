package edu.unomaha.oc.domain;

public class CharacterToContribution {
	private int id;
	private int character;
	private int contribution;
	
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
	public int getContribution() {
		return contribution;
	}
	public void setContribution(int contribution) {
		this.contribution = contribution;
	}

	public CharacterToContribution(int id, int character, int contribution) {
		this.id = id;
		this.character = character;
		this.contribution = contribution;
	}
	
	public CharacterToContribution() {
		
		
	}
	
}