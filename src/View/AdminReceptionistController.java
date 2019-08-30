package View;

import java.util.Date;

import Controller.Validation;
import Model.Address;
import Model.Coach;
import Model.Customer;
import Model.Match;
import Model.Receptionist;
import Model.Stadium;
import Model.Subscription;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import utils.E_Periods;
/**
 * Class AdminReceptionistController ~ Admin Receptionist window.
 * 
 * @author ID: 205791056
 * @author ID: 312181605
 */
public class AdminReceptionistController extends AllWindowsController {
	// ------------------------------ Variables ------------------------------
	@FXML
	private BorderPane pane;

	@FXML
	private Button backButton;

	@FXML
	private Button logoutButton;

	@FXML
	private Button addRecButton;

	@FXML
	private Button updateRecButton;

	@FXML
	private Button removeRecButton;

	// ========================== Rec DB Table ==========================
	@FXML
	private TableView<Receptionist> recTable;

	@FXML
	private TableColumn<Receptionist, Integer> recIdColumn;

	@FXML
	private TableColumn<Receptionist, String> recFirstNameColumn;

	@FXML
	private TableColumn<Receptionist, String> recLastNameColumn;

	@FXML
	private TableColumn<Receptionist, Stadium> recStadiumColumn;

	@FXML
	private TableColumn<Coach, Date> recBirthDayColumn;

	@FXML
	private TableColumn<Coach, Date> recWorkingDateColumn;

	@FXML
	private TableColumn<Coach, Address> recAddressColumn;

	//private ArrayList<Receptionist> allRec = new ArrayList<>();

	//private ObservableList<Receptionist> receptionists;

	// ========================== Subscriptions Sold ==========================
	@FXML
	private TableView<Subscription> subTable;

	@FXML
	private TableColumn<Subscription, Integer> idColumnSub;

	@FXML
	private TableColumn<Subscription, Customer>  cusColumnSub;

	@FXML
	private TableColumn<Subscription, E_Periods>  periodColumnSub;

	@FXML
	private TableColumn<Subscription, Date>  startDateColumnSub;

	private ObservableList<Subscription> subscriptions;

	@FXML
	private ListView<String> matchSubList;

	// private ObservableList<Match> subMatches; // list

	// ------------------------------ Methods ------------------------------
	// ========================== General Methods ==========================
	@Override
	public void initialize() {
		ViewLogic.adminReceptionistController = this;

		idColumnSub.setCellValueFactory(new PropertyValueFactory<>("id")); // According to variable name
		cusColumnSub.setCellValueFactory(new PropertyValueFactory<>("customer")); //Same here
		periodColumnSub.setCellValueFactory(new PropertyValueFactory<>("period")); // And here
		startDateColumnSub.setCellValueFactory(new PropertyValueFactory<>("startDate")); // And here

		recIdColumn.setCellValueFactory(new PropertyValueFactory<>("id")); // According to variable name
		recFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName")); //Same here
		recLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName")); // And here
		recBirthDayColumn.setCellValueFactory(new PropertyValueFactory<>("birthdate")); // And here
		recAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address")); // And here
		recStadiumColumn.setCellValueFactory(new PropertyValueFactory<>("workingStadium")); // And here
		recWorkingDateColumn.setCellValueFactory(new PropertyValueFactory<>("startWorkingDate")); // And here

		setRecTable();

	}

	@Override
	protected void closeWindow() {
		Stage stage = (Stage)logoutButton.getScene().getWindow();
		stage.close();
		ViewLogic.adminReceptionistController = null;
	}

	/**
	 * This method updates the sub table
	 */
	protected void setSubTable() {
		subscriptions.clear();

		//fills the table with data to display 
		Receptionist rec = recTable.getSelectionModel().getSelectedItem();
		if (rec != null) {
			subscriptions = FXCollections.observableArrayList();
			subscriptions.setAll(rec.getSubscriptions());
			subTable.setItems(subscriptions);
			subTable.refresh();
		}
	}
	/**
	 * This method updates the rec DB table
	 */
	protected void setRecTable() {
		//fills the table with data to display 
		//receptionists = FXCollections.observableArrayList();
		//receptionists.setAll(ViewLogic.sysData.getReceptionists().values());
		//	recTable.setItems(receptionists);
		recTable.getItems().setAll(ViewLogic.sysData.getReceptionists().values());
		recTable.refresh();
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
	 * Opens Rec Creation to add rec to the system when Add Rec button is clicked.
	 */
	@FXML
	private void addRec() {

		//closeWindow();
		ViewLogic.newRecCreationWindow();
	}

	/**
	 * Opens Receptionist Update to update selected Receptionist when Update Receptionist button is clicked.
	 */
	@FXML
	private void updateRec() {
		AdminRecUpdateController.rec = recTable.getSelectionModel().getSelectedItem();
		if (AdminRecUpdateController.rec != null) {
			ViewLogic.newRecUpdateWindow();
		}
		else
			Validation.alert("Receptionist Error", "Please choose a receptionist to from the table.");
	}


	/**
	 * Removes the selected rec from the system when remove rec button is clicked.
	 */
	@FXML
	private void removeRec() {
		Receptionist rec = recTable.getSelectionModel().getSelectedItem();

		if(rec != null) {
			if (ViewLogic.sysData.removeReceptionist(rec.getId())) {
				recTable.getItems().remove(rec);
				recTable.refresh();
				subTable.getItems().clear();
				matchSubList.getItems().clear();

			}else
				Validation.alert("Receptionist Error", "Problem occurred");	

		}else
			Validation.alert("Receptionist Error", "Please choose a receptionist to remove");
	}


	/**
	 *This method displays the subscriptions of 
	 *the selected customer, on the list  and enables some buttons
	 */
	@FXML
	private void tableOnClick() {
		removeRecButton.setDisable(false);
		updateRecButton.setDisable(false);
		matchSubList.getItems().clear();
		// sublist
		subTable.getItems().clear();
		Receptionist rec = recTable.getSelectionModel().getSelectedItem();

		// case a customer is selected
		if (rec != null) {
			subscriptions = FXCollections.observableArrayList();
			subscriptions.addAll(rec.getSubscriptions());
			setSubTable();
		}
	}

	/**
	 *This method displays the subscriptions of 
	 *the selected customer, on the list  and enables some buttons
	 */
	@FXML
	private void subTableOnClick() {

		// sublist
		matchSubList.getItems().clear();
		matchSubList.refresh();
		Subscription sub = subTable.getSelectionModel().getSelectedItem();

		// case a customer is selected
		if (sub != null) {
			ObservableList<Match> matches = FXCollections.observableArrayList();
			ObservableList<String> matchName = FXCollections.observableArrayList();

			matches.addAll(sub.getMatches());

			// creates an array of names and and him to the list
			if (!matches.isEmpty()) {
				for (Match temp : matches)
					if (temp.getHomeTeam().getStadium() != null)
						matchName.add(temp.toString());

				matchSubList.getItems().setAll(matchName);
			}
		}
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
