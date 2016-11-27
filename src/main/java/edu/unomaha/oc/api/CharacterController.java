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

import edu.unomaha.oc.database.CharacterDao;
import edu.unomaha.oc.database.CharacterRowMapper;
import edu.unomaha.oc.domain.OriginalCharacter;
import edu.unomaha.oc.utilities.AuthUtilities;

@RestController
public class CharacterController {
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private AuthUtilities auth;
	
	@Autowired
	private CharacterDao characterDao;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="/api/characters", method=RequestMethod.GET)
	public ResponseEntity<List<OriginalCharacter>> searchCharactersByName(@RequestParam(value="name") String name) {
		logger.debug("searchCharactersByName(): name=" + name);
		
		List<OriginalCharacter> characters = characterDao.searchByName(name); 
		
		return new ResponseEntity<List<OriginalCharacter>>(characters, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/api/characters/{id}", method=RequestMethod.GET)
	public ResponseEntity<OriginalCharacter> getCharacter(@PathVariable("id") int id) {
		logger.debug("getCharacter(): id=" + id);
		
		OriginalCharacter character = characterDao.read(id);
		
		logger.debug("getCharacter(): return=" + character);
		return new ResponseEntity<OriginalCharacter>(character, HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/characters/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Object> updateCharacter(@PathVariable("id") int id, @RequestParam(value="name") String name, @RequestParam(value="appearance", defaultValue="") String appearance,
			@RequestParam(value="personality", defaultValue="") String personality, @RequestParam(value="notes", defaultValue="") String notes) {
		OriginalCharacter character = characterDao.read(id);
		if (auth.isAuthorized(character.getOwner())) {
			character.setAppearance(appearance);
			character.setName(name);
			character.setNotes(notes);
			character.setPersonality(personality);
			
			characterDao.update(id, character);
			
			return new ResponseEntity<Object>(null, HttpStatus.OK);
		} else {
			logger.warn("Unauthorized attempt to update character " + id);
			return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);
		}
	}
	
	@RequestMapping(value="/api/characters", method=RequestMethod.POST)
	public ResponseEntity<OriginalCharacter> saveCharacter(@RequestParam(value="name") String name, @RequestParam(value="appearance", defaultValue="") String appearance,
			@RequestParam(value="personality", defaultValue="") String personality, @RequestParam(value="notes", defaultValue="") String notes) {
		int owner = auth.getActiveUser();
		OriginalCharacter character = new OriginalCharacter(-1, owner, name, appearance, personality, notes);
		
		int createdId = characterDao.create(character).intValue();
		character.setId(createdId);
		return new ResponseEntity<OriginalCharacter>(character, HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/users/{id}/characters", method=RequestMethod.GET)
	public ResponseEntity<List<OriginalCharacter>> getCharactersByUser(@PathVariable("id") int id) {
		List<OriginalCharacter> characters = characterDao.searchByOwner(id);
		
		return new ResponseEntity<List<OriginalCharacter>>(characters, HttpStatus.OK);
	}
	
	
}
