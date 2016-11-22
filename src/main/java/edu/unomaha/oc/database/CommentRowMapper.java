package edu.unomaha.oc.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import edu.unomaha.oc.domain.Comment;

public class CommentRowMapper implements RowMapper<Comment> {

	@Override
	public Comment mapRow(ResultSet rs, int row) throws SQLException {
		int id = rs.getInt("id");
		int story = rs.getInt("story");
		int commenter = rs.getInt("commenter");
		String date = rs.getString("date");
		String comment = rs.getString("comment");

		return new Comment(id, story, commenter, date, comment);
	}

}
