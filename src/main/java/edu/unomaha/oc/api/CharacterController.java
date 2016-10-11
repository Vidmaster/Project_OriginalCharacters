package edu.unomaha.oc.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.unomaha.oc.database.StoryRowMapper;
import edu.unomaha.oc.domain.Story;

@RestController
public class CharacterController {
	@Autowired
	private DataSource dataSource;
	
	@RequestMapping(value="/characters", method=RequestMethod.GET)
	public ResponseEntity<List<Character>> searchStoriesByTitle(@RequestParam(value="name") String name) {
		System.out.println("This is /character");
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
		
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("name", name);
		
		List<character> characters = template.query("SELECT id, name, owner, appearance, personality, notes FROM story WHERE name LIKE '%:name%'", paramMap, new CharacterRowMapper()); 
		
		return new ResponseEntity<List<Character>>(characters, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/characters/{id}", method=RequestMethod.GET)
	public ResponseEntity<Character> getCharacter(@PathVariable("id") int id) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		
		Character character = template.queryForObject("select id, name, appearance, personality, notes from story where id=:id", paramMap, new CharacterRowMapper());
		return new ResponseEntity<Character>(character, HttpStatus.OK);
	}
	
	@RequestMapping(value="/characters", method=RequestMethod.POST)
	public void saveStory() {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("value", "stuff");
		
		template.update("insert into Character values (....)", paramMap);
	}
	
	
}
