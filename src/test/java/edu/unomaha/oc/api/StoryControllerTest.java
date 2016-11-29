package edu.unomaha.oc.api;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.unomaha.oc.config.TestConfiguration;
import edu.unomaha.oc.database.ContributionDao;
import edu.unomaha.oc.database.StoryDao;
import edu.unomaha.oc.domain.Contribution;
import edu.unomaha.oc.domain.Story;
import edu.unomaha.oc.utilities.AuthUtilities;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfiguration.class})
public class StoryControllerTest {
	
	@Autowired
    private StoryDao mockStoryDao;
    
	@Autowired
	private AuthUtilities mockAuth;
	
	@Autowired
	private ContributionDao mockContributionDao;
	
	@InjectMocks
	private StoryController sc = new StoryController();
	
	// Reset our mocks before each test to get a clean read on the number of method invocations
	@Before
	public void setup() {
		reset(mockAuth);
		reset(mockStoryDao);
	}
	
    @Test
    public void testGetStoriesByUserId() {
    	when(mockStoryDao.getStoriesByUser(1)).thenReturn(
    			Arrays.asList(new Story(1, 1, "test1", "desc1", "genre1", true, true), new Story(2, 2, "test2", "desc2", "genre2", true, true)));
    	
    	ResponseEntity<List<Story>> response = sc.getStoriesByUser(1);
    	
    	assertEquals(HttpStatus.OK, response.getStatusCode());
    	
    	assertEquals(2, response.getBody().size());
    	
    	verify(mockStoryDao, times(1)).getStoriesByUser(1);
        verifyNoMoreInteractions(mockStoryDao);
    }
    
    @Test
    public void testCreateStory() {
    	when(mockStoryDao.create(any())).thenReturn(new Integer(12345));
    	
    	ResponseEntity<Story> response = sc.createStory(2, "testStory", "description", "genre", true, false);
    	
    	assertEquals(HttpStatus.OK, response.getStatusCode());
    	
    	assertEquals(12345, response.getBody().getId());
    	assertEquals("testStory", response.getBody().getTitle());
    	
    	verify(mockStoryDao, times(1)).create(any());
    	verifyNoMoreInteractions(mockStoryDao);
    }
    
    @Test
    public void testDeleteStoryUserAuthorized() {
    	when(mockAuth.isAuthorized(1)).thenReturn(true);
    	when(mockStoryDao.read(123)).thenReturn(new Story(123, 1, "test", "test", "test", true, true));
    	
    	ResponseEntity<Object> response = sc.deleteStory(123);
    	assertEquals(HttpStatus.OK, response.getStatusCode());
    	
    	verify(mockAuth, times(1)).isAuthorized(1);
    	verifyNoMoreInteractions(mockAuth);
    	verify(mockStoryDao, times(1)).read(123);
    	verify(mockStoryDao, times(1)).delete(123);
    	verifyNoMoreInteractions(mockStoryDao);
    }
    
    @Test
    public void testDeleteStoryUserNotAuthorized() {
    	when(mockAuth.isAuthorized(2)).thenReturn(false);
    	when(mockStoryDao.read(123)).thenReturn(new Story(123, 2, "test", "test", "test", true, true));
    	
    	ResponseEntity<Object> response = sc.deleteStory(123);
    	assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    	
    	verify(mockAuth, times(1)).isAuthorized(2);
    	verifyNoMoreInteractions(mockAuth);
    	verify(mockStoryDao, times(1)).read(123);
    	verify(mockStoryDao, times(0)).delete(123);
    	verifyNoMoreInteractions(mockStoryDao);
    }
    
    @Test
    public void testEditContributionUserAuthorized() {
    	when(mockAuth.isAuthorized(1)).thenReturn(true);
    	when(mockContributionDao.update(eq(1), any())).thenReturn(1);
    	
    	Contribution contribution = new Contribution(1, 1, 1, 1, "test", "test body", "APPROVED");
    	
    	ResponseEntity<Contribution> response = sc.editContribution(1, contribution);
    	
    	assertEquals(HttpStatus.OK, response.getStatusCode());
    	assertEquals(contribution, response.getBody());
    	
    	verify(mockAuth, times(1)).isAuthorized(1);
    	verifyNoMoreInteractions(mockAuth);
    	verify(mockContributionDao, times(1)).update(1, contribution);
    	verifyNoMoreInteractions(mockContributionDao);
    }
    
    @Test
    public void testEditContributionUserNotAuthorized() {
    	when(mockAuth.isAuthorized(1)).thenReturn(false);
    	when(mockContributionDao.update(eq(1), any())).thenReturn(0);
    	
    	Contribution contribution = new Contribution(1, 1, 1, 1, "test", "test body", "APPROVED");
    	
    	ResponseEntity<Contribution> response = sc.editContribution(1, contribution);
    	
    	assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    	assertEquals(contribution, response.getBody());
    	
    	verify(mockAuth, times(1)).isAuthorized(1);
    	verifyNoMoreInteractions(mockAuth);
    	verify(mockContributionDao, times(0)).update(1, contribution);
    	verifyNoMoreInteractions(mockContributionDao);
    }
    
