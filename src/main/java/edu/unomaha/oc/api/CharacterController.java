package edu.unomaha.oc.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.unomaha.oc.database.CharacterRowMapper;
import edu.unomaha.oc.domain.OriginalCharacter;

@RestController
public class CharacterController {
	@Autowired
	private DataSource dataSource;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="/api/characters", method=RequestMethod.GET)
	public ResponseEntity<List<OriginalCharacter>> searchCharactersByName(@RequestParam(value="name") String name) {
		logger.debug("searchStoriesByTitle(): name=" + name);
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
		
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("name", '%' + name + '%');
		
		List<OriginalCharacter> characters = template.query("SELECT id, name, owner, appearance, personality, notes FROM character WHERE name LIKE ':name'", paramMap, new CharacterRowMapper()); 
		
		return new ResponseEntity<List<OriginalCharacter>>(characters, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/api/characters/{id}", method=RequestMethod.GET)
	public ResponseEntity<OriginalCharacter> getCharacter(@PathVariable("id") int id) {
		logger.debug("getCharacter(): id=" + id);
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		
		OriginalCharacter character = template.queryForObject("SELECT id, name, appearance, personality, notes FROM character WHERE id=:id", paramMap, new CharacterRowMapper());
		return new ResponseEntity<OriginalCharacter>(character, HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/characters", method=RequestMethod.POST)
	public void saveCharacter() {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("value", "stuff");
		
		template.update("insert into character values (....)", paramMap);
	}
	
	
}
