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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Constants;
import utils.E_Formations;
import utils.E_Levels;
import utils.E_Position;

/**
 * Class AdminTeamDetailsController ~ Admin Team Details window.
 * 
 * @author ID: 205791056
 * @author ID: 312181605
 */
public class AdminTeamDetailsController extends AllWindowsController {
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
	private ComboBox<String> comboStadium;
	@FXML
	private TextField coachText;
	@FXML
	private ComboBox<String> comboCoaches;
	@FXML
	private TextField textSuper;
	@FXML
	private Button removeButton;
	@FXML
	private Button addPlayer;
	@FXML
	private Button addFirstTeamPlayerBut;
	@FXML
	private Button replaceFirstTeamPlayerBut;
	@FXML
	private ComboBox<String> comboPlayers;
	@FXML
	private Label alertLabel;
	@FXML
	private Button submitButton;
	@FXML
	private Label labelMode;


	// ========================== Team's players DB Table ==========================
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
	private TableColumn<Player, Date> workingDateColumnTeam;
	@FXML
	private TableColumn<Player, Address> addressColumTeam;

	private ArrayList<Player> teamPlayers;
	// =================================== formation Variables =====================================
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

	// =================================== class Variables =====================================

	private Stadium stadium;

	private ArrayList<Stadium> allStadiums;

	private ArrayList<Player> allPlayers;

	private ArrayList<Coach> allCoaches;



	// ------------------------------ Methods ------------------------------
	// ========================== General Methods ==========================

	@Override
	public void initialize() {

		if (AdminTeamController.selectedTeam.getCoach() != null)
			coachText.setText(AdminTeamController.selectedTeam.getCoach().toString());

		nameText.setText(AdminTeamController.selectedTeam.getName());
		valueTextField.setText(Integer.toString(AdminTeamController.selectedTeam.getValue()));

		setStadiums();
		setCoachs();
		setPlayMaker();
		setComboPlayers();

		idColumnTeam.setCellValueFactory(new PropertyValueFactory<>("id"));
		firstNameColumnTeam.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		lastNameColumnTeam.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		valueColumnTeam.setCellValueFactory(new PropertyValueFactory<>("value"));
		rightLegColumnTeam.setCellValueFactory(new PropertyValueFactory<>("isRightLegKicker"));
		firstTeamColumnTeam.setCellValueFactory(new PropertyValueFactory<>("isFirstTeamPlayer"));
		positionColumnTeam.setCellValueFactory(new PropertyValueFactory<>("position"));
		bdayColumnTeam.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
		workingDateColumnTeam.setCellValueFactory(new PropertyValueFactory<>("startWorkingDate"));
		addressColumTeam.setCellValueFactory(new PropertyValueFactory<>("address"));
		setTable();

		comboLevel.getItems().setAll(E_Levels.values());
		comboLevel.getSelectionModel().select(AdminTeamController.selectedTeam.getLevel());
		nameText.setText(AdminTeamController.selectedTeam.getName());

		idText.setText(Integer.toString(AdminTeamController.selectedTeam.getId()));

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

		matchPositions = ViewLogic.sysData.getMatchPositions();
		unassignedPlayers = FXCollections.observableArrayList();
		assignedPlayers = FXCollections.observableArrayList();

		matches = new ArrayList<>(AdminTeamController.selectedTeam.getMatches());
		setFormationTab();

	}

	/**
	 * Setting players table
	 */
	private void setTable() {
		teamPlayers = new ArrayList<>(AdminTeamController.selectedTeam.getPlayers().keySet());
		teamTable.getItems().setAll(teamPlayers);
		teamTable.refresh();

	}

	/**
	 * setting all players combo box
	 */
	private void setComboPlayers() {
		Team team = AdminTeamController.selectedTeam;

		allPlayers = new ArrayList<>(ViewLogic.sysData.getPlayers().values());
		/*allPlayers.removeAll(AdminTeamController.selectedTeam.getPlayers().keySet());*/

		ArrayList<Player> tempA = new ArrayList<> (team.getPlayers().keySet());
		allPlayers.removeAll(tempA);

		ObservableList<String> playerStr = FXCollections.observableArrayList();

		for (Player temp : allPlayers)
			playerStr.add(temp.getId() + " | " + temp.getFirstName() + " " + temp.getLastName());

		comboPlayers.setItems(playerStr);

	}

	/**
	 * setting team's playMaker
	 */
	private void setPlayMaker() {


		Player player = ViewLogic.sysData.getSuperPlayerMaker(AdminTeamController.selectedTeam.getId());

		if (player != null)
			textSuper.setText(player.toString());
		else {
			textSuper.setStyle("-fx-text-inner-color: gray;");
			textSuper.setText("There is no such player");
		}

	}

