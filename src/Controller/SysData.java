
package Controller;

import Model.*;
import utils.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.*;


import Exceptions.MatchException;

import Exceptions.SubException;

/**
 * This SysData object ~ represents the class system
 * 
 * @author Java Course Team 2018 - Shai Gutman
 * @author University Of Haifa - Israel
 * @author ID: 205791056
 * @author ID: 312581605
 */
@SuppressWarnings("rawtypes")
public class SysData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// -------------------------------Class Members------------------------------
	private static SysData instance;
	private User admin;
	private HashMap<String, User> users;
	private HashMap<Integer, Coach> coaches;
	private HashMap<Integer, Receptionist> receptionists;
	private HashMap<Integer, Player> players;
	private HashMap<Integer, Stadium> stadiums;
	private HashMap<Integer, Team> teams;
	private HashMap<String, Customer> customers;
	private HashMap<Integer, Match> matches;
	private HashSet<Trophy> trophies;
	private HashSet<MatchPosition> matchPositions;

	// -------------------------------Constructors------------------------------
	private SysData() {
		users = new HashMap<>();
		coaches = new HashMap<>();
		receptionists = new HashMap<>();
		players = new HashMap<>();
		stadiums = new HashMap<>();
		teams = new HashMap<>();
		customers = new HashMap<>();
		matches = new HashMap<>();
		trophies = new HashSet<>();
		matchPositions = new HashSet<>();
		setAdmin();
	}

	// -----------------------------------------Getters--------------------------------------
	public HashMap<String, User> getUsers() {
		return users;
	}

	public HashMap<Integer, Coach> getCoachs() {
		return coaches;
	}

	public HashMap<Integer, Receptionist> getReceptionists() {
		return receptionists;
	}

	public HashMap<Integer, Player> getPlayers() {
		return players;
	}

	public HashMap<Integer, Team> getTeams() {
		return teams;
	}

	public HashMap<Integer, Stadium> getStadiums() {
		return stadiums;
	}

	public HashMap<String, Customer> getCustomers() {
		return customers;
	}

	public HashMap<Integer, Match> getMatchs() {
		return matches;
	}

	public HashSet<Trophy> getTrophies() {
		return trophies;
	}
	
	public HashSet<MatchPosition> getMatchPositions() {
		return matchPositions;
	}

	public static SysData getInstance() {
		if (instance == null)
			instance = new SysData();
		return instance;
	}

	public User getAdmin() {
		return admin;
	}

	public void setAdmin() {
		if (admin == null) {
			admin = new User("Admin", E_UserType.ADMIN);
			users.put(admin.getUsername(), admin);
		}
	}

	// ------------------------------- Serialize & Deserialize
	// ------------------------------
	public boolean serialize() throws IOException {
		FileOutputStream fileOut = null;
		ObjectOutputStream out = null;
		try {
			fileOut = new FileOutputStream("DataBase.ser");
			out = new ObjectOutputStream(fileOut);
			out.writeObject(instance);
			System.out.println("Serialized data is saved in the project's folder as DataBase.ser");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (out != null)
				out.close();
			if (fileOut != null)
				fileOut.close();
		}
	}

	public static SysData deserialize() throws IOException {
		//SysData recoveredData = null;
		FileInputStream fileIn = null;
		ObjectInputStream in = null;
		try {
			fileIn = new FileInputStream("DataBase.ser");
			in = new ObjectInputStream(fileIn);
			/*
			 * recoveredData = (SysData) in.readObject(); return recoveredData;
			 */
			instance = (SysData) in.readObject();
			return instance;
		} catch (IOException e) {
			System.out.println("The file DataBase.ser wasn't found");
			// when the system is opened for the first time
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (in != null)
				in.close();
			if (fileIn != null)
				fileIn.close();
		}
	}

	// -------------------------------Add && Remove
	// Methods------------------------------
	/**
	 * This method adds a new stadium to the JavaLeague only if all the parameters
	 * are valid and the stadium doesn't already exist in the system
	 * 
	 * @param id          of stadium
	 * @param name        of stadium
	 * @param capacity    of stadium
	 * @param city        of stadium
	 * @param street      of stadium
	 * @param houseNumber of stadium
	 * @param phoneNumber of stadium
	 * @return true if the stadium was added successfully, false otherwise
	 */
	public boolean addStadium(int id, String name, int capacity, E_Cities city, String street, int houseNumber,
			String[] phoneNumber) {
		// TODO
		// input check
		if (id <= 0 || name == null || capacity <= 0 || city == null || street == null || houseNumber <= 0
				|| phoneNumber == null)
			return false;

		// checking if stadium exists
		if (stadiums.containsKey(id))
			return false;

		// ***** adding stadium ***** //
		Address address = new Address(city, street, houseNumber, phoneNumber);
		stadiums.put(id, new Stadium(id, name, address, capacity));

		return true;
	} // ~ END OF addStadium

	/**
	 * This method adds a new team to the JavaLeague only if all the parameters are
	 * valid and the team doesn't already exist in the system IMPORTANT: In order to
	 * add a new team, the team must be added to it's stadium first. Don't forget to
	 * roll-back :)
	 * 
	 * @param id        of team
	 * @param name      of team
	 * @param value     of team
	 * @param level     of team
	 * @param stadiumId of team's stadium
	 * @return true if the team was added successfully, false otherwise
	 */
	public boolean addTeam(int id, String name, int value, E_Levels level, int stadiumId) {
		// TODO
		// input check
		if (id <= 0 || name == null || value < 0 || level == null || stadiumId <= 0)
			return false;

		// checking if stadium and team exist
		if (!stadiums.containsKey(stadiumId) || teams.containsKey(id))
			return false;

		Stadium stadium = stadiums.get(stadiumId);
		Team team = new Team(id, name, value, level, stadium);

		// ***** adding team to relevant places ***** //
		if (stadium.addTeam(team)) {
			if (teams.put(id, team) == null) {
				team.setStadium(stadium);
				return true;
			}

			else if (stadium.removeTeam(team)) // roll-back
				return false;
		}

		return false;

	} // ~ END OF addTeam

	/**
	 * This method adds a new coach to the JavaLeague only if all the parameters are
	 * valid and the coach doesn't already exist in the system
	 * 
	 * @param id               of coach
	 * @param firstName        of coach
	 * @param lastName         of coach
	 * @param birthdate        of coach
	 * @param startWorkingDate of coach
	 * @param level            of coach
	 * @param address          of coach
	 * @return true if the coach was added successfully, false otherwise
	 */
	public boolean addCoach(int id, String firstName, String lastName, Date birthdate, Date startWorkingDate,
			E_Levels level, Address address) {
		// TODO
		// input check
		if (id <= 0 || firstName == null || lastName == null || birthdate == null || startWorkingDate == null
				|| level == null || address == null)
			return false;

		Date today = new Date();

		// started working after today OR before he was born
		if (startWorkingDate.after(today) || birthdate.after(startWorkingDate))
			return false;

		// checking if coach exists
		if (coaches.containsKey(id))
			return false;

		// ***** adding coach ***** //
		Coach coach = new Coach(id, firstName, lastName, birthdate, startWorkingDate, address, level);
		coaches.put(id, coach);

		userCreator(coach);

		return true;

	} // ~ END OF addCoach

	/**
	 * This method adds a new player to the JavaLeague only if all the parameters
	 * are valid and the player doesn't already exist in the system
	 * 
	 * @param id               of player
	 * @param firstName        of player
	 * @param lastName         of player
	 * @param birthdate        of player
	 * @param startWorkingDate of player
	 * @param level            of player
	 * @param value            of player
	 * @param isRightLegKicker whether player kicks with right leg
	 * @param position         of player
	 * @param address          of player
	 * @return true if the player was added successfully, false otherwise
	 */
	public boolean addPlayer(int id, String firstName, String lastName, Date birthdate, Date startWorkingDate,
			E_Levels level, long value, Boolean isRightLegKicker, E_Position position, Address address) {
		// TODO
		// input check
		if (id <= 0 || firstName == null || lastName == null || birthdate == null || startWorkingDate == null
				|| level == null || value <= 0 || address == null || isRightLegKicker == null || position == null)
			return false;

		Date today = new Date();

		// started working after today OR before he was born
		if (startWorkingDate.after(today) || birthdate.after(startWorkingDate))
			return false;

		// checking if player exists
		if (players.containsKey(id))
			return false;

		// ***** adding player ***** //
		Player player = new Player(id, firstName, lastName, birthdate, startWorkingDate, address, level, value,
				isRightLegKicker, position);
		players.put(id, player);

		return true;
	} // ~ END OF addPlayer

	/**
	 * This method adds a new receptionist to the JavaLeague only if all the
	 * parameters are valid and the receptionist doesn't already exist in the system
	 * 
	 * @param id               of receptionist
	 * @param firstName        of receptionist
	 * @param lastName         of receptionist
	 * @param birthdate        of receptionist
	 * @param startWorkingDate of receptionist
	 * @param stadiumId        to be added to receptionist
	 * @param address          of receptionist
	 * @return true if the receptionist was added successfully, false otherwise
	 */
	public boolean addReceptionist(int id, String firstName, String lastName, Date birthdate, Date startWorkingDate,
			Address address) {
		// TODO
		// input check
		if (id <= 0 || firstName == null || lastName == null || birthdate == null || startWorkingDate == null
				|| address == null)
			return false;

		Date today = new Date();

		// started working after today OR before he was born
		if (startWorkingDate.after(today) || birthdate.after(startWorkingDate))
			return false;

		// checking if receptionist exists
		if (receptionists.containsKey(id))
			return false;

		// ***** adding receptionist ***** //
		Receptionist receptionist = new Receptionist(id, firstName, lastName, birthdate, startWorkingDate, address);
		receptionists.put(id, receptionist);

		userCreator(receptionist);
		return true;
	} // ~ END OF addReceptionist

	/**
	 * This method adds a new customer to the JavaLeague only if all the parameters
	 * are valid and the customer doesn't already exist in the system IMPORTANT:
	 * Every customer has a favorite team, if the team doesn't exist in the system
	 * the customer cannot be added :(
	 * 
	 * @param id             of customer
	 * @param firstName      of customer
	 * @param lastName       of customer
	 * @param birthDate      of customer
	 * @param level          of customer
	 * @param email          of customer
	 * @param favoriteTeamId of customer
	 * @param address        of customer
	 * @return true if the customer was added successfully, false otherwise
	 */
	public boolean addCustomer(String id, String firstName, String lastName, Date birthDate, E_Levels level, URL email,
			int favoriteTeamId, Address address) {
		// TODO
		// input check
		if (id == null || firstName == null || lastName == null || birthDate == null || level == null || email == null
				|| favoriteTeamId <= 0 || address == null || Customer.checkId(id) == "0")
			return false;

		Date today = new Date();

		// can't be born after today
		if (birthDate.after(today))
			return false;

		// team doesn't exist in the system OR customer already exists
		if (!teams.containsKey(favoriteTeamId) || customers.containsKey(id))
			return false;

		// ***** adding customer ***** //
		Team team = teams.get(favoriteTeamId);
		Customer customer = new Customer(id, firstName, lastName, birthDate, level, email, address, team);
		customers.put(id, customer);

		userCreator(customer);
		return true;
	}// ~ END OF addCustomer

	/**
	 * This method adds an existing coach to an existing team of the JavaLeague only
	 * if all the parameters are valid and the both coach & team already exist in
	 * the system IMPORTANT: If the given coach already belongs to another team,
	 * transfer the coach to the given team.
	 * 
	 * @param coachId to be added to team
	 * @param teamId  to be added to coach
	 * @return true if the coach was added successfully to team, false otherwise
	 */
	public boolean addCoachToTeam(int coachId, int teamId) {
		// TODO
		// checking if both ids' exist in the system
		if (!coaches.containsKey(coachId) || !teams.containsKey(teamId))
			return false;

		// getting data
		Coach coach = coaches.get(coachId);
		Team team = teams.get(teamId);

		// ***** adding coach to team ***** //
		if (coach.getCurrentTeam() == null) {
			if (team.registerCoach(coach)) {
				coach.setCurrentTeam(team);
				return true;
			}
		}
		// transferring the coach
		else if (coach.transferTo(team))
			return true;

		return false;
	}// ~ END OF addCoachToTeam

	/**
	 * This method adds an existing player to an existing team of the JavaLeague
	 * only if all the parameters are valid and the both player & team already exist
	 * in the system IMPORTANT: If the given player already belongs to another team,
	 * transfer the player to the given team.
	 * 
	 * @param playerId to be added to team
	 * @param teamId   to be added to player
	 * @return true if the player was added successfully to team, false otherwise
	 */
	public boolean addPlayerToTeam(int playerId, int teamId) {
		// TODO
		// checking if both ids' exist in the system
		if (!players.containsKey(playerId) || !teams.containsKey(teamId))
			return false;

		// getting data
		Player player = players.get(playerId);
		Team team = teams.get(teamId);

		// ***** adding player to team ***** //
		if (player.getCurrentTeam() == null) {
			if (team.addPlayer(player)) {
				player.setCurrentTeam(team);
				return true;
			}
		}

		// transferring the player
		else if (player.transferTo(team))
			return true;

		return false;
	}// ~ END OF addCoachToTeam

	/**
	 * This method adds an existing player to an existing team first players only if
	 * all the parameters are valid and the both player & team already exist in the
	 * system
	 * 
	 * @param playerId to be added to firstTeam
	 * @param teamId   to add players to the team's firstTeam
	 * @return true if the player was added successfully to team first players,
	 *         false otherwise
	 */
	public boolean addPlayerToTeamFirstPlayers(int playerId, int teamId) {
		// TODO

		// checking if both ids' exist in the system
		if (!players.containsKey(playerId) || !teams.containsKey(teamId))
			return false;

		// getting data
		Player player = players.get(playerId);
		Team team = teams.get(teamId);

		return team.addPlayerToFirstTeam(player);
	}// ~ END OF addPlayerToTeamFirstPlayers

	/**
	 * This method adds an existing receptionist to an existing stadium of the
	 * JavaLeague only if all the parameters are valid and the both receptionis &
	 * stadium already exist in the system
	 * 
	 * @param receptionistId to be added to stadium
	 * @param stadiumId      to be added to receptionist
	 * @return true if the receptionist was added successfully to stadium, false
	 *         otherwise
	 */
	public boolean addReceptionistToStadium(int receptionistId, int stadiumId) {
		// TODO
		// checking if both ids' exist in the system
		if (!stadiums.containsKey(stadiumId) || !receptionists.containsKey(receptionistId))
			return false;

		// getting data
		Receptionist rec = receptionists.get(receptionistId);
		Stadium stadium = stadiums.get(stadiumId);

		// ***** adding receptionist to stadium ***** //
		if (stadium.addReceptionist(rec)) {

			if (rec.getWorkingStadium() == null) {
				rec.setWorkingStadium(stadium);
				return true;
			}

			// transferring receptionist to stadium
			else if (rec.getWorkingStadium().removeReceptionist(rec)) {
				rec.setWorkingStadium(stadium);
				return true;
			}
		}

		return false;
	}// ~ END OF addReceptionistToStadium

	/**
	 * This method adds a new subscription to an existing customer of the JavaLeague
	 * only if all the parameters are valid and the customer already exist in the
	 * system IMPORTANT: Every subscription was sold by a receptionist, hence it's
	 * very important that the receprionist belongs to a stadium. ALSO IMPORTANT:
	 * Subscription must be added to all the relevant arrays, Don't forget to
	 * roll-back :)
	 * 
	 * @param subscriptionId to be added to customer
	 * @param customerId     to be added to sub
	 * @param receptionistId that sold the sub
	 * @param period         of sub
	 * @param startDate      of sub
	 * @return true if the suscription was added successfully to customer, false
	 *         otherwise
	 */
	public boolean addSubscriptionToCustomer(int subscriptionId, String customerId, int receptionistId,
			E_Periods period, Date startDate) {
		// TODO
		// input check
		if (subscriptionId <= 0 || Customer.checkId(customerId) == "0" || receptionistId <= 0 || period == null
				|| startDate == null)
			return false;

		// checking if both ids' exist in the system
		if (!customers.containsKey(customerId) || !receptionists.containsKey(receptionistId))
			return false;

		// getting receptionist data
		Receptionist rec = receptionists.get(receptionistId);

		// receptionist is unemployed
		if (rec.getWorkingStadium() == null)
			throw new SubException("Receptionist does not belong to a Stadium");

		// getting customer data
		Customer customer = customers.get(customerId);

		// creating new subscription
		Subscription sub = new Subscription(subscriptionId, customer, rec, period, startDate);

		// ***** adding subscription to relevant places ***** //
		if (customer.addSubscription(sub)) {
			if (rec.addSubscription(sub))
				return true;
			else
				customer.removeSubscription(sub); // roll-back

		}

		return false;
	}// ~ END OF addSubscriptionToCustomer

	/**
	 * This method adds a new match to the JavaLeague only if all the parameters are
	 * valid and both teams already exist in the system VERY IMPORTENT: Match must
	 * be added to all the relevant arrays, Don't forget to roll-back :)
	 * 
	 * @param id            of match
	 * @param dateTime      of match
	 * @param extraTime     of match
	 * @param homeTeamId    that plays in the match
	 * @param awayTeamId    that plays in the match
	 * @param homeTeamScore score in match
	 * @param awayTeamScore score in match
	 * @return true if the match was added successfully, false otherwise
	 */
	public boolean addMatch(int id, Date dateTime, int extraTime, int homeTeamId, int awayTeamId, int homeTeamScore,
			int awayTeamScore) {
		// TODO
		if (id <= 0 || dateTime == null || extraTime < 0 || homeTeamId <= 0 || awayTeamId <= 0 || homeTeamScore < 0
				|| awayTeamScore < 0)
			return false;

		// checking if all ids' exist in the system
		if (!teams.containsKey(homeTeamId) || !teams.containsKey(awayTeamId) || matches.containsKey(id))
			return false;

		// getting teams data
		Team home = teams.get(homeTeamId); // getting home data
		Team away = teams.get(awayTeamId); // getting away data



		if (home.getStadium() == null)
			throw new MatchException(home.getName() + " doesn't belong to a stadium, so it can't participate in a match");

		if (away.getStadium() == null)
			throw new MatchException(away.getName() + " doesn't belong to a stadium, so it can't participate in a match");



		// creating new match
		Match match = new Match(id, dateTime, extraTime, home, homeTeamScore, away, awayTeamScore);

		// ***** adding match to relevant places ***** //
		// adding the match to home team
		if (home.addMatch(match)) {

			// adding the match to away team
			try {
				if (away.addMatch(match)) {

					// adding match to the stadium of home team
					if (home.getStadium() != null && home.getStadium().addMatch(match)) {
						matches.put(id, match);
						return true;
					} else { // roll-back
						home.removeMatch(match);
						away.removeMatch(match);
					}
				} else // roll-back
					home.removeMatch(match);

				// handling overlap exception for adMatch
			} catch (MatchException e) {

				/*
				 * I made the message thrown from every object contain the word "team" or the
				 * word "stadium"
				 * 
				 * this way i know from where is the exception thrown and how to handle it.
				 * 
				 * 
				 * from here the exception has two places to go.
				 * 
				 * case we only run the system the exception will be handled in the main.
				 * 
				 * case the command comes from the UI it will be handled there
				 */

				// case the message is thrown from away team
				if (e.getMessage().contains("team")) {
					home.removeMatch(match);
					throw e;

					// case the message is thrown from stadium
				} else if (e.getMessage().contains("stadium")) {
					home.removeMatch(match);
					away.removeMatch(match);
					throw e;

				}

			}

		}

		return false;
	}// ~ END OF addMatch

	/**
	 * This method adds an existing customer to an existing match of the JavaLeague
	 * only if all the parameters are valid and both customer & match already exist
	 * in the system VERY IMPORTANT: Customer must be added to all the relevant
	 * arrays, Don't forget to roll-back :)
	 * 
	 * @param customerId to be added to match
	 * @param matchId    to be added to customer
	 * @return true if the customer was added successfully to match, false otherwise
	 */
	public boolean addCustomerToMatch(String customerId, int matchId) {
		// TODO

		// checking if both ids' exist in the system
		if (!matches.containsKey(matchId) || !customers.containsKey(customerId))
			return false;

		// getting data
		Customer customer = customers.get(customerId);
		Match match = matches.get(matchId);

		// ***** adding customer to match ***** //
		if (match.addFan(customer)) {

			try {

				if (customer.addMatch(match))
					return true;
				else
					match.removeFan(customer); // roll-back

			} catch (Exception e) {
				match.removeFan(customer); // roll-back
				throw e;
			}
		}

		return false;
	} // ~ END OF addCustomerToMatch

	/**
	 * This method removes an existing subscription from its customer only if all
	 * the parameters are valid and the subscription already exist in the system
	 * 
	 * @param subscriptionId to be removed from customer
	 * @return true if sub was removed successfully. false otherwise
	 */
	public boolean removeSubscription(int subscriptionId) {
		// TODO
		// input check
		if (subscriptionId <= 0)
			return false;

		// creating subscription
		Subscription sub = new Subscription(subscriptionId);

		for (Map.Entry<String, Customer> entry : customers.entrySet()) {
			if (entry != null)
				for (Subscription temp : entry.getValue().getSubscriptions()) {
					if (temp != null)
						// checking if subscription exists and relevant
						if (sub.equals(temp)) {
							entry.getValue().removeSubscription(temp);
							return temp.getReceptionist().removeSubscription(temp);
						}
				}
		}

		return false;
	}// ~ END OF removeSubscription

	/**
	 * This method adds a Trophy to the system only if all the parameters are valid
	 * and the Trophy does not already exist in the system
	 * 
	 * @param                   <T> either stadium, team, coach or player
	 * @param owner             of trophy
	 * @param trophy            to be added
	 * @param trophyWinningDate of tophy
	 * @return true if trophy was added successfully
	 */
	public <T> boolean addTrophy(E_Trophy trophy, T owner, Date trophyWinningDate) {
		// TODO
		// input check
		if (trophy == null || owner == null || trophyWinningDate == null)
			return false;

		// can't get trophy before player/coach started working
		if (owner instanceof Employee && ((Employee) owner).getStartWorkingDate().after(trophyWinningDate))
			return false;

		Trophy<T> t = new Trophy<T>(trophy, owner, trophyWinningDate);

		// checking if trophy exists
		if (trophies.contains(t))
			return false;

		// checking if trophy is relevant to owner
		if (owner instanceof Stadium && t.getTrophy().equals(E_Trophy.STADIUM_OF_THE_YEAR)
				|| owner instanceof Player && t.getTrophy().equals(E_Trophy.PLAYER_OF_THE_YEAR)
				|| owner instanceof Coach && t.getTrophy().equals(E_Trophy.COACH_OF_THE_YEAR)
				|| owner instanceof Team && t.getTrophy().equals(E_Trophy.TEAM_OF_THE_YEAR)) {
			return trophies.add(t);

		}

		return false;
	}// ~ END OF addTrophy
	// -------------------------------Queries------------------------------
	// ===================================================
	// HW_2_Queries
	// ===================================================

	/**
	 * This query finds the "Super Play Maker" player, a "Super Play Maker" player
	 * is the player that has the largest value, is a right leg kicker, is in
	 * MIDFIELDER position and is in a team's first players. if there are more than
	 * one player the method returns the first player
	 * 
	 * @param teamId to get its SuperPlayerMaker
	 * @return player object if found, null otherwise
	 */
	public Player getSuperPlayerMaker(int teamId) {
		// checking if team exists
		if (!teams.containsKey(teamId))
			return null;

		// getting team data
		Team team = teams.get(teamId);

		Player superPlayer = null;


		for (Map.Entry<Player, Boolean> temp : team.getPlayers().entrySet())
			// if the player meets the requirements
			if (temp.getValue() && temp.getKey().getPosition() != null && temp.getKey().getPosition().equals(E_Position.MIDFIELDER)
			&& temp.getKey().isRightLegKicker()) {

				/*
				 * if it's the first iteration OR the max player has lesser value than current
				 * player
				 */
				if (superPlayer == null || superPlayer.getValue() < temp.getKey().getValue())
					superPlayer = temp.getKey();
			}



		return superPlayer;
	}

	/**
	 * This query returns an array with the "Super Play Maker" player from all the
	 * teams, a "Super Play Maker" player is as defined in the first query. the
	 * returned ArrayList must be sorted by player's value.
	 * 
	 * @return player array if found, null otherwise
	 */
	public ArrayList<Player> getAllSuperPlayerMakers() {
		// TODO
		// creating a new array
		TreeSet<Player> superPlayers = new TreeSet<Player>(new PlayerValueComparator());

		for (Map.Entry<Integer, Team> entry : teams.entrySet()) {
			if (entry != null)
				// adding player makers to array
				superPlayers.add(getSuperPlayerMaker(entry.getKey()));
		}

		/*
		 * // we return null if array is empty if (superPlayers.isEmpty()) return null;
		 */

		// sorting the players by value
		ArrayList<Player> players = new ArrayList<>();
		players.addAll(superPlayers);
		return players;
	}

	/**
	 * This query returns the most popular position. Most popular position is the
	 * type that belongs to the highest number of players.
	 * 
	 * @return position object if found, null otherwise
	 */
	public E_Position getTheMostPopularPosition() {

		E_Position popularPos = null;
		int max = 0;

		for (E_Position p : E_Position.values()) {
			if (p != null) {

				// counting amount of players in this position
				int posCounter = 0;

				for (Map.Entry<Integer, Player> entry : players.entrySet()) {
					if (entry != null) {
						// if the current positions are equal, we count them
						if (p.equals(entry.getValue().getPosition()))
							posCounter++;
					}
				}

				// getting the max position (most popular)
				if (posCounter > max) {
					popularPos = p;
					max = posCounter;
				}
			}
		}

		return popularPos;
	}

	/**
	 * This query returns the most favored team. Most favored team is the team that
	 * has the highest number of customers that the team is their favorite team.
	 * 
	 * @return team object if found, null otherwise
	 */
	public Team getTheMostFavoredTeam() {
		// TODO
		Team mostFav = null;
		int max = 0;

		for (Map.Entry<Integer, Team> team : teams.entrySet()) {
			if (team != null) {

				// counter that counts the amount of fans that support the team
				int teamCounter = 0;

				for (Map.Entry<String, Customer> entry : customers.entrySet()) {
					if (entry != null) {
						// if the current teams are equal, we count them
						if (team.getValue().equals(entry.getValue().getFavoriteTeam()))
							teamCounter++;
					}
				}

				// getting the most favorite team
				if (teamCounter > max) {
					mostFav = team.getValue();
					max = teamCounter;
				}
			}
		}

		return mostFav;

	}

	/**
	 * This query finds the most active city of a given stadium, the most active
	 * city is the city with the highest number of employees that are working in the
	 * given stadium.
	 * 
	 * @param stadiumId to get its most active city
	 * @return city object, null otherwise
	 */
	public E_Cities getTheMostActiveCity(int stadiumId) {
		// TODO
		// checking if stadium exists
		if (!stadiums.containsKey(stadiumId))
			return null;

		// getting stadium data
		Stadium stadium = stadiums.get(stadiumId);

		E_Cities[] cities = E_Cities.values();
		int[] buckets = new int[cities.length];

		// checking receptionists
		for (Receptionist rec : stadium.getReceptionists()) {
			boolean flag = true;
			for (int i = 0; i < cities.length && flag; i++)
				if (rec != null && cities[i].equals(rec.getAddress().getCity())) {
					buckets[i]++;
					flag = false;
				}
		}

		// checking coaches
		for (Coach coach : coaches.values()) {
			boolean flag = true;
			if (coach.getCurrentTeam() != null && stadium.equals(coach.getCurrentTeam().getStadium()))
				for (int i = 0; i < cities.length && flag; i++)
					if (cities[i] != null && cities[i].equals(coach.getAddress().getCity())) {
						buckets[i]++;
						flag = false;
					}
		}

		// checking players
		for (Player player : players.values()) {
			boolean flag = true;
			if (player.getCurrentTeam() != null && stadium.equals(player.getCurrentTeam().getStadium()))
				for (int i = 0; i < cities.length && flag; i++)
					if (cities[i] != null && cities[i].equals(player.getAddress().getCity())) {
						buckets[i]++;
						flag = false;
					}
		}

		// getting the most active city
		E_Cities city = null;
		int maxCount = 0;

		for (int i = 0; i < buckets.length; i++)
			if (buckets[i] >= maxCount) {
				maxCount = buckets[i];
				city = cities[i];
			}
		if(maxCount == 0)
			return null;

		return city;
	}

	/**
	 * This query returns the entity that has won the most trophies.
	 * 
	 * @see winnerFinder
	 * @return object if found. null otherwise
	 */
	public Object getEntityWithMostTrophies() {
		// TODO
		Object winner = null;
		Object[] tempWin = { 0, null };
		Integer max = 0;

		// finding the winner in teams'
		tempWin = winnerFinder(teams);
		if ((Integer) tempWin[0] > max) {
			winner = tempWin[1];
			max = (Integer) tempWin[0];
		}

		// finding the winner in stadiums'
		tempWin = winnerFinder(stadiums);
		if ((Integer) tempWin[0] > max) {
			winner = tempWin[1];
			max = (Integer) tempWin[0];
		}

		// finding the winner in coaches'
		tempWin = winnerFinder(coaches);
		if ((Integer) tempWin[0] > max) {
			winner = tempWin[1];
			max = (Integer) tempWin[0];
		}

		// finding the winner in players'
		tempWin = winnerFinder(players);
		if ((Integer) tempWin[0] > max) {
			winner = tempWin[1];
			max = (Integer) tempWin[0];
		}

		// returning the winner
		return winner;
	}

	/**
	 * This query returns the home team of the match that contained the largest
	 * crowd of customers that their favorite team is the match's home team
	 * 
	 * @return team object if found, null otherwise
	 */
	public Team getTeamWithLargestHomeCrowd() {
		// TODO
		Team largest = null;
		int max = 0;

		for (Map.Entry<Integer, Match> match : matches.entrySet()) {
			if (match != null) {

				// counter that counts the amount of fans that support the team
				int teamCounter = 0;

				for (Customer cus : match.getValue().getCrowd().keySet()) {
					if (cus != null) {
						// if the current teams are equal, we count them
						if (match.getValue().getHomeTeam().equals(cus.getFavoriteTeam()))
							teamCounter++;
					}
				}

				// getting the largest team
				if (teamCounter > max) {
					largest = match.getValue().getHomeTeam();
					max = teamCounter;
				}
			}
		}

		// returning the largest team
		return largest;

	}

	/**
	 * This query returns all the customers that have a subscription to stadiun1 or
	 * stadium2 but not to both
	 * 
	 * @param stad1 id of 1st stadium
	 * @param stad2 id of 2nd stadium
	 * @return array of customers if customers were found, empty list otherwise
	 */
	public ArrayList<Customer> getCustomersStadium1XORStadium2(int stad1, int stad2) {
		// TODO
		ArrayList<Customer> customerXor = new ArrayList<>();

		// checking if stadiums exist
		if (!stadiums.containsKey(stad1) || !stadiums.containsKey(stad2))
			return null;

		// checking if stadiums are the same stadium
		if (stad1 == stad2)
			return null;

		// getting stadiums data
		Stadium st1 = stadiums.get(stad1);
		Stadium st2 = stadiums.get(stad2);

		// checking customers
		for (Map.Entry<String, Customer> entry : customers.entrySet()) {
			boolean isFound1 = false, isFound2 = false;

			if (entry != null) {
				// checking customer's subs
				for (Subscription sub : entry.getValue().getSubscriptions()) {
					if (sub != null) {

						// checking if stadium1 is found
						if (sub.getReceptionist().getWorkingStadium().equals(st1))
							isFound1 = true;

						// checking if stadium2 is found
						else if (sub.getReceptionist().getWorkingStadium().equals(st2))
							isFound2 = true;
					}
				}
			}

			// adding if it's either stadium1 or stadium2, but not both
			if ((isFound1 && !isFound2) || (isFound2 && !isFound1))
				customerXor.add(entry.getValue());
		}

		// returning customerXor array
		return customerXor;
	}

	/**
	 * This query returns all the first players of the "Best Home Team" "Best Home
	 * Team" is the team with the highest Home Away winning rate returned players
	 * must be sorted by their value.
	 * 
	 * @return array of players if players were found, empty list otherwise
	 */
	public ArrayList<Player> getFirstPlayersOfBestHomeTeam() {
		// TODO
		double max = 0;
		Team team = null;

		// finding the best team
		for (Map.Entry<Integer, Team> entry : teams.entrySet()) {
			if (entry != null) {
				if (entry.getValue() != null && entry.getValue().getHomeAwayWinningsRate() > max) {
					team = entry.getValue();
					max = team.getHomeAwayWinningsRate();
				}
			}
		}

		// creating the players array
		ArrayList<Player> theBest = new ArrayList<Player>();

		// finding the firstTeam players of the best team
		if (team != null)
			for (Map.Entry<Player, Boolean> entry : team.getPlayers().entrySet())
				if (entry.getKey() != null && entry.getValue())
					theBest.add(entry.getKey());

		// sorting the list if it's not empty
		if (!theBest.isEmpty())
			theBest.sort(new PlayerValueComparator());

		return theBest;
	}

	// -------------------------------Helper Methods------------------------------
	/**
	 * This method finds the temporary entity that has the most trophies
	 * 
	 * @param winners to be searched the winner in
	 * @return the winner and amount of wins
	 */
	private <K, V> Object[] winnerFinder(Map<K, V> winners) {
		Object[] tempWin = { 0, null };

		for (K key : winners.keySet()) {
			Integer counter = 0;

			for (Trophy<?> t : trophies) {
				// if we find the owner in the map, we count it
				if (t.getOwner() != null && t.getOwner().equals(winners.get(key)))
					counter++;
			}

			// getting the winner
			if (counter > (Integer) tempWin[0]) {
				tempWin[0] = counter;
				tempWin[1] = winners.get(key);
			}
		}

		return tempWin;
	}

	/**
	 * This method creates new users in the system
	 * 
	 * @param person to become a user
	 */
	public void userCreator(Object person) {
		User user = null;
		if (person instanceof Customer)
			user = new User(((Customer) person).getId(), E_UserType.CUSTOMER);

		if (person instanceof Receptionist) {
			Integer username = new Integer(((Receptionist) person).getId());
			user = new User(username.toString(), E_UserType.RECEPTIONIST);
		}
		if (person instanceof Coach) {
			Integer username = new Integer(((Coach) person).getId());
			user = new User(username.toString(), E_UserType.COACH);
		}
		if (user != null)
			users.put(user.getUsername(), user);
		else
			System.out.println("Can't create user!");
	}

	/**
	 * This method validates users in the system
	 * 
	 * @param username to check
	 * @param pw       to check
	 * @return user type
	 */
	public E_UserType validateUser(String userName, String password) {
		if (!users.containsKey(userName) || !users.get(userName).getPw().equals(password))
			return null;

		return users.get(userName).getUserType();
	}

	// ---------------- remove methods, from the system! ------------------
	/**
	 * This method removes stadium in the system
	 * 
	 * @param stadiumId to be removed
	 * @param           true if stadium removed successfully, false otherwise
	 */
	public boolean removeStadium(Integer id) {
		// input check
		if (!stadiums.containsKey(id))
			return false;

		Stadium stadium = stadiums.get(id);

		HashSet<Match> tempMatches = new HashSet<>(stadium.getMatches());
		
		for (Match temp : tempMatches)
			removeMatch(temp.getId());

		stadium.getMatches().clear();

		for (Receptionist r : stadium.getReceptionists())
			r.setWorkingStadium(null);



		for (Team t : teams.values())
			stadium.removeTeam(t);


		removeTrophyHelper(stadium);



		// deletes the key and the object
		stadiums.remove(id, stadiums.get(id));

		return true;
	}

	/**
	 * This method removes coach in the system
	 * 
	 * @param coachId to be removed * @param true if coach removed successfully,
	 *                false otherwise
	 */
	public boolean removeCoach(Integer id) {
		// input check
		if (!coaches.containsKey(id))
			return false;

		Coach coach = coaches.get(id);

		if (coach.getCurrentTeam() != null)
			coach.getCurrentTeam().setCoach(null);


		removeTrophyHelper(coach);


		users.remove(id.toString());

		// deletes the key and the object
		coaches.remove(id, coaches.get(id));
		return true;
	}

	/**
	 * This method removes player in the system
	 * 
	 * @param playerId to be removed * @param true if player removed successfully,
	 *                 false otherwise
	 */
	public boolean removePlayer(Integer id) {
		// input check
		if (!players.containsKey(id))
			return false;

		Player player = players.get(id);

		for (Team t : teams.values())
			t.removePlayer(player);



		removeTrophyHelper(player);


		// deletes the key and the object
		players.remove(id, players.get(id));
		return true;
	}

	/**
	 * This method removes match in the system
	 * 
	 * @param matchId to be removed * @param true if match removed successfully,
	 *                false otherwise
	 */
	public boolean removeMatch(Integer id) {
		// input check
		if (!matches.containsKey(id))
			return false;

		Match match = matches.get(id);
		
		for (Team t : teams.values())
			t.removeMatch(match);

		match.getHomeTeam().getStadium().removeMatch(match);
		
		for (Customer c : customers.values())
			c.removeMatch(match);
		
		// deletes the key and the object
		matches.remove(id, matches.get(id));
		return true;
	}

	/**
	 * This method removes team in the system
	 * 
	 * @param teamId to be removed * @param true if team removed successfully, false
	 *               otherwise
	 */
	public boolean removeTeam(Integer id) {
		// input check
		if (!teams.containsKey(id))
			return false;

		Team team = teams.get(id);
		
		HashSet<Match> tempMatches = new HashSet<>(team.getMatches());
		
		for (Match m : tempMatches)
			removeMatch(m.getId());
		
		team.getMatches().clear();
		

		if (team.getStadium() != null) {
			team.getStadium().removeTeam(team);
			team.setStadium(null);
		}
		
		if (team.getCoach() != null) {
			team.registerCoach(team.getCoach());
		}
		
		for (Trophy temp : trophies) {
			if (temp.getOwner().equals(team))
				trophies.remove(temp);
		}

		removeTrophyHelper(team);
		
		for (Player temp : players.values())
			team.removePlayer(temp);

		// deletes the key and the object
		teams.remove(id, teams.get(id));
		return true;
	}

	/**
	 * This method removes receptionist in the system
	 * 
	 * @param receptionistId to be removed * @param true if receptionist removed
	 *                       successfully, false otherwise
	 */
	public boolean removeReceptionist(Integer id) {
		// input check
		if (!receptionists.containsKey(id))
			return false;

		Receptionist rec = receptionists.get(id);

		if (rec.getWorkingStadium() != null) {
			rec.getWorkingStadium().removeReceptionist(rec);
			
			if(!rec.getSubscriptions().isEmpty()) 
				for(Subscription temp : rec.getSubscriptions()) 
					removeSubscription(temp.getId());
				
			
		}
		
		
		// deletes the key and the object
		receptionists.remove(id, receptionists.get(id));
		

		users.remove(id.toString());

		return true;
	}

	/**
	 * This method removes customer in the system
	 * 
	 * @param customerId to be removed * @param true if customer removed
	 *                   successfully, false otherwise
	 */
	public boolean removeCustomer(String id) {
		// input check
		if (!customers.containsKey(id))
			return false;

		Customer cus = customers.get(id);

		for (Match m : matches.values())
			m.removeFan(cus);

		for (Subscription scus : cus.getSubscriptions()) {
			if (scus.getReceptionist() != null)
				for (Subscription srec : scus.getReceptionist().getSubscriptions()) {
					if (scus.equals(srec)) {
						scus.getReceptionist().getSubscriptions().remove(srec);
						break;
					}
				}
		}

		cus.getSubscriptions().clear();

		// deletes the key and the object
		customers.remove(id, customers.get(id));
		users.remove(id);

		return true;
	}
	/**
	 * This method removes trophy in the system
	 * 
	 * @param  trophy to be removed * @param true if  trophy removed successfully,
	 *                false otherwise
	 */
	public boolean removeTrophy(Trophy trophy) {

		if(trophy == null)
			return false;


		return trophies.remove(trophy);

	}
	/**
	 * Helps the remove methods to 
	 * remove there trophies from
	 * the system
	 * @param entity
	 */
	private <T> void removeTrophyHelper(T entity) {

		HashSet<Trophy> newTrophies =new HashSet<>();

		for (Trophy temp : trophies) {
			if (!temp.getOwner().equals(entity))
				newTrophies.add(temp);

		}

		trophies.clear();
		trophies = newTrophies;
	}
	// ====================== Match Position Methods ======================
	/**
	 * Adds a match position to the matchPositions Set, if it doesn't exist
	 * @param matchPosition
	 * @return true if added successfully, false otherwise
	 */
	public boolean addMatchPos(MatchPosition matchPosition) {
		if (matchPosition == null)
			return false;
		
		if (!doesMatchPositionExist(matchPosition.getMatch(), matchPosition.getTeam()))
			return matchPositions.add(matchPosition);
		return false;
	}
	/**
	 * Removes the given matchPosition from the matchPositions Set
	 * @param matchPosition
	 * @return true if removed successfully, false otherwise
	 */
	public boolean removeMatchPos(MatchPosition matchPosition) {
		if (matchPosition == null)
			return false;
		
		if (doesMatchPositionExist(matchPosition.getMatch(), matchPosition.getTeam()))
			return matchPositions.remove(matchPosition);
		return false;
	}
	/**
	 * Checking if match position exists in the matchPositions Set 
	 * @param match
	 * @param team
	 * @return true if exists, false otherwise
	 */
	public boolean doesMatchPositionExist(Match match, Team team) {
		if (match == null || team == null)
			return false;
		
		for (MatchPosition mp : matchPositions)
			if (match.equals(mp.getMatch()) && team.equals(mp.getTeam()))
				return true;
		return false;
	}
	
	/**
	 * Getting the match position data from the Set
	 * @param match
	 * @param team
	 * @return match position if exists, null otherwise
	 */
	public MatchPosition getMatchPosition(Match match, Team team) {
		if (match == null || team == null)
			return null;
		
		for (MatchPosition mp : matchPositions)
			if (match.equals(mp.getMatch()) && team.equals(mp.getTeam()))
				return mp;
		return null;
	}

}// ~ END OF Class SysData
