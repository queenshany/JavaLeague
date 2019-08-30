package Model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Class Receptionist ~ represent a single Receptionist of the company,
 * inheritor of Employee
 * 
 * @author Java Course Team 2017 - Shai Gutman
 * @author University Of Haifa - Israel
 * @author ID: 205791056
 */
public class Receptionist extends Employee implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// -------------------------------Class Members------------------------------
	private Stadium workingStadium;
	private Set<Subscription> subscriptions;
	private String recpString;

	// -------------------------------Constructors------------------------------
	public Receptionist(int id, String firstName, String lastName, Date birthdate, Date startWorkingDate, 
			Address address) {
		super(id, firstName, lastName, birthdate, startWorkingDate, address);
		//TODO
		subscriptions = new HashSet<>();
		setRecpString();
	}

	public String getRecpString() {
		return recpString;
	}

	public void setRecpString() {
		recpString = getId() + " | " + getFirstName() + " " + getLastName();
	}

	public Receptionist(int empNum) {
		super(empNum);
	}

	// -------------------------------Getters And Setters------------------------------
	public void setWorkingStadium(Stadium workingStadium) {
		this.workingStadium = workingStadium;
	}

	public Stadium getWorkingStadium() {
		return workingStadium;
	}
	
	public void setSubscriptions(Set<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}
	
	public Set<Subscription> getSubscriptions() {
		return subscriptions;
	}

	// -------------------------------More Methods------------------------------
	/**
	 * This method adds a subscription to the subscriptions array 
	 * only if it doesn't already exist in receptionist's subscriptions array
	 * 
	 * @param subscription to be added
	 * @return true if the subscription was added successfully, false otherwise
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
	 * This method removes a subscription from the subscriptions array
	 * 
	 * @param subscription to be removed
	 * @return true if the subscription was removed successfully, false otherwise
	 */
	public boolean removeSubscription(Subscription subscription) {
		//TODO
		// input check
		if (subscription == null)
			return false;
		
		// checking if subscription exists already
		if (!subscriptions.contains(subscription))
			return false;
		
		return subscriptions.remove(subscription);
	}
}
