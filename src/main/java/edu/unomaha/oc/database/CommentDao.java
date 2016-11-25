package edu.unomaha.oc.database;

import java.util.List;

import edu.unomaha.oc.domain.Comment;

public interface CommentDao {
	public List<Comment> searchByCommenter(int commenter);
	
	public List<Comment> searchByStory(int story);
	
	public Comment read(int id);
	
	public Number update(int id, Comment comment);
	
	public Number create(Comment comment);
	
	public void delete(int id);
}
