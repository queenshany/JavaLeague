package View;

import java.util.Date;

import Controller.Validation;
import Model.Address;
import Model.Player;
import Model.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utils.E_Levels;
import utils.E_Position;
/**
 * Class AdminMatchController ~ Admin Player window.
 * 
 * @author ID: 205791056
 * @author ID: 312181605
 */
public class AdminPlayerController extends AllWindowsController {
	// ------------------------------ Variables ------------------------------
	@FXML
	private Tab systemPlayersTab;

	@FXML
	private Button addPlayerButton;
	
	@FXML
	private Button updatePlayerButton;

	@FXML
	private Button removePlayerButton;

	@FXML
	private Tab generalQueriesTab;

	@FXML
	private Label allSuperPlayerMaker;

	@FXML
	private TextField mosPopulerPotisionText;

	@FXML
	private HBox pane;

	@FXML
	private Button backButton;

	@FXML
	private Button logoutButton;

	// ========================== Players DB Table ==========================
	@FXML
	private TableView<Player> playersTable;

	@FXML
	private TableColumn<Player, Integer> playerIdColumn;

	@FXML
	private TableColumn<Player, String> playerFirstNameColumn;

	@FXML
	private TableColumn<Player, String> playersLastNameColumn;

	@FXML
	private TableColumn<Player, E_Position> playerPositonColumn;

	@FXML
	private TableColumn<Player, Boolean> playerRightLegColumn;

	@FXML
	private TableColumn<Player, Long> playerValueColumn;

	@FXML
	private TableColumn<Player, Team> playersTeamColumn;

	@FXML
	private TableColumn<Player, Date> playersBdayColumn;

	@FXML
	private TableColumn<Player, Date> playersWorkingDateColumn;

	@FXML
	private TableColumn<Player, Address> playersAddressColumn;
	
	@FXML
	private TableColumn<Player, E_Levels> playersLevelColumn;

	// ========================== Player Makers Table ==========================
	@FXML
	private TableView<Player> playMakersTable;

	@FXML
	private TableColumn<Player, Integer> idColumnPlayerMaker;

	@FXML
	private TableColumn<Player, String> firstNameColumnPlayerMaker;

	@FXML
	private TableColumn<Player, String> playMakerLastNameColumn;

	@FXML
	private TableColumn<Player, Team> teamColumnPlayerMaker;

	@FXML
	private TableColumn<Player, Long> playMakerValueColumn;

	@FXML
	private TableColumn<Player, Date> playMakerBdayColumn;

	@FXML
	private TableColumn<Player, Date> playMakerWorkingDateColumn;

	@FXML
	private TableColumn<Player, Address> playMakerAddressColumn;

	@FXML
	private TableColumn<Player, E_Position> playMakerPosColumn;

	@FXML
	private TableColumn<Player, Boolean> playMakerFootColumn;
	
	@FXML
	private TableColumn<Player, E_Levels>  playMakerLevelColumn;

	// ------------------------------ Methods ------------------------------
	// ========================== General Methods ==========================

	@Override
	public void initialize() {
		ViewLogic.adminPlayerController = this;

		idColumnPlayerMaker.setCellValueFactory(new PropertyValueFactory<>("id"));
		firstNameColumnPlayerMaker.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		playMakerLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		playMakerValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
		teamColumnPlayerMaker.setCellValueFactory(new PropertyValueFactory<>("currentTeam"));
		playMakerBdayColumn.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
		playMakerWorkingDateColumn.setCellValueFactory(new PropertyValueFactory<>("startWorkingDate"));
		playMakerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
		playMakerPosColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
		playMakerFootColumn.setCellValueFactory(new PropertyValueFactory<>("isRightLegKicker"));
		playMakerLevelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
		
		playerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		playerFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		playersLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		playerPositonColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
		playerRightLegColumn.setCellValueFactory(new PropertyValueFactory<>("isRightLegKicker"));
		playerValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
		playersTeamColumn.setCellValueFactory(new PropertyValueFactory<>("currentTeam"));
		playersBdayColumn.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
		playersWorkingDateColumn.setCellValueFactory(new PropertyValueFactory<>("startWorkingDate"));
		playersAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
		playersLevelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
		setPlayersTable();
		

	}

	@Override
	protected void closeWindow() {
		Stage stage = (Stage) backButton.getScene().getWindow();
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				stage.close();

			}
		});

		stage.close();

		ViewLogic.adminPlayerController = null;
	}
	
	/**
	 * Setting the most popular position text
	 */
	private void setMostPopularText() {
		E_Position position = ViewLogic.sysData.getTheMostPopularPosition();
	
		if (position != null ) {
			mosPopulerPotisionText.setStyle(null);
			mosPopulerPotisionText.setText(position.name());
		}
		else {
			mosPopulerPotisionText.setStyle("-fx-text-fill: gray");
			mosPopulerPotisionText.setText("No such position");
		}
	}
	
	/**
	 * setting the playmakers table
	 */
	private void setPlayMakersTable() {

		playMakersTable.getItems().setAll(ViewLogic.sysData.getAllSuperPlayerMakers());
		playMakersTable.refresh();
	}

	/**
	 * setting the players DB table
	 */
	protected void setPlayersTable() {

		ObservableList<Player> players = FXCollections.observableArrayList();
		players.setAll(ViewLogic.sysData.getPlayers().values());
		playersTable.setItems(players);
		playersTable.refresh();
		setPlayMakersTable();
		setMostPopularText();
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
	 * Opens Player Creation to add player to the system when Add Player button is clicked.
	 */
	@FXML
	private void addPlayer() {
		// closeWindow();
		ViewLogic.newPlayerCreationWindow();
	}
	
	/**
	 * enables buttons when table item is clicked.
	 */
	@FXML
	private void tableOnClick() {
		updatePlayerButton.setDisable(false);
		removePlayerButton.setDisable(false);
	}
	
	

	/**
	 * Opens Player Update to update selected Player when Update Player button is clicked.
	 */
	@FXML
	private void updatePlayer() {
		//	closeWindow();
		AdminPlayerUpdateController.player = playersTable.getSelectionModel().getSelectedItem();
		if (AdminPlayerUpdateController.player != null)
			ViewLogic.newPlayerUpdateWindow();
			else {
			Validation.alert("Player Error", "Please choose a player to from the table.");
		}
	}
	
	/**
	 * Removes the selected player to the system when remove player button is clicked.
	 */
	@FXML
	private void removePlayer() {
		Player player = playersTable.getSelectionModel().getSelectedItem();

		if (player != null) {

			if (ViewLogic.sysData.removePlayer(player.getId())) {

				playMakersTable.getItems().remove(player);
				setMostPopularText();
				setPlayMakersTable();
			

				playersTable.getItems().remove(player);
				Validation.info("Removed Successfully", "The player was removed successfully");
				
			} else {
				Validation.alert("Player Error", "Error occurred");
			}
		} else
			Validation.alert("Player Error", "Please choose a player to remove");
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
