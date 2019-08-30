package View;

import java.util.ArrayList;

import Model.Match;
import Model.Receptionist;
import Model.Stadium;
import Model.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import utils.E_Cities;
import Controller.Validation;;

/**
 * Class AdminStadiumController ~ Admin Stadium window.
 * 
 * @author ID: 205791056
 * @author ID: 312181605
 */
public class AdminStadiumController extends AllWindowsController {
	// ------------------------------ Variables ------------------------------
	@FXML
	private HBox pane;

	@FXML
	private Button removeStdBut;

	@FXML
	private Button updateStdBut;

	@FXML
	private Button removeStdRecp;

	@FXML
	private Button backButton;

	@FXML
	private Button logoutButton;

	@FXML
	private Accordion accord; // with love, to the best partner in the world <3

	@FXML
	private ListView<Receptionist> recList;

	@FXML
	private ListView<Match> matchList;

	@FXML
	private ListView<Team> teamList;

	@FXML
	private TextField activeCityText;

	@FXML
	private Button addStdBut;

	@FXML
	private Button addRecp;

	@FXML
	private ComboBox<String> comboRecp;

	private ArrayList<Receptionist> recpArr;

	// ========================== Stadiums DB Table ==========================
	@FXML
	private TableView<Stadium> table;

	@FXML
	private TableColumn<Stadium, Integer> idColumn;

	@FXML
	private TableColumn<Stadium, String> nameColumn;

	@FXML
	private TableColumn<Stadium, String> addressColumn;

	@FXML
	private TableColumn<Stadium, Integer> capacityColumn;

	private ObservableList<Stadium> stadiums;

	// ------------------------------ Methods ------------------------------
	// ========================== General Methods ==========================
	@Override
	public void initialize() {
		// setting the columns to display the data
		ViewLogic.adminStadiumController = this;

		idColumn.setCellValueFactory(new PropertyValueFactory<>("id")); // According to variable name
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name")); // Same here
		addressColumn.setCellValueFactory(new PropertyValueFactory<>("address")); // And here
		capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity")); // And here

		setTable();
		setComboBox();

	}

	@Override
	protected void closeWindow() {
		Stage stage = (Stage) pane.getScene().getWindow();

		stage.close();
		ViewLogic.adminStadiumController = null;
	}

	/**
	 * This method add the reciptionists in the system to the comboBox
	 */
	private void setComboBox() {
		ArrayList<String> recpName = new ArrayList<>();
		recpArr = new ArrayList<>();

		for (Receptionist temp : ViewLogic.sysData.getReceptionists().values()) {

			if (temp.getWorkingStadium() == null) {
				recpArr.add(temp);
				recpName.add(temp.getRecpString());

			}
		}

		comboRecp.getItems().setAll(recpName);

	}

