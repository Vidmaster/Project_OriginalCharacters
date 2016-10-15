package edu.unomaha.oc.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import edu.unomaha.oc.domain.Character;

public class CharacterRowMapper implements RowMapper<Character> {

	@Override
	public Character mapRow(ResultSet rs, int row) throws SQLException {
		int id = rs.getInt("id");
		int owner = rs.getInt("owner");
		String name = rs.getString("name");
		String appearance = rs.getString("appearance");
		String personality = rs.getString("personality");
		String notes = rs.getString("notes");

		return new Character(id, owner, name, appearance, personality, notes);
	}

}
