package View;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import Controller.Validation;
import Model.Address;
import Model.Customer;
import Model.Match;
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
import utils.E_Levels;

/**
 * Class AdminCustomerController ~ Admin Customer window.
 * 
 * @author ID: 205791056
 * @author ID: 312181605
 */
public class AdminCustomerController extends AllWindowsController {

	// ------------------------------ Variables ------------------------------

	// ========================== All Window Vars ==========================
	@FXML
	private Tab generalQueriesTab;
	@FXML
	private Tab subSoldTab;
	@FXML
	private Tab cusPropertiesTab;
	@FXML
	private Button logoutButton;
	@FXML
	private Button backButton;
	@FXML
	private HBox pane;
	@FXML
	private Label currentRecepDetails;

	// ========================== Update Customer Vars ==========================
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

	// ================================ Class Vars ================================	
	private ObservableList<Customer> customers;
	private ArrayList<String> MatchName;
	private ArrayList<Match> allMatchArr = new ArrayList<>(); // combobox
	private ArrayList<Subscription> subArr = new ArrayList<>();
	private ObservableList<Match> cusMatches; // list
	private ObservableList<Subscription> subsOfCus; // list

	// ========================== General Queries Vars ==========================
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

	// private ArrayList<Player> playerArr;

	// ------------------------------ Methods ------------------------------
	// ========================== General Methods ==========================
	@Override
	public void initialize() {
		ViewLogic.adminCustomerController = this;

		idColumnCus.setCellValueFactory(new PropertyValueFactory<>("id")); // According to variable name
		firstNameColumnCus.setCellValueFactory(new PropertyValueFactory<>("firstName")); // Same here
		lastNameColumnCus.setCellValueFactory(new PropertyValueFactory<>("lastName")); // And here
		birthdateColumnCus.setCellValueFactory(new PropertyValueFactory<>("birthdate")); // And here
		levelColumnCus.setCellValueFactory(new PropertyValueFactory<>("level")); // And here
		emailColumnCus.setCellValueFactory(new PropertyValueFactory<>("email")); // And here
		addressColumnCus.setCellValueFactory(new PropertyValueFactory<>("address")); // And here
		teamColumnCus.setCellValueFactory(new PropertyValueFactory<>("favoriteTeam")); // And here

		idColumnXor.setCellValueFactory(new PropertyValueFactory<>("id")); // According to variable name
		firstNameColumnXor.setCellValueFactory(new PropertyValueFactory<>("firstName")); // Same here
		lastNameColumnXor.setCellValueFactory(new PropertyValueFactory<>("lastName")); // And here
		birthdateColumnXor.setCellValueFactory(new PropertyValueFactory<>("birthdate")); // And here
		levelColumnXor.setCellValueFactory(new PropertyValueFactory<>("level")); // And here
		emailColumnXor.setCellValueFactory(new PropertyValueFactory<>("email")); // And here
		addressColumnXor.setCellValueFactory(new PropertyValueFactory<>("address")); // And here
		teamColumnXor.setCellValueFactory(new PropertyValueFactory<>("favoriteTeam")); // And here
		MatchName = new ArrayList<>();
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

		ViewLogic.adminCustomerController = null;
	}

	/**
	 * This method sets Customer Properties tab
	 */
	private void setCustomerProperties() {
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
			if (temp != null) {
				if (temp.getHomeTeam().getStadium() != null)
					MatchName.add(temp.showSumDetails());
			}
		comboMatches.getItems().setAll(MatchName);
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
	}
	// ========================== Action Listeners ==========================

	/**
	 * Return to the previous Window
	 */
	@FXML
	void goBack(ActionEvent event) {
		closeWindow();
		ViewLogic.newAdminWindow();
	}

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

		// match list clear
		matchList.getItems().clear();

		Customer customer = customerTable.getSelectionModel().getSelectedItem();

