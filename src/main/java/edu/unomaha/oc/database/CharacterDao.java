package edu.unomaha.oc.database;

import java.util.List;

import edu.unomaha.oc.domain.OriginalCharacter;

public interface CharacterDao {
	public List<OriginalCharacter> search(String characterName);

	public List<OriginalCharacter> searchByName(String name);
	
	public List<OriginalCharacter> searchByOwner(int owner);
	
	public List<OriginalCharacter> searchByContribution(int contribution);
	
	public List<OriginalCharacter> searchByStory(int story);
	
	public OriginalCharacter read(int id);
	
	public Number update(int id, OriginalCharacter character);
	
	public Number create(OriginalCharacter character);
	
	public void delete(int id);
}
