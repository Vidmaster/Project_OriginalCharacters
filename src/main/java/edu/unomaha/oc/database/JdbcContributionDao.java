package edu.unomaha.oc.database;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import edu.unomaha.oc.domain.Contribution;
import edu.unomaha.oc.domain.Invitation;
import edu.unomaha.oc.domain.OriginalCharacter;

public class JdbcContributionDao implements ContributionDao {
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private InvitationDao invitationDao;
	
	private static String CONTRIBUTION_FIELDS = "id, owner, story, order, title, body, status";
	
	@Override
	public List<Contribution> searchByOwner(int owner) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("owner", owner);
		
		List<Contribution> contributions = template.query("SELECT " + CONTRIBUTION_FIELDS + " FROM contribution "
				+ "WHERE owner = :owner", paramMap, new ContributionRowMapper()); 
		
		return contributions;
	}
	
	@Override
	public List<Contribution> searchByStory(int story) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("story", story);
		
		List<Contribution> contributions = template.query("SELECT " + CONTRIBUTION_FIELDS + " FROM contribution "
				+ "WHERE story = :story", paramMap, new ContributionRowMapper()); 
		
		return contributions;
	}

	@Override
	public Contribution read(int id) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", id);
		
		return template.queryForObject("select " + CONTRIBUTION_FIELDS + " from contribution where id = :id", paramMap, new ContributionRowMapper());
	}

	@Override
	public Number update(int id, Contribution contribution) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", id);
		paramMap.addValue("updates", "stuff");
		
		Number rowsUpdated = template.update("UPDATE contribution SET title=:title, body=:body, status=:status WHERE id=:id ", paramMap);
		
		template.update("DELETE FROM CharacterToContribution WHERE contribution = :id", paramMap);
		List<OriginalCharacter> characters = contribution.getCharacters();
		for (OriginalCharacter character : characters) {
			paramMap.addValue("characterId", character.getId());
			template.update("INSERT INTO CharacterToContribution original_character, contribution VALUES (:characterId, :id)", paramMap);
		}
		
		return rowsUpdated;
	}
	
	public Number updateStatus(int id, String status) {
		return 0;
	}
	

	@Override
	public Number create(Contribution contribution) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("owner", contribution.getOwner());
		paramMap.addValue("story", contribution.getStory());
		paramMap.addValue("title", contribution.getTitle());
		paramMap.addValue("body", contribution.getBody());
		contribution.setStatus(determineStatus(contribution));
		paramMap.addValue("status", contribution.getStatus());
		
		template.update("INSERT INTO contribution (owner, story, title, body, status) VALUES (:owner, :story, :title, :body, :status)", paramMap, keyHolder, new String[]{"id"});
		int id = keyHolder.getKey().intValue();
		paramMap.addValue("id", id);
		List<OriginalCharacter> characters = contribution.getCharacters();
		for (OriginalCharacter character : characters) {
			paramMap.addValue("characterId", character.getId());
			template.update("INSERT INTO CharacterToContribution (original_character, contribution) VALUES (:characterId, :id)", paramMap);
		}
		
		if (contribution.getStatus().equals(Contribution.STATUS_PENDING_APPROVAL)) {
			requestApprovals(contribution);
		}
		
		return keyHolder.getKey();
	}

	@Override
	public void delete(int id) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", id);
		
		template.update("DELETE FROM contribution WHERE id = :id", paramMap);
	}

	// TODO: This method could probably benefit from a better algorithm at some point...
	String determineStatus(Contribution contribution) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		// A contribution requires approvals if it has characters tagged in it and those characters are owned by people other than the owner
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		int owner = contribution.getOwner();
		Set<Integer> usersToNotify = new HashSet<Integer>();
		
		for (OriginalCharacter character : contribution.getCharacters()) {
			paramMap.addValue("id", character.getId());
			int characterOwner = template.queryForObject("SELECT owner FROM characters where id = :id", paramMap, Integer.class);
			usersToNotify.add(characterOwner);
		}
		usersToNotify.remove(owner);
		if (usersToNotify.isEmpty()) {
			return Contribution.STATUS_APPROVED;
		} else {
			return Contribution.STATUS_PENDING_APPROVAL;
		}
	}
	
	void requestApprovals(Contribution contribution) {
		// Request an approval from each character owner in this character's contributions
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		// A contribution requires approvals if it has characters tagged in it and those characters are owned by people other than the owner
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		int owner = contribution.getOwner();
		int story = contribution.getStory();
		Set<Integer> usersToNotify = new HashSet<Integer>();
		
		for (OriginalCharacter character : contribution.getCharacters()) {
			paramMap.addValue("id", character.getId());
			int characterOwner = template.queryForObject("SELECT owner FROM characters where id = :id", paramMap, Integer.class);
			usersToNotify.add(characterOwner);
		}
		usersToNotify.remove(owner);
		
		for (Integer userId : usersToNotify) {
			invitationDao.create(new Invitation(-1, owner, userId, story, Invitation.STATUS_PENDING, Invitation.TYPE_APPROVAL));
		}
	}
	
}
