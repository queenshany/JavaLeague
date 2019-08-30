package View;

import java.util.ArrayList;
import java.util.Date;

import Controller.Validation;
import Exceptions.FanException;
import Model.Customer;
import Model.Match;
import Model.Receptionist;
import Model.Subscription;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utils.E_Periods;

/**
 * Class CustomerMainController ~ Controller of Customer-Main.
 * 
 * @author ID: 205791056
 * @author ID: 312181605
 */
public class CustomerMainController extends AllWindowsController {

	// ------------------------------ Variables ------------------------------
	Customer cus = ViewLogic.sysData.getCustomers().get(ViewLogic.currentUserID);

	@FXML
	private Button logoutButton;
	@FXML
	private HBox pane;
	@FXML
	private Label currentCusDetails;

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
	private ListView<String> subList;

	//private ObservableList<Customer> customers;

	 // combobox

	//private ObservableList<Subscription> subsOfCus; // list

	// ========================== Sub Sold Table ==========================
	@FXML
	private TableView<Subscription> subTable;
	@FXML
	private TableColumn<Subscription, Integer> idColumnSub;
	@FXML
	private TableColumn<Subscription, E_Periods> periodColumnSub;
	@FXML
	private TableColumn<Subscription, Date> startDateColumnSub;
	@FXML
	private TableColumn<Subscription, Receptionist> recColumnSub;
	@FXML
	private ListView<String> matchSubList;
	
	
	private ObservableList<Match> subMatches;
	private	ObservableList<Subscription> subscriptions;
	private ArrayList<String> MatchName = new ArrayList<>();
	private ArrayList<Match> allMatchArr = new ArrayList<>();


	// ------------------------------ Methods ------------------------------
	// ========================== General Methods ==========================
	@Override
	public void initialize() {
		ViewLogic.customerMainController = this;

		idColumnSub.setCellValueFactory(new PropertyValueFactory<>("id")); // According to variable name
		periodColumnSub.setCellValueFactory(new PropertyValueFactory<>("period")); // And here
		startDateColumnSub.setCellValueFactory(new PropertyValueFactory<>("startDate")); // And here
		recColumnSub.setCellValueFactory(new PropertyValueFactory<>("receptionist"));
		currentCusDetails.setText(cus.toString());
		setSubTable();
		setMatchesComboBox();
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
		ViewLogic.customerMainController = null;
	}

	/**
	 * Setting match combobox
	 */
	private void setMatchesComboBox() {
		allMatchArr.addAll(ViewLogic.sysData.getMatchs().values());

		for (Match temp : allMatchArr)
			if (temp.getHomeTeam().getStadium() != null)
				MatchName.add(temp.showSumDetails());

		comboMatches.getItems().addAll(MatchName);
	}

	/**
	 * This method updates the sub table in Update Team tab
	 */
	protected void setSubTable() {

		// fills the table with data to display
		subscriptions = FXCollections.observableArrayList();
		subscriptions.setAll(cus.getSubscriptions());

		if (subscriptions != null)
			subTable.setItems(subscriptions);

		subTable.refresh();

	}

	private void setMatchList(Subscription sub) {
		if (sub != null) {
			subMatches = FXCollections.observableArrayList();
			ObservableList<String> matchName = FXCollections.observableArrayList();
			subMatches.setAll(sub.getMatches());

			// creates an array of names and and him to the list
			if (!subMatches.isEmpty()) {
				for (Match temp : subMatches)
					matchName.add(temp.toString());

				matchSubList.getItems().setAll(matchName);
			} else
				removeMatchBut.setDisable(true);
		}
		matchSubList.refresh();
	}

	// ========================== Action Listeners ==========================
	/**
	 * This method enables removeMatchBut
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
			// case user didnt chose a customer


			try {
				if (cus.getSubscriptions() == null
						|| (cus.getSubscriptions() != null && cus.getSubscriptions().isEmpty())) {

					Validation.alert( "Customer Error", "Customer must be subscribed. Add a subscription and try again");

				} else if (ViewLogic.sysData.addCustomerToMatch(cus.getId(), match.getId())) {

					Validation.info("Match added successfully!", cus.getFirstName() + " " + cus.getLastName() + ", ID: " + cus.getId()
					+ " was added to match: " + match.showSumDetails());
				
					setSubTable();
					
				}
			} catch (FanException e) {
				Validation.alert( "Add Customer to Match Error", e.getMessage());
			} 
		}
	}

	/**
	 * This method allows removing match from customer
	 */
	@FXML
	private void removeMatch() {
		// the index of the selected match
		int indexMatch = matchSubList.getSelectionModel().getSelectedIndex();
		// if (!matchList.is
		//

		

		Subscription sub = subTable.getSelectionModel().getSelectedItem();

		// case the user didnt chose a receptionist
		if (sub == null)
			Validation.alert("Subscription Error", "Please choose a Subscription from the table.");

		else if (indexMatch < 0) {
			
			Validation.alert("Match Error", "Please choose a Match from the list.");
			// case everything is valid
		} else {

			Match match = subMatches.get(indexMatch);
			matchSubList.getItems().remove(match.showSumDetails());
			match.removeFan(cus);
			sub.removeMatch(match);
			matchSubList.getItems().clear();
			setMatchList(sub);
			
			Validation.info("Match removed successfully!", match.showSumDetails() + " was removed from Sub: " + sub.getId());
		
		}
	}

	/**
	 * this method adds subscription to selected customer
	 */
	@FXML
	private void addSub() {
		AdminCusSubCreationController.cusID = cus.getId();
		// closeWindow();
		ViewLogic.newAdminCusSubWindow();
	}

	/**
	 * This method removes the selected subscription from the system
	 */
	@FXML
	private void removeSubscription() {

		// the selected sub
		Subscription sub = subTable.getSelectionModel().getSelectedItem();
	
		// case rec didnt choose anything, diplays Error Message
		if (sub == null) {
			Validation.alert("Subscription Error", "Please choose a Subscription from the list");

			// case everything is valid , removes the cus from the system
		} else if (ViewLogic.sysData.removeSubscription(sub.getId())) {
			matchSubList.getItems().clear();
			subTable.getItems().remove(sub);
		
			Validation.info("Subscription removed successfully!", sub + " was removed from Customer: " + cus.getFirstName() + " " + cus.getLastName()
			+ ", ID: " + cus.getId());
			
		} else {
		
			Validation.alert("Subscription Remove Error", "removeSubscription returned false.");
		}
	}

	/**
	 * This method displays the subscriptions of the selected customer, on the list
	 * and enables some buttons
	 */
	@FXML
	private void subTableOnClick() {
		removeMatchBut.setDisable(false);
		removeSubBut.setDisable(false);
		// sublist
		matchSubList.getItems().clear();
		matchSubList.refresh();
		Subscription sub = subTable.getSelectionModel().getSelectedItem();

		// case a customer is selected
		setMatchList(sub);
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
