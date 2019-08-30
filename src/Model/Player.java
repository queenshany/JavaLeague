package Model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import utils.E_Levels;
import utils.E_Position;

/**
 * Class Player ~ represent a single Player in the league,
 * inheritor of coach
 * 
 * @author Java Course Team 2018 - Shai Gutman
 * @author University Of Haifa - Israel
 * @author ID: 205791056
 */
public class Player extends Coach implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// -------------------------------Class Members------------------------------
	private long value;
	private boolean isRightLegKicker;
	private E_Position position;
	private Set<Match> matches;
	private String name;
	private boolean isFirstTeamPlayer;

	// -------------------------------Constructors------------------------------
	public Player(int id, String firstName, String lastName, Date birthdate, Date startWorkingDate, Address address,
			E_Levels level, long value, boolean isRightLegKicker, E_Position position) {
		super(id, firstName, lastName, birthdate, startWorkingDate, address, level);
		this.value = value;
		this.isRightLegKicker = isRightLegKicker;
		this.position = position;
		//TODO
		matches = new HashSet<>(); 
		isFirstTeamPlayer = false;
		setName();
	}
	
	public Player(int id) {
		super(id);
	}

	// -------------------------------Getters And Setters------------------------------
	public void setName() {
		name = getFirstName()+" "+getLastName();
	}
	
	public void setFirstTeamPlayer(boolean bool) {
		this.isFirstTeamPlayer = bool;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean getIsFirstTeamPlayer() {
		return isFirstTeamPlayer;
	}
	
	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public boolean isRightLegKicker() {
		return isRightLegKicker;
	}

	public boolean getIsRightLegKicker() {
		return isRightLegKicker;
	}

	
	public void setRightLegKicker(boolean isRightLegKicker) {
		this.isRightLegKicker = isRightLegKicker;
	}

	public E_Position getPosition() {
		return position;
	}

	public void setPosition(E_Position position) {
		this.position = position;
	}

	public Set<Match> getMatches() {
		return matches;
	}

	public void setMatches(Set<Match> matches) {
		this.matches = matches;
	}

	// -------------------------------More Methods------------------------------
	/**
	 * This method adds the player to the given team and removes the player from its current team
	 * only if the given team doesn't equal to the Player's current team.
	 *
	 * @param team to be transferred to
	 * @return true if the player was added successfully to team, false otherwise
	 */
	public boolean transferTo(Team team) {
		//TODO
		// input check 
		if (team == null || (team != null && team.equals(getCurrentTeam())))
			return false;

		// removing player from current team
		if (getCurrentTeam().removePlayer(this)) {
			// adding player to new team
			if (team.addPlayer(this)) {
				setCurrentTeam(team);
				return true;
			}
		}

		return false;
	}
	/**
	 * This method adds a new match to the matches array
	 * only iF the match doesn't already exists in the Player's matches array and 
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
			return false;

		// checking if dates overlap
		for (Match temp : matches)
			if (temp != null && overlapDates(temp.getStartDateTime(), temp.getFinishDateTime(), match.getStartDateTime(), match.getFinishDateTime()))
				return false;

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
}
