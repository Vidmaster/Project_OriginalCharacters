package edu.unomaha.oc.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.unomaha.oc.database.StoryRowMapper;
import edu.unomaha.oc.domain.Story;

@RestController
public class DashboardController {
	
	@Autowired
	private DataSource ds;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static String STORY_FIELDS = "id, title, genre, owner, description, visible, inviteonly";
	
	@RequestMapping("/api/dashboard")
	public ResponseEntity<List<Story>> getDashboardStories(@RequestParam(value="number", defaultValue="5") int number) {
		System.out.println("hey");
		logger.debug("/api/dashboard");
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
			logger.info("stories = " + Arrays.toString(stories.toArray()));
			return new ResponseEntity<List<Story>>(stories, HttpStatus.OK);
		} catch (Exception ex) {
			logger.error("getDashboardStories(): Exception occurred: " + ex.getMessage());
			return new ResponseEntity<List<Story>>(new ArrayList<Story>(), HttpStatus.OK);
		}
	}
}
