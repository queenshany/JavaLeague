package View;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import Controller.Validation;
import Model.Address;
import Model.Customer;
import Model.Team;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Constants;
import utils.E_Cities;
import utils.E_Levels;

/**
 * Class CustomerCreationController ~ add new customer to the system window
 * 
 * @author ID: 205791056
 * @author ID: 312181605
 */
public class CustomerCreationController extends AllWindowsController {
	// ------------------------------ Variables ------------------------------
	@FXML
	private VBox vboxPane;

	@FXML
	private TextField idField;

	@FXML
	private TextField emailField;

	@FXML
	private TextField firstNameField;

	@FXML
	private TextField lastNameField;

	@FXML
	private DatePicker bdayPicker;

	@FXML
	private ComboBox<E_Levels> comboLevel;

	@FXML
	private TextField streetText;

	@FXML
	private TextField houseText;

	@FXML
	private TextField phoneText;

	@FXML
	private Label alertLabel;

	@FXML
	private Label streetLabel;

	@FXML
	private Label houseLabel;

	@FXML
	private ComboBox<E_Cities> comboCity;

	@FXML
	private ComboBox<String> comboTeams;

	@FXML
	private ComboBox<String> comboPhone;

	@FXML
	private Button submitButton;

	protected static boolean isAdmin;

	ArrayList<Team> allTeamArr = new ArrayList<>();
	ArrayList<String> allTeamStr = new ArrayList<>();

	// ------------------------------ Methods ------------------------------
	// ========================== General Methods ==========================
	@Override
	public void initialize() {
		vboxPane.setStyle("-fx-background-image: url(\"/resources/img-customerBackground.jpg\");"
				+ "-fx-background-repeat: no-repeat; -fx-background-size: cover; ");

		allTeamArr.addAll(ViewLogic.sysData.getTeams().values());
		for (Team temp : allTeamArr) {
			allTeamStr.add(temp.getId() + " | " + temp.getName());
		}

		comboTeams.getItems().addAll(allTeamStr);
		comboCity.getItems().addAll(E_Cities.values());
		comboLevel.getItems().addAll(E_Levels.values());
		comboPhone.getItems().addAll("050", "052", "053", "054", "02", "03", "04", "08", "09");
		comboPhone.getSelectionModel().select(6);

	}

	@Override
	protected void closeWindow() {
		Stage stage = (Stage) submitButton.getScene().getWindow();
		stage.close();
	}

	// ========================== Action Listeners ==========================
	/**
	 * When submit button is clicked, we check the data. If all is valid, we add the
	 * customer to the system
	 */
	@FXML
	private void submit() {

		// getting all the input values
		String firstNameStr = firstNameField.getText();
		String email = emailField.getText();
		String lastNameStr = lastNameField.getText();
		String streetStr = streetText.getText();
		String phoneStr = phoneText.getText();
		LocalDate localDate = bdayPicker.getValue();
		Date bday = new Date();
		String areaCode = comboPhone.getSelectionModel().getSelectedItem();
		int teamIndex = comboTeams.getSelectionModel().getSelectedIndex();

		E_Cities city = comboCity.getSelectionModel().getSelectedItem();
		E_Levels level = comboLevel.getSelectionModel().getSelectedItem();

		String id = idField.getText();
		// Validate id
		if (!ViewLogic.sysData.getUsers().containsKey(id)) {
			if (id.length() == Constants.ID_NUMBER_SIZE) {

				if (Customer.checkId(id) != "0") {

					// Validate first name
					if (Validation.validName(firstNameStr)) {

						// Validate last name
						if (Validation.validName(lastNameStr)) {

							// Validate date
							if (localDate != null) {
								bday = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
								if (bday.before(new Date())) {

									// Validate email
									if (Validation.validEmailAddress(email)) {
										try {
											URL urlEmail = new URL("hTTp:\\" + email);

											// Validate level
											if (level != null) {

												// Validate team
												if (teamIndex >= 0) {
													int teamId = allTeamArr.get(teamIndex).getId();

													// Validate phone
													if (Validation.validPhone(phoneStr)) {
														String[] phoneNumber = { areaCode + "-" + phoneStr };

														// Validate city
														if (city != null) {

															// Validate street
															if (Validation.validName(streetStr)) {
																try {
																	// Validate housename
																	Integer house = Integer
																			.parseInt(houseText.getText());
																	if (house > 0) {
																		// adding the customer to the system and
																		// clearing data
																		if (ViewLogic.sysData.addCustomer(id,
																				firstNameStr, lastNameStr, bday, level,
																				urlEmail, teamId,
																				new Address(city, streetStr, house,
																						phoneNumber))) {
																			idField.clear();
																			firstNameField.clear();
																			lastNameField.clear();
																			houseText.clear();
																			phoneText.clear();
																			emailField.clear();
																			streetText.clear();
																			bdayPicker.getEditor().clear();
																			comboCity.getSelectionModel()
																					.clearSelection();
																			comboLevel.getSelectionModel()
																					.clearSelection();
																			comboPhone.getSelectionModel()
																					.clearSelection();
																			comboTeams.getSelectionModel()
																					.clearSelection();

																			alertLabel.setText("Added succesfully!");

																			// case created from admin controller
																			if (isAdmin) {
																				if (ViewLogic.adminCustomerController != null)
																					ViewLogic.adminCustomerController
																							.setCusTable();
																			}
																			// case created from receptionist main
																			else {
																				if (ViewLogic.recepMainController != null)
																					ViewLogic.recepMainController
																							.setCusTable();
																			}

																		} else
																			alertLabel.setText(
																					"addCustomer returned false.");
																	} else
																		alertLabel.setText("Invalid house number!");
																} catch (NumberFormatException e) {
																	if (houseText.getText().equals(""))
																		alertLabel
																				.setText("Please enter a house number");
																	else
																		alertLabel.setText(
																				"Invalid input for house number!");
																}
															} else {
																if (streetStr.equals(""))
																	alertLabel.setText("Please enter a street name");
																else
																	alertLabel.setText("Invalid Street name!");
															}
														} else

															alertLabel.setText("Please choose a city");

													} else {
														if (phoneStr.equals(""))
															alertLabel.setText("Please enter phone number");
														else
															alertLabel.setText("Invalid phone number!");
													}
												} else
													alertLabel.setText("Please choose a Team");
											} else
												alertLabel.setText("Please choose a Level");

										} catch (MalformedURLException e) {
											alertLabel.setText("Invalid email URL!");
										}
									} else
										alertLabel.setText("Invalid email!");
								} else
									alertLabel.setText("Customer must be born before today");
							} else
								alertLabel.setText("Please pick a date");
						} else {
							if (lastNameStr.equals(""))
								alertLabel.setText("Please enter Last name");
							else
								alertLabel.setText("Invalid last name!");
						}
					} else {
						if (firstNameStr.equals(""))
							alertLabel.setText("Please enter First Name");
						else
							alertLabel.setText("Invalid first name!");
					}

				} else if (ViewLogic.sysData.getCustomers().containsKey(id))
					alertLabel.setText("ID already exists in the system!");
				else if (idField.getText().equals(""))
					alertLabel.setText("Please enter ID");
				else
					alertLabel.setText("Invalid ID!");

			} else
				alertLabel.setText("Enter 9 digits for ID");

		} else
			alertLabel.setText("ID already exists in the system");

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