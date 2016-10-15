package edu.unomaha.oc.database;

import java.util.List;

import edu.unomaha.oc.domain.Story;

public interface StoryDao {
	public List<Story> search();
	
	public Story read(int id);
	
	public int update(int id);
	
	public int create();
	
	public int delete(int id);
}
