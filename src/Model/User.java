package Model;

import java.io.Serializable;

import utils.E_UserType;
/**
 * Class User ~ represent a user that can use the system, a user may be: admin, coach or receptionst or customer.
 * 
 * @author ID: 205791056
 * @author ID: 312181605
 */
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// -------------------------------Class Members------------------------------
	private String username;
	private String pw;
	private E_UserType userType;
	// -------------------------------Constructors------------------------------
	public User(String username, E_UserType userType) {
		this.username = username;
		this.pw = username;
		this.userType = userType;
	}

	public User(String username, String pw, E_UserType userType) {
		this.username = username;
		this.pw = pw;
		this.userType = userType;
	}
	
	// -------------------------------Getters And Setters------------------------------
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public E_UserType getUserType() {
		return userType;
	}

	public void setUserType(E_UserType userType) {
		this.userType = userType;
	}

	// -------------------------------hashCode equals & toString------------------------------
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", pw=" + pw + ", userType=" + userType + "]";
	}
}
