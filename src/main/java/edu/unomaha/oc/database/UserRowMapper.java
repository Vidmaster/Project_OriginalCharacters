package edu.unomaha.oc.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import edu.unomaha.oc.domain.User;

public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int row) throws SQLException {
		int id = rs.getInt("id");
		String username = rs.getString("username");
		String email = rs.getString("email");
		String description = rs.getString("description");
		int facebookId = rs.getInt("facebookId");
		String password = rs.getString("password");
		  
		return new User(id, username, email, description, facebookId, password);
	}

}
