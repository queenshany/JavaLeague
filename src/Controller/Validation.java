package Controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import utils.Constants;

/**
 * Class Validation ~ class that contains  methods for validating user input
 * 
 * @author ID: 205791056
 * @author ID: 312181605
 */

public class Validation {


	//	public static boolean validLastName(String str) {
	//
	//		// checks if the the string is a valid last name
	//		if (!str.matches("[a-zA-z]+([ '-][a-zA-Z]+)*")) 
	//			return false;
	//		return true;
	//	}

/**
 * This method checks if a name is valid
 * @param str
 * @return true if valid, false otherwise
 */
	public static boolean validName (String str) {
		// checks if the string is valid for name;
		if (!str.matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$")) 
			return false;
		return true;
	}

	/**
	 * This method checks if a phone number is valid
	 * @param str
	 * @return true if valid, false otherwise
	 */
	public static boolean validPhone(String phone) {

		String regexStr = "^[0-9]*$";

		if (phone.matches(regexStr) && phone.length() == Constants.PHONE_LENGTH )
			return true;
		return false;

	}
	/**
	 * This method checks if a value is valid
	 * @param str
	 * @return true if valid, false otherwise
	 */
	public static boolean validValue (int val) {
		if (val < 0) 
			return false;
		return true;
	}

	/**
	 * This method checks if an ID is valid
	 * @param str
	 * @return true if valid, false otherwise
	 */
	public static boolean validID (int id) {
		if (id <= 0) 
			return false;
		return true;
	}

	/**
	 * This method checks if an email address is valid
	 * @param str
	 * @return true if valid, false otherwise
	 */
	public static boolean validEmailAddress(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * This method pops up an alert error dialog
	 * @param strHeader
	 * @param strText
	 */
	public static void alert(String strHeader , String strText) {
		Alert alert = new Alert(AlertType.ERROR);

		alert.setTitle("Error Dialog");
		alert.setHeaderText(strHeader);
		alert.setContentText(strText);

		alert.showAndWait();
	}
	
	/**
	 * This method pops up an alert error dialog
	 * @param strHeader
	 * @param strText
	 */
	public static void alert(String strHeader) {
		Alert alert = new Alert(AlertType.ERROR);

		alert.setTitle("Error Dialog");
		alert.setHeaderText(strHeader);

		alert.showAndWait();
	}
	
	/**
	 * This method pops up an alert info dialog
	 * @param strHeader
	 * @param strText
	 */
	public static void info(String header, String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(header);
		alert.setContentText(text);
		alert.showAndWait();
	}
	
	/**
	 * This method pops up an alert info dialog
	 * @param strHeader
	 * @param strText
	 */
	public static void info(String header) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(header);
		alert.showAndWait();
	}
}
