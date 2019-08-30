package View;

import java.util.Date;

import Controller.Validation;
import Model.Address;
import Model.Coach;
import Model.Stadium;
import Model.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import utils.E_Levels;

/**
 * Class AdminCoachController ~ Admin Coach window.
 * 
 * @author ID: 205791056
 * @author ID: 312181605
 */

public class AdminCoachController extends AllWindowsController {

	// ------------------------------ Variables ------------------------------
	@FXML
	private BorderPane pane;

	@FXML
	private Button backButton;

	@FXML
	private Button logoutButton;

	@FXML
	private Button addCoachButton;

	@FXML
	private Button removeCoachButton;

	@FXML
	private Button updateCoachButton;

	// ========================== Coaches DB Table ==========================
	@FXML
	private TableView<Coach> tableCoach;

	@FXML
	private TableColumn<Coach, Integer> idColumn;

	@FXML
	private TableColumn<Coach, String> firstNameColumn;

	@FXML
	private TableColumn<Coach, String> lastNameColumn;

	@FXML
	private TableColumn<Coach, E_Levels> lvlColumn;

	@FXML
	private TableColumn<Coach, Team> teamColumn;

	@FXML
	private TableColumn<Coach, Date> birthDayColumn;

	@FXML
	private TableColumn<Coach, Date> workiungColumn;

	@FXML
	private TableColumn<Coach, Address> addressColumn;

	// ========================== Previous Teams Table ==========================
	@FXML
	private TableView<Team> tableTeam;

	@FXML
	private TableColumn<Team, Integer> idTeamColumn;

	@FXML
	private TableColumn<Team, String> nameTeamColumn;

	@FXML
	private TableColumn<Team, Integer> valueColumn;

	@FXML
	private TableColumn<Team, E_Levels> teamLevelColumn;

	@FXML
	private TableColumn<Team, Stadium> stadiumColumn;

	// ------------------------------ Methods ------------------------------
	// ========================== General Methods ==========================
	@Override
	public void initialize() {
		ViewLogic.adminCoachController = this;

		idTeamColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameTeamColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		teamLevelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
		stadiumColumn.setCellValueFactory(new PropertyValueFactory<>("stadium"));
		valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
		firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		birthDayColumn.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
		workiungColumn.setCellValueFactory(new PropertyValueFactory<>("startWorkingDate"));
		teamColumn.setCellValueFactory(new PropertyValueFactory<>("currentTeam"));
		lvlColumn.setCellValueFactory(new PropertyValueFactory<>("level"));

		setCoachTable();
	}

	@Override
	protected void closeWindow() {
		Stage stage = (Stage)logoutButton.getScene().getWindow();
		stage.close();

		ViewLogic.adminCoachController = null;
	}

	/**
	 * Sets the coaches table
	 */

	protected void setCoachTable() {
		tableCoach.getItems().setAll(ViewLogic.sysData.getCoachs().values());
		tableCoach.refresh();
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
	 * Opens Coach Creation to add coach to the system when Add Coach button is clicked.
	 */
	@FXML
	private void addCoach() {
		//	closeWindow();
		ViewLogic.newAdminCoachCreationWindow();
	}

	/**
	 * Opens Coach Update to update selected coach when Update Coach button is clicked.
	 */
	@FXML
	private void updateCoach() {
		//	closeWindow();
		AdminCoachUpdateController.coach = tableCoach.getSelectionModel().getSelectedItem();
		if (AdminCoachUpdateController.coach != null)
			ViewLogic.newCoachUpdateWindow();
		else {
			Validation.alert("Coach Error", "Please choose a coach from the table.");
		}
	}

	/**
	 * Removes the selected coach to the system when Add Coach button is clicked.
	 */
	@FXML
	private void removeCoach(ActionEvent event) {
		Coach coach = tableCoach.getSelectionModel().getSelectedItem();

		if(coach != null) {
			if(ViewLogic.sysData.removeCoach(coach.getId())) {
				tableCoach.getItems().remove(coach);
				tableTeam.getItems().clear();

			}else
				Validation.alert("Coach Error", "Problem occurred");	

		}else
			Validation.alert("Coach Error", "Please choose a coach to remove.");
	}

	/**
	 * Updates the selected coach's previous teams table
	 */
	@FXML
	private void updateTeamTable() {
		tableOnClick();
		Coach coach = tableCoach.getSelectionModel().getSelectedItem();

		tableTeam.getItems().clear();
		if(coach != null)
			tableTeam.getItems().addAll(coach.getTeams());
	}

	/**
	 * Enables buttons when an item is selected
	 */
	@FXML
	private void tableOnClick() {
		removeCoachButton.setDisable(false);
		updateCoachButton.setDisable(false);
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
