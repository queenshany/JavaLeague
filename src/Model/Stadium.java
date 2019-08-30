package Model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import Exceptions.MatchException;
import utils.Constants;

/**
 * Class Stadium ~ represent a single stadium of the league
 * 
 * @author Java Course Team 2018 - Shai Gutman
 * @author University Of Haifa - Israel
 * @author ID: 205791056
 */
public class Stadium  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// -------------------------------Class Members------------------------------
	private int id;
	private String name;
	private Address address;
	private int capacity;
	private Set<Receptionist> receptionists;
	private Set<Team> teams;
	private Set<Match> matches;

	
	
	// -------------------------------Constructors------------------------------
	public Stadium(int id, String name, Address address, int capacity) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.capacity = capacity;
		//TODO
		teams = new HashSet<>();
		matches = new HashSet<>();
		receptionists = new HashSet<>();
	}

	
	public Stadium(int id) {
		this.id = id;
	}
	
	// -------------------------------Getters And Setters------------------------------

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public Set<Receptionist> getReceptionists() {
		return receptionists;
	}

	public void setReceptionists(Set<Receptionist> receptionists) {
		this.receptionists = receptionists;
	}

	public Set<Team> getTeams() {
		return teams;
	}

	public void setTeams(Set<Team> teams) {
		this.teams = teams;
	}

	public Set<Match> getMatches() {
		return matches;
	}

	public void setMatches(Set<Match> matches) {
		this.matches = matches;
	}

	// -------------------------------More Methods------------------------------
	/**
	 * This method adds a new receptionist to the receptionists array 
	 * only if the given receptionist doesn't already exist in the Stadium's receptionists array.
	 * 
	 * @param receptionist to be added
	 * @see utils.Constants
	 * @return true if the receptionist was added successfully, false otherwise
	 */
	public boolean addReceptionist(Receptionist receptionist) {
		//TODO
		// input check
		if (receptionist == null)
			return false;
		
		// checking if receptionist exists
		if (receptionists.contains(receptionist))
			return false;
		
		// checking if there are 4 receptionists or more in the team
		if (receptionists.size() >= Constants.MAX_RESEPTIONISTS_FOR_STADIUM)
			return false;
		
		receptionists.add(receptionist);
		
		return true;
	}

	/**
	 * This method removes a given receptionist form the receptionists array.
	 * 
	 * @param receptionist to be removed
	 * @return true if the receptionist was removed successfully, false otherwise
	 */
	public boolean removeReceptionist(Receptionist receptionist) {
		//TODO
		// input check
		if (receptionist == null)
			return false;
		
		// checking if receptionist exists
		if (!receptionists.contains(receptionist))
			return false;

		// removing the receptionist from the stadium
		receptionists.remove(receptionist);
		receptionist.setWorkingStadium(null);
		return true;
	}

	/**
	 * This method adds a new team to the teams array 
	 * only if the given team doesn't already exists in the Stadium's teams array.
	 * 
	 * @param team to be added
	 * @see utils.Constants
	 * @return true if the team was added successfully, false otherwise
	 */
	public boolean addTeam(Team team) {
		//TODO
		// input check
		if (team == null)
			return false;
		
		// checking if team exists already
		if (teams.contains(team))
			return false;
		
		// checking if there are two teams or more in the stadium
		if (teams.size() >= Constants.MAX_TEAMS_FOR_STADIUM)
			return false;
		
		
		return teams.add(team);
	}

	/**
	 * This method removes a given team form the teams array.
	 * 
	 * @param team to be removed
	 * @return true if the team was removed successfully, false otherwise
	 */
	public boolean removeTeam(Team team) {
		//TODO
		// input check
		if (team == null)
			return false;
		
		// checking if the team exists in the stadium
		if (!teams.contains(team))
			return false;
		
		team.setStadium(null);
		
		return teams.remove(team);
	}

	/**
	 * This method adds a new match to the matches array
	 * only if the match doesn't already exists in the Stadium's matches array and 
	 * the time doesn't overlap with any other match in the array
	 * 
	 * @param match to be added
	 * @see overlapDates
	 * @return true if the match was added successfully, false otherwise
	 */
	public boolean addMatch(Match match) {
		//TODO
		// input check
		if (match == null)
			return false;

		// checking if match exists
		if (matches.contains(match))
			throw new MatchException("The game already exists in this stadium");

		// checking if dates overlap
		for (Match temp : matches)
			if (temp != null && overlapDates(temp.getStartDateTime(), temp.getFinishDateTime(), match.getStartDateTime(), match.getFinishDateTime()))
				throw new MatchException("The game overlaps with another game in this stadium");

		return matches.add(match);
	}
	
	/**
	 * This method removes a given match from the matches array.
	 * 
	 * @param match to be removed
	 * @return true if match was removed successfully, false otherwise
	 */
	public boolean removeMatch(Match match) {
		//TODO
		// input check
		if (match == null)
			return false;

		// checking if match exists
		if (!matches.contains(match))
			return false;

		// removing the match from the team
		return matches.remove(match);
	}
	
	// -------------------------------Helper Methods------------------------------
		/**
		 * Helper method that checks overlap dates
		 * @param start1 The start of date1
		 * @param end1 The end of date1
		 * @param start2 The start of date2
		 * @param end2 The end of date2
		 * @return true if dates overlap, false, otherwise
		 */
		private boolean overlapDates(Date start1, Date end1, Date start2, Date end2) {
			return start1.before(end2) && start2.before(end1); // true means overlap
		}
		
	// -------------------------------hashCode equals & toString------------------------------
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stadium other = (Stadium) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Stadium | id: " + id + ", name: " + name + ", capacity: " + capacity + ", address: " + address;
	}

}
