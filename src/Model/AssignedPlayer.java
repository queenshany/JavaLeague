package Model;

import java.io.Serializable;
import java.util.Date;

import utils.E_Levels;
import utils.E_Position;
/**
 * Class AssignedPlayer ~ represent a single Assigned Player that plays in a match and his position,
 * every AssignedPlayer has a position in the match.
 * 
 * @author ID: 312181605
 * @author ID: 205791056
 */
public class AssignedPlayer extends Player implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// -------------------------------Class Members------------------------------
	
	private String assPosition;
	
	// -------------------------------Constructors------------------------------
	
	public AssignedPlayer(int id, String firstName, String lastName, Date birthdate,
			Date startWorkingDate, Address address, E_Levels level, long value,
			boolean isRightLegKicker, E_Position position, String assPosition) {
		super(id, firstName, lastName, birthdate, startWorkingDate, address,
				level, value, isRightLegKicker, position);
		this.assPosition = assPosition;
	}

	// by given player and position
	public AssignedPlayer(Player player, String assPosition) {
		super(player.getId(), player.getFirstName(), player.getLastName(),
				player.getBirthdate(), player.getStartWorkingDate(), player.getAddress(),
				player.getLevel(), player.getValue(), player.getIsRightLegKicker(),
				player.getPosition());
		this.assPosition = assPosition;
	}
	
	// -------------------------------Getters And Setters------------------------------
	
	public AssignedPlayer(int id) {
		super(id);
	}
	
	public String getAssPosition() {
		return assPosition;
	}

	public void setAssPosition(String assPosition) {
		this.assPosition = assPosition;
	}
	
}
