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
import edu.unomaha.oc.domain.Story;
import edu.unomaha.oc.utilities.AuthUtilities;

@RestController
public class StoryController {
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private StoryDao storyDao;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="/api/stories", method=RequestMethod.GET)
	public ResponseEntity<List<Story>> searchStoriesByTitle(HttpServletRequest request, @RequestParam(value="title") String title) {
		logger.debug("searchStoriesByTitle(): title=" + title);
		
		int owner = AuthUtilities.getActiveUser(request);
		
		List<Story> stories = storyDao.search(title, owner);
		
		logger.debug("searchStoriesByTitle(): return=" + stories.toString());
		
		return new ResponseEntity<List<Story>>(stories, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/api/stories/{id}", method=RequestMethod.GET)
	public ResponseEntity<Story> getStory(@PathVariable("id") int id) {
		logger.debug("getStory(): id=" + id);
		
		Story story = storyDao.read(id);
		
		logger.debug("getStory(): return=" + story.toString()); 
		
		return new ResponseEntity<Story>(story, HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/stories", method=RequestMethod.POST)
	public ResponseEntity<Story> createStory(@RequestParam(value="owner") int owner, @RequestParam(value="title") String title, 
			@RequestParam(value="description", defaultValue="") String description, @RequestParam(value="genre", defaultValue="") String genre,
			@RequestParam(value="visible", defaultValue="true") boolean visible, @RequestParam(value="inviteOnly", defaultValue="false") boolean inviteOnly) {
		logger.debug("createStory(): owner=" + owner + ", title=\"" + title + "\", description=\"" + description 
				+ "\", genre=\"" + genre + "\", visible=" + visible + ", inviteOnly=" + inviteOnly);
		
		Story story = new Story(-1, owner, title, description, genre, visible, inviteOnly);
		
		Number storyId = storyDao.create(story);
		story.setId(storyId.intValue());
		
		logger.debug("createStory(): id=" + storyId);
		
		return new ResponseEntity<Story>(story, HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/stores/{id}", method=RequestMethod.PUT, consumes="application/json")
	public void updateStory(@PathVariable("id") int id) {
		// TODO: Check authenticated user is authorized
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		
		template.update("UPDATE Story SET value=stuff WHERE id=:id", paramMap);
	}
	
	@RequestMapping(value="/api/stores/{id}", method=RequestMethod.DELETE, consumes="application/json")
	public ResponseEntity<Object> deleteStory(HttpServletRequest request, @PathVariable("id") int id) throws AuthException {
		// TODO: Check authenticated user owns the story
		Story story = storyDao.read(id);	
		if (story.getOwner() == Integer.parseInt(request.getHeader("userId"))) {
			storyDao.delete(id);
			return new ResponseEntity<Object>(null, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);
		}
	}
	
}
