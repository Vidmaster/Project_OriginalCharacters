package edu.unomaha.oc.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import edu.unomaha.oc.domain.CharacterToStory;

public class CharacterToStoryRowMapper implements RowMapper<CharacterToStory> {

	@Override
	public CharacterToStory mapRow(ResultSet rs, int row) throws SQLException {
		int id = rs.getInt("id");
		int character = rs.getInt("character");
		int story = rs.getInt("story");

		return new CharacterToStory(id, character, story);
	}

}
