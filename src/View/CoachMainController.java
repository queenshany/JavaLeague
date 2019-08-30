package View;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import Controller.Validation;
import Exceptions.FirstException;
import Model.Address;
import Model.AssignedPlayer;
import Model.Coach;
import Model.Match;
import Model.MatchPosition;
import Model.Player;
import Model.Stadium;
import Model.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utils.Constants;
import utils.E_Formations;
import utils.E_Levels;
import utils.E_Position;

/**
 * Class CoachMainController ~ Controller of Coach-Main.
 * 
 * @author ID: 205791056
 * @author ID: 312181605
 */
public class CoachMainController extends AllWindowsController {

	// ------------------------------ Variables ------------------------------
	// All Window Vars
	@FXML
	private Tab updateTeamTab, manageMatchFormationTab, generalQueriesTab, prevTeamsTab;
	@FXML
	private Button logoutButton;
	@FXML
	private HBox pane;
	@FXML
	private Label currentTeamDetails;

	private Coach coach;

	private Team team;

	// $$$$$$$$$$$$$$$$$$$$$$$$$$$ Team Table $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
	@FXML
	private TableView<Player> teamTable;
	@FXML
	private TableColumn<Player, Integer> idColumnTeam;
	@FXML
	private TableColumn<Player, String> firstNameColumnTeam;
	@FXML
	private TableColumn<Player, String> lastNameColumnTeam;
	@FXML
	private TableColumn<Player, Long> valueColumnTeam;
	@FXML
	private TableColumn<Player, Boolean> rightLegColumnTeam;
	@FXML
	private TableColumn<Player, Boolean> firstTeamColumnTeam;
	@FXML
	private TableColumn<Player, E_Position> positionColumnTeam;
	@FXML
	private TableColumn<Player, Date> bdayColumnTeam;
	@FXML
	private TableColumn<Player, Date> startWorkColumnTeam;
	@FXML
	private TableColumn<Player, Address> addressColumnTeam;
	@FXML
	private TableColumn<Player, E_Levels> levelColumnTeam;

	@FXML
	private Button addPlayerBut;
	@FXML
	private Button addFirstTeamPlayerBut;
	@FXML
	private Button replaceFirstTeamPlayerBut;
	@FXML
	private Button removePlayerBut;
	@FXML
	private ComboBox<String> comboPlayer;

	private ObservableList<Player> teamPlayers;

	private ArrayList<Player> allPlayerArr = new ArrayList<>();

	// $$$$$$$$$$$$$$$$$$$$$$$$$$$$ General Queries
	// $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

	// ----------------------------PlayMakers Table -----------------------------
	@FXML
	private TableView<Player> playerTable;
	@FXML
	private TableColumn<Player, Integer> idColumnPlayerMaker;
	@FXML
	private TableColumn<Player, String> firstNameColumnPlayerMaker;
	@FXML
	private TableColumn<Player, String> lastNameColumnPlayerMaker;
	@FXML
	private TableColumn<Player, Long> valueColumnPlayerMaker;
	@FXML
	private TableColumn<Player, E_Position> positionColumnPlayerMaker;
	@FXML
	private TableColumn<Player, Boolean> rightFootColumnPlayerMaker;

	// ------------------------ Best First Team Players Table
	// -----------------------
	@FXML
	private TableView<Player> bestTable;
	@FXML
	private TableColumn<Player, Integer> idColumnBest;
	@FXML
	private TableColumn<Player, String> firstNameColumnBest;
	@FXML
	private TableColumn<Player, String> lastNameColumnBest;
	@FXML
	private TableColumn<Player, Long> valueColumnBest;
	@FXML
	private TableColumn<Player, E_Position> positionColumnBest;
	@FXML
	private TableColumn<Player, Boolean> rightFootColumnBest;

	// -------------------------- Labels and TextFields
	// ------------------------------

	@FXML
	private TextField playerMakerField;
	@FXML
	private TextField popularPosField;
	@FXML
	private TextField favoredTeamField;
	@FXML
	private TextField largestCrowdField;
	@FXML
	private Label superPlayerMaker;
	@FXML
	private Label allSuperPlayerMaker;
	@FXML
	private Label mostPopularPos;
	@FXML
	private Label mostFavTeam;
	@FXML
	private Label largestCrowd;
	@FXML
	private Label labelMode;

	// ========================== Prev Teams Table ==========================
	@FXML
	private TableView<Team> prevTeamsTable;
	@FXML
	private TableColumn<Team, Integer> idColumnPrevTeam;
	@FXML
	private TableColumn<Team, String> nameColumnPrevTeam;
	@FXML
	private TableColumn<Team, Integer> valueColumnPrevTeam;
	@FXML
	private TableColumn<Team, E_Levels> levelColumnPrevTeam;
	@FXML
	private TableColumn<Team, Stadium> stadiumColumnPrevTeam;

	private ObservableList<Player> playersSuper;

	// $$$$$$$$$$$$$$$$$$$$$$$$$$$$ Manage Match Formation $$$$$$$$$$$$$$$$$$$$$$$$$$$$$

	@FXML
	private ImageView image;