	/**
	 * setting all coaches combo box
	 */
	private void setCoachs() {

		allCoaches = new ArrayList<>(ViewLogic.sysData.getCoachs().values());
		allCoaches.remove(AdminTeamController.selectedTeam.getCoach());

		ArrayList<String> coachStr = new ArrayList<>();

		for (Coach temp : allCoaches)
			coachStr.add(temp.getId() + " | Name: " + temp.getFirstName() + " " + temp.getLastName() + " | Level: " + temp.getLevel());

		comboCoaches.getItems().setAll(coachStr);
	}

	/**
	 * setting all stadiums combo box
	 */
	private void setStadiums() {




		if (AdminTeamController.selectedTeam.getStadium() != null) 
			this.stadium = AdminTeamController.selectedTeam.getStadium(); 

		allStadiums = new ArrayList<>();
		allStadiums.addAll( ViewLogic.sysData.getStadiums().values());
		ArrayList<String> stadiumStr = new ArrayList<>();

		if (stadium != null) {

			allStadiums.remove(stadium);
			comboStadium.getSelectionModel().select(stadium.getId() + " | " + stadium.getName());

		}


		for (Stadium temp : allStadiums)
			if (!allStadiums.isEmpty())
				if (temp.getTeams().size() < Constants.MAX_TEAMS_FOR_STADIUM )
					stadiumStr.add(temp.getId() + " | " + temp.getName());


		comboStadium.getItems().setAll(stadiumStr);




	}

	@Override
	protected void closeWindow() {
		Stage stage = (Stage) idText.getScene().getWindow();
		AdminTeamController.selectedTeam = null;
		stage.close();

	}

	// ========================== Action Listeners ==========================
	/**
	 * adds a player to the team from the market
	 */
	@FXML
	private void addPlayer() {
		int index = comboPlayers.getSelectionModel().getSelectedIndex();
		Player player = null;
		if (index != -1)
			player = allPlayers.get(index);

		Team team = AdminTeamController.selectedTeam;

		alertLabel.setText("");



		if (team.getPlayers().size() < Constants.MAX_PLAYERS_FOR_TEAM) {
			if (player != null) {
				if (team.getPlayers().containsKey(player)) 
					alertLabel.setText("The player already exists in the team or has the same seniority");

				else if (ViewLogic.sysData.addPlayerToTeam(player.getId(), team.getId())) {
					setTable();
					setComboPlayers();
					setPlayMaker();

				} else {
					alertLabel.setText("The team cannot have two players with the same seniority");

				}

			}else
				alertLabel.setText("Please choose a player to add.");


		} else {
			alertLabel.setText("Team is full");

		}

	}

	/**
	 * This method enables buttons when table is clicked
	 */
	@FXML
	private void tableOnClick() {

		if (replaceFirstTeamPlayerBut.isDisable()) {	
			removeButton.setDisable(false);
			addFirstTeamPlayerBut.setDisable(false);
		}
	}

	/**
	 * removes the selected player from the team
	 */
	@FXML
	private void removePlayer(ActionEvent event) {
		Player player = teamTable.getSelectionModel().getSelectedItem();
		Team team = AdminTeamController.selectedTeam;

		if (player != null) {
			if (team.removePlayer(player)) {
				setTable();
				setComboPlayers();
				setPlayMaker();

			}
		}else
			alertLabel.setText("Please choose player to remove.");
	}

