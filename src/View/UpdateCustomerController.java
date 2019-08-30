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
import utils.E_Cities;
import utils.E_Levels;

/**
 * Class UpdateCustomerController ~ updates customers in the system window
 * 
 * @author ID: 205791056
 * @author ID: 312181605
 */
public class UpdateCustomerController extends AllWindowsController {
	// ------------------------------ Variables ------------------------------
	protected static boolean isAdmin;
	protected static String cusID;
	private Customer customer;

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

	private ArrayList<Team> allTeamArr = new ArrayList<>();
	private ArrayList<String> allTeamStr = new ArrayList<>();

	// ------------------------------ Methods ------------------------------
	// ========================== General Methods ==========================
	@Override
	public void initialize() {
		vboxPane.setStyle("-fx-background-image: url(\"/resources/img-customerBackground.jpg\");"
				+ "-fx-background-repeat: no-repeat; -fx-background-size: cover; ");
		customer = ViewLogic.sysData.getCustomers().get(cusID);

		idField.setText(customer.getId());
		firstNameField.setText(customer.getFirstName());
		lastNameField.setText(customer.getLastName());

		Date input = customer.getBirthdate();
		LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		bdayPicker.setValue(date);

		String emailParts = customer.getEmail().toString().substring(6);
		emailField.setText(emailParts);

		String s = "";
		for (String p : customer.getAddress().getPhoneNumber())
			s += p;

		String[] phoneParts = s.split("-");
		phoneText.setText(phoneParts[1]);
		streetText.setText(customer.getAddress().getStreet());
		houseText.setText(Integer.toString(customer.getAddress().getHouseNumber()));
		allTeamArr.addAll(ViewLogic.sysData.getTeams().values());

		String team = customer.getFavoriteTeam().getId() + " | " + customer.getFavoriteTeam().getName();

		for (Team temp : allTeamArr)
			allTeamStr.add(temp.getId() + " | " + temp.getName());

		comboTeams.getItems().addAll(allTeamStr);
		comboTeams.getSelectionModel().select(team);
		comboCity.getItems().addAll(E_Cities.values());
		comboCity.getSelectionModel().select(customer.getAddress().getCity());

		comboLevel.getItems().addAll(E_Levels.values());
		comboLevel.getSelectionModel().select(customer.getLevel());
		comboPhone.getItems().addAll("050", "052", "053", "054", "02", "03", "04", "08", "09");
		comboPhone.getSelectionModel().select(phoneParts[0]);

	}

	@Override
	protected void closeWindow() {
		Stage stage = (Stage) submitButton.getScene().getWindow();
		stage.close();
	}

	// ========================== Action Listeners ==========================
	/**
	 * A method which validates users input and updates the customer in the system
	 */
	@FXML
	private void submit() {

		// getting all the input values
		String firstNameStr = firstNameField.getText();
		String email = emailField.getText();
		String lastNameStr = lastNameField.getText();
		String streetStr = streetText.getText();
		String phoneStr = phoneText.getText();
		String areaCode = comboPhone.getSelectionModel().getSelectedItem();
		int teamIndex = comboTeams.getSelectionModel().getSelectedIndex();

		E_Cities city = comboCity.getSelectionModel().getSelectedItem();
		E_Levels level = comboLevel.getSelectionModel().getSelectedItem();

		// Validates first name
		if (Validation.validName(firstNameStr)) {

			// Validates last name
			if (Validation.validName(lastNameStr)) {

				// Validates email
				if (Validation.validEmailAddress(email)) {
					try {
						URL urlEmail = new URL("hTTp:\\" + email);

						// Validates level
						if (level != null) {

							// Validates team
							if (teamIndex >= 0) {
								Team team = allTeamArr.get(teamIndex);

								// Validates phone
								if (Validation.validPhone(phoneStr)) {
									String[] phoneNumber = { areaCode + "-" + phoneStr };

									// Validates city
									if (city != null) {

										// Validates street
										if (Validation.validName(streetStr)) {
											try {
												// Validates house num
												Integer house = Integer.parseInt(houseText.getText());
												if (house > 0) {
													customer.setAddress(
															new Address(city, streetStr, house, phoneNumber));
													customer.setFirstName(firstNameStr);
													customer.setLastName(lastNameStr);
													customer.setEmail(urlEmail);
													customer.setFavoriteTeam(team);
													customer.setLevel(level);

													alertLabel.setText("Updated succesfully!");
													if (isAdmin) {
														if (ViewLogic.adminCustomerController != null)
															ViewLogic.adminCustomerController.setCusTable();
													} else {
														if (ViewLogic.recepMainController != null)
															ViewLogic.recepMainController.setCusTable();
													}

												} else
													alertLabel.setText("Invalid house number!");
											} catch (NumberFormatException e) {
												if (houseText.getText().equals(""))
													alertLabel.setText("Please enter a house number");
												else
													alertLabel.setText("Invalid input for house number!");
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