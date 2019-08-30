package View;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import Controller.Validation;
import Exceptions.FanException;
import Model.Address;
import Model.Customer;
import Model.Match;
import Model.Receptionist;
import Model.Stadium;
import Model.Subscription;
import Model.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
import utils.E_Cities;
import utils.E_Levels;
import utils.E_Periods;

/**
 * Class RecepMainController ~ Controller of Receptionist-Main.
 * 
 * @author ID: 205791056
 * @author ID: 312181605
 */
public class RecepMainController extends AllWindowsController {

	// ------------------------------ Variables ------------------------------
	// All Window Vars
	@FXML
	private Tab generalQueriesTab;
	@FXML
	private Tab subSoldTab;
	@FXML
	private Tab cusPropertiesTab;
	@FXML
	private Button logoutButton;
	@FXML
	private HBox pane;
	@FXML
	private Label currentRecepDetails;

	// ========================== Customers DB Table ==========================
	@FXML
	private TableView<Customer> customerTable;
	@FXML
	private TableColumn<Customer, String> idColumnCus;
	@FXML
	private TableColumn<Customer, String> firstNameColumnCus;
	@FXML
	private TableColumn<Customer, String> lastNameColumnCus;
	@FXML
	private TableColumn<Customer, Date> birthdateColumnCus;
	@FXML
	private TableColumn<Customer, E_Levels> levelColumnCus;
	@FXML
	private TableColumn<Customer, URL> emailColumnCus;
	@FXML
	private TableColumn<Customer, Address> addressColumnCus;
	@FXML
	private TableColumn<Customer, Team> teamColumnCus;

	@FXML
	private Button addCustomerBut;
	@FXML
	private Button updateCustomerBut;
	@FXML
	private Button removeCustomerBut;
	@FXML
	private Button addSubBut;
	@FXML
	private Button removeSubBut;
	@FXML
	private Button addCusToMatchBut;
	@FXML
	private Button removeMatchBut;

	@FXML
	private ComboBox<String> comboMatches;
	@FXML
	private ListView<String> matchList;
	@FXML
	private ListView<String> subList;
	private ObservableList<Customer> customers;
	private ArrayList<String> MatchName = new ArrayList<>();

	private ArrayList<Match> allMatchArr = new ArrayList<>(); // combobox
	private ArrayList<Subscription> subArr = new ArrayList<>();
	private ObservableList<Match> cusMatches; // list
	private ObservableList<Subscription> subsOfCus; // list

	// ========================== Sub Sold Table ==========================
	@FXML
	private TableView<Subscription> subTable;
	@FXML
	private TableColumn<Subscription, Integer> idColumnSub;
	@FXML
	private TableColumn<Subscription, Customer> cusColumnSub;
	@FXML
	private TableColumn<Subscription, E_Periods> periodColumnSub;
	@FXML
	private TableColumn<Subscription, Date> startDateColumnSub;
	private ObservableList<Subscription> subscriptions;
	@FXML
	private ListView<String> matchSubList;

	// ========================== XOR Table ==========================
	@FXML
	private TableView<Customer> xorTable;
	@FXML
	private TableColumn<Customer, String> idColumnXor;
	@FXML
	private TableColumn<Customer, String> firstNameColumnXor;
	@FXML
	private TableColumn<Customer, String> lastNameColumnXor;
	@FXML
	private TableColumn<Customer, Date> birthdateColumnXor;
	@FXML
	private TableColumn<Customer, E_Levels> levelColumnXor;
	@FXML
	private TableColumn<Customer, URL> emailColumnXor;
	@FXML
	private TableColumn<Customer, Address> addressColumnXor;
	@FXML
	private TableColumn<Customer, Team> teamColumnXor;

	@FXML
	private ComboBox<String> comboStadium1;
	@FXML
	private ComboBox<String> comboStadium2;
	@FXML
	private ComboBox<String> comboStadiumActiveCity;

	private ArrayList<Stadium> allStadiumArr = new ArrayList<>();;

	@FXML
	private TextField activeCityField;

	private ObservableList<Customer> xorCustomers;
	@FXML
	private Button generateXorBut;

	private Receptionist rec = ViewLogic.sysData.getReceptionists().get(Integer.parseInt(ViewLogic.currentUserID));
	// Team team = coach.getCurrentTeam();

	// private ArrayList<Player> playerArr;

