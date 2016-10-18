package edu.unomaha.oc.database;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import edu.unomaha.oc.domain.Story;

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
	public Story read(int id) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", id);
		
		return template.queryForObject("select " + STORY_FIELDS + " from story where id=:id", paramMap, new StoryRowMapper());
	}

	@Override
	public Number update(int id, Story story) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Number create(Story story) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("value", "stuff");
		
		template.update("insert into Story values (....)", paramMap, keyHolder, new String[]{"id"});
		
		return keyHolder.getKey();
	}

	@Override
	public void delete(int id) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", id);
		
		template.update("DELETE FROM Story WHERE id=:id", paramMap);
	}

}