	@FXML
	private VBox xbox11, xbox12, xbox13, xbox14,
	xbox21, xbox22, xbox23, xbox24, xbox25,
	xbox31, xbox32, xbox33, xbox34;
	@FXML
	private Label label11, label12, label13, label14,
	label21, label22, label23, label24, label25,
	label31, label32, label33, label34;

	private E_Formations formation;
	private HashSet<MatchPosition> matchPositions;
	private ObservableList<AssignedPlayer> assignedPlayers;

	@FXML
	private AnchorPane formationPane;

	@FXML
	private ComboBox<E_Formations> comboFormations;

	@FXML
	private ComboBox<String> comboMatch;

	@FXML
	private TableView<AssignedPlayer> tableFormation;
	@FXML
	private TableColumn<AssignedPlayer, String> columnFormFirstName;
	@FXML
	private TableColumn<AssignedPlayer, String> columnFormLastName;
	@FXML
	private TableColumn<AssignedPlayer, E_Position> columnFormPosition;
	@FXML
	private TableColumn<AssignedPlayer, Integer> columnFormID;
	@FXML
	private TableColumn<AssignedPlayer, String> columnFormPos;

	@FXML
	private TableView<Player> tablePlayers;
	@FXML
	private TableColumn<Player, Boolean> columnIsFirstTeam;
	@FXML
	private TableColumn<Player, Integer> columnIdPlayers;
	@FXML
	private TableColumn<Player, String> columnFirstName;
	@FXML
	private TableColumn<Player, String> columnLastName;
	@FXML
	private TableColumn<Player, Integer> coulumnPlayerValue;
	@FXML
	private TableColumn<Player, E_Position> columnPlayerPosition;

	@FXML
	private Button buttonAddToForm;

	@FXML
	private Button buttonRemoveFromForm;

	@FXML
	private ComboBox<String> comboPosition;

	private ObservableList<Player> unassignedPlayers; // team players without the assigned players per match

	private ArrayList<Match> matches;

	// ------------------------------ Methods ------------------------------
	// ========================== General Methods ==========================
	@Override
	public void initialize() {
		ViewLogic.coachMainController = this;

		idColumnTeam.setCellValueFactory(new PropertyValueFactory<>("id")); // According to variable name
		firstNameColumnTeam.setCellValueFactory(new PropertyValueFactory<>("firstName")); // Same here
		lastNameColumnTeam.setCellValueFactory(new PropertyValueFactory<>("lastName")); // Same here
		valueColumnTeam.setCellValueFactory(new PropertyValueFactory<>("value")); // And here
		rightLegColumnTeam.setCellValueFactory(new PropertyValueFactory<>("isRightLegKicker")); // And here
		firstTeamColumnTeam.setCellValueFactory(new PropertyValueFactory<>("isFirstTeamPlayer")); // And here
		positionColumnTeam.setCellValueFactory(new PropertyValueFactory<>("position")); // And here
		bdayColumnTeam.setCellValueFactory(new PropertyValueFactory<>("birthdate")); // And here
		startWorkColumnTeam.setCellValueFactory(new PropertyValueFactory<>("startWorkingDate")); // And here
		addressColumnTeam.setCellValueFactory(new PropertyValueFactory<>("address"));
		levelColumnTeam.setCellValueFactory(new PropertyValueFactory<>("level"));

		idColumnPlayerMaker.setCellValueFactory(new PropertyValueFactory<>("id")); // According to variable name
		firstNameColumnPlayerMaker.setCellValueFactory(new PropertyValueFactory<>("firstName")); // Same here
		lastNameColumnPlayerMaker.setCellValueFactory(new PropertyValueFactory<>("lastName")); // Same here
		valueColumnPlayerMaker.setCellValueFactory(new PropertyValueFactory<>("value")); // And here
		positionColumnPlayerMaker.setCellValueFactory(new PropertyValueFactory<>("position"));
		rightFootColumnPlayerMaker.setCellValueFactory(new PropertyValueFactory<>("isRightLegKicker"));

		idColumnBest.setCellValueFactory(new PropertyValueFactory<>("id")); // According to variable name
		firstNameColumnBest.setCellValueFactory(new PropertyValueFactory<>("firstName")); // Same here
		lastNameColumnBest.setCellValueFactory(new PropertyValueFactory<>("lastName")); // Same here
		valueColumnBest.setCellValueFactory(new PropertyValueFactory<>("value")); // And here
		positionColumnBest.setCellValueFactory(new PropertyValueFactory<>("position"));
		rightFootColumnBest.setCellValueFactory(new PropertyValueFactory<>("isRightLegKicker"));

		idColumnPrevTeam.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameColumnPrevTeam.setCellValueFactory(new PropertyValueFactory<>("name"));
		valueColumnPrevTeam.setCellValueFactory(new PropertyValueFactory<>("value"));
		levelColumnPrevTeam.setCellValueFactory(new PropertyValueFactory<>("level"));
		stadiumColumnPrevTeam.setCellValueFactory(new PropertyValueFactory<>("stadium"));

		columnFormFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		columnFormLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		columnFormPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
		columnFormID.setCellValueFactory(new PropertyValueFactory<>("id"));
		columnFormPos.setCellValueFactory(new PropertyValueFactory<>("assPosition"));

		columnIdPlayers.setCellValueFactory(new PropertyValueFactory<>("id"));
		columnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		columnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		columnPlayerPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
		coulumnPlayerValue.setCellValueFactory(new PropertyValueFactory<>("value"));
		columnIsFirstTeam.setCellValueFactory(new PropertyValueFactory<>("isFirstTeamPlayer"));

		try {

			coach = ViewLogic.sysData.getCoachs().get(Integer.parseInt(ViewLogic.currentUserID));
			team = coach.getCurrentTeam();

		} catch (NumberFormatException e) {
			currentTeamDetails.setText("");
		} catch (NullPointerException e) {
			currentTeamDetails.setText("");
		}

		if (team != null) {
			currentTeamDetails.setText(team.toString());
			matches = new ArrayList<>(team.getMatches());
		}

		matchPositions = ViewLogic.sysData.getMatchPositions();
		unassignedPlayers = FXCollections.observableArrayList();
		assignedPlayers = FXCollections.observableArrayList();

		setUpdateTeam();
		setGeneralQueries();
		setPrevTeams();

		if (team != null)
			setFormationTab();

	}

