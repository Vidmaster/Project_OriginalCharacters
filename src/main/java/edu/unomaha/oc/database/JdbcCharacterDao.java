package edu.unomaha.oc.database;

import java.util.ArrayList;
import java.util.List;

import edu.unomaha.oc.domain.OriginalCharacter;

public class JdbcCharacterDao implements CharacterDao {

	public List<OriginalCharacter> search(String characterName) {
		return new ArrayList<OriginalCharacter>();
	}
	
}