    @Test
    public void testGetContribution() {
    	Contribution contribution = new Contribution(123, 1, 1, 1, "test", "test body", "APPROVED");
    	when(mockContributionDao.read(123)).thenReturn(contribution);
    	
    	ResponseEntity<Contribution> response = sc.getContribution(123);
    	assertEquals(HttpStatus.OK, response.getStatusCode());
    	assertEquals(contribution, response.getBody());
    	
    	verify(mockContributionDao, times(1)).read(123);
    	verifyNoMoreInteractions(mockContributionDao);
    }
    
    @Test
    public void testGetContributionsByUser() {
    	Contribution c1 = new Contribution(1, 123, 1, 1, "test1", "test body", "APPROVED");
    	Contribution c2 = new Contribution(2, 123, 1, 1, "test2", "test body", "APPROVED");
    	List<Contribution> contributions = Arrays.asList(c1, c2);
    	when(mockContributionDao.searchByOwner(123)).thenReturn(contributions);
    	
    	ResponseEntity<List<Contribution>> response = sc.getContributionsByUser(123);
    	
    	assertEquals(HttpStatus.OK, response.getStatusCode());
    	assertEquals(contributions, response.getBody());
    	
    	verify(mockContributionDao, times(1)).searchByOwner(123);
    	verifyNoMoreInteractions(mockContributionDao);
    }
    
    @Test
    public void testGetStory() {
    	Story story = new Story(123, 1, "test", "description", "genre", true, true);
    	when(mockStoryDao.read(123)).thenReturn(story);
    	
    	ResponseEntity<Story> response = sc.getStory(123);
    	assertEquals(HttpStatus.OK, response.getStatusCode());
    	assertEquals(story, response.getBody());
    	
    	verify(mockStoryDao, times(1)).read(123);
    	verifyNoMoreInteractions(mockStoryDao);
    }
    
    @Test
    public void testJoinStoryPublic() {
    	Story story = new Story(123, 1, "test", "description", "genre", true, false);
    	when(mockAuth.getActiveUser()).thenReturn(456);
    	when(mockStoryDao.read(123)).thenReturn(story);
    	
    	ResponseEntity<Object> response = sc.joinStory(123);
    	
    	assertEquals(HttpStatus.OK, response.getStatusCode());
    	
    	verify(mockAuth, times(1)).getActiveUser();
    	verifyNoMoreInteractions(mockAuth);
    	verify(mockStoryDao, times(1)).read(123);
    	verify(mockStoryDao, times(1)).joinStory(123, 456);
    	verifyNoMoreInteractions(mockStoryDao);
    }
    
    @Test
    public void testJoinStoryInviteOnlyFails() {
    	Story story = new Story(123, 1, "test", "description", "genre", true, true);
    	when(mockStoryDao.read(123)).thenReturn(story);
    	
    	ResponseEntity<Object> response = sc.joinStory(123);
    	
    	assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    	
    	verify(mockAuth, times(0)).getActiveUser();
    	verifyNoMoreInteractions(mockAuth);
    	verify(mockStoryDao, times(1)).read(123);
    	verify(mockStoryDao, times(0)).joinStory(123, 456);
    	verifyNoMoreInteractions(mockStoryDao);
    }
    
    @Test
    public void testJoinStoryNotVisibleFails() {
    	Story story = new Story(123, 1, "test", "description", "genre", false, false);
    	when(mockStoryDao.read(123)).thenReturn(story);
    	
    	ResponseEntity<Object> response = sc.joinStory(123);
    	
    	assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    	
    	verify(mockAuth, times(0)).getActiveUser();
    	verifyNoMoreInteractions(mockAuth);
    	verify(mockStoryDao, times(1)).read(123);
    	verify(mockStoryDao, times(0)).joinStory(123, 456);
    	verifyNoMoreInteractions(mockStoryDao);
    }
    
    @Test
    public void testNewContributionUserIsOwner() {
    	Contribution contribution = new Contribution(-1, 123, 1, 1, "test1", "test body", "APPROVED");
    	Story story = new Story(1, 123, "test", "description", "genre", true, true);
    	story.setContributors(new ArrayList<Integer>());
    	
    	when(mockContributionDao.create(contribution)).thenReturn(456);
    	when(mockStoryDao.read(1)).thenReturn(story);
    	when(mockAuth.getActiveUser()).thenReturn(123);
    	
    	ResponseEntity<Contribution> response = sc.newContribution(1, contribution);
    	
    	assertEquals(HttpStatus.OK, response.getStatusCode());
    	assertEquals(456, response.getBody().getId());
    	
    	verify(mockStoryDao, times(1)).read(1);
    	verifyNoMoreInteractions(mockStoryDao);
    	verify(mockAuth, times(2)).getActiveUser();
    	verifyNoMoreInteractions(mockAuth);
    	verify(mockContributionDao, times(1)).create(contribution);
    	verifyNoMoreInteractions(mockContributionDao);
    }
    
