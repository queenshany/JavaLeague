package View;

import java.util.ArrayList;

import Controller.Validation;
import Model.Coach;
import Model.Player;
import Model.Stadium;
import Model.Team;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.E_Levels;
import utils.E_Position;

/**
 * Class AdminTeamController ~ Admin Team window.
 * 
 * @author ID: 205791056
 * @author ID: 312181605
 */
public class AdminTeamController extends AllWindowsController {
	// ------------------------------ Variables ------------------------------
	@FXML
	private Button backButton;

	@FXML
	private Button logoutButton;


	@FXML
	private TextField bestTeamText;

	@FXML
	private Button addTeamButton;

	@FXML
	private Button updateTeamButton;

	@FXML
	private Button removeTeamButton;

	@FXML
	private TextField textCrowd;

	@FXML
	private TextField textFavourite;

	protected static Team selectedTeam;

	// ========================== Teams DB Table ==========================
	@FXML
	private TableView<Team> tableTeam;

	@FXML
	private TableColumn<Team, Integer> teamIdColumn;

	@FXML
	private TableColumn<Team, String> teamNameColumn;

	@FXML
	private TableColumn<Team, E_Levels> teamLvlColumn;

	@FXML
	private TableColumn<Team, Stadium> teamStadiumColumn;

	@FXML
	private TableColumn<Team, Coach> teamCoachColumn;

	@FXML
	private TableColumn<Team, Integer> teamValueColumn;

	// ========================== Best Home Team Table ==========================
	@FXML
	private TableView<Player> tablePlayer;

	@FXML
	private TableColumn<Player, Integer> idColumnPlayer;

	@FXML
	private TableColumn<Player, String> firstNameColumnPlayer;

	@FXML
	private TableColumn<Player, String> lastNameColumnPlayer;

	@FXML
	private TableColumn<Player, E_Position> positionColumnPlayer;

	@FXML
	private TableColumn<Player, Boolean> rightLegColumnPlayer;

	@FXML
	private TableColumn<Player, Integer> valueColumnPlayer;

	// ========================== All Super Makers Table ==========================
	@FXML
	private TableView<Player> tableSuperPlayers;

	@FXML
	private TableColumn<Player, Integer> idColumnSuperPlayer;

	@FXML
	private TableColumn<Player, String> firstNameColumnSuperPlayer;

	@FXML
	private TableColumn<Player, String> lastNameColumnSuperPlayer;

	@FXML
	private TableColumn<Player, E_Position> positionColumnSuperPlayer;

	@FXML
	private TableColumn<Player, Boolean> rightLegColumnSuperPlayer;

	@FXML
	private TableColumn<Player, Integer> valueColumnSuperPlayer;

	// ------------------------------ Methods ------------------------------
	// ========================== General Methods ==========================
	@Override
	public void initialize() {
		ViewLogic.adminTeamController = this;

		teamIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		teamNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		teamLvlColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
		teamStadiumColumn.setCellValueFactory(new PropertyValueFactory<>("stadium"));
		teamValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
		teamCoachColumn.setCellValueFactory(new PropertyValueFactory<>("coach"));

		idColumnPlayer.setCellValueFactory(new PropertyValueFactory<>("id"));
		firstNameColumnPlayer.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		lastNameColumnPlayer.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		positionColumnPlayer.setCellValueFactory(new PropertyValueFactory<>("position"));
		rightLegColumnPlayer.setCellValueFactory(new PropertyValueFactory<>("isRightLegKicker"));
		valueColumnPlayer.setCellValueFactory(new PropertyValueFactory<>("value"));

		idColumnSuperPlayer.setCellValueFactory(new PropertyValueFactory<>("id"));
		firstNameColumnSuperPlayer.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		lastNameColumnSuperPlayer.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		positionColumnSuperPlayer.setCellValueFactory(new PropertyValueFactory<>("position"));
		rightLegColumnSuperPlayer.setCellValueFactory(new PropertyValueFactory<>("isRightLegKicker"));
		valueColumnSuperPlayer.setCellValueFactory(new PropertyValueFactory<>("value"));

		setTeamTable();
		setPlayersTable();
		setSuperPlayersTable();

		if (ViewLogic.sysData.getTeamWithLargestHomeCrowd() != null)
			textCrowd.setText(ViewLogic.sysData.getTeamWithLargestHomeCrowd().toString());

		if (ViewLogic.sysData.getTheMostFavoredTeam() != null)
			textFavourite.setText(ViewLogic.sysData.getTheMostFavoredTeam().toString());

	}

