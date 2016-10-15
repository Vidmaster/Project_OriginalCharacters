package edu.unomaha.oc.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
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

import edu.unomaha.oc.database.StoryDao;
import edu.unomaha.oc.database.StoryRowMapper;
import edu.unomaha.oc.domain.Story;

@RestController
public class StoryController {
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private StoryDao storyDao;
	
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="/stories", method=RequestMethod.GET)
	public ResponseEntity<List<Story>> searchStoriesByTitle(@RequestParam(value="title") String title) {
		logger.debug("searchStoriesByTitle(): title=" + title);
		
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
		
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("title", title);
		
		List<Story> stories = template.query("SELECT id, title, genre, owner, description, visible, inviteonly FROM story WHERE title LIKE '%:title%' and visible=true", paramMap, new StoryRowMapper()); 
		
		logger.debug("searchStoriesByTitle(): return=" + stories.toString());
		
		return new ResponseEntity<List<Story>>(stories, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/stories/{id}", method=RequestMethod.GET)
	public ResponseEntity<Story> getStory(@PathVariable("id") int id) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
		
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		
		Story story = template.queryForObject("select id, title, genre, owner, description, visible, inviteonly from story where id=:id", paramMap, new StoryRowMapper());
		
		return new ResponseEntity<Story>(story, HttpStatus.OK);
	}
	
	@RequestMapping(value="/stories", method=RequestMethod.PUT, consumes="application/json")
	public void createStory() {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("value", "stuff");
		
		template.update("insert into Story values (....)", paramMap);
	}
	
	@RequestMapping(value="/stores/{id}", method=RequestMethod.POST, consumes="application/json")
	public void updateStory(@PathVariable("id") int id) {
		// TODO: Check authenticated user is authorized
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		
		template.update("UPDATE Story SET value=stuff WHERE id=:id", paramMap);
	}
	
	@RequestMapping(value="/stores/{id}", method=RequestMethod.DELETE, consumes="application/json")
	public ResponseEntity<Object> deleteStory(HttpServletRequest request, @PathVariable("id") int id) throws AuthException {
		// TODO: Check authenticated user owns the story
		Story story = storyDao.read(id);	
		if (story.getOwner() == Integer.parseInt(request.getHeader("userId"))) {
			NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
			Map<String,Object> paramMap = new HashMap<>();
			paramMap.put("id", id);
			
			template.update("DELETE FROM Story WHERE id=:id", paramMap);
			return new ResponseEntity<Object>(null, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);
		}
	}
	
}
