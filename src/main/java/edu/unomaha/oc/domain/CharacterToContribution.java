package edu.unomaha.oc.domain;

public class CharacterToContribution {
	private int character;
	private int contribution;
	
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

	public CharacterToContribution(int character, int contribution) {
		this.character = character;
		this.contribution = contribution;
	}
	
	public CharacterToContribution() {
		
		
	}
	
}