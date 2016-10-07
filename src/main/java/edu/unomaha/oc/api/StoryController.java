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
public class StoryController {
	@Autowired
	private DataSource dataSource;
	
	@RequestMapping(value="/stories", method=RequestMethod.GET)
	public ResponseEntity<List<Story>> searchStoriesByTitle(@RequestParam(value="title") String title) {
		System.out.println("This is /stories");
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
		
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("title", title);
		
		List<Story> stories = template.query("SELECT id, title, genre, owner, description, visible, inviteonly FROM story WHERE title LIKE '%:title%' and visible=true", paramMap, new StoryRowMapper()); 
		
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
	
	@RequestMapping(value="/stories", method=RequestMethod.POST)
	public void saveStory() {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("value", "stuff");
		
		template.update("insert into Story values (....)", paramMap);
	}
	
	
}
