package View;

import java.util.Date;


import Model.Customer;
import Model.Match;
import Model.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.E_Levels;
/**
 * Class AdminMatchController ~ Admin Match window.
 * 
 * @author ID: 205791056
 * @author ID: 312181605
 */
public class AdminMatchController extends AllWindowsController {
	// ------------------------------ Variables ------------------------------
	@FXML
	private VBox pane;

	@FXML
	private Button backButton;

	@FXML
	private Button logoutButton;

	@FXML
	private ImageView logOut;

	@FXML
	private Label alertLabel;

	@FXML
	private Button addMatchButton;

	@FXML
	private Button removeMatchButton;

	@FXML
	private Button updateButton;

	protected static Match matchSelected;
	
	// ========================== Crowd Table ==========================
	@FXML
	private TableView<Customer> fanTable;

	@FXML
	private TableColumn<Customer, String> idFanColumn;

	@FXML
	private TableColumn<Customer, String> firstNameColumn;

	@FXML
	private TableColumn<Customer, String> lastNameColumn;

	@FXML
	private TableColumn<Customer, E_Levels> fanLevelColumn;

	@FXML
	private TableColumn<Customer, Boolean> hTeamFanColumn;

	// ========================== Matches DB Table ==========================
	@FXML
	private TableView<Match> tableMatch;

	@FXML
	private TableColumn<Match, Integer> idColumn;

	@FXML
	private TableColumn<Match, Team> hTeamColumn;

	@FXML
	private TableColumn<Match, Integer> hTeamScoreColumn;

	@FXML
	private TableColumn<Match, Team> aTeamColumn;

	@FXML
	private TableColumn<Match, Integer> aTeamScore;

	@FXML
	private TableColumn<Match, E_Levels> matchLvlColumn;

	@FXML
	private TableColumn<Match, Date> dateColumn;

	@FXML
	private TableColumn<Match, Integer> durationColumn;

	@FXML
	private TableColumn<Match, Integer> extraColumn;

	// ------------------------------ Methods ------------------------------
	// ========================== General Methods ==========================
	@Override
	public void initialize() {
		ViewLogic.adminMatchController = this; 

		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		hTeamColumn.setCellValueFactory(new PropertyValueFactory<>("homeTeam"));
		hTeamScoreColumn.setCellValueFactory(new PropertyValueFactory<>("homeTeamScore"));
		aTeamColumn.setCellValueFactory(new PropertyValueFactory<>("awayTeam"));
		aTeamScore.setCellValueFactory(new PropertyValueFactory<>("awayTeamScore"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
		matchLvlColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
		durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
		extraColumn.setCellValueFactory(new PropertyValueFactory<>("extraTime"));

		idFanColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		fanLevelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
		hTeamFanColumn.setCellValueFactory(new PropertyValueFactory<>("favoriteTeam"));

		setMatchTable();
	}
	
	@Override
	protected void closeWindow() {
		Stage stage = (Stage)logoutButton.getScene().getWindow();
		stage.close();

		ViewLogic.adminCoachController = null;
	}
	
	/**
	 * Setting the match table
	 */
	protected void setMatchTable() {
		tableMatch.getItems().setAll(ViewLogic.sysData.getMatchs().values());
		tableMatch.refresh();
	}
	
	// ========================== Action Listeners ==========================
	/**
	 * Return to the previous Window
	 */
	@FXML
	private void goBack(ActionEvent event) {
		closeWindow();
		ViewLogic.newAdminWindow();
	}
	
	/**
	 * Opens Match Creation to add match to the system when Add Match button is clicked.
	 */
	@FXML
	private void addMatch() {
		ViewLogic.newAdminMatchCreationWindow();
	}

	/**
	 * Removes the selected match from the system when remove match button is clicked.
	 */
	@FXML
	private void removeMatch() {
		Match match = tableMatch.getSelectionModel().getSelectedItem();

		if (match != null) { 
			if(ViewLogic.sysData.removeMatch(match.getId())) {
				alertLabel.setText("Match was removed successfully");
				setMatchTable();
				
			}else
				alertLabel.setText("Problem occurred");
		}
	}
	
	/**
	 * Updates the selected match's previous teams table
	 */
	@FXML
	private void updateFanTable() {

		Match match = tableMatch.getSelectionModel().getSelectedItem();

		if (match != null ) {
			removeMatchButton.setDisable(false);
			updateButton.setDisable(false);
			fanTable.getItems().setAll(match.getCrowd().keySet());
			fanTable.refresh();
		}
	}
	
	/**
	 * Open a window in order to updates the selected match
	 */
	@FXML
	private void updateMatch() {
		matchSelected = tableMatch.getSelectionModel().getSelectedItem();
		ViewLogic.newAdminMatchUpdateWindow();
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