	/**
	 * This method sets the table to display the required parameters
	 */
	protected void setTable() {
		// fills the table with data to display
		stadiums = FXCollections.observableArrayList();
		stadiums.setAll(ViewLogic.sysData.getStadiums().values());
		table.setItems(stadiums);
		table.refresh();
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
	 * Opens Stadium Update to update selected Stadium when Update Stadium button is
	 * clicked.
	 */
	@FXML
	private void updateStadium() {
		// closeWindow();
		AdminStadiumUpdateController.stadium = table.getSelectionModel().getSelectedItem();
		if (AdminStadiumUpdateController.stadium != null)
			ViewLogic.newStadiumUpdateWindow();
		else {
			Validation.alert("Stadium Error", "Please choose a stadium to from the table.");
		}
	}

	/**
	 * removes the chosen receptionist from its current stadium
	 */
	@FXML
	private void removeRec() {

		// the selected stadium
		Stadium stadium = table.getSelectionModel().getSelectedItem();

		// the index of the selected receptionist
		Receptionist rec = recList.getSelectionModel().getSelectedItem();

		// case user didnt chose a stadium
		if (stadium == null) {
			Validation.alert("Stadium Error", "Please choose a stadium from the table.");

			// case the user didnt chose a receptionist
		} else if (rec == null) {

			Validation.alert("Receptionist Error", "Please choose a receptionist from the list.");

			/*
			 * case everything is valid updates the list and removes the receptionist from
			 * the stadium
			 */
		} else {

			stadium.removeReceptionist(rec);
			recList.getItems().remove(rec);
			setComboBox();
			setActiveCity(stadium);
			
			Validation.info("Receptionist removed successfully!", rec.getRecpString() + " was removed from " + stadium.getName());


		}

	}

	/**
	 * This method displays the receptionists of the selected stadium in the list
	 */
	@FXML
	private void updateList() {
		removeStdBut.setDisable(false);
		updateStdBut.setDisable(false);
		comboRecp.setDisable(false);
		addRecp.setDisable(false);
		accord.setDisable(false);

		recList.getItems().clear();
		matchList.getItems().clear();
		teamList.getItems().clear();
		Stadium stadium = table.getSelectionModel().getSelectedItem();

		// case a stadium is selected
		if (stadium != null) {
			ObservableList<Receptionist> recpArr = FXCollections.observableArrayList(stadium.getReceptionists());
			ObservableList<Match> matchArr = FXCollections.observableArrayList(stadium.getMatches());
			ObservableList<Team> teamArr = FXCollections.observableArrayList(stadium.getTeams());

			if (!recpArr.isEmpty())
				recList.getItems().setAll(recpArr);

			if (!matchArr.isEmpty())
				matchList.getItems().setAll(matchArr);

			if (!teamArr.isEmpty())
				teamList.getItems().setAll(teamArr);

			setActiveCity(stadium);
		}
	}

	/**
	 * This method sets the active city textfield
	 * 
	 * @param stadium
	 */
	private void setActiveCity(Stadium stadium) {
		E_Cities city = ViewLogic.sysData.getTheMostActiveCity(stadium.getId());
		if (city != null) {
			activeCityText.setStyle(null);
			activeCityText.setText(city.toString());
		}
		else {
			activeCityText.setStyle("-fx-text-inner-color: gray;");
			activeCityText.setText("No such city");
		}

	}

	/**
	 * This method adds the selected receptionist to the stadium
	 */
	@FXML
	private void addRec() {

		// the index of the selected receptionist
		int index = comboRecp.getSelectionModel().getSelectedIndex();

		// the selected stadium
		Stadium stadium = table.getSelectionModel().getSelectedItem();

		// case the admin didn't choose a receptionist, creates alert message
		if (index < 0) {
			Validation.alert("Receptionist Error", "Please choose a receptionist from the comboBox.");

			// case the admin didn't choose a stadium, creates alert message
		} else if (stadium == null) {

			Validation.alert("Stadium Error", "Please choose a Stadium");
		} else {

			// adding the receptionist to the selected stadium
			Receptionist recp = recpArr.get(index);

			if (ViewLogic.sysData.addReceptionistToStadium(recp.getId(), stadium.getId())) {
				recList.getItems().add(recp);
				setComboBox();
				setActiveCity(stadium);

				// case there was addReceptionistToStadium method failed, display Error Message
			} else {

				Validation.alert("Cannot Add Receptionist", "Error occurred.");

			}
		}

	}

	@FXML
	private void addStadium(ActionEvent event) {
		// closeWindow();
		ViewLogic.newStadiumCreationWindow();
	}

	/**
	 * This method removes the selected stadium from the system
	 */
	@FXML
	private void removeStadium() {

		// the selected stadium
		Stadium stadium = table.getSelectionModel().getSelectedItem();

		// case admin didnt choose anything, diplays Error Message
		if (stadium == null) {
			Validation.alert("Stadium Error", "Please choose a stadium from the table");

			// case everything is valid , removes the stadium from the system
		} else {
			table.getItems().remove(stadium);
			recpArr.addAll(stadium.getReceptionists());

			for (Receptionist temp : stadium.getReceptionists())
				comboRecp.getItems().add(temp.getRecpString());

			ViewLogic.sysData.removeStadium(stadium.getId());
			recList.getItems().clear();

		}
	}

	/**
	 * this method enables buttons when the user selects a reclist item
	 */
	@FXML
	private void enableButtonsOnListClick() {
		removeStdRecp.setDisable(false);
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