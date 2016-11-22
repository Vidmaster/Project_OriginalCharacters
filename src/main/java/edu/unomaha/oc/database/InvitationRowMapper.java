package edu.unomaha.oc.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import edu.unomaha.oc.domain.Invitation;

public class InvitationRowMapper implements RowMapper<Invitation> {

	@Override
	public Invitation mapRow(ResultSet rs, int row) throws SQLException {
		int id = rs.getInt("id");
		int sender = rs.getInt("sender");
		int recipient = rs.getInt("recipient");
		int story = rs.getInt("story");
		String status = rs.getString("status");
		String type = rs.getString("type");

		return new Invitation(id, sender, recipient, story, status, type);
	}

}
