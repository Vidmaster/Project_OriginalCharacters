package edu.unomaha.oc.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import edu.unomaha.oc.domain.UserToStory;

public class UserToStoryRowMapper implements RowMapper<UserToStory> {

	@Override
	public UserToStory mapRow(ResultSet rs, int row) throws SQLException {
		int contributor = rs.getInt("contributor");
		int story = rs.getInt("story");

		return new UserToStory(contributor, story);
	}

}