	/**
	 * update the team if all is valid
	 */
	@FXML
	private void save() {

		// Getting users input
		Team team = AdminTeamController.selectedTeam;

		String name = nameText.getText(), valueStr = valueTextField.getText();

		E_Levels level = comboLevel.getSelectionModel().getSelectedItem();


		int indexStd = comboStadium.getSelectionModel().getSelectedIndex();
		Stadium stadium = null;

		if (indexStd != -1)
			stadium = allStadiums.get(indexStd);
		else if(indexStd == -1 && this.stadium != null)
			stadium = this.stadium;

		int indexC = comboCoaches.getSelectionModel().getSelectedIndex();
		Coach coach = null;
		if (indexC != -1)
			coach = allCoaches.get(indexC);

		// Validates the user selected a level

		if(Validation.validName(name)) {
			if (level != null) {
				try {

					// Validates value
					int value = Integer.parseInt(valueStr);

					if (value > 0) {
						if (stadium != null) {

							if (coach == null || ViewLogic.sysData.addCoachToTeam(coach.getId(), team.getId()) ) {

								if (this.stadium != null)
									this.stadium.removeTeam(team);

								stadium.addTeam(team);
								team.setLevel(level);
								team.setName(name);

								team.setStadium(stadium);
								team.setValue(value);
								setCoachs();
								setStadiums();
								setPlayMaker();

								showCoachDetails();
								setStadiums();

								alertLabel.setText("Team details were changed successfully");
								ViewLogic.adminTeamController.setTeamTable();
								ViewLogic.adminTeamController.setPlayersTable();;
								ViewLogic.adminTeamController.setSuperPlayersTable();;


							} else
								alertLabel.setText("Coach's level is not high enough");

						}else
							alertLabel.setText("Please select a stadium");


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
		}else
			alertLabel.setText("Invalid name");

	}

	/**
	 * This method adds a player to firstTeamPlayers
	 */
	@FXML
	private void addPlayerToFirstTeam(ActionEvent event) {
		Player player = teamTable.getSelectionModel().getSelectedItem();

		if (player == null) {
			Validation.alert("Player Error!", "Please choose a Player");
		} else {
			
			try {
			// adding player to first team
			if (ViewLogic.sysData.addPlayerToTeamFirstPlayers(player.getId(),
					AdminTeamController.selectedTeam.getId())) {
				setTable();
				setPlayMaker();
				// teamTable.refresh();

				Validation.info("Player Added to FirstTeamPlayers Successfully!", player.getId() + " | " + player.getName() + " was added successfully to FirstTeamPlayers!");


			} else {
				Validation.alert("Cannot Add Player to FirstTeamPlayers!", "addPlayerToTeamFirstPlayers returned false.");
			}
			
			}catch(FirstException e) {
				Validation.alert(e.getMessage());
			}
		}
	}
	/**
	 *The method  replaces chosen player
	 * from the first team with a chosen player from 
	 * second team
	 * @param event
	 */

	@FXML
	private void replaceFirstTeamPlayer(ActionEvent event) {
		ArrayList<Player> selectedPlayers = new ArrayList<>();
		selectedPlayers.addAll( teamTable.getSelectionModel().getSelectedItems());

		if (selectedPlayers.size() == 2) {

			if (selectedPlayers.get(0).getIsFirstTeamPlayer()) {	
				if (AdminTeamController.selectedTeam.replacePlayerOfFirstTeam(selectedPlayers.get(0), selectedPlayers.get(1))){
					setTable();
					setPlayMaker();
					alertLabel.setText("changed successfully");

				}else
					alertLabel.setText("Cant change players those players ");

			}else
				if(AdminTeamController.selectedTeam.replacePlayerOfFirstTeam(selectedPlayers.get(1), selectedPlayers.get(0))){
					setTable();
					setPlayMaker();
					alertLabel.setText("changed successfully");
				}else
					alertLabel.setText("Cant change players those players ");

		}else
			alertLabel.setText("Choose two players to change");
	}

	/**
	 * Activates or disables replace mode.
	 * 
	 * replace mode allows the admin to replace players from the first team
	 * with players from the second team. 
	 * @param event
	 */
	@FXML
	private void replaceOption(KeyEvent event) {
		//case the user press the TAB key
		if (event.getCode() == KeyCode.ALT) {

			//case replaced mode is disabled
			if(replaceFirstTeamPlayerBut.isDisabled()) {

				//changes the selection model
				teamTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

				//and allows to replace first team players
				replaceFirstTeamPlayerBut.setDisable(false);

				//Disables all the buttons that are relevent to the team 
				addFirstTeamPlayerBut.setDisable(true);
				addPlayer.setDisable(true);
				removeButton.setDisable(true);
				submitButton.setDisable(true);
				comboPlayers.setDisable(true);


				labelMode.setText("To leave replace mode press ALT");

			}
			//case replaced mode is enabled
			else {
				//changes the selection model
				teamTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

				//and cancels the replace option
				replaceFirstTeamPlayerBut.setDisable(true);

				//enables all the buttons that are relevant to the team 
				addFirstTeamPlayerBut.setDisable(false);
				addPlayer.setDisable(false);
				removeButton.setDisable(false);
				submitButton.setDisable(false);
				comboPlayers.setDisable(false);

				labelMode.setText("To enter replace mode press ALT");
			}
		}
	}

	/**
	 * this method updates the coach's details in the text field.
	 */
	@FXML
	private void showCoachDetails() {
		String idStr = comboCoaches.getSelectionModel().getSelectedItem();

		if(idStr != null) {
			idStr = comboCoaches.getSelectionModel().getSelectedItem().split(" ")[0];
			if (!idStr.isEmpty()) {
				try {
					Integer id = Integer.parseInt(idStr);
					Coach coach = ViewLogic.sysData.getCoachs().get(id);
					coachText.setText(coach.toString());
				}
				catch (Exception e) {
					e.printStackTrace();
				}
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
			if (match.equals(mp.getMatch()) && AdminTeamController.selectedTeam.equals(mp.getTeam())) {
				matchPosition = mp;
				break;
			}

		// creating new match position data, if not exists
		if (matchPosition == null) {
			matchPosition = new MatchPosition(formation, match, AdminTeamController.selectedTeam);
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
			if (match.equals(mp.getMatch()) && AdminTeamController.selectedTeam.equals(mp.getTeam())) {
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
			if (match.equals(mp.getMatch()) && AdminTeamController.selectedTeam.equals(mp.getTeam())) {
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
			((Button) e.getSource()).setStyle("-fx-background-color: black; -fx-text-fill: white;");
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
			((Button) e.getSource()).setStyle("-fx-background-color: white; -fx-text-fill: black;");
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
