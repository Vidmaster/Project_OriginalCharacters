package edu.unomaha.oc.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import edu.unomaha.oc.domain.Contribution;
import edu.unomaha.oc.domain.OriginalCharacter;
import edu.unomaha.oc.domain.Story;

/**
 * This class is a JDBC/MySQL implementation of the StoryDao interface using a configurable DataSource
 * For improved compatibility, the queries could be refactored out into an injectable class so the MySQL syntax isn't hardwired into this class
 *
 */
public class JdbcStoryDao implements StoryDao {

	@Autowired
	private DataSource ds;
	
	private static String STORY_FIELDS = "id, title, genre, owner, description, visible, inviteonly";
	
	@Override
	public List<Story> search(String title, int owner) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("title", '%' + title + '%');
		paramMap.addValue("owner", owner);
		
		List<Story> stories = template.query("SELECT " + STORY_FIELDS + " FROM story "
				+ "WHERE title LIKE :title AND (visible = true OR owner = :owner)", paramMap, new StoryRowMapper()); 
		
		return stories;
	}
	
	@Override
	public List<Story> getStoriesByUser(int userId) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("userId", userId);
		
		List<Story> stories = template.query("SELECT " + STORY_FIELDS + " FROM story "
				+ "WHERE visible=true AND owner=:userId", paramMap, new StoryRowMapper()); 
		
		return stories;
	}

	@Override
	public Story read(int id) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", id);
		
		Story story = template.queryForObject("select " + STORY_FIELDS + " from story where id=:id", paramMap, new StoryRowMapper());
		
		// TODO: Make use of concurrency here
		List<Integer> contributors = template.query("SELECT id FROM users WHERE users.id in (select contributor from UserToStory where story = :id)"
				, paramMap, new RowMapper<Integer>() {
					@Override
					public Integer mapRow(ResultSet rs, int row) throws SQLException {
						return rs.getInt("id");
					} });
		story.setContributors(contributors);
		
		List<OriginalCharacter> characters = template.query("SELECT id, owner, name, appearance, personality, notes FROM characters "
				+ "WHERE id in (select original_character from CharacterToStory where story=:id)",
				paramMap, new CharacterRowMapper());
		
		List<Contribution> contributions = template.query("SELECT id, owner, story, ordering, title, body, status FROM contribution WHERE story = :id ORDER BY ordering", 
				paramMap, new ContributionRowMapper());
				
		story.setCharacters(characters);
		story.setContributions(contributions);
		
		return story;
	}

	@Override
	public Number update(int id, Story story) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", id);
		paramMap.addValue("title", story.getTitle());
		paramMap.addValue("description", story.getDescription());
		paramMap.addValue("genre", story.getGenre());
		paramMap.addValue("visible", story.isVisible());
		paramMap.addValue("inviteOnly", story.isInviteOnly());
		
		int rowsAffected = template.update("UPDATE story SET title=:title, description=:description, genre=:genre, visible=:visible, inviteonly=:inviteOnly WHERE id=:id", paramMap);
		
		return rowsAffected;
	}

	@Override
	public Number create(Story story) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		KeyHolder keyHolder = new GeneratedKeyHolder();

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("owner", story.getOwner());
		paramMap.addValue("title", story.getTitle());
		paramMap.addValue("description", story.getDescription());
		paramMap.addValue("genre", story.getGenre());
		paramMap.addValue("visible", story.isVisible());
		paramMap.addValue("inviteOnly", story.isInviteOnly());
		
		template.update("insert into story (owner, title, description, genre, visible, inviteonly) values (:owner, :title, :description, :genre, :visible, :inviteOnly)", paramMap, keyHolder, new String[]{"id"});
		
		return keyHolder.getKey();
	}

	@Override
	public void delete(int id) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", id);
		
		// TODO: Proper implementation of delete - should mark the story as inactive or something instead so people don't lose their work
		template.update("DELETE FROM CharacterToContribution WHERE contribution in (SELECT id FROM contribution where story = :id)", paramMap);
		template.update("DELETE FROM contribution WHERE story = :id", paramMap);
		template.update("DELETE FROM CharacterToStory WHERE story = :id", paramMap);
		template.update("DELETE FROM comment WHERE story = :id", paramMap);
		template.update("DELETE FROM story WHERE id=:id", paramMap);
	}

	@Override
	public void joinStory(int storyId, int userId) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("story", storyId);
		paramMap.addValue("contributor", userId);
		
		template.update("INSERT INTO UserToStory (story, contributor) VALUES (:story, :contributor)", paramMap);
	}

}