    @Test
    public void testNewContributionUserIsContributor() {
    	Contribution contribution = new Contribution(1, 456, 1, 1, "test1", "test body", "APPROVED");
    	Story story = new Story(1, 123, "test", "description", "genre", true, true);
    	List<Integer> contributors = new ArrayList<Integer>();
    	contributors.add(456);
    	story.setContributors(contributors);
    	
    	when(mockContributionDao.create(contribution)).thenReturn(456);
    	when(mockStoryDao.read(1)).thenReturn(story);
    	when(mockAuth.getActiveUser()).thenReturn(456);
    	
    	ResponseEntity<Contribution> response = sc.newContribution(1, contribution);
    	
    	assertEquals(HttpStatus.OK, response.getStatusCode());
    	assertEquals(456, response.getBody().getId());
    	
    	verify(mockStoryDao, times(1)).read(1);
    	verifyNoMoreInteractions(mockStoryDao);
    	verify(mockAuth, times(2)).getActiveUser();
    	verifyNoMoreInteractions(mockAuth);
    	verify(mockContributionDao, times(1)).create(contribution);
    	verifyNoMoreInteractions(mockContributionDao);
    }
    
    @Test
    public void testNewContributionUserIsNotAuthorized() {
    	Contribution contribution = new Contribution(1, 789, 1, 1, "test1", "test body", "APPROVED");
    	Story story = new Story(1, 123, "test", "description", "genre", true, true);
    	List<Integer> contributors = new ArrayList<Integer>();
    	contributors.add(456);
    	story.setContributors(contributors);
    	
    	when(mockAuth.getActiveUser()).thenReturn(789);
    	when(mockStoryDao.read(1)).thenReturn(story);
    	
    	ResponseEntity<Contribution> response = sc.newContribution(1, contribution);
    	
    	assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    	
    	verify(mockAuth, times(1)).getActiveUser();
    	verifyNoMoreInteractions(mockAuth);
    	verify(mockStoryDao, times(1)).read(1);
    	verifyNoMoreInteractions(mockStoryDao);
    	verifyNoMoreInteractions(mockContributionDao);
    }
    
    @Test
    public void testSearchStoriesByTitle() {
    	Story s1 = new Story(1, 123, "test", "description", "genre", false, false);
    	Story s2 = new Story(2, 123, "test2", "description", "genre", false, false);
    	List<Story> stories = Arrays.asList(s1, s2);
    	when(mockStoryDao.search("test", 123)).thenReturn(stories);
    	when(mockAuth.getActiveUser()).thenReturn(123);
    	
    	ResponseEntity<List<Story>> response = sc.searchStoriesByTitle("test");
    	
    	assertEquals(HttpStatus.OK, response.getStatusCode());
    	assertEquals(stories, response.getBody());
    	
    	verify(mockAuth, times(1)).getActiveUser();
    	verifyNoMoreInteractions(mockAuth);
    	verify(mockStoryDao, times(1)).search("test", 123);
    	verifyNoMoreInteractions(mockStoryDao);
    }
    
    @Test
    public void testUpdateStoryUserAuthorized() {
    	Story story = new Story(1, 123, "test", "description", "genre", false, false);
    	when(mockStoryDao.update(eq(1), any())).thenReturn(1);
    	when(mockAuth.isAuthorized(123)).thenReturn(true);
    	when(mockStoryDao.read(1)).thenReturn(story);
    	
    	ResponseEntity<Story> response = sc.updateStory(1, "new title", "new description", "genre", true, false);
    	
    	assertEquals(HttpStatus.OK, response.getStatusCode());
    	assertEquals(story, response.getBody());
    	assertEquals("new title", response.getBody().getTitle());
    	
    	verify(mockAuth, times(1)).isAuthorized(123);
    	verifyNoMoreInteractions(mockAuth);
    	verify(mockStoryDao, times(1)).read(1);
    	verify(mockStoryDao, times(1)).update(eq(1), any());
    	verifyNoMoreInteractions(mockStoryDao);
    }
    
    @Test
    public void testUpdateStoryUserNotAuthorized() {
    	Story story = new Story(1, 123, "test", "description", "genre", false, false);
    	when(mockStoryDao.update(eq(1), any())).thenReturn(0);
    	when(mockAuth.getActiveUser()).thenReturn(456);
    	when(mockStoryDao.read(1)).thenReturn(story);
    	
    	ResponseEntity<Story> response = sc.updateStory(1, "new title", "new description", "genre", true, false);
    	
    	assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    	assertEquals(story, response.getBody());
    	
    	verify(mockAuth, times(1)).isAuthorized(123);
    	verifyNoMoreInteractions(mockAuth);
    	verify(mockStoryDao, times(1)).read(1);
    	verify(mockStoryDao, times(0)).update(eq(1), any());
    	verifyNoMoreInteractions(mockStoryDao);
    	
    }
}