package Model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import Exceptions.FanException;
import utils.Constants;
import utils.E_Levels;

/**
 * Class Match ~ represent a single Match of the league
 * 
 * @author Java Course Team 2018 - Shai Gutman
 * @author University Of Haifa - Israel
 * @author ID: 205791056
 */
public class Match implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// -------------------------------Class Members------------------------------
	private int id;
	private Date startDateTime;
	private int duration;
	private int extraTime;
	private E_Levels level;
	private Team homeTeam;
	private int homeTeamScore;
	private Team awayTeam;
	private int awayTeamScore;
	private Map<Customer, Boolean> crowd;

	// -------------------------------Constructors------------------------------
	public Match(int id, Date startDateTime, int extraTime, Team homeTeam,
			int homeTeamScore, Team awayTeam, int awayTeamScore) {
		this.id = id;
		this.startDateTime = startDateTime;
		this.extraTime = extraTime;
		this.duration = Constants.DEFAULT_MATCH_DURATION + extraTime;
		this.homeTeam = homeTeam;
		this.homeTeamScore = homeTeamScore;
		this.awayTeam = awayTeam;
		this.awayTeamScore = awayTeamScore;
		calculateMatchLevel();
		//TODO
		crowd = new TreeMap<>();
	}

	public Match(int id) {
		this.id = id;
	}

	// -------------------------------Getters And Setters------------------------------
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getExtraTime() {
		return extraTime;
	}

	public void setExtraTime(int extraTime) {
		this.extraTime = extraTime;
	}

	public E_Levels getLevel() {
		return level;
	}

	public void setLevel(E_Levels level) {
		this.level = level;
	}

	public Team getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(Team homeTeam) {
		this.homeTeam = homeTeam;
	}

	public int getHomeTeamScore() {
		return homeTeamScore;
	}

	public void setHomeTeamScore(int homeTeamScore) {
		this.homeTeamScore = homeTeamScore;
	}

	public Team getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(Team awayTeam) {
		this.awayTeam = awayTeam;
	}

	public int getAwayTeamScore() {
		return awayTeamScore;
	}

	public void setAwayTeamScore(int awayTeamScore) {
		this.awayTeamScore = awayTeamScore;
	}

	public Map<Customer, Boolean> getCrowd() {
		return crowd;
	}

	public void setCrowd(Map<Customer, Boolean> crowd) {
		this.crowd = crowd;
	}

	// -------------------------------More Methods------------------------------
	/**
	 * This method calculates the end date/time of the match
	 * @return Date object of the end date
	 */
	public Date getFinishDateTime() {
		//TODO
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDateTime);
		calendar.add(Calendar.MINUTE, duration);
		return calendar.getTime();
	}

	/**
	 * This method calculates the level of the match,
	 * the level will be the average of teams levels 
	 */
	private void calculateMatchLevel() {
		//TODO
		// getting home level
		int home = homeTeam.getLevel().getLevel(); 
		// getting away level
		int away = awayTeam.getLevel().getLevel();
		
		// calculating average
		this.level = E_Levels.returnLevel((int)(home + away) / 2);
	}
	
	/**
	 * this method adds a fan to this match's crowd if there is enough space on the stadium
	 * 
	 * @param fan to be added
	 * @return true if the fan was added successfully, false otherwise
	 */
	public boolean addFan(Customer fan) {
		//TODO
		// input check
		if (fan == null)
			return false;

		// checking if fan exists already
		
		if (crowd.containsKey(fan))
			throw new FanException("Fan already in the crowd");
		
		// checking if there's space in the stadium. we return false if there isn't
		if (crowd.size() >= homeTeam.getStadium().getCapacity())
			throw new FanException("Can't add fan to a full stadium");
		
		// adding fan to crowd
		if (fan.getFavoriteTeam() != null && fan.getFavoriteTeam().equals(homeTeam))
			crowd.put(fan, true); 		// fan's favTeam is the homeTeam
		else
			crowd.put(fan, false);

		return true; 
	}

	/**
	 * This method removes a fan from the crowd array 
	 * only if the fan exists there
	 * 
	 * @param fan to be removed
	 * @return true if the fan was deleted, false otherwise
	 */
	public boolean removeFan(Customer fan) {
		//TODO
		// input check
		if (fan == null)
			return false;

		// checking if fan exists
		if (!crowd.containsKey(fan))
			return false;

		crowd.remove(fan);
		return true; 
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
		Match other = (Match) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		DateFormat dft = new SimpleDateFormat("dd/MM/yyyy;HH:mm");
		return "Match | id: " + id + ", start date: " + dft.format(startDateTime) + ", duration: " + duration + ", extra time: "
				+ extraTime + ", level: " + level + ", teams: " + homeTeam.getName() + " vs " + awayTeam.getName() 
				+ ", score: " + homeTeamScore + " - " + awayTeamScore;
	}

	public String showSumDetails() {
		DateFormat dft = new SimpleDateFormat("dd/MM/yyyy;HH:mm");
		return + id +  " | " + homeTeam.getName() + " vs " + awayTeam.getName()
		+ " | start date: " + dft.format(startDateTime) + 
		 " | stadium: " + homeTeam.getStadium().getId() + " - " + homeTeam.getStadium().getName();
	}
	
}