		// case a customer is selected
		if (customer != null)
			setSubList(customer);
	}



	/**
	 * This method and enables removeMatchBut
	 * 
	 * @param event
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

			// case user didnt chose a customer
			try {

				if (customer == null)
					Validation.alert("Customer Error", "Please choose a customer from the table.");
				else if (customer.getSubscriptions() == null
						|| (customer.getSubscriptions() != null && customer.getSubscriptions().isEmpty())) {

					Validation.alert("Customer Error", "Customer must be subscribed. Add a subscription and try again");
				} else if (ViewLogic.sysData.addCustomerToMatch(customer.getId(), match.getId())) {

					matchList.getItems().add(match.showSumDetails());

					Validation.info("Match added successfully!", customer.getFirstName() + " " + customer.getLastName() + ", ID: "
							+ customer.getId() + " was added to match: " + match.showSumDetails());

				} else {
					Validation.alert("Add Customer to Match Error", "addCustomerToMatch returned false");
				}
			} catch (Exception e) {
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

		if (sub != null) {
			// case a stadium is selected
			ObservableList<String> cusMatchName = FXCollections.observableArrayList();
			cusMatches = FXCollections.observableArrayList();

			cusMatches.addAll(sub.getMatches());

			// creates an array of names and adds him to the list
			if (cusMatches != null && !cusMatches.isEmpty()) {
				for (Match temp : cusMatches)
					cusMatchName.add(temp.showSumDetails());
				matchList.getItems().addAll(cusMatchName);
			}
		}
	}

	/**
	 * this method adds customer to customer Table
	 */
	@FXML
	private void addCustomer() {
		// closeWindow();
		CustomerCreationController.isAdmin = true;
		ViewLogic.newNewCustomerCreation();
	}

	/**
	 * this method adds subscription to selected customer
	 */
	@FXML
	private void addSub() {
		Customer customer = customerTable.getSelectionModel().getSelectedItem();


		if (customer == null) {
			Validation.alert("Customer Error!",  "Please choose a Customer from the table");
		} else {
			AdminCusSubCreationController.cusID = customer.getId();
			AdminCusSubCreationController.isAdmin = true;
			// closeWindow();
			ViewLogic.newAdminCusSubWindow();
		}
	}

	/**
	 * This method removes the selected stadium from the system
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
			ViewLogic.sysData.removeCustomer(customer.getId());
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
			Validation.info("Subscription removed successfully!", sub + " was removed from Customer: " + customer.getFirstName() + " "
					+ customer.getLastName() + ", ID: " + customer.getId());


		} else {
			
			Validation.alert( "Subscription Remove Error", "removeSubscription returned false.");
		}
	}

	/**
	 * This method updates a customer
	 */
	@FXML
	private void updateCustomer() {
		Customer customer = customerTable.getSelectionModel().getSelectedItem();
		
		if (customer == null) {
			Validation.alert("Customer Error!", "Please choose a Customer from the table");
		} else {
			UpdateCustomerController.cusID = customer.getId();
			UpdateCustomerController.isAdmin = true;
			// closeWindow();
			ViewLogic.newUpdateCustomerData();
		}
	}

	/**
	 * Action Listener of Generate Xor button.
	 */
	@FXML
	private void generateXor() {
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
			msg = "Please choose Stadium2 from the comboBox";
			Validation.alert(header, msg);
		}
	}

	/**
	 * A method that sets the Subscriptions list
	 * @param customer
	 */
	protected void setSubList(Customer customer) {

		ObservableList<String> subName = FXCollections.observableArrayList();
		subsOfCus = FXCollections.observableArrayList();
		subsOfCus.addAll(customer.getSubscriptions());

		// creates an array of names and add him to the list
		if (!subsOfCus.isEmpty()) {

			for (Subscription temp : subsOfCus)
				subName.add(temp.toString());

			subArr.clear();
			subArr.addAll(subsOfCus);
			subList.getItems().setAll(subName);
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