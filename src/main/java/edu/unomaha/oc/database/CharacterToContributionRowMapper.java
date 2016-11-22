package edu.unomaha.oc.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import edu.unomaha.oc.domain.CharacterToContribution;

public class CharacterToContributionRowMapper implements RowMapper<CharacterToContribution> {

	@Override
	public CharacterToContribution mapRow(ResultSet rs, int row) throws SQLException {
		int id = rs.getInt("id");
		int character = rs.getInt("character");
		int contribution = rs.getInt("contribution");

		return new CharacterToContribution(id, character, contribution);
	}

}
