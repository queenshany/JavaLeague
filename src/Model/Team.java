package Model;

import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;

import Exceptions.FirstException;
import Exceptions.MatchException;
import utils.Constants;
import utils.E_Levels;

/**
 * Class Team ~ represent a single team of the league
 * 
 * @author Java Course Team 2018 - Shai Gutman
 * @author University Of Haifa - Israel
 * @author ID: 205791056
 */
public class Team  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// -------------------------------Class Members------------------------------
	private int id;
	private String name;
	private int value;
	private E_Levels level;
	private Stadium stadium;
	private Coach coach;
	private Map<Player, Boolean> players;
	private Set<Match> matches;

	// -------------------------------Constructors------------------------------
	public Team(int id, String name, int value, E_Levels level, Stadium stadium) {
		this.id = id;
		this.name = name;
		this.value = value;
		this.level = level;
		this.stadium = stadium;
		//TODO
		players = new TreeMap<>();
		matches = new HashSet<>();
	}
	
	public Team(int id) {
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

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public E_Levels getLevel() {
		return level;
	}

	public void setLevel(E_Levels level) {
		this.level = level;
	}

	public Stadium getStadium() {
		return stadium;
	}
	
	public Coach getCoach() {
		return coach;
	}

	public void setCoach(Coach coach) {
		this.coach = coach;
	}

	public void setStadium(Stadium stadium) {
		this.stadium = stadium;
	}

	public Map<Player, Boolean> getPlayers() {
		return players;
	}

	public void setPlayers(Map<Player, Boolean> players) {
		this.players = players;
	}

	public Set<Match> getMatches() {
		return matches;
	}

	public void setMatches(Set<Match> matches) {
		this.matches = matches;
	}
	
	// -------------------------------More Methods------------------------------
	/**
	 * This method registers a new coach to the team
	 * only if coach's level is greater or equal to team's level
	 * 
	 * @param coach to be registered
	 * @return true if coach was registered successfully, false otherwise
	 */
	public boolean registerCoach(Coach coach) {
		//TODO
		// input check
		if (coach == null)
			return false;

		// coach already trains the team
		if (coach.equals(this.coach))
			return false;
		
		// if coach's level equals or higher than the team's, we add him
		if (coach.getLevel().getLevel() >= this.level.getLevel()) {
		
			if(this.coach != null)
				this.coach.setCurrentTeam(null);
			
			this.coach = coach;
			return true;
		}
		
		return false;
	}
	
	/**
	 * This method adds a new player to the players array 
	 * only if the given player doesn't already exists in the Team's players array.
	 * 
	 * @param player to be added
	 * @see utils.Constants
	 * @return true if the player was added successfully, false otherwise
	 */
	public boolean addPlayer(Player player) {
		//TODO
		// input check
		if (player == null)
			return false;
		
		// checking if player exists
		if (players.containsKey(player))
			return false;
		
		// checking if there are 7 players or more in the team
		if (players.size() >= Constants.MAX_PLAYERS_FOR_TEAM)
			return false;
		
		// adding players to firstTeam array
		Boolean boll = players.put(player, false);
		if(boll != null)
			return false;
		
		
		return true;
	}

	/**
	 * This method removes a given player form the players array.
	 * 
	 * @param player to be removed
	 * @return true if the player was removed successfully, false otherwise
	 */
	public boolean removePlayer(Player player) {
		//TODO
		// input check
		if (player == null)
			return false;
		
		// checking if player exists
		if (!players.containsKey(player))
			return false;
		
		if (players.get(player))
			player.setFirstTeamPlayer(false);
		
		// removing the player from the team
		players.remove(player);
		player.setCurrentTeam(null);
		return true;
	}
	
	/**
	 * This method adds a new player to the first team players array 
	 * only if the given player doesn't already exists in the Team's first players array.
	 * 
	 * @param player to be added to firstTeam
	 * @see utils.Constants
	 * @return true if the player was added successfully, false otherwise
	 */
	public boolean addPlayerToFirstTeam(Player player) {
		//TODO
		// input check
		if (player == null)
			return false;
			
		// checking if player exists
		if (!players.containsKey(player))
			return false;
		
		if (players.get(player))
			throw new FirstException("Player is already in the first team");
		
		if (player.getCurrentTeam() != null  && !player.getCurrentTeam().equals(this))
			return false;
			
		// counting the amount of players in the first team players
		int trueCounter = 0;
		for (Map.Entry<Player, Boolean> entry : players.entrySet())
			if (entry != null && entry.getValue().equals(true))
				trueCounter++;
		
		// adding first team players, if trueCounter is 4 or less
		if (trueCounter < Constants.NUM_OF_FIRST_TEAM_PLAYERS) {
			players.replace(player, true);
			player.setFirstTeamPlayer(true);
			return true;
		}

		throw new FirstException("The first team is full");
	}

	/**
	 * This method replace a given player form the first players array
	 * with another given player.
	 * 
	 * @param oldPlayer to be replaced
	 * @param newPlayer to be added instead of old
	 * @return true if the player was removed successfully, false otherwise
	 */
	public boolean replacePlayerOfFirstTeam(Player oldPlayer, Player newPlayer) {
		//TODO
		// input check
		if (oldPlayer == null || newPlayer == null)
			return false;
		
		// checking is players exist and can be replaced
		if (players.containsKey(oldPlayer) && players.get(oldPlayer).equals(true)) 
			if (players.containsKey(newPlayer) && players.get(newPlayer).equals(false)) 
				
				// replacing the players
				if (players.replace(oldPlayer, false)) {
				
					players.replace(newPlayer, true);
					oldPlayer.setFirstTeamPlayer(false);
					newPlayer.setFirstTeamPlayer(true);
					return true; 
				}
		return false;			
	}
	
	/**
	 * This method adds a new match to the matches array
	 * only if the match doesn't already exists in the Team's matches array and 
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
			throw new MatchException("Match already exists for team " + name );

		// checking if dates overlap
		for (Match temp : matches)
			if (temp != null && overlapDates(temp.getStartDateTime(), temp.getFinishDateTime(), match.getStartDateTime(), match.getFinishDateTime()))
				throw new MatchException("The match overlaps with other match for team " + name);

		// adding match to each player in the team
		for (Entry<Player, Boolean> entry : players.entrySet())
			if (entry.getKey() != null && !entry.getKey().addMatch(match))
				throw new MatchException("The match overlaps with other match for player " + entry.getKey().getId() + 
						"in team " + name);
		
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

		// removing match from each player in the team
		for (Entry<Player, Boolean> entry : players.entrySet())
			if (entry.getKey() != null && !entry.getKey().removeMatch(match))
				return false;
		
		// removing the match from the team
		return matches.remove(match);
	}
	
	/**
	 * This method returns the total number of goals from home wins
	 * divided by the total number of goals from away wins
	 * @return rate if valid, 0 otherwise
	 */
	public double getHomeAwayWinningsRate() {
		//TODO
		int homeGoals = 0, awayGoals = 0;

		for (Match match : matches) {
			if (match != null) {

				// won as Away team
				if (match.getAwayTeam().equals(this))
					if (match.getAwayTeamScore() > match.getHomeTeamScore())
						awayGoals += match.getAwayTeamScore();

				// won as Home team
				if (match.getHomeTeam().equals(this))
					if (match.getAwayTeamScore() < match.getHomeTeamScore())
						homeGoals += match.getHomeTeamScore();
			}
		}

		// can't divide by 0
		if (awayGoals == 0)
			return 0;

		return (double)homeGoals / awayGoals;
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
		Team other = (Team) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Team | id: " + id + ", name: " + name + ", value: " + value + ", level: " + level + ", stadium: " +(stadium != null ? stadium.getName() : "team has no stadium")
				+ ", coach: " + (coach != null ? coach.getFirstName() + " " + coach.getLastName() : "team has no coach");
	}

}
