package edu.unomaha.oc.database;

import java.util.ArrayList;
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
	public List<OriginalCharacter> searchByName(String name) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("name", '%' + name + '%');
		
		List<OriginalCharacter> characters = template.query("SELECT " + CHARACTER_FIELDS + " FROM characters "
				+ "WHERE name LIKE :name", paramMap, new CharacterRowMapper()); 
		
		return characters;
	}
	
	@Override
	public List<OriginalCharacter> searchByOwner(int owner) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("owner", owner);
		
		List<OriginalCharacter> characters = template.query("SELECT " + CHARACTER_FIELDS + " FROM characters "
				+ "WHERE owner = :owner", paramMap, new CharacterRowMapper()); 
		
		return characters;
	}
	
	@Override
	public List<OriginalCharacter> searchByContribution(int contribution) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("contribution", contribution);
		
		List<OriginalCharacter> characters = template.query("SELECT " + CHARACTER_FIELDS + " FROM characters, CharacterToContribution ctc "
				+ "WHERE ctc.character = character.id AND ctc.contribution = :contribution", paramMap, new CharacterRowMapper()); 
		
		return characters;
	}
	
	@Override
	public List<OriginalCharacter> searchByStory(int story) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("story", story);
		
		List<OriginalCharacter> characters = template.query("SELECT " + CHARACTER_FIELDS + " FROM characters, CharacterToStory cts "
				+ "WHERE cts.character = characters.id AND cts.story = :story", paramMap, new CharacterRowMapper()); 
		
		return characters;
	}

	@Override
	public OriginalCharacter read(int id) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", id);
		
		return template.queryForObject("select " + CHARACTER_FIELDS + " from characters where id = :id", paramMap, new CharacterRowMapper());
	}

	@Override
	public Number update(int id, OriginalCharacter character) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("name", character.getName());
		paramMap.addValue("appearance", character.getAppearance());
		paramMap.addValue("personality", character.getPersonality());
		paramMap.addValue("notes", character.getNotes());
		
		int rowsUpdated = template.update("UPDATE characters SET (name = :name, appearance = :appearance, personality = :personality, notes = :notes) WHERE id=:id ", paramMap);
		
		return rowsUpdated;
	}

	@Override
	public Number create(OriginalCharacter character) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("owner", character.getOwner());
		paramMap.addValue("name", character.getName());
		paramMap.addValue("appearance", character.getAppearance());
		paramMap.addValue("personality", character.getPersonality());
		paramMap.addValue("notes", character.getNotes());
		
		template.update("INSERT INTO characters (owner, name, appearance, personality, notes) VALUES (:owner, :name, :appearance, :personality, :notes)", paramMap, keyHolder, new String[]{"id"});
		
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

