package edu.unomaha.oc.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import edu.unomaha.oc.domain.Invitation;

public class JdbcInvitationDao implements InvitationDao {

	@Autowired
	private DataSource ds;
	
	private static String INVITATION_FIELDS = "id, sender, recipient, story, status, type";
	
	@Override
	public List<Invitation> searchBySender(int sender) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("sender", sender);
		
		List<Invitation> invitations = template.query("SELECT " + INVITATION_FIELDS + " FROM Invitation "
				+ "WHERE sender = :sender", paramMap, new InvitationRowMapper()); 
		
		return invitations;
	}
	
	@Override
	public List<Invitation> searchByRecipient(int recipient) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("recipient", recipient);
		
		List<Invitation> invitations = template.query("SELECT " + INVITATION_FIELDS + " FROM Invitation "
				+ "WHERE recipient = :recipient", paramMap, new InvitationRowMapper()); 
		
		return invitations;
	}
	
	@Override
	public List<Invitation> searchByStory(int story) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("story", story);
		
		List<Invitation> invitations = template.query("SELECT " + INVITATION_FIELDS + " FROM Invitation "
				+ "WHERE story = :story", paramMap, new InvitationRowMapper()); 
		
		return invitations;
	}

	@Override
	public Invitation read(int id) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", id);
		
		return template.queryForObject("select " + INVITATION_FIELDS + " from Invitation where id = :id", paramMap, new InvitationRowMapper());
	}

	@Override
	public Number update(int id, Invitation invitation) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		paramMap.put("updates", "stuff");
		
		template.update("UPDATE users SET (:updates) WHERE id=:id ", paramMap);
		
		return 0;
	}

	@Override
	public Number create(Invitation invitation) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("value", "stuff");
		
		template.update("insert into Invitation values (....)", paramMap, keyHolder, new String[]{"id"});
		
		return keyHolder.getKey();
	}

	@Override
	public void delete(int id) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", id);
		
		template.update("DELETE FROM Invitation WHERE id = :id", paramMap);
	}

}
