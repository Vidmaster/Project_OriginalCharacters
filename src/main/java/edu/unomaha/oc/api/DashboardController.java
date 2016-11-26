package edu.unomaha.oc.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.unomaha.oc.database.CharacterRowMapper;
import edu.unomaha.oc.database.StoryRowMapper;
import edu.unomaha.oc.domain.OriginalCharacter;
import edu.unomaha.oc.domain.Story;
import edu.unomaha.oc.utilities.AuthUtilities;

@RestController
public class DashboardController {
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private AuthUtilities auth;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static String STORY_FIELDS = "id, title, genre, owner, description, visible, inviteonly";
	
	@RequestMapping("/api/dashboard")
	public ResponseEntity<List<Story>> getDashboardStories(@RequestParam(value="number", defaultValue="5") int number) {
		logger.debug("getDashboardStories(): number=" + number);
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("number", number);
		
		String sql = "SELECT " + STORY_FIELDS
				+ " FROM story "
				+ "WHERE visible=true "
				+ "ORDER BY id DESC "
				+ "LIMIT :number";
		try {
			List<Story> stories = template.query(sql, paramMap, new StoryRowMapper());
			logger.debug("stories = " + Arrays.toString(stories.toArray()));
			return new ResponseEntity<List<Story>>(stories, HttpStatus.OK);
		} catch (Exception ex) {
			logger.error("getDashboardStories(): Exception occurred: " + ex.getMessage());
			return new ResponseEntity<List<Story>>(new ArrayList<Story>(), HttpStatus.OK);
		}
	}
	
	@RequestMapping("/api/dashboard/{id}")
	public ResponseEntity<Map<String,Object>> getUserDashboard(@PathVariable("id") int id) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds);
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		Map<String,Object> results = new HashMap<String,Object>();
		HttpStatus status = HttpStatus.OK;
		
		String storiesSql = "SELECT " + STORY_FIELDS + " FROM story WHERE owner = :userId OR id in (SELECT story FROM UserToStory WHERE contributor = :userId) ORDER BY id DESC";
		String charactersSql = "SELECT id, owner, name, appearance, personality, notes FROM characters WHERE owner = :userId ORDER BY name";
		String contributionsSql = "SELECT id, owner, story, order, title, body, status FROM contribution WHERE owner = :userId";
		
		paramMap.addValue("userId", id);
		
		if (auth.isAuthorized(id)) {
			List<Story> stories = template.query(storiesSql, paramMap, new StoryRowMapper());
			logger.debug("stories = " + Arrays.toString(stories.toArray()));
			List<OriginalCharacter> characters = template.query(charactersSql, paramMap, new CharacterRowMapper());
			logger.debug("characters = " + Arrays.toString(characters.toArray()));
			
			results.put("stories", stories);
			results.put("characters", characters);
		} else {
			results.put("message", "User is not authorized to access this resource");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String,Object>>(results, status);
	}
	
}
