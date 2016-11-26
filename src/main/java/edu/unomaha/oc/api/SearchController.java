package edu.unomaha.oc.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.unomaha.oc.database.CharacterDao;
import edu.unomaha.oc.database.StoryDao;
import edu.unomaha.oc.database.UserDao;
import edu.unomaha.oc.utilities.AuthUtilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class SearchController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private StoryDao storyDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CharacterDao characterDao;
	
	@Autowired
	private AuthUtilities auth;
	
	@RequestMapping("/api/search")
	public ResponseEntity<List<Object>> searchEverything(@RequestParam("value") String searchString) {
		List<Object> results = new ArrayList<Object>();
		
		int userId = auth.getActiveUser();
		
		results.addAll(storyDao.search(searchString, userId));
		results.addAll(characterDao.searchByName(searchString));
		results.addAll(userDao.searchByUsername(searchString));
		
		logger.debug(Arrays.toString(results.toArray()));
		return new ResponseEntity<List<Object>>(results, HttpStatus.OK);
	}
}
