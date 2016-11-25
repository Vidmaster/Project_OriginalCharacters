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

import edu.unomaha.oc.domain.OriginalCharacter;

public class JdbcCharacterDao implements CharacterDao {

	@Autowired
	private DataSource ds;
	
	private static String CHARACTER_FIELDS = "id, owner, name, appearance, personality, notes";
	
	@Override
	public List<OriginalCharacter> search(String characterName) {
		return new ArrayList<OriginalCharacter>();
	}
	
	@Override
	public List<OriginalCharacter> searchByName(String name) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("name", '%' + name + '%');
		
		List<OriginalCharacter> characters = template.query("SELECT " + CHARACTER_FIELDS + " FROM Character "
				+ "WHERE name LIKE :name", paramMap, new CharacterRowMapper()); 
		
		return characters;
	}
	
	@Override
	public List<OriginalCharacter> searchByOwner(int owner) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("owner", owner);
		
		List<OriginalCharacter> characters = template.query("SELECT " + CHARACTER_FIELDS + " FROM Character "
				+ "WHERE owner = :owner", paramMap, new CharacterRowMapper()); 
		
		return characters;
	}
	
	@Override
	public List<OriginalCharacter> searchByContribution(int contribution) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("contribution", contribution);
		
		List<OriginalCharacter> characters = template.query("SELECT " + CHARACTER_FIELDS + " FROM Character, CharacterToContribution ctc "
				+ "WHERE ctc.character = character.id AND ctc.contribution = :contribution", paramMap, new CharacterRowMapper()); 
		
		return characters;
	}
	
	@Override
	public List<OriginalCharacter> searchByStory(int story) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("story", story);
		
		List<OriginalCharacter> characters = template.query("SELECT " + CHARACTER_FIELDS + " FROM Character, CharacterToStory cts "
				+ "WHERE cts.character = character.id AND cts.story = :story", paramMap, new CharacterRowMapper()); 
		
		return characters;
	}

	@Override
	public OriginalCharacter read(int id) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", id);
		
		return template.queryForObject("select " + CHARACTER_FIELDS + " from Character where id = :id", paramMap, new CharacterRowMapper());
	}

	@Override
	public Number update(int id, OriginalCharacter character) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		paramMap.put("updates", "stuff");
		
		template.update("UPDATE users SET (:updates) WHERE id=:id ", paramMap);
		
		return 0;
	}

	@Override
	public Number create(OriginalCharacter character) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("value", "stuff");
		
		template.update("insert into Character values (....)", paramMap, keyHolder, new String[]{"id"});
		
		return keyHolder.getKey();
	}

	@Override
	public void delete(int id) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", id);
		
		template.update("DELETE FROM Character WHERE id = :id", paramMap);
	}

}

