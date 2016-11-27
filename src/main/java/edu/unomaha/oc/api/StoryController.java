package edu.unomaha.oc.api;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.unomaha.oc.database.ContributionDao;
import edu.unomaha.oc.database.StoryDao;
import edu.unomaha.oc.domain.Contribution;
import edu.unomaha.oc.domain.OriginalCharacter;
import edu.unomaha.oc.domain.Story;
import edu.unomaha.oc.utilities.AuthUtilities;

@RestController
public class StoryController {
	
	@Autowired
	private StoryDao storyDao;
	
	@Autowired
	private ContributionDao contributionDao;
	
	@Autowired
	private AuthUtilities auth;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="/api/stories", method=RequestMethod.GET)
	public ResponseEntity<List<Story>> searchStoriesByTitle(HttpServletRequest request, @RequestParam(value="title") String title) {
		logger.debug("searchStoriesByTitle(): title=" + title);
		
		int owner = auth.getActiveUser();
		
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
	
	@RequestMapping(value="/api/stories/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Story> updateStory(@PathVariable("id") int id, @RequestParam(value="title") String title, 
			@RequestParam(value="description", defaultValue="") String description, @RequestParam(value="genre", defaultValue="") String genre,
			@RequestParam(value="visible", defaultValue="true") boolean visible, @RequestParam(value="inviteOnly", defaultValue="false") boolean inviteOnly) {
		
		Story story = storyDao.read(id);
		story.setTitle(title);
		story.setDescription(description);
		story.setGenre(genre);
		story.setVisible(visible);
		story.setInviteOnly(inviteOnly);
		
		if (auth.isAuthorized(story.getOwner())) {
			storyDao.update(id, story);
			
			return new ResponseEntity<Story>(story, HttpStatus.OK);
		} else {
			return new ResponseEntity<Story>(story, HttpStatus.UNAUTHORIZED);
		}
	}
	
	@RequestMapping(value="/api/stories/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteStory(@PathVariable("id") int id) {
		// TODO: Check authenticated user owns the story
		Story story = storyDao.read(id);	
		if (auth.isAuthorized(story.getOwner())) {
			storyDao.delete(id);
			return new ResponseEntity<Object>(null, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);
		}
	}
	
	@RequestMapping(value="/api/users/{id}/stories", method=RequestMethod.GET)
	public ResponseEntity<List<Story>> getStoriesByUser(@PathVariable("id") int id) {
		List<Story> stories = storyDao.getStoriesByUser(id);
		
		return new ResponseEntity<List<Story>>(stories, HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/stories/{id}/join", method=RequestMethod.POST)
	public ResponseEntity<Object> joinStory(@PathVariable("id") int id) {
		logger.debug("joinStory(): id=" + id);
		
		Story story = storyDao.read(id);
		if (story.isInviteOnly() || !story.isVisible()) {
			return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);
		} else {
			storyDao.joinStory(id, auth.getActiveUser());
			
			return new ResponseEntity<Object>(null, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/api/stories/{id}/contribute", method=RequestMethod.POST)
	public ResponseEntity<Contribution> newContribution(@PathVariable("id") int storyId, @RequestBody Contribution contribution) {
		
		logger.debug("newContribution: storyId=" + storyId + "contribution=" + contribution.toString());
		if (isContributor(auth.getActiveUser(), storyId)) {	
			contribution.setOwner(auth.getActiveUser());
			contribution.setStory(storyId);
			Number contributionId = contributionDao.create(contribution);
			contribution.setId(contributionId.intValue());
			
			return new ResponseEntity<Contribution>(contribution, HttpStatus.OK);
		} else {
			return new ResponseEntity<Contribution>(contribution, HttpStatus.UNAUTHORIZED);
		}
	}
	
	@RequestMapping(value="/api/contributions/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Contribution> editContribution(@PathVariable("id") int id, @RequestBody Contribution contribution) {
		if (auth.isAuthorized(contribution.getOwner())) {
			contributionDao.update(id, contribution);
			
			return new ResponseEntity<Contribution>(contribution, HttpStatus.OK);
		} else {
			return new ResponseEntity<Contribution>(contribution, HttpStatus.UNAUTHORIZED);
		}
	}
	
	@RequestMapping(value="/api/contributions/{id}", method=RequestMethod.GET)
	public ResponseEntity<Contribution> getContribution(@PathVariable("id") int id) {
		Contribution contribution = contributionDao.read(id);
		
		return new ResponseEntity<Contribution>(contribution, HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/users/{id}/contributions", method=RequestMethod.GET)
	public ResponseEntity<List<Contribution>> getContributionsByUser(@PathVariable("id") int id) {
		List<Contribution> contributions = contributionDao.searchByOwner(id);
		
		return new ResponseEntity<List<Contribution>>(contributions, HttpStatus.OK);
	}
	
	
	private boolean isContributor(int userId, int storyId) {
		Story story = storyDao.read(storyId);
		return (userId == story.getOwner()) || (story.getContributors().contains(userId));
	}
	
}
