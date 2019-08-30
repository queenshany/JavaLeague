package Model;

import java.io.Serializable;

import utils.E_Formations;
/**
 * Class MatchPosition ~ represent a single Match Position per team and match
 * 
 * @author ID: 312181605
 * @author ID: 205791056
 */
public class MatchPosition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// -------------------------------Class Members------------------------------
	
	private AssignedPlayer playerA, playerB, playerC, playerD, playerG;
	private E_Formations formation;
	private Match match;
	private Team team;

	// -------------------------------Constructors------------------------------
	public MatchPosition(AssignedPlayer playerA, AssignedPlayer playerB,
			AssignedPlayer playerC, AssignedPlayer playerD, AssignedPlayer playerG,
			E_Formations formation, Match match, Team team) {
	
		// assigning players
		
		this.playerA = new AssignedPlayer(playerA.getId(), playerA.getFirstName(),
				playerA.getLastName(), playerA.getBirthdate(),
				playerA.getStartWorkingDate(), playerA.getAddress(),
				playerA.getLevel(), playerA.getValue(),
				playerA.getIsRightLegKicker(),
				playerA.getPosition(), "A");
		
		this.playerB = new AssignedPlayer(playerB.getId(), playerA.getFirstName(),
				playerB.getLastName(), playerA.getBirthdate(),
				playerB.getStartWorkingDate(), playerA.getAddress(),
				playerB.getLevel(), playerA.getValue(),
				playerB.getIsRightLegKicker(),
				playerB.getPosition(), "B");
		
		this.playerC = new AssignedPlayer(playerC.getId(), playerA.getFirstName(),
				playerC.getLastName(), playerA.getBirthdate(),
				playerC.getStartWorkingDate(), playerA.getAddress(),
				playerC.getLevel(), playerA.getValue(),
				playerC.getIsRightLegKicker(),
				playerC.getPosition(), "C");
		
		this.playerD = new AssignedPlayer(playerD.getId(), playerA.getFirstName(),
				playerD.getLastName(), playerA.getBirthdate(),
				playerD.getStartWorkingDate(), playerA.getAddress(),
				playerD.getLevel(), playerA.getValue(),
				playerD.getIsRightLegKicker(),
				playerD.getPosition(), "D");
		
		this.playerG = new AssignedPlayer(playerG.getId(), playerA.getFirstName(),
				playerG.getLastName(), playerA.getBirthdate(),
				playerG.getStartWorkingDate(), playerA.getAddress(),
				playerG.getLevel(), playerA.getValue(),
				playerG.getIsRightLegKicker(),
				playerG.getPosition(), "G");
		
		this.formation = formation;
		this.match = match;
		this.team = team;
	}

	public MatchPosition(E_Formations formation, Match match, Team team) {
		this.formation = formation;
		this.match = match;
		this.team = team;
	}

	// -------------------------------Getters And Setters------------------------------
	
	public AssignedPlayer getPlayerA() {
		return playerA;
	}

	public void setPlayerA(Player playerA) {
		if (playerA == null)
			this.playerA = null;
		else
			this.playerA = new AssignedPlayer(playerA.getId(), playerA.getFirstName(),
					playerA.getLastName(), playerA.getBirthdate(),
					playerA.getStartWorkingDate(), playerA.getAddress(),
					playerA.getLevel(), playerA.getValue(),
					playerA.getIsRightLegKicker(),
					playerA.getPosition(), "A");
	}

	public AssignedPlayer getPlayerB() {
		return playerB;
	}

	public void setPlayerB(Player playerB) {
		if (playerB == null)
			this.playerB = null;
		else
			this.playerB = new AssignedPlayer(playerB.getId(), playerB.getFirstName(),
					playerB.getLastName(), playerB.getBirthdate(),
					playerB.getStartWorkingDate(), playerB.getAddress(),
					playerB.getLevel(), playerB.getValue(),
					playerB.getIsRightLegKicker(),
					playerB.getPosition(), "B");
	}

	public AssignedPlayer getPlayerC() {
		return playerC;
	}

	public void setPlayerC(Player playerC) {
		if (playerC == null)
			this.playerC = null;
		else
			this.playerC = new AssignedPlayer(playerC.getId(), playerC.getFirstName(),
					playerC.getLastName(), playerC.getBirthdate(),
					playerC.getStartWorkingDate(), playerC.getAddress(),
					playerC.getLevel(), playerC.getValue(),
					playerC.getIsRightLegKicker(),
					playerC.getPosition(), "C");
	}

	public AssignedPlayer getPlayerD() {
		return playerD;
	}

	public void setPlayerD(Player playerD) {
		if (playerD == null)
			this.playerD = null;
		else
			this.playerD = new AssignedPlayer(playerD.getId(), playerD.getFirstName(),
					playerD.getLastName(), playerD.getBirthdate(),
					playerD.getStartWorkingDate(), playerD.getAddress(),
					playerD.getLevel(), playerD.getValue(),
					playerD.getIsRightLegKicker(),
					playerD.getPosition(), "D");
	}

	public AssignedPlayer getPlayerG() {
		return playerG;
	}

	public void setPlayerG(Player playerG) {
		if (playerG == null)
			this.playerG = null;
		else
			this.playerG = new AssignedPlayer(playerG.getId(), playerG.getFirstName(),
					playerG.getLastName(), playerG.getBirthdate(),
					playerG.getStartWorkingDate(), playerG.getAddress(),
					playerG.getLevel(), playerG.getValue(),
					playerG.getIsRightLegKicker(),
					playerG.getPosition(), "G");
	}

	public E_Formations getFormation() {
		return formation;
	}

	public void setFormation(E_Formations formation) {
		this.formation = formation;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	// -------------------------------More Methods------------------------------
	
	/**
	 * This method checks if a given player is contained in the match formation
	 * @param assignedPlayer
	 * @return true if the player is contained, false otherwise
	 */
	public boolean containsAssignedPlayer(AssignedPlayer assignedPlayer) {
		if (assignedPlayer == null)
			return false;

		return (playerA != null && playerA.getId() == assignedPlayer.getId()) ||
				(playerB != null && playerB.getId() == assignedPlayer.getId()) ||
				(playerC != null && playerC.getId() == assignedPlayer.getId()) ||
				(playerD != null && playerD.getId() == assignedPlayer.getId()) ||
				(playerG != null && playerG.getId() == assignedPlayer.getId());
	}
	
	
	/**
	 *  This method removes a given player from the match formation
	 * @param assignedPlayer
	 * @return true if removed successfully, false otherwise
	 */
	public boolean removeAssignedPlayer(AssignedPlayer assignedPlayer) {
		if (assignedPlayer == null || !containsAssignedPlayer(assignedPlayer))
			return false;
		
		if (assignedPlayer.equals(playerA))
			setPlayerA(null);
		else if (assignedPlayer.equals(playerB))
			setPlayerB(null);
		else if (assignedPlayer.equals(playerC))
			setPlayerC(null);
		else if (assignedPlayer.equals(playerD))
			setPlayerD(null);
		else if (assignedPlayer.equals(playerG))
			setPlayerG(null);
		
		return true;
	}

	// -------------------------------hashCode equals & toString------------------------------
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((match == null) ? 0 : match.hashCode());
		result = prime * result + ((team == null) ? 0 : team.hashCode());
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
		MatchPosition other = (MatchPosition) obj;
		if (match == null) {
			if (other.match != null)
				return false;
		} else if (!match.equals(other.match))
			return false;
		if (team == null) {
			if (other.team != null)
				return false;
		} else if (!team.equals(other.team))
			return false;
		return true;
	}
	
}
