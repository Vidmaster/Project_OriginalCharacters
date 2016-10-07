package edu.unomaha.oc.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import edu.unomaha.oc.domain.Story;

public class StoryRowMapper implements RowMapper<Story> {

	@Override
	public Story mapRow(ResultSet rs, int row) throws SQLException {
		int id = rs.getInt("id");
		int owner = rs.getInt("owner");
		String title = rs.getString("title");
		String description = rs.getString("description");
		String genre = rs.getString("genre");
		boolean visible = rs.getBoolean("visible");
		boolean inviteOnly = rs.getBoolean("inviteonly");
		
		return new Story(id, owner, title, description, genre, visible, inviteOnly);
	}

}
