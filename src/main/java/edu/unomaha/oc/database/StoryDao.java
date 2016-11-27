package edu.unomaha.oc.database;

import java.util.List;

import edu.unomaha.oc.domain.Story;

/**
 * This interface holds operations related to stories
 * 
 */
public interface StoryDao {
	public List<Story> search(String title, int owner);
	
	public List<Story> getStoriesByUser(int userId);
	
	public Story read(int id);
	
	public Number update(int id, Story story);
	
	public Number create(Story story);
	
	public void delete(int id);
	
	public void joinStory(int storyId, int userId);
}
