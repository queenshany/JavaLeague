package Model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import Exceptions.FanException;
import utils.E_Periods;

/**
 * Class Subscription ~ represent a single Subscription in the company Each
 * Subscription belongs to a specific Customer
 * 
 * @author Java Course Team 2018 - Shai Gutman
 * @author University Of Haifa - Israel
 * @author ID: 205791056
 */
public class Subscription implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// -------------------------------Class Members------------------------------
	private int id;
	private Customer customer;
	private Receptionist receptionist;
	private E_Periods period;
	private Date startDate;
	private Set<Match> matches;

	// -------------------------------Constructors------------------------------
	public Subscription(int id, Customer customer, Receptionist receptionist, E_Periods period, Date startDate) {
		this.id = id;
		this.customer = customer;
		this.receptionist = receptionist;
		this.period = period;
		this.startDate = startDate;
		//TODO
		matches = new HashSet<>();
	}
	
	public Subscription(int id) {
		this.id = id;
	}

	// -------------------------------Getters And Setters------------------------------
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<Match> getMatches() {
		return matches;
	}

	public void setMatches(Set<Match> matches) {
		this.matches = matches;
	}

	public Receptionist getReceptionist() {
		return receptionist;
	}
	
	public void setReceptionist(Receptionist receptionist) {
		this.receptionist = receptionist;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer resCustomer) {
		this.customer = resCustomer;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public E_Periods getPeriod() {
		return period;
	}

	public void setPeriod(E_Periods period) {
		this.period = period;
	}

	// -------------------------------More Methods------------------------------
	/**
	 * This method calculate the last date the subscription is valid for
	 * 
	 * @see utils.E_Periods
	 * @return lastDate of the subscription if no nulls, null otherwise
	 */
	public Date getLastDay() {
		//TODO
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		if (calendar.getTime() != null) {
			calendar.add(Calendar.MONTH, period.getNumber());
			return calendar.getTime();
		}
		
		return null;
	}

	/**
	 * This method adds a new match to the matches array
	 * only iF the match doesn't already exists in the Subscription's matches array, match is valid 
	 * and the time doesn't overlap with any other match in the array
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
			throw new FanException("The match already exists in this subscription");
			
		// checking if dates overlap
		for (Match temp : matches)
			if (temp != null && overlapDates(temp.getStartDateTime(), temp.getFinishDateTime(), match.getStartDateTime(), match.getFinishDateTime()))
				throw new FanException("The match overlaps with another match in this subscription");

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

		// removing the match from sub
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
		Subscription other = (Subscription) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		DateFormat dft = new SimpleDateFormat("dd/MM/yyyy;HH:mm");
		return "Subscription | id: " + id + ", customer: " + customer.getFirstName() + " " + customer.getLastName() 
				+ ", receptionist: " + receptionist.getFirstName() + " " + receptionist.getLastName() 
				+ ", period: " + period + ", start date: " + dft.format(startDate);
	}

}
