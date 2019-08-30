package Model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import utils.E_Levels;

/**
 * Class Coach ~ represent a single Coach in the league,
 * every coach has a current team that being coached by that coach.
 * 
 * @author Java Course Team 2018 - Shai Gutman
 * @author University Of Haifa - Israel
 * @author ID: 205791056
 */
public class Coach extends Employee implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// -------------------------------Class Members------------------------------
	private Team currentTeam;
	private E_Levels level;
	private Set<Team> teams;

	// -------------------------------Constructors------------------------------
	public Coach(int id, String firstName, String lastName, Date birthdate, Date startWorkingDate, Address address,
			E_Levels level) {
		super(id, firstName, lastName, birthdate, startWorkingDate, address);
		this.level = level;
		//TODO
		teams = new HashSet<>();
	}

	public Coach(int id) {
		super(id);
	}

	// -------------------------------Getters And Setters------------------------------
	public Team getCurrentTeam() {
		return currentTeam;
	}

	public void setCurrentTeam(Team currentTeam) {
		this.currentTeam = currentTeam;
	}

	public E_Levels getLevel() {
		return level;
	}

	public void setLevel(E_Levels level) {
		this.level = level;
	}

	public Set<Team> getTeams() {
		return teams;
	}

	public void setTeams(Set<Team> teams) {
		this.teams = teams;
	}

	// -------------------------------More Methods------------------------------
	/**
	 * This method adds the coach to the given team and removes the coach from its current team
	 * only if the given team doesn't equal to the Coach's current team.
	 *
	 * @param team to be transferred to
	 * @return true if the coach was added successfully to team, false otherwise
	 */
	public boolean transferTo(Team team) {
		//TODO
		// input check
		if (team == null || (team != null && team.equals(currentTeam)))
			return false;

		// if the team exists in the teams array, we remove it 
		if (teams.contains(team))
			teams.remove(team);
		
		// adding coach to new team and transferring old team to teams' Set
		if (addTeam(currentTeam) && team.registerCoach(this)) {
			currentTeam.setCoach(null);
			currentTeam = team;
			return true;
		}
		
		return false;
	}
	
	/**
	 * This method adds a new team to the teams array 
	 * only if the given team doesn't already exists in the Coach's teams array.
	 * 
	 * @param team to be added
	 * @return true if the team was added successfully, false otherwise
	 */
	protected boolean addTeam(Team team) {
		//TODO
		// input check
		if (team == null)
			return false;
		
		// checking if team exists already
		if (teams.contains(team))
			return false;
			
		return teams.add(team);
	}

	/**
	 * This method removes a given team form the teams array.
	 * 
	 * @param team to be removed
	 * @return true if the team was removed successfully, false otherwise
	 */
	protected boolean removeTeam(Team team) {
		//TODO
		// input check
		if (team == null)
			return false;
		
		// checking if team exists already
		if (!teams.contains(team))
			return false;
		
		return teams.remove(team);
	}
	
}
