package edu.unomaha.oc.database;

import java.util.List;

import edu.unomaha.oc.domain.Invitation;

public interface InvitationDao {
	public List<Invitation> searchBySender(int sender);
	
	public List<Invitation> searchByRecipient(int recipient);
	
	public List<Invitation> searchByStory(int story);
	
	public Invitation read(int id);
	
	public Number update(int id, Invitation invitation);
	
	public Number create(Invitation invitation);
	
	public void delete(int id);
}
