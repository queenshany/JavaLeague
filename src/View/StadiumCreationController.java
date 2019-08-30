package View;

import Controller.Validation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.E_Cities;

/**
 * Class StadiumCreationController ~ add new stadium to the system window
 * 
 * @author ID: 205791056
 * @author ID: 312181605
 */
public class StadiumCreationController extends AllWindowsController {
	// ------------------------------ Variables ------------------------------
	@FXML
	private VBox vboxPane;

	@FXML
	private TextField idText;

	@FXML
	private Label IDLabel;

	@FXML
	private Label nameLabel;

	@FXML
	private Label capacityLabel;

	@FXML
	private Label phoneLabel;

	@FXML
	private TextField nameText;



	@FXML
	private TextField capacityText;

	@FXML
	private TextField phoneText;

	@FXML
	private Label addressLabel;

	@FXML
	private TextField streetText;

	@FXML
	private TextField houseText;

	@FXML
	private Label alertLabel;

	@FXML
	private Label streetLabel;

	@FXML
	private Label houseLabel;

	@FXML
	private ComboBox<E_Cities> comboCity;

	@FXML
	private ComboBox<String> comboPhone;

	@FXML
	private ComboBox<String> comboPhone1;

	@FXML
	private TextField phoneText1;

	@FXML
	private Button submitButton;

	// ------------------------------ Methods ------------------------------
	// ========================== General Methods ==========================

	@Override
	public void initialize() {
		vboxPane.setStyle("-fx-background-image: url(\"/resources/img-addStadiumBackground.jpg\");"
				+ "-fx-background-repeat: no-repeat; -fx-background-size: stretch; ");

		comboCity.getItems().addAll(E_Cities.values());
		comboPhone.getItems().addAll("050", "052", "053", "054", "02", "03", "04", "08", "09");
		comboPhone1.getItems().addAll("050", "052", "053", "054", "02", "03", "04", "08", "09");

		comboPhone.getSelectionModel().select(6);
		comboPhone1.getSelectionModel().select(6);


	}

	@Override
	protected void closeWindow() {
		Stage stage = (Stage) submitButton.getScene().getWindow();
		stage.close();
	}

	// ========================== Action Listeners ==========================
	/**
	 * A method which validates users input and adds the stadium to the system
	 * 
	 */
	@FXML
	private void submit() {

		// getting all the strings the input values
		String nameStr = nameText.getText(), phoneStr = phoneText.getText(), streetStr = streetText.getText(),
				phoneStr1 = phoneText1.getText();

		E_Cities city = comboCity.getSelectionModel().getSelectedItem();

		String areaCode = comboPhone.getSelectionModel().getSelectedItem(),
				areaCode1 = comboPhone1.getSelectionModel().getSelectedItem();



		try {
			Integer id = Integer.parseInt(idText.getText());

			//checks if its valid ID 
			if (!ViewLogic.sysData.getStadiums().containsKey(id) || Validation.validID(id)) {
				if (Validation.validName(nameStr)) {
					try {

						Integer capacity = Integer.parseInt(capacityText.getText());

						if (capacity > 0) {

							if (Validation.validPhone(phoneStr) && Validation.validPhone(phoneStr1)) {
								String[] phoneNumber = { areaCode + "-" + phoneStr, areaCode1 + "-" + phoneStr1};
								if(!phoneNumber[0].equals(phoneNumber[1])) {

									if (city != null) {

										if (Validation.validName(streetStr)) {
											try {
												Integer house = Integer.parseInt(houseText.getText());
												if (house > 0) {


													// adds the stadium and clears the fields
													if (ViewLogic.sysData.addStadium(id, nameStr, capacity, city, streetStr,
															house, phoneNumber)) {
														nameText.clear();
														houseText.clear();
														phoneText.clear();
														capacityText.clear();
														idText.clear();
														streetText.clear();
														comboCity.getSelectionModel().clearSelection();
														phoneText1.clear();
														comboPhone.getSelectionModel().clearSelection();
														comboPhone1.getSelectionModel().clearSelection();

														alertLabel.setText("Added succesfully!");
														if (ViewLogic.adminStadiumController != null)
															ViewLogic.adminStadiumController.setTable();
													} else
														alertLabel.setText("addStadium returned false.");
												} else
													alertLabel.setText("Invalid house number!");
											} catch (NumberFormatException e) {
												if (houseText.getText().equals(""))
													alertLabel.setText("Please enter a house number");
												else
													alertLabel.setText("Invalid input for house number!");
											}
										} else if (streetStr.equals(""))
											alertLabel.setText("Please enter a street name");
										else
											alertLabel.setText("Invalid Street name!");
									} else
										alertLabel.setText("Please choose a city");
								}else
									alertLabel.setText("Phone numbers must be different");

							} else if (phoneStr.equals("") || phoneStr1.equals(""))
								alertLabel.setText("Please enter phone number");
							else
								alertLabel.setText("Invalid phone number!");
						} else
							alertLabel.setText("Invalid Capacity");
					} catch (NumberFormatException e) {
						if (capacityText.getText().equals(""))
							alertLabel.setText("Please enter capacity");
						else
							alertLabel.setText("Invalid input for capacity!");
					}
				} else if (nameStr.equals(""))
					alertLabel.setText("Please enter name");
				else
					alertLabel.setText("Invalid name!");
			} else
				alertLabel.setText("ID already exists!");
		} catch (NumberFormatException e) {
			if (idText.getText().equals(""))
				alertLabel.setText("Please enter ID");
			else
				alertLabel.setText("Invalid ID!");
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