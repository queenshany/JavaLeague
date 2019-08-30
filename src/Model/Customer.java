package Model;

import java.io.Serializable;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import Exceptions.FanException;
import utils.Constants;
import utils.E_Levels;

/**
 * Class Customer ~ represent a single customer/fan of the league
 * 
 * @author Java Course Team 2018 - Shai Gutman
 * @author University Of Haifa - Israel
 * @author ID: 205791056
 */
public class Customer implements Comparable<Customer>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// -------------------------------Class Members------------------------------
	private String id;
	private String firstName;
	private String lastName;
	private Date birthdate; // Calendar can also be used here
	private E_Levels level;
	private URL email;
	private Address address;
	private Team favoriteTeam;
	private Set<Subscription> subscriptions;

	// -------------------------------Constructors------------------------------
	public Customer(String id, String firstName, String lastName, Date birthdate, E_Levels level,
			URL email, Address address, Team favoriteTeam) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.level = level;
		this.email = email;
		this.address = address;
		this.favoriteTeam = favoriteTeam;
		//TODO
		subscriptions = new HashSet<>();
	}

	public Customer(String id) {
		this.id = id;
	}

	// -------------------------------Getters And Setters------------------------------
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public E_Levels getLevel() {
		return level;
	}

	public void setLevel(E_Levels level) {
		this.level = level;
	}

	public URL getEmail() {
		return email;
	}

	public void setEmail(URL email) {
		this.email = email;
	}

	public Set<Subscription> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(Set<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Team getFavoriteTeam() {
		return favoriteTeam;
	}

	public void setFavoriteTeam(Team favoriteTeam) {
		this.favoriteTeam = favoriteTeam;
	}

	// -------------------------------More Methods------------------------------
	/**
	 * This method adds a new Subscription to the subscriptions array 
	 * only if the given subscription doesn't already exist in the Customer's subscriptions array.
	 * 
	 * @param subscription to be added
	 * @return true if this subscription was successfully added, false otherwise
	 */
	public boolean addSubscription(Subscription subscription) {
		//TODO
		// input check
		if (subscription == null)
			return false;

		// checking if subscription exists already
		if (subscriptions.contains(subscription))
			return false;

		return subscriptions.add(subscription);
	}

	/**
	 * This method removes an existing subscription from the subscriptions array 
	 * only if the subscription exists
	 * 
	 * @param subscription to be removed
	 * @return true if this subscription was removed successfully, false otherwise
	 */
	public boolean removeSubscription(Subscription subscription) {
		//TODO
		// input check
		if (subscription == null)
			return false;

		// checking if subscription exists already
		if (!subscriptions.contains(subscription) )
			return false;

		// deleting all matches from the given sub
		for (Match m : subscription.getMatches()) {
			m.removeFan(subscription.getCustomer());
		}
		subscription.getMatches().clear();

		return subscriptions.remove(subscription);
	}

	/**
	 * This method counts the number of the valid subscriptions that belongs to the customer.
	 * a valid subscription is a subscription that its last date is after today.
	 * 
	 * @return customerSubs number of subscriptions
	 */
	public int getNumOfCustomerSubscriptions() {
		//TODO
		int subCounter = 0;

		Date today = new Date();
		///////
		for (Subscription temp : subscriptions)
			if (temp != null && temp.getLastDay().after(today))
				subCounter++;

		return subCounter;
	}

	/**
	 * This method adds a given match to the first valid subscription of the customer
	 * a subscription is defined valid iff:
	 * 		1. its last date wasn't expired
	 * 		2. the subscription's stadium is the home team's stadium
	 * *subscription's stadium is the working stadium of the receptionist that sold the subscription
	 * IMPORTANT: the match will be added only if its time doesn't ovelap
	 * 			  with any other match in the valid subscription
	 * 
	 * @param match to be added
	 * @return true if the match was added successfully, false otherwise
	 */
	public boolean addMatch(Match match) {
		//TODO
		// input check
		if (match == null)
			return false;

		boolean notExpired = false , stadiumRecp = false ,notBefore = false;

		for (Subscription sub : subscriptions) {



			if(!match.getStartDateTime().before(sub.getStartDate())) {
				notBefore = true;
				// checking if sub date hasn't expired
				if (sub != null && sub.getLastDay().after(match.getStartDateTime()) ) {
					notExpired =true;

					// checking if it's the same stadium for home team and receptionist
					if (sub.getReceptionist() != null &&
							sub.getReceptionist().getWorkingStadium() != null &&
							sub.getReceptionist().getWorkingStadium().equals(match.getHomeTeam().getStadium()))
						return sub.addMatch(match);
				}
			}
		}
		
		if(!notBefore)
			throw new FanException("all subscriptions were given after the game was held");

		if(!notExpired)
			throw new FanException("All the subscriptions are out of date for this game or");

		if(!stadiumRecp)
			throw new FanException("There is no subscription that belongs to the match stadium");

		return false;
	}

	/**
	 * This method removes a given match from the relevant subscription
	 * 
	 * @param match to be removed
	 * @return true if the match was removed successfully, false otherwise
	 */
	public boolean removeMatch(Match match) {
		//TODO
		// input check
		if (match == null)
			return false;

		// removing match from subscription
		for (Subscription sub : subscriptions)
			if (sub != null) 
				for (Match m : sub.getMatches())
					if (m != null && m.equals(match)) 
						return sub.removeMatch(m);
		return false;
	}

	/**
	 * This method gets a string and checks if the string is a valid ID number
	 * 
	 * @see utils.Constants
	 * @param id to be checked
	 * @return the id if it's a valid id, "0" otherwise
	 */
	public static String checkId(String id) {
		//TODO
		if (id.length() == Constants.ID_NUMBER_SIZE) {
			for (int i = 0; i < id.length(); i++) {
				if (!Character.isDigit(id.charAt(i)))
					return "0";
			}
			return id;
		}
		return "0";
	}

	/**
	 * Customer objects must be ordered by customer's level
	 */
	@Override
	public int compareTo(Customer o) {
		//TODO
		if (o != null) {
			if (level.getLevel() > o.getLevel().getLevel())
				return 1;

			if (level.getLevel() < o.getLevel().getLevel())
				return -1;

			// if it's the same level, we organize by id
			else
				return id.compareTo(o.getId());
		}

		return 0;
	}


	// -------------------------------hashCode equals & toString------------------------------
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Customer other = (Customer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		DateFormat dft = new SimpleDateFormat("dd/MM/yyyy;HH:mm");
		return "Customer | id: " + id + ", name: " + firstName + " " + lastName + ", birthdate: " + dft.format(birthdate)
		+ ", level: " + level + ", email: " + email + ", favorite team: " + favoriteTeam.getName() + ", address: " + address;
	}

}
