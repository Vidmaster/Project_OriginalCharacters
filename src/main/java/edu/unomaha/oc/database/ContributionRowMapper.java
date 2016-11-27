package edu.unomaha.oc.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import edu.unomaha.oc.domain.Contribution;

public class ContributionRowMapper implements RowMapper<Contribution> {

	@Override
	public Contribution mapRow(ResultSet rs, int row) throws SQLException {
		int id = rs.getInt("id");
		int owner = rs.getInt("owner");
		int story = rs.getInt("story");
		int order = rs.getInt("ordering");
		String title = rs.getString("title");
		String body = rs.getString("body");
		String status = rs.getString("status");

		return new Contribution(id, owner, story, order, title, body, status);
	}

}
