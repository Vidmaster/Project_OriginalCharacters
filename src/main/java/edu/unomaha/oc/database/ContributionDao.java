package edu.unomaha.oc.database;

import java.util.List;

import edu.unomaha.oc.domain.Contribution;

public interface ContributionDao {
	public List<Contribution> searchByOwner (int owner);
	
	public List<Contribution> searchByStory(int story);
	
	public Contribution read(int id);
	
	public Number update(int id, Contribution contribution);
	
	public Number create(Contribution contribution);
	
	public void delete(int id);
}
