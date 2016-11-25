package edu.unomaha.oc.database;

import java.util.List;

import edu.unomaha.oc.domain.OriginalCharacter;

public interface CharacterDao {
	public List<OriginalCharacter> search(String characterName);
}
