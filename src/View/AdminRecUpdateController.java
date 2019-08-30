package View;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import Controller.Validation;
import Model.Address;
import Model.Receptionist;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.E_Cities;

/**
 * Class AdminRecCreationController ~ add new receptionist to the system window
 * 
 * @author ID: 205791056
 * @author ID: 312181605
 */

public class AdminRecUpdateController extends AllWindowsController {
	// ------------------------------ Variables ------------------------------

	protected static Receptionist rec;

	@FXML
	private VBox vboxPane;

	@FXML
	private TextField idText;

	@FXML
	private TextField firstNameText;

	@FXML
	private TextField lastNameText;

	@FXML
	private DatePicker birthDayDate;

	@FXML
	private DatePicker workingDate;

	@FXML
	private ComboBox<String> comboPhone;

	@FXML
	private TextField phoneText;

	@FXML
	private Label houseLabel;

	@FXML
	private TextField houseText;

	@FXML
	private TextField streetText;

	@FXML
	private Label streetLabel;

	@FXML
	private ComboBox<E_Cities> comboCity;

	@FXML
	private Label alertLabel;

	@FXML
	private Button submitButton;
	// ------------------------------ Methods ------------------------------
	// ========================== General Methods ==========================
	@Override
	public void initialize() {

		vboxPane.setStyle("-fx-background-image: url(\"/resources/img-addStadiumBackground.jpg\");"
				+ "-fx-background-repeat: no-repeat; -fx-background-size: stretch;");
		setComboCity();
		setComboPhone();

		comboCity.getSelectionModel().select(rec.getAddress().getCity());

		birthDayDate.setValue(rec.getBirthdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		workingDate.setValue(rec.getStartWorkingDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		String s = "";
		for (String p : rec.getAddress().getPhoneNumber())
			s += p;

		String[] phoneParts = s.split("-");
		phoneText.setText(phoneParts[1]);
		comboPhone.getSelectionModel().select(phoneParts[0]);
		streetText.setText(rec.getAddress().getStreet());
		houseText.setText(Integer.toString(rec.getAddress().getHouseNumber()));
		lastNameText.setText(rec.getLastName());
		firstNameText.setText(rec.getFirstName());
		idText.setText(((Integer) rec.getId()).toString());

	}

	@Override
	protected void closeWindow() {
		Stage stage = (Stage) submitButton.getScene().getWindow();
		stage.close();
	}
	/**
	 * setting the phone combobox
	 */
	private void setComboPhone() {
		comboPhone.getItems().setAll("050", "052", "053", "054", "02", "03", "04", "08", "09");
	}
	/**
	 * setting the city combobox
	 */
	private void setComboCity() {
		comboCity.getItems().setAll(E_Cities.values());
	}
	// ========================== Action Listeners ==========================
	/**
	 * When submit button is clicked, we check the data.
	 * If all is valid, we add the rec to the system
	 */
	@FXML
	private void submit() {

		// Getting users input
		String firstName = firstNameText.getText(), lastName = lastNameText.getText(),
				street = streetText.getText(), house = houseText.getText(), phone = phoneText.getText(),
				areaCode = comboPhone.getSelectionModel().getSelectedItem();

		LocalDate birthday = birthDayDate.getValue(), startWorkingDate = workingDate.getValue();

		Calendar c = Calendar.getInstance();

		// set the calendar to start of today
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		// and get that as a Date
		 Date today = c.getTime();

		E_Cities city = comboCity.getSelectionModel().getSelectedItem();

		// Validates first name
		if (Validation.validName(firstName)) {

			// Validates last name
			if (Validation.validName(lastName)) {

				// Validates if the user selected a birth date
				if (birthday != null) {

					// Validates if the user selected a start working date
					if (startWorkingDate != null) {

						if (birthday.isBefore(startWorkingDate)) {

							Date workingD = Date.from(startWorkingDate
									.atStartOfDay(ZoneId.systemDefault()).toInstant());

							// Validates the user added player before today
							if (workingD.before(today) || workingD.equals(today)) {

								// Validates are code was selected
								if (areaCode != null) {

									// Validates phone
									if (Validation.validPhone(phone)) {

										String[] realPhone = { areaCode + "-" + phone };

										// Validates a city was selected
										if (city != null) {

											// Validates the street name
											if (Validation.validName(street)) {

												try {

													// Validates house number input
													Integer realHouse = Integer.parseInt(house);
													if (realHouse > 0) {
														Address address = new Address(city, street,
																realHouse, realPhone);

														rec.setAddress(address);
														rec.setFirstName(firstName);
														rec.setLastName(lastName);
														rec.setStartWorkingDate(workingD);

														alertLabel.setText(
																"Receptionist updated Successfully");
														if (ViewLogic.adminReceptionistController != null)
															ViewLogic.adminReceptionistController.setRecTable();

													} else
														alertLabel.setText("Invalid house number");

												} catch (NumberFormatException e) {
													if (house.equals(""))
														alertLabel
														.setText("Please enter a house number");
													else
														alertLabel.setText("Invalid house number");

												}

											} else if (street.equals(""))
												alertLabel.setText("Please enter a street name");

											else
												alertLabel.setText("Invalid street name");

										} else
											alertLabel.setText("Please select a city");

									} else if (phone.equals(""))
										alertLabel.setText("Please enter a phone number");
									else
										alertLabel.setText("Invalid phone number");
								} else
									alertLabel.setText("Please choose an area code");

							} else
								alertLabel.setText("Start working date needs to be today or before it");

						} else
							alertLabel.setText("Receptionist can't start working before he was born");

					} else
						alertLabel.setText("Please select a start working date");

				} else
					alertLabel.setText("Please select a birthdate");

			} else if (lastName.equals(""))
				alertLabel.setText("Please enter last name");
			else
				alertLabel.setText("Invalid last name");

		} else if (firstName.equals(""))
			alertLabel.setText("Please enter first name");
		else
			alertLabel.setText("Invalid first name");

	}
	
	// ========================== Display ==========================

	/**
	 * Changes buttons' style
	 */
	@FXML
	private void buttonOnMouseEntered(MouseEvent e) {
		Button butt = (Button) e.getSource();

		if (butt.equals(submitButton))
			submitButton.setStyle("-fx-background-color: radial-gradient(center 50% 0%, radius 100%, white, #DDDDDD);");
		
	}

	/**
	 * Changes buttons' style
	 */
	@FXML
	private void buttonOnMouseExited(MouseEvent e) {
		Button butt = (Button) e.getSource();

		if (butt.equals(submitButton))
			submitButton.setStyle("-fx-background-color: white; -fx-text-fill: black");
	}
}