	// ------------------------------ Methods ------------------------------
	// ========================== General Methods ==========================
	@Override
	public void initialize() {
		ViewLogic.recepMainController = this;

		idColumnCus.setCellValueFactory(new PropertyValueFactory<>("id")); // According to variable name
		firstNameColumnCus.setCellValueFactory(new PropertyValueFactory<>("firstName")); // Same here
		lastNameColumnCus.setCellValueFactory(new PropertyValueFactory<>("lastName")); // And here
		birthdateColumnCus.setCellValueFactory(new PropertyValueFactory<>("birthdate")); // And here
		levelColumnCus.setCellValueFactory(new PropertyValueFactory<>("level")); // And here
		emailColumnCus.setCellValueFactory(new PropertyValueFactory<>("email")); // And here
		addressColumnCus.setCellValueFactory(new PropertyValueFactory<>("address")); // And here
		teamColumnCus.setCellValueFactory(new PropertyValueFactory<>("favoriteTeam")); // And here

		idColumnSub.setCellValueFactory(new PropertyValueFactory<>("id")); // According to variable name
		cusColumnSub.setCellValueFactory(new PropertyValueFactory<>("customer")); // Same here
		periodColumnSub.setCellValueFactory(new PropertyValueFactory<>("period")); // And here
		startDateColumnSub.setCellValueFactory(new PropertyValueFactory<>("startDate")); // And here

		idColumnXor.setCellValueFactory(new PropertyValueFactory<>("id")); // According to variable name
		firstNameColumnXor.setCellValueFactory(new PropertyValueFactory<>("firstName")); // Same here
		lastNameColumnXor.setCellValueFactory(new PropertyValueFactory<>("lastName")); // And here
		birthdateColumnXor.setCellValueFactory(new PropertyValueFactory<>("birthdate")); // And here
		levelColumnXor.setCellValueFactory(new PropertyValueFactory<>("level")); // And here
		emailColumnXor.setCellValueFactory(new PropertyValueFactory<>("email")); // And here
		addressColumnXor.setCellValueFactory(new PropertyValueFactory<>("address")); // And here
		teamColumnXor.setCellValueFactory(new PropertyValueFactory<>("favoriteTeam")); // And here

		try {
			rec = ViewLogic.sysData.getReceptionists().get(Integer.parseInt(ViewLogic.currentUserID));
			currentRecepDetails.setText("Working " + rec.getWorkingStadium().toString());
		} catch (NumberFormatException e) {
			currentRecepDetails.setText("");
		} catch (NullPointerException e) {
			currentRecepDetails.setText("");
		}
		setCustomerProperties();
		setGeneralQueries();
		// setManageFormation()
	}

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
		ViewLogic.recepMainController = null;
	}

	/**
	 * This method sets Customer Properties tab
	 */
	private void setCustomerProperties() {
		setSubTable();
		setCusTable();
		setMatchesComboBox();
	}

	/**
	 * This method updates the customer table in Update Team tab
	 */
	protected void setCusTable() {
		// fills the table with data to display
		customers = FXCollections.observableArrayList();
		customers.setAll(ViewLogic.sysData.getCustomers().values());
		customerTable.setItems(customers);
		customerTable.refresh();
	}

	private void setMatchesComboBox() {
		allMatchArr.addAll(ViewLogic.sysData.getMatchs().values());

		if (cusMatches != null && !cusMatches.isEmpty())
			allMatchArr.removeAll(cusMatches);

		for (Match temp : allMatchArr)
			if (temp.getHomeTeam().getStadium() != null)
				MatchName.add(temp.showSumDetails());
		comboMatches.getItems().setAll(MatchName);
	}

	/**
	 * This method updates the sub table in Update Team tab
	 */
	protected void setSubTable() {
		// fills the table with data to display
		subscriptions = FXCollections.observableArrayList();
		subscriptions.setAll(
				ViewLogic.sysData.getReceptionists().get(Integer.parseInt(ViewLogic.currentUserID)).getSubscriptions());
		subTable.setItems(subscriptions);
		subTable.refresh();

	}

	/**
	 * This method sets the General Queries Tab
	 */
	private void setGeneralQueries() {

		ArrayList<String> stadiumName = new ArrayList<>();
		// allStadiumArr = new ArrayList<>();

		allStadiumArr.addAll(ViewLogic.sysData.getStadiums().values());

		for (Stadium temp : allStadiumArr)
			stadiumName.add(temp.getId() + " | " + temp.getName());

		comboStadium1.getItems().addAll(stadiumName);
		comboStadium2.getItems().addAll(stadiumName);
		comboStadiumActiveCity.getItems().addAll(stadiumName);
	}
	//TODO
	private void setSubofClickedCus(Customer cus) {
		ObservableList<String> subName = FXCollections.observableArrayList();
		subsOfCus = FXCollections.observableArrayList();

		subsOfCus.addAll(cus.getSubscriptions());

		// creates an array of names and and him to the list
		if (!subsOfCus.isEmpty()) {
			for (Subscription temp : subsOfCus)
				subName.add(temp.toString());

			subArr.clear();
			subArr.addAll(subsOfCus);
			subList.getItems().setAll(subName);
		}
	}
	//TODO
	private void setMatchesofClickedSub(Subscription sub){
		// case a stadium is selected
		ObservableList<String> cusMatchName = FXCollections.observableArrayList();
		cusMatches = FXCollections.observableArrayList();

		cusMatches.setAll(sub.getMatches());

		// creates an array of names and and him to the list
		if (cusMatches != null && !cusMatches.isEmpty()) {
			for (Match temp : cusMatches)
				if (temp.getHomeTeam().getStadium() != null)
					cusMatchName.add(temp.showSumDetails());
			matchList.getItems().setAll(cusMatchName);
			matchList.refresh();
	}
	}

	// ========================== Action Listeners ==========================
	/**
	 * This method displays the subscriptions of the selected customer, on the list
	 * and enables some buttons
	 */
	@FXML
	protected void tableOnClick() {
		addSubBut.setDisable(false);
		removeCustomerBut.setDisable(false);
		comboMatches.setDisable(false);
		updateCustomerBut.setDisable(false);
		addCusToMatchBut.setDisable(false);
		// sublist
		subList.getItems().clear();
		matchList.getItems().clear();
		Customer customer = customerTable.getSelectionModel().getSelectedItem();

		// case a customer is selected
		if (customer != null) 
			setSubofClickedCus(customer);
	}

	/**
	 * This method and enables removeMatchBut
	 */
	@FXML
	private void matchListOnClick() {
		removeMatchBut.setDisable(false);
	}

	/**
	 * this method adds a customer to match
	 */
	@FXML
	private void addCusToMatch() {
		int indexMatch = comboMatches.getSelectionModel().getSelectedIndex();


		if (indexMatch < 0)
			Validation.alert("Match Error", "Please choose a Match from the comboBox.");
		else {
			Match match = allMatchArr.get(indexMatch);
			// the selected customer
			Customer customer = customerTable.getSelectionModel().getSelectedItem();

			try {
				// case user didn't chose a customer
				if (customer == null)
					Validation.alert("Customer Error", "Please choose a customer from the table.");
				else if (customer.getSubscriptions() == null
						|| (customer.getSubscriptions() != null && customer.getSubscriptions().isEmpty())) {

					Validation.alert("Customer Error", "Customer must be subscribed. Add a subscription and try again");
				} else if (ViewLogic.sysData.addCustomerToMatch(customer.getId(), match.getId())) {

				
					Validation.info("Match added successfully!", customer.getFirstName() + " " + customer.getLastName() + ", ID: "
							+ customer.getId() + " was added to match: " + match.showSumDetails());
					
					matchSubList.refresh();
					matchList.refresh();
				}
			} catch (FanException e) {
				Validation.alert("Add Customer to Match Error", e.getMessage());
			} 
		}
	}

	/**
	 * This method and allows removing match from customer
	 */
	@FXML
	private void removeMatch() {
		// the index of the selected match
		int indexMatch = matchList.getSelectionModel().getSelectedIndex();

		// index of selected sub
		int indexSub = subList.getSelectionModel().getSelectedIndex();

		// the selected customer
		Customer customer = customerTable.getSelectionModel().getSelectedItem();
	

		// case user didnt chose a customer
		if (customer == null) {
			Validation.alert("Customer Error", "Please choose a customer from the table.");

			// case the user didnt chose a receptionist
		} else if (indexSub < 0) {
		

			Validation.alert("Subscription Error", "Please choose a Subscription from the list.");
		} else if (indexMatch < 0) {
			
			Validation.alert("Match Error", "Please choose a Match from the list.");
			// case everything is valid
		} else {
			Subscription sub = subArr.get(indexSub);
			Match match = cusMatches.get(indexMatch);

			matchList.getItems().remove(match.showSumDetails());
			match.removeFan(customer);
			cusMatches.remove(match);
			sub.removeMatch(match);

		
			Validation.info("Match removed successfully!", match.showSumDetails() + " was removed from Sub: " + sub.getId());
		
		}
	}

	/**
	 * This method displays the subscriptions of the selected customer, on the list
	 * and enables some buttons
	 */
	@FXML
	private void subListOnClick() {
		// removeMatchBut.setDisable(false);
		removeSubBut.setDisable(false);

		// matchlist
		matchList.getItems().clear();
		int index = subList.getSelectionModel().getSelectedIndex();
		Subscription sub = null;
		if (index >= 0)
			sub = subArr.get(index);

		if (sub != null) 
			setMatchesofClickedSub(sub);
		}

	/**
	 * this method adds customer to customer Table
	 */
	@FXML
	private void addCustomer() {
		// closeWindow();
		ViewLogic.newNewCustomerCreation();
	}

	/**
	 * this method adds subscription to selected customer
	 */
	@FXML
	private void addSub() {
		Customer customer = customerTable.getSelectionModel().getSelectedItem();

		if (customer == null) {
			Validation.alert("Customer Error", "Please choose a Customer from the table");
		} else {
			SubCreationController.cusID = customer.getId();
			// closeWindow();
			ViewLogic.newNewSubCreation();
		}
	}

	/**
	 * This method removes the selected customer from the system
	 */
	@FXML
	private void removeCustomer() {

		// the selected stadium
		Customer customer = customerTable.getSelectionModel().getSelectedItem();

		

		// case rec didnt choose anything, diplays Error Message
		if (customer == null) {
			Validation.alert("Customer Error", "Please choose a customer from the table");

			// case everything is valid , removes the cus from the system
		} else {
			subList.getItems().clear();
			matchList.getItems().clear();
			customerTable.getItems().remove(customer);

			subTable.getItems().removeAll(customer.getSubscriptions());
			ViewLogic.sysData.removeCustomer(customer.getId());
			subTable.refresh();
		}
	}

	/**
	 * This method removes the selected subscription from the system
	 */
	@FXML
	private void removeSubscription() {

		// the selected sub
		int index = subList.getSelectionModel().getSelectedIndex();
		Customer customer = customerTable.getSelectionModel().getSelectedItem();
		String header = "Subscription Error";
		String msg = "Please choose a Subscription from the list";
		// case rec didnt choose anything, diplays Error Message
		if (index < 0)
			Validation.alert(header, msg);

		Subscription sub = subArr.get(index);

		// case rec didnt choose anything, diplays Error Message
		if (sub == null) {
			Validation.alert(header, msg);

			// case everything is valid , removes the cus from the system
		} else if (ViewLogic.sysData.removeSubscription(sub.getId())) {
			matchList.getItems().clear();
			subList.getItems().remove(index);
			subArr.remove(sub);
			subList.refresh();
		
			Validation.info("Subscription removed successfully!", sub + " was removed from Customer: " + customer.getFirstName() + " "
					+ customer.getLastName() + ", ID: " + customer.getId());
			
		} else {
			
			Validation.alert("Subscription Remove Error", "removeSubscription returned false.");
		}
	}

	/**
	 * this method updates a selected customer
	 */
	@FXML
	private void updateCustomer() {
		Customer customer = customerTable.getSelectionModel().getSelectedItem();
	

		if (customer == null) {
			Validation.alert("Customer Error!", "Please choose a Customer from the table");
		} else {
			UpdateCustomerController.cusID = customer.getId();
			// closeWindow();
			ViewLogic.newUpdateCustomerData();
		}
	}

	/**
	 * This method displays the subscriptions of the selected customer, on the list
	 * and enables some buttons
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
					matchName.add(temp.toString());

				matchSubList.getItems().addAll(matchName);
				matchSubList.refresh();
			}
		}
	}

	/**
	 * Action Listener of Active City combo box. it generates active city per
	 * stadium
	 */
	@FXML
	private void comboActiveCity(ActionEvent event) {
		int index = comboStadiumActiveCity.getSelectionModel().getSelectedIndex();
		E_Cities city = ViewLogic.sysData.getTheMostActiveCity(allStadiumArr.get(index).getId());
		if (city != null)
			activeCityField.setText(city.toString());
	}

	/**
	 * Action Listener of Generate Xor button.
	 */
	@FXML
	private void generateXor(ActionEvent event) {
		ArrayList<Customer> returnedXor = new ArrayList<>();
		int indexStd1 = comboStadium1.getSelectionModel().getSelectedIndex();
		int indexStd2 = comboStadium2.getSelectionModel().getSelectedIndex();
		String header = "Stadium Error";
		String msg = "Please choose Stadium1 from the comboBox";

		if (indexStd1 >= 0 && indexStd2 >= 0) {
			int stad1 = allStadiumArr.get(indexStd1).getId();
			int stad2 = allStadiumArr.get(indexStd2).getId();
			returnedXor = ViewLogic.sysData.getCustomersStadium1XORStadium2(stad1, stad2);

			// fills the table with data to display
			xorCustomers = FXCollections.observableArrayList();
			xorTable.getItems().clear();

			if (returnedXor != null) {
				xorCustomers.addAll(returnedXor);
				xorTable.setItems(xorCustomers);
			}
		}
		// case stadium 1 is invalid
		else if (indexStd1 < 0)
			Validation.alert(header, msg);
		// case stadium 2 is invalid
		else if (indexStd2 < 0) {
			Validation.alert(header, "Please choose Stadium2 from the comboBox");
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

			if (butt.equals(logoutButton)) {
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

			if (butt.equals(logoutButton)) {
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