	@Override
	protected void closeWindow() {
		Stage stage = (Stage) removeTeamButton.getScene().getWindow();
		stage.close();

		ViewLogic.adminTeamController = null;

	}
	/**
	 * Setting the teams DB table
	 */
	protected void setTeamTable() {
		tableTeam.getItems().setAll(ViewLogic.sysData.getTeams().values());
		tableTeam.refresh();
	}

	/**
	 * This method enables buttons when table is clicked
	 */
	@FXML
	private void tableOnClick() {
		updateTeamButton.setDisable(false);
		removeTeamButton.setDisable(false);
	}

	/**
	 * Setting the best players table
	 */
	protected void setPlayersTable() {

		ArrayList<Player> players = ViewLogic.sysData.getFirstPlayersOfBestHomeTeam();

		// sets the table and adds the current best team to the text
		if(players != null)
			if (!players.isEmpty()) {
				bestTeamText.setStyle(null);
				tablePlayer.getItems().setAll(players);

				String teamStr = Integer.toString(players.get(0).getCurrentTeam().getId()) +
						" | " + players.get(0).getCurrentTeam().getName(); 

				bestTeamText.setText(teamStr);

			}else {
				bestTeamText.setStyle("-fx-text-fill: gray");
				bestTeamText.setText("No players");
				tablePlayer.getItems().clear();
			}
		tablePlayer.refresh();
	}
	/**
	 * setting the super playmakers table
	 */
	protected void setSuperPlayersTable() {
		ArrayList<Player> players = ViewLogic.sysData.getAllSuperPlayerMakers();
		tableSuperPlayers.getItems().setAll(players);
		tableSuperPlayers.refresh();
	}

	// ========================== Action Listeners ==========================
	/**
	 * Return to the previous Window
	 */
	@FXML
	private void goBack() {
		closeWindow();
		ViewLogic.newAdminWindow();
	}

	/**
	 * Opens Team Creation to add Team to the system when Add Team button is
	 * clicked.
	 */
	@FXML
	private void createTeam() {
		ViewLogic.newAdminTeamCreatinWindow();
	}

	/**
	 * Removes the selected Team from the system when remove Team button is clicked.
	 */
	@FXML
	private void removeTeam() {

		Team team = tableTeam.getSelectionModel().getSelectedItem();
		if (team != null) {
			if (ViewLogic.sysData.removeTeam(team.getId())) {
				setTeamTable();
				setPlayersTable();
				setSuperPlayersTable();


			} else
				Validation.alert("Team Error", "Unable to remove team");

		} else
			Validation.alert("Team Error", "Please select a team");

	}

	/**
	 * Updates the selected team
	 */
	@FXML
	private void updateTeam() {
		selectedTeam = tableTeam.getSelectionModel().getSelectedItem();

		if (selectedTeam != null)
			ViewLogic.newAdminTeamDetailsWindow();
		else
			Validation.alert("Team Error", "Please choose a team to update");
	}
	
	// ========================== Display ==========================
	
	/**
	 * Changes buttons' style
	 */
	@FXML
	private void buttOnMouseEntered(MouseEvent e) {
		try {
			Button butt = (Button) e.getSource();
			butt.setStyle("-fx-background-color: black; -fx-text-fill: white;");
			
			if (butt.equals(backButton)) {
				Image image = new Image("resources/icon-back-white.png");
				ImageView imageView = new ImageView(image);
				imageView.setFitWidth(15);
				imageView.setFitHeight(15);
				backButton.setGraphic(imageView);
			}
			
			else if (butt.equals(logoutButton)) {
				Image image = new Image("resources/icon-sign-out-white.png");
				ImageView imageView = new ImageView(image);
				imageView.setFitWidth(10);
				imageView.setFitHeight(10);
				logoutButton.setGraphic(imageView);
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Changes buttons' style
	 */
	@FXML
	private void buttOnMouseExited(MouseEvent e) {
		try {
			Button butt = (Button) e.getSource();
			butt.setStyle("-fx-background-color: white; -fx-text-fill: black;");
			
			if (butt.equals(backButton)) {
				Image image = new Image("resources/icon-back.png");
				ImageView imageView = new ImageView(image);
				imageView.setFitWidth(15);
				imageView.setFitHeight(15);
				backButton.setGraphic(imageView);
			}
			
			else if (butt.equals(logoutButton)) {
				Image image = new Image("resources/icon-sign-out.png");
				ImageView imageView = new ImageView(image);
				imageView.setFitWidth(10);
				imageView.setFitHeight(10);
				logoutButton.setGraphic(imageView);
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
