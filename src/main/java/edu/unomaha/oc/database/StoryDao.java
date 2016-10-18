package edu.unomaha.oc.database;

import java.util.List;

import edu.unomaha.oc.domain.Story;

public interface StoryDao {
	public List<Story> search(String title, int owner);
	
	public Story read(int id);
	
	public Number update(int id, Story story);
	
	public Number create(Story story);
	
	public void delete(int id);
}