	/**
	 * A method that closes the current window
	 */
	@Override
	protected void closeWindow() {
		Stage stage = (Stage) pane.getScene().getWindow();
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				stage.close();

			}
		});
		stage.close();
		ViewLogic.coachMainController = null;
	}

	/**
	 * This method sets Update Team tab
	 */
	private void setUpdateTeam() {
		setTeamTable();
		setComboBox();
	}

	/**
	 * This method updates the teamPlayers table in Update Team tab
	 */
	private void setTeamTable() {

		coach = ViewLogic.sysData.getCoachs().get(Integer.parseInt(ViewLogic.currentUserID));
		team = coach.getCurrentTeam();

		if (team != null) {
			teamPlayers = FXCollections.observableArrayList();
			teamPlayers.setAll(team.getPlayers().keySet());
			teamTable.setItems(teamPlayers);
		}
		teamTable.refresh();
	}

	/**
	 * This method updates the ComboBox in Update Team tab
	 */
	private void setComboBox() {
		ArrayList<String> allPlayers = new ArrayList<>();

		allPlayerArr.clear();
		allPlayerArr.addAll(ViewLogic.sysData.getPlayers().values());

		if (team != null) {
			ArrayList<Player> teamPlayers = new ArrayList<>(team.getPlayers().keySet());
			allPlayerArr.removeAll(teamPlayers);
		}

		for (Player temp : allPlayerArr) {
			allPlayers.add(temp.getId() + " | " + temp.getName() + " | " + temp.getPosition() + " | "
					+ (temp.getCurrentTeam() != null ? temp.getCurrentTeam().getName() : "doesnt belong to a team")
					+ " | Value :" + temp.getValue());
		}
		comboPlayer.getItems().clear();
		comboPlayer.getItems().setAll(allPlayers);
	}

	/**
	 * This method sets the General Queries Tab
	 */
	private void setGeneralQueries() {
		setPlayerMakersTable();
		setBestTable();
		setGeneralFields();
	}

	/**
	 * This method sets the fields in General Queries tab
	 */
	private void setGeneralFields() {
		E_Position position = ViewLogic.sysData.getTheMostPopularPosition();
		Team ft = ViewLogic.sysData.getTheMostFavoredTeam();
		Team lc = ViewLogic.sysData.getTeamWithLargestHomeCrowd();

		if (coach.getCurrentTeam() == null) {
			playerMakerField.setStyle("-fx-text-inner-color: gray;");
			playerMakerField.setText("Coach has no team");
		} else {
			Player p = ViewLogic.sysData.getSuperPlayerMaker(coach.getCurrentTeam().getId());
			if (p == null) {
				playerMakerField.setStyle("-fx-text-inner-color: gray;");
				playerMakerField.setText("There is no such player");
			} else
				playerMakerField.setText(p.toString());

		}
		if (position == null) {
			popularPosField.setStyle("-fx-text-inner-color: gray;");
			popularPosField.setText("There is no position");
		} else
			popularPosField.setText(position.toString());
		if (ft == null) {
			favoredTeamField.setStyle("-fx-text-inner-color: gray;");
			favoredTeamField.setText("There is no team");
		} else
			favoredTeamField.setText(ft.toString());

		if (lc == null) {
			largestCrowdField.setStyle("-fx-text-inner-color: gray;");
			largestCrowdField.setText("There is no team");
		} else
			largestCrowdField.setText(lc.toString());
	}

	/**
	 * This method sets the PlayerMakers table in General Queries Tab
	 */
	private void setPlayerMakersTable() {
		playersSuper = FXCollections.observableArrayList();
		playersSuper.setAll(ViewLogic.sysData.getAllSuperPlayerMakers());
		playerTable.setItems(playersSuper);
		playerTable.refresh();
	}

	/**
	 * This method sets the best first players table in General Queries Tab
	 */
	private void setBestTable() {
		ObservableList<Player> best = FXCollections.observableArrayList();
		best.setAll(ViewLogic.sysData.getFirstPlayersOfBestHomeTeam());
		bestTable.setItems(best);
		bestTable.refresh();
	}

	/**
	 * This method sets the previous teams table in General Queries Tab
	 */
	private void setPrevTeams() {
		ObservableList<Team> prevTeams = FXCollections.observableArrayList();
		prevTeams.setAll(coach.getTeams());
		prevTeamsTable.setItems(prevTeams);
		prevTeamsTable.refresh();
	}

	// ========================== Action Listeners ==========================

	/**
	 * This method adds a player to firstTeamPlayers
	 */
	@FXML
	private void addPlayerToFirstTeam() {
		Player player = teamTable.getSelectionModel().getSelectedItem();

		if (player == null) {
			Validation.alert("Player Error!", "Please choose a Player");
		} else {
			// adding player to first team
			try {
				if (ViewLogic.sysData.addPlayerToTeamFirstPlayers(player.getId(), team.getId())) {
					setTeamTable();
					setGeneralQueries();

					tablePlayers.refresh();

					Validation.info("Player Added to FirstTeamPlayers Successfully!",
							player.getId() + " | " + player.getName() + " was added successfully to FirstTeamPlayers!");

				} else 
					Validation.alert("Cannot Add Player to FirstTeamPlayers!",
							"addPlayerToTeamFirstPlayers returned false.");

			}catch(FirstException e) {
				Validation.alert(e.getMessage());
			}

		}
	}


	/**
	 * This method adds a new player to the teamTable
	 */
	@FXML
	private void addPlayer() {
		int index = comboPlayer.getSelectionModel().getSelectedIndex();
		Player player = null;
		if (index != -1)
			player = allPlayerArr.get(index);

		if (team.getPlayers().size() < Constants.MAX_PLAYERS_FOR_TEAM) {
			if (player != null) {
				if (team.getPlayers().containsKey(player))
					Validation.alert("The player already exists in the team or has the same seniority");

				else if (ViewLogic.sysData.addPlayerToTeam(player.getId(), team.getId())) {
					setUpdateTeam();
					setComboBox();
					setGeneralQueries();
					tablePlayers.getItems().add(player);

				} else {
					Validation.alert("The team cannot have two players with the same seniority");

				}

			} else
				Validation.alert("Please choose a player to add.");

		} else {
			Validation.alert("Team is full");

		}

	}

	/**
	 * This method removes a player from the teamTable
	 */
	@FXML
	private void removePlayer() {
		// ObservableList<Player> playersSelected, allplayers;
		teamPlayers = teamTable.getItems();
		// ArrayList<Player> prevPlayers = new ArrayList<>();


		Player player = teamTable.getSelectionModel().getSelectedItem();
		// prevPlayers.add(playersSelected);

		if (player == null) {
			Validation.alert("Player Error", "Please choose a Player");
		} else {
			// removing player succeeded
			if (team.removePlayer(player)) {
				setComboBox();
				setGeneralQueries();
				teamTable.getItems().remove(player);
				teamPlayers.remove(player);

				// remove player from Formation table
				if (tableFormation.getItems().contains(player))
					tableFormation.getItems().remove(player);
				// remove player from unassigned players table
				else 
					tablePlayers.getItems().remove(player);

				AssignedPlayer assPlayer = new AssignedPlayer(player.getId());

				// removing player from match position
				for (MatchPosition mp : ViewLogic.sysData.getMatchPositions()) 
					mp.removeAssignedPlayer(assPlayer);

				// resetting the formation info
				initFormationInfo();

				Validation.info("Removed Successfully!", player.getId() + " | " + player.getName() + " was successfully removed!");

				// case there is no players in the team
				if (teamPlayers.isEmpty())
					buttonAddToForm.setDisable(true);

			} else {
				Validation.alert("Cannot Remove Player", "Error occurred.");
			}
		}
	}

	/**
	 * This method enables buttons when table is clicked
	 */
	@FXML
	private void tableOnClick() {

		if (replaceFirstTeamPlayerBut.isDisable()) {
			removePlayerBut.setDisable(false);
			addFirstTeamPlayerBut.setDisable(false);
		}
	}

	/**
	 * This method replaces a player from firstTeamPlayers
	 */
	@FXML
	private void replaceFirstTeamPlayer() {
		ArrayList<Player> selectedPlayers = new ArrayList<>();
		selectedPlayers.addAll(teamTable.getSelectionModel().getSelectedItems());

		if (selectedPlayers.size() == 2) {

			if (selectedPlayers.get(0).getIsFirstTeamPlayer()) {
				if (team.replacePlayerOfFirstTeam(selectedPlayers.get(0), selectedPlayers.get(1))) {
					setTeamTable();
					setGeneralQueries();
					tablePlayers.refresh();

				} else
					Validation.alert("Replace Error", "Cannot replace these players");

			} else if (team.replacePlayerOfFirstTeam(selectedPlayers.get(1), selectedPlayers.get(0))) {
				setTeamTable();
				setGeneralQueries();
				tablePlayers.refresh();
			} else
				Validation.alert("Replace Error", "Can't replace these players");

		} else
			Validation.alert("Replace Error", "Please choose two players to replace");
	}

	/**
	 * Activates or disables replace mode.
	 * 
	 * replace mode allows the coach to replace players from the first team with
	 * players from the second team.
	 * 
	 * @param event
	 */
	@FXML
	private void replaceOption(KeyEvent event) {
		// case the user press the TAB key
		if (event.getCode() == KeyCode.ALT) {

			// case replaced mode is disabled
			if (replaceFirstTeamPlayerBut.isDisabled()) {

				// changes the selection model
				teamTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

				// and allows to replace first team players
				replaceFirstTeamPlayerBut.setDisable(false);

				// disables all the buttons that are relevant to the team
				addFirstTeamPlayerBut.setDisable(true);
				addPlayerBut.setDisable(true);
				removePlayerBut.setDisable(true);
				comboPlayer.setDisable(true);

				labelMode.setText("To leave replace mode press ALT");

			}
			// case replaced mode is enabled
			else {
				// changes the selection model
				teamTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

				// and cancels the replace option
				replaceFirstTeamPlayerBut.setDisable(true);

				// enables all the buttons that are relevant to the team
				addPlayerBut.setDisable(false);
				comboPlayer.setDisable(false);

				removePlayerBut.setDisable(false);
				addFirstTeamPlayerBut.setDisable(false);

				labelMode.setText("To enter replace mode press ALT");
			}
		}
	}

	// ========================== Formation ==========================
	/**
	 * Sets the Formation Tab
	 */
	private void setFormationTab() {
		// setting the team's matches combo box
		for (Match match : matches)
			if (match.getHomeTeam().getStadium() != null)
				comboMatch.getItems().add(match.showSumDetails());

		// setting the formation combo box
		comboFormations.getItems().setAll(E_Formations.values());

		// setting tables with data
		tableFormation.setItems(assignedPlayers); // table in the left
		unassignedPlayers.setAll(teamPlayers); // table in the bottom
		tablePlayers.setItems(unassignedPlayers);
		tablePlayers.refresh();

		// setting the match position combo box
		comboPosition.getItems().setAll("A", "B", "C", "D", "G");
	}

	/**
	 * Setting the formation image with labels and images per formation chosen
	 * @see initializeFormationGrid
	 */
	@FXML
	private void initializeFormation() {		
		this.formation = comboFormations.getValue();

		// hiding all the elements in the grid
		xbox11.setVisible(false);
		xbox12.setVisible(false);
		xbox13.setVisible(false);
		xbox14.setVisible(false);
		xbox21.setVisible(false);
		xbox22.setVisible(false);
		xbox23.setVisible(false);
		xbox24.setVisible(false);
		xbox25.setVisible(false);
		xbox31.setVisible(false);
		xbox32.setVisible(false);
		xbox33.setVisible(false);
		xbox34.setVisible(false);

		// if formation is chosen, we set the image based on it
		if (formation != null) {
			switch (formation) {

			case BOX:  //2-0-2
				xbox12.setVisible(true);
				xbox32.setVisible(true);
				xbox14.setVisible(true);
				xbox34.setVisible(true);
				xbox25.setVisible(true);
				//Initialize Labels
				label12.setText("A");
				label32.setText("B");
				label14.setText("C");
				label34.setText("D");
				label25.setText("G");
				break;

			case DIAMOND:  //1-2-1
				xbox21.setVisible(true);
				xbox12.setVisible(true);
				xbox32.setVisible(true);
				xbox23.setVisible(true);
				xbox25.setVisible(true);
				//Initialize Labels
				label21.setText("A");
				label12.setText("B");
				label32.setText("C");
				label23.setText("D");
				label25.setText("G");
				break;

			case PYRAMID:  //2-1-1
				xbox21.setVisible(true);
				xbox23.setVisible(true);
				xbox14.setVisible(true);
				xbox34.setVisible(true);
				xbox25.setVisible(true);
				//Initialize Labels
				label21.setText("A");
				label23.setText("B");
				label14.setText("C");
				label34.setText("D");
				label25.setText("G");
				break;

			case Y: //1-1-2 ; to Yoav, with love <3 8)
				xbox11.setVisible(true);
				xbox31.setVisible(true);
				xbox22.setVisible(true);
				xbox24.setVisible(true);
				xbox25.setVisible(true);
				//Initialize Labels
				label11.setText("A");
				label31.setText("B");
				label22.setText("C");
				label24.setText("D");
				label25.setText("G");
				break;
			}

			// getting match data, so if a match has a formation already, it can be applied
			if (comboMatch.getValue() != null) {
				Match match = ViewLogic.sysData.getMatchs().get(Integer.parseInt(comboMatch.getValue().split(" ")[0]));
				initializeFormationGrid(match);
			}
		}
	}

	/**
	 * Allows the use of the add to assign players operation
	 * 
	 */
	@FXML
	private void teamOnClick() {
		if (tablePlayers.getSelectionModel().getSelectedItem() != null) {
			buttonAddToForm.setDisable(false);
			comboPosition.setDisable(false);
		}
	}

	/**
	 * Allows to remove player from the squad
	 * 
	 */
	@FXML
	private void squadOnClick() {
		if (tableFormation.getSelectionModel().getSelectedItem() != null)
			buttonRemoveFromForm.setDisable(false);
	}

	/**
	 * Getting the details of match, formation, position and player to be assigned
	 * @see assignPlayer
	 */
	@FXML
	private void getDetailsBeforeAdd() {
		if (comboMatch.getValue() == null) {
			Validation.alert("Match Error", "Please choose a match from the combo box");
			return;
		}
		else if (formation == null) {
			Validation.alert("Formation Error", "Please choose a formation from the combo box");
			return;
		}

		Match match = ViewLogic.sysData.getMatchs().get(Integer.parseInt(comboMatch.getValue().split(" ")[0]));
		String assPosition = comboPosition.getValue();
		Player player = tablePlayers.getSelectionModel().getSelectedItem();

		if (match == null) {
			Validation.alert("Match Error", "Please choose a match from the combo box");
			return;
		}
		else if (assPosition == null) {
			Validation.alert("Position Error", "Please choose a position from the combo box");
			return;
		}
		else if (player == null) {
			Validation.alert("Player Error", "Please choose a player from the table"); 
			return;
		}
		else
			assignPlayer(match, player, assPosition, true);
	}

	/**
	 * Assigning the player to the squad
	 * @param match
	 * @param player
	 * @param assPosition
	 * @param updateLists
	 * @see assignToFormationGrid
	 */
	private void assignPlayer(Match match, Player player, String assPosition, boolean updateLists) {
		MatchPosition matchPosition = null;

		// getting match position data, if exists
		for (MatchPosition mp : matchPositions)
			if (match.equals(mp.getMatch()) && team.equals(mp.getTeam())) {
				matchPosition = mp;
				break;
			}

		// creating new match position data, if not exists
		if (matchPosition == null) {
			matchPosition = new MatchPosition(formation, match, team);
			// adding to the hashset in sysdata
			ViewLogic.sysData.addMatchPos(matchPosition);
			//
			matchPositions = ViewLogic.sysData.getMatchPositions();
		}

		AssignedPlayer assignedPlayer = new AssignedPlayer(player, assPosition);

		if (matchPosition.containsAssignedPlayer(assignedPlayer))
			removePlayerAssignment(match, assignedPlayer, assPosition, false);

		// setting assigned player data, based on the selected position
		if (assPosition.equals("A")) {
			if (matchPosition.getPlayerA() != null)
				removePlayerAssignment(match, matchPosition.getPlayerA(), assPosition, true);
			matchPosition.setPlayerA(assignedPlayer);
		}
		else if (assPosition.equals("B")) {
			if (matchPosition.getPlayerB() != null)
				removePlayerAssignment(match, matchPosition.getPlayerB(), assPosition, true);
			matchPosition.setPlayerB(assignedPlayer);
		}
		else if (assPosition.equals("C")) {
			if (matchPosition.getPlayerC() != null)
				removePlayerAssignment(match, matchPosition.getPlayerC(), assPosition, true);
			matchPosition.setPlayerC(assignedPlayer);
		}
		else if (assPosition.equals("D")) {
			if (matchPosition.getPlayerD() != null)
				removePlayerAssignment(match, matchPosition.getPlayerD(), assPosition, true);
			matchPosition.setPlayerD(assignedPlayer);
		}
		else if (assPosition.equals("G")) {
			if (matchPosition.getPlayerG() != null)
				removePlayerAssignment(match, matchPosition.getPlayerG(), assPosition, true);
			matchPosition.setPlayerG(assignedPlayer);
		}

		// updating the tables
		if (updateLists) {
			unassignedPlayers.remove(player);
			tablePlayers.refresh();
			assignedPlayers.add(assignedPlayer);
			tableFormation.refresh();
		}

		assignToFormationGrid(assignedPlayer, assPosition);
	}

	/**
	 * Adding player's name to the formation gridpane, based on his position
	 * @param assPlayer
	 * @param assPosition
	 */
	private void assignToFormationGrid(AssignedPlayer assPlayer, String assPosition) {
		switch (formation) {

		case BOX:
			if (assPosition.equals("A"))
				label12.setText(assPlayer.getLastName() + " " + assPlayer.getFirstName().substring(0, 1) + ".");
			else if (assPosition.equals("B"))
				label32.setText(assPlayer.getLastName() + " " + assPlayer.getFirstName().substring(0, 1) + ".");
			else if (assPosition.equals("C"))
				label14.setText(assPlayer.getLastName() + " " + assPlayer.getFirstName().substring(0, 1) + ".");
			else if (assPosition.equals("D"))
				label34.setText(assPlayer.getLastName() + " " + assPlayer.getFirstName().substring(0, 1) + ".");
			else if (assPosition.equals("G"))
				label25.setText(assPlayer.getLastName() + " " + assPlayer.getFirstName().substring(0, 1) + ".");
			break;

		case DIAMOND: 
			if (assPosition.equals("A"))
				label21.setText(assPlayer.getLastName() + " " + assPlayer.getFirstName().substring(0, 1) + ".");
			else if (assPosition.equals("B"))
				label12.setText(assPlayer.getLastName() + " " + assPlayer.getFirstName().substring(0, 1) + ".");
			else if (assPosition.equals("C"))
				label32.setText(assPlayer.getLastName() + " " + assPlayer.getFirstName().substring(0, 1) + ".");
			else if (assPosition.equals("D"))
				label23.setText(assPlayer.getLastName() + " " + assPlayer.getFirstName().substring(0, 1) + ".");
			else if (assPosition.equals("G"))
				label25.setText(assPlayer.getLastName() + " " + assPlayer.getFirstName().substring(0, 1) + ".");
			break;

		case PYRAMID: 
			if (assPosition.equals("A"))
				label21.setText(assPlayer.getLastName() + " " + assPlayer.getFirstName().substring(0, 1) + ".");
			else if (assPosition.equals("B"))
				label23.setText(assPlayer.getLastName() + " " + assPlayer.getFirstName().substring(0, 1) + ".");
			else if (assPosition.equals("C"))
				label14.setText(assPlayer.getLastName() + " " + assPlayer.getFirstName().substring(0, 1) + ".");
			else if (assPosition.equals("D"))
				label34.setText(assPlayer.getLastName() + " " + assPlayer.getFirstName().substring(0, 1) + ".");
			else if (assPosition.equals("G"))
				label25.setText(assPlayer.getLastName() + " " + assPlayer.getFirstName().substring(0, 1) + ".");
			break;

		case Y: 
			if (assPosition.equals("A"))
				label11.setText(assPlayer.getLastName() + " " + assPlayer.getFirstName().substring(0, 1) + ".");
			else if (assPosition.equals("B"))
				label31.setText(assPlayer.getLastName() + " " + assPlayer.getFirstName().substring(0, 1) + ".");
			else if (assPosition.equals("C"))
				label22.setText(assPlayer.getLastName() + " " + assPlayer.getFirstName().substring(0, 1) + ".");
			else if (assPosition.equals("D"))
				label24.setText(assPlayer.getLastName() + " " + assPlayer.getFirstName().substring(0, 1) + ".");
			else if (assPosition.equals("G"))
				label25.setText(assPlayer.getLastName() + " " + assPlayer.getFirstName().substring(0, 1) + ".");
			break;
		}
	}

	/**
	 * Getting the assigned players details, before we remove him
	 * @see removePlayerAssignment
	 */
	@FXML
	private void getDetailsBeforeRemove() {
		if (comboMatch.getValue() == null) {
			Validation.alert("Match Error", "Please choose a match from the combo box");
			return;
		}
		else if (formation == null) {
			Validation.alert("Formation Error", "Please choose a formation from the combo box");
			return;
		}

		Match match = ViewLogic.sysData.getMatchs().get(Integer.parseInt(comboMatch.getValue().split(" ")[0]));
		//String assPosition = comboPosition.getValue();
		AssignedPlayer assPlayer = tableFormation.getSelectionModel().getSelectedItem();

		if (match == null) {
			Validation.alert("Match Error", "Please choose a match from the combo box");
			return;
		}
		else if (assPlayer == null) {
			Validation.alert("Player Error", "Please choose a player from the Formation table");
			return;
		}
		else
			removePlayerAssignment(match, assPlayer, assPlayer.getAssPosition(), true);
	}

	/**
	 * Removing Player assignment from the squad
	 * @param match
	 * @param player
	 * @param assPosition
	 * @param updateLists
	 * @see removeAssignmentFromGrid
	 */
	private void removePlayerAssignment(Match match, Player player, String assPosition, boolean updateLists) {

		MatchPosition matchPosition = null;

		for (MatchPosition mp : matchPositions)
			if (match.equals(mp.getMatch()) && team.equals(mp.getTeam())) {
				matchPosition = mp;
				break;
			}

		if (matchPosition == null) {
			Validation.alert("Match Position Error", "Match position doesn't exist");
			return;
		}

		AssignedPlayer assignedPlayer = new AssignedPlayer(player, assPosition);

		// removing the player from his position
		if (assPosition.equals("A"))
			matchPosition.setPlayerA(null);
		else if (assPosition.equals("B"))
			matchPosition.setPlayerB(null);
		else if (assPosition.equals("C"))
			matchPosition.setPlayerC(null);
		else if (assPosition.equals("D"))
			matchPosition.setPlayerD(null);
		else if (assPosition.equals("G"))
			matchPosition.setPlayerG(null);

		// updating the tables
		if (updateLists) {
			assignedPlayers.remove(assignedPlayer);
			tableFormation.refresh();
			unassignedPlayers.add(player);
			tablePlayers.refresh();
		}

		removeAssignmentFromGrid(assignedPlayer);
	}

	/**
	 * Removing player assignment from the gridpane
	 * @param assPlayer
	 */
	private void removeAssignmentFromGrid(AssignedPlayer assPlayer) {

		String playerName = assPlayer.getLastName() + " " + assPlayer.getFirstName().substring(0, 1) + ".";

		switch (formation) {

		case BOX:
			if (label12.getText().equals(playerName))
				label12.setText("A");
			else if (label32.getText().equals(playerName))
				label32.setText("B");
			else if (label14.getText().equals(playerName))
				label14.setText("C");
			else if (label34.getText().equals(playerName))
				label34.setText("D");
			else if (label25.getText().equals(playerName))
				label25.setText("G");
			break;

		case DIAMOND: 
			if (label21.getText().equals(playerName))
				label21.setText("A");
			else if (label12.getText().equals(playerName))
				label12.setText("B");
			else if (label32.getText().equals(playerName))
				label32.setText("C");
			else if (label23.getText().equals(playerName))
				label23.setText("D"); 
			else if (label25.getText().equals(playerName))
				label25.setText("G");
			break;

		case PYRAMID: 
			if (label21.getText().equals(playerName))
				label21.setText("A");
			else if (label23.getText().equals(playerName))
				label23.setText("B");
			else if (label14.getText().equals(playerName))
				label14.setText("C");
			else if (label34.getText().equals(playerName))
				label34.setText("D");
			else if (label25.getText().equals(playerName))
				label25.setText("G");
			break;

		case Y: 
			if (label11.getText().equals(playerName))
				label11.setText("A");
			else if (label31.getText().equals(playerName))
				label31.setText("B");
			else if (label22.getText().equals(playerName))
				label22.setText("C");
			else if (label24.getText().equals(playerName))
				label24.setText("D");
			else if (label25.getText().equals(playerName))
				label25.setText("G");
			break;
		}
	}

	/**
	 * Initializing the formation grid based on a given match
	 * @param match
	 */
	private void initializeFormationGrid(Match match) {
		if (match == null) {
			//System.out.println("Match Null");
			return;
		}

		MatchPosition matchPosition = null;

		// getting the match position
		for (MatchPosition mp : matchPositions)
			if (match.equals(mp.getMatch()) && team.equals(mp.getTeam())) {
				matchPosition = mp;
				break;
			}

		if (matchPosition == null) {
			//System.out.println("Match Position Null");
			return;
		}

		assignedPlayers.clear();
		unassignedPlayers.setAll(teamPlayers);

		if (matchPosition.getPlayerA() != null) {
			assignToFormationGrid(matchPosition.getPlayerA(), "A");
			unassignedPlayers.remove(ViewLogic.sysData.getPlayers().get(matchPosition.getPlayerA().getId()));
			assignedPlayers.add(matchPosition.getPlayerA());
		}
		if (matchPosition.getPlayerB() != null) {
			assignToFormationGrid(matchPosition.getPlayerB(), "B");
			unassignedPlayers.remove(ViewLogic.sysData.getPlayers().get(matchPosition.getPlayerB().getId()));
			assignedPlayers.add(matchPosition.getPlayerB());
		}
		if (matchPosition.getPlayerC() != null) {
			assignToFormationGrid(matchPosition.getPlayerC(), "C");
			unassignedPlayers.remove(ViewLogic.sysData.getPlayers().get(matchPosition.getPlayerC().getId()));
			assignedPlayers.add(matchPosition.getPlayerC());
		}
		if (matchPosition.getPlayerD() != null) {
			assignToFormationGrid(matchPosition.getPlayerD(), "D");
			unassignedPlayers.remove(ViewLogic.sysData.getPlayers().get(matchPosition.getPlayerD().getId()));
			assignedPlayers.add(matchPosition.getPlayerD());
		}
		if (matchPosition.getPlayerG() != null) {
			assignToFormationGrid(matchPosition.getPlayerG(), "G");
			unassignedPlayers.remove(ViewLogic.sysData.getPlayers().get(matchPosition.getPlayerG().getId()));
			assignedPlayers.add(matchPosition.getPlayerG());
		}





		//tableFormation.getItems().setAll(assignedPlayers);
		tableFormation.refresh();
		tablePlayers.refresh();
	}

	/**
	 * Resetting the formation tab info
	 */
	@FXML
	private void initFormationInfo() {
		if (comboMatch.getValue() == null) {
			System.out.println("Match Null");
			clearFormationData();
			return;
		}

		int matchID = Integer.parseInt(comboMatch.getValue().split(" ")[0]);
		MatchPosition matchPosition = null;

		for (MatchPosition mp : ViewLogic.sysData.getMatchPositions())
			if (matchID == mp.getMatch().getId()) {
				matchPosition = mp;
				break;
			}

		if (matchPosition == null) {
			System.out.println("Match Position Null");
			clearFormationData();
			return;
		}

		E_Formations formation = matchPosition.getFormation();

		if (formation == null) {
			System.out.println("Formation Null");
			clearFormationData();
		}
		else {
			comboFormations.setValue(formation);
			initializeFormation();
		}
	}

	/**
	 * This method clears the formation tab data
	 */
	private void clearFormationData() {
		assignedPlayers.clear();
		unassignedPlayers.setAll(teamPlayers);
		tableFormation.refresh();
		tablePlayers.refresh();
		comboFormations.getSelectionModel().clearSelection();
		initializeFormation();
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

			if (butt.equals(logoutButton)) {
				Image image = new Image("resources/icon-sign-out-white.png");
				ImageView imageView = new ImageView(image);
				imageView.setFitWidth(10);
				imageView.setFitHeight(10);
				logoutButton.setGraphic(imageView);
			}
		} catch (Exception ex) {
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

			if (butt.equals(logoutButton)) {
				Image image = new Image("resources/icon-sign-out.png");
				ImageView imageView = new ImageView(image);
				imageView.setFitWidth(10);
				imageView.setFitHeight(10);
				logoutButton.setGraphic(imageView);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
