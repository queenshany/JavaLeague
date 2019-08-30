package View;

import java.util.HashSet;

import Controller.Validation;
import Model.Stadium;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Constants;
import utils.E_Levels;

/**
 * Class AdminTeamCreationController ~ add new team to the system window
 * 
 * @author ID: 205791056
 * @author ID: 312181605
 */
public class AdminTeamCreationController extends AllWindowsController {
	// ------------------------------ Variables ------------------------------

	@FXML
	private VBox vboxPane;

	@FXML
	private TextField idText;

	@FXML
	private TextField nameText;

	@FXML
	private ComboBox<E_Levels> comboLevel;

	@FXML
	private TextField valueTextField;

	@FXML
	private ComboBox<Stadium> comboStadium;

	@FXML
	private Label alertLabel;

	@FXML
	private Button submitButton;

	// ------------------------------ Methods ------------------------------
	// ========================== General Methods ==========================

	@Override
	public void initialize() {
		vboxPane.setStyle("-fx-background-image: url(\"/resources/img-addTeamBackground.jpg\");"
				+ "-fx-background-repeat: no-repeat; -fx-background-size: cover;");
		setComboStadiums();
		comboLevel.getItems().setAll(E_Levels.values());
	}

	/**
	 * setting the stadium combo box
	 */
	private void setComboStadiums() {
		HashSet<Stadium> stadiums = new HashSet<>();

		for (Stadium temp : ViewLogic.sysData.getStadiums().values())
			if (temp.getTeams().size() < Constants.MAX_TEAMS_FOR_STADIUM)
				stadiums.add(temp);

		if (stadiums.isEmpty())
			alertLabel.setText("Please add a stadium to the system and return to add a team");

		comboStadium.getItems().setAll(stadiums);
	}

	@Override
	protected void closeWindow() {
		Stage stage = (Stage) submitButton.getScene().getWindow();
		stage.close();

	}

	// ========================== Action Listeners ==========================
	/**
	 * When submit button is clicked, we check the data. If all is valid, we add the
	 * team to the system
	 */
	@FXML
	private void submit() {

		// Getting users input
		String idStr = idText.getText(), name = nameText.getText(), valueStr = valueTextField.getText();

		E_Levels level = comboLevel.getSelectionModel().getSelectedItem();

		Stadium stadium = comboStadium.getSelectionModel().getSelectedItem();
		try {
			// Validates ID
			Integer id = Integer.parseInt(idStr);
			if (id > 0) {

				if (!ViewLogic.sysData.getTeams().containsKey(id)) {

					// Validates first name
					if (Validation.validName(name)) {
						// Validates the user selected a level
						if (level != null) {
							try {

								// Validates value
								int value = Integer.parseInt(valueStr);

								if (value > 0) {
									if (stadium != null) {

										if (ViewLogic.sysData.addTeam(id, name, value, level, stadium.getId())) {

											if (stadium.getTeams().size() == Constants.MAX_TEAMS_FOR_STADIUM)
												comboStadium.getItems().remove(stadium);

											valueTextField.clear();
											nameText.clear();
											idText.clear();
											comboLevel.getSelectionModel().clearSelection();
											comboStadium.getSelectionModel().clearSelection();

											alertLabel.setText("Team was added successfully");
											ViewLogic.adminTeamController.setTeamTable();
											ViewLogic.adminTeamController.setSuperPlayersTable();
											ViewLogic.adminTeamController.setPlayersTable();
											;

										} else
											alertLabel.setText("Problem occurred");

									} else
										alertLabel.setText("Please choose a stadium");
								} else
									alertLabel.setText("Invalid value");

							} catch (NumberFormatException e) {

								if (valueStr.equals("")) {
									alertLabel.setText("Please enter a value");

								} else {
									alertLabel.setText("Invalid value");
								}
							}

						} else
							alertLabel.setText("Please select a level");
					} else if (name.equals(""))
						alertLabel.setText("Please enter first name");
					else
						alertLabel.setText("Invalid name");

				} else
					alertLabel.setText("ID already exists in the system");

			} else
				alertLabel.setText("Invalid ID");

		} catch (NumberFormatException e) {
			if (idStr.equals("")) {
				alertLabel.setText("Please enter ID");
			} else {
				alertLabel.setText("Invalid ID");

			}
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
