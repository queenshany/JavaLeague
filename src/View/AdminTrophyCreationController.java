package View;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Model.Coach;
import Model.Player;
import Model.Stadium;
import Model.Team;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.E_Trophy;

/**
 * Class AdminTrophyCreationController ~ add new trophy to the system window
 * 
 * @author ID: 205791056
 * @author ID: 312181605
 */
public class AdminTrophyCreationController extends AllWindowsController {
	// ------------------------------ Variables ------------------------------
	@FXML
	private VBox vboxPane;

	@FXML
	private DatePicker datePicker;

	@FXML
	private ComboBox<E_Trophy> comboTrophyType;

	@FXML
	private ComboBox<String> comboEntity;

	@FXML
	private Label alertLabel;

	@FXML
	private Button submitButton;

	ArrayList<Coach> coaches = new ArrayList<>();
	ArrayList<Player> players = new ArrayList<>();
	ArrayList<Team> teams = new ArrayList<>();
	ArrayList<Stadium> stadiums = new ArrayList<>();
	ArrayList<String> coachesStr = new ArrayList<>();
	ArrayList<String> playersStr = new ArrayList<>();
	ArrayList<String> teamsStr = new ArrayList<>();
	ArrayList<String> stadiumsStr = new ArrayList<>();

	// ------------------------------ Methods ------------------------------
	// ========================== General Methods ==========================
	@Override
	public void initialize() {

		vboxPane.setStyle("-fx-background-image: url(\"/resources/img-addTrophyBackground.jpg\");"
				+ "-fx-background-repeat: no-repeat; -fx-background-size: stretch;");

		coaches.addAll(ViewLogic.sysData.getCoachs().values());
		for (Coach temp : coaches)
			coachesStr.add(temp.getId() + " | " + temp.getFirstName() + " " + temp.getLastName());

		players.addAll(ViewLogic.sysData.getPlayers().values());
		for (Player temp : players)
			playersStr.add(temp.getId() + " | " + temp.getFirstName() + " " + temp.getLastName());

		stadiums.addAll(ViewLogic.sysData.getStadiums().values());
		for (Stadium temp : stadiums)
			stadiumsStr.add(temp.getId() + " | " + temp.getName());

		teams.addAll(ViewLogic.sysData.getTeams().values());
		for (Team temp : teams)
			teamsStr.add(temp.getId() + " | " + temp.getName());

		setComboTrophyType();
	}

	@Override
	protected void closeWindow() {
		Stage stage = (Stage) submitButton.getScene().getWindow();
		stage.close();
	}

	/**
	 * setting the trophy type combobox
	 */
	private void setComboTrophyType() {
		comboTrophyType.getItems().addAll(E_Trophy.values());
	}

	/**
	 * setting the entities combobox
	 */
	private void setComboEntity() {
		E_Trophy type = comboTrophyType.getSelectionModel().getSelectedItem();
		if (type != null) {
			comboEntity.setDisable(false);

			switch (type) {
			case COACH_OF_THE_YEAR:
				comboEntity.getItems().clear();
				comboEntity.getItems().addAll(coachesStr);
				break;
			case PLAYER_OF_THE_YEAR:
				comboEntity.getItems().clear();
				comboEntity.getItems().addAll(playersStr);
				break;
			case STADIUM_OF_THE_YEAR:
				comboEntity.getItems().clear();
				comboEntity.getItems().addAll(stadiumsStr);
				break;
			case TEAM_OF_THE_YEAR:
				comboEntity.getItems().clear();
				comboEntity.getItems().addAll(teamsStr);
				break;
			}
		}
	}

	// ========================== Action Listeners ==========================
	/**
	 * setting the combo entity when trophy type is chosen
	 */
	@FXML
	private void comboTrophyOnClick() {
		setComboEntity();
	}

	/**
	 * When submit button is clicked, we check the data. If all is valid, we add the
	 * trophy to the system
	 */
	@FXML
	private void submit() {

		// Getting users input
		LocalDate date = datePicker.getValue();
		
		Calendar c = Calendar.getInstance();

		// set the calendar to start of today
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		// and get that as a Date
		 Date today = c.getTime();
		
		E_Trophy trophy = comboTrophyType.getSelectionModel().getSelectedItem();
		int index = comboEntity.getSelectionModel().getSelectedIndex();
		Object owner = null;
		// Validates trophy type
		if (trophy != null) {
			// Validates owner
			if (index >= 0) {

				switch (trophy) {
				case COACH_OF_THE_YEAR:
					owner = coaches.get(index);
					break;
				case PLAYER_OF_THE_YEAR:
					owner = players.get(index);
					break;
				case STADIUM_OF_THE_YEAR:
					owner = stadiums.get(index);
					break;
				case TEAM_OF_THE_YEAR:
					owner = teams.get(index);
					;
					break;
				}

				if (owner != null) {

					// Validates if the user selected a birth date
					if (date != null) {
						Date winningDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
						// Validates the user added before today
						if (winningDate.equals(today) || winningDate.after(today)) {

							// Validates the user selected a level
							if (ViewLogic.sysData.addTrophy(trophy, owner, winningDate)) {

								comboTrophyType.getSelectionModel().clearSelection();
								comboEntity.getSelectionModel().clearSelection();
								comboEntity.setDisable(true);
								datePicker.setValue(null);
								alertLabel.setText("Trophy was added Successfully");
								owner = null;
								if (ViewLogic.adminTrophyController != null) {
									ViewLogic.adminTrophyController.setTrophyTable();
									ViewLogic.adminTrophyController.setMostTrophy();

								}
							} else
								alertLabel.setText("addTrophy returned false");
						} else
							alertLabel.setText("Can't receive trophy before today!");
					} else
						alertLabel.setText("Invalid date");
				} else
					alertLabel.setText("Please choose an owner");
			} else
				alertLabel.setText("Please choose an owner");
		} else
			alertLabel.setText("Please choose a trophy type");
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
