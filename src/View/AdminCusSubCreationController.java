package View;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Exceptions.SubException;
import Model.Receptionist;
import Model.Subscription;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.E_Periods;

/**
 * Class AdminCusSubCreationController ~ add new sub to the system window
 * 
 * @author ID: 205791056
 * @author ID: 312181605
 */
public class AdminCusSubCreationController extends AllWindowsController {
	// ------------------------------ Variables ------------------------------
	protected static String cusID;
	protected static boolean isAdmin;

	@FXML
	private VBox vboxPane;

	@FXML
	private TextField subIDField;

	@FXML
	private ComboBox<String> comboRec;

	@FXML
	private TextField cusIDField;

	@FXML
	private DatePicker datePicker;

	@FXML
	private ComboBox<E_Periods> comboPeriods;

	@FXML
	private Label alertLabel;

	@FXML
	private Label infoLabel;

	@FXML
	private Button submitButton;

	ArrayList<Receptionist> allRec = new ArrayList<>();
	ArrayList<String> allRecStr = new ArrayList<>();

	// ------------------------------ Methods ------------------------------
	// ========================== General Methods ==========================
	@Override
	public void initialize() {
		vboxPane.setStyle("-fx-background-image: url(\"/resources/img-newSubBackground.jpg\");"
				+ "-fx-background-repeat: no-repeat; -fx-background-size: cover; ");
		cusIDField.setText(cusID);
		setComboRec();
		comboPeriods.getItems().addAll(E_Periods.values());

	}

	@Override
	protected void closeWindow() {
		Stage stage = (Stage) submitButton.getScene().getWindow();
		stage.close();
	}

	/**
	 * Setting Receptionists ComboBox
	 */
	private void setComboRec() {
		allRec.addAll(ViewLogic.sysData.getReceptionists().values());
		for (Receptionist temp : allRec)
			allRecStr.add(temp.getId() + " | " + temp.getFirstName() + " " + temp.getLastName() + " | "
					+ (temp.getWorkingStadium() != null ? "Working Stadium: " + temp.getWorkingStadium().getName()
							: "Doesn't work in a stadium"));
		comboRec.getItems().addAll(allRecStr);
	}

	// ========================== Action Listeners ==========================
	/**
	 * When submit button is clicked, we check the data. If all is valid, we add the
	 * subscription to the system
	 */
	@FXML
	private void submit() {

		// getting all the input values
		LocalDate localDate = datePicker.getValue();
		Date startDate = new Date();
		E_Periods period = comboPeriods.getSelectionModel().getSelectedItem();

		try {
			// Validates ID
			Integer subID = Integer.parseInt(subIDField.getText());
			if (!ViewLogic.sysData.getCustomers().get(cusID).getSubscriptions().contains(new Subscription(subID)))

				// Validates rec
				if (comboRec.getSelectionModel().getSelectedIndex() >= 0) {
					Integer recId = allRec.get(comboRec.getSelectionModel().getSelectedIndex()).getId();
					if (!ViewLogic.sysData.getReceptionists().get(recId).getSubscriptions()
							.contains(new Subscription(subID))) {

						// Validates date
						if (localDate != null) {
							startDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

							Calendar c = Calendar.getInstance();

							// set the calendar to start of today
							c.set(Calendar.HOUR_OF_DAY, 0);
							c.set(Calendar.MINUTE, 0);
							c.set(Calendar.SECOND, 0);
							c.set(Calendar.MILLISECOND, 0);

							// and get that as a Date
							Date today = c.getTime();

							// Checking that Sub starts after today
							if (startDate.equals(today) || startDate.after(today)) {

								// Validates period
								if (period != null) {

									try {
										// adds the sub and cleans the system
										if (ViewLogic.sysData.addSubscriptionToCustomer(subID, cusID, recId, period,
												startDate)) {
											subIDField.clear();
											comboPeriods.getSelectionModel().clearSelection();
											datePicker.getEditor().clear();
											alertLabel.setText(
													"Added succesfully! Add another? if not, close the window");

											// Checking if approached from Admin Customer
											if (isAdmin) {
												if (ViewLogic.adminCustomerController != null) {
													ViewLogic.adminCustomerController.tableOnClick();
													ViewLogic.adminCustomerController
															.setSubList(ViewLogic.sysData.getCustomers().get(cusID));
													isAdmin = false;
												}
											}
											// Checking if approached from Customer Main
											else {
												if (ViewLogic.customerMainController != null)
													ViewLogic.customerMainController.setSubTable();
											}
										} else
											alertLabel.setText("error occurred.");

									} catch (SubException e) {
										alertLabel.setText(e.getMessage());
									}

								} else
									alertLabel.setText("Please choose a Period");
							} else
								alertLabel.setText("Can't start a Subscription before today!");
						} else
							alertLabel.setText("Please pick a date");

					} else
						alertLabel.setText("Subcscription ID already exists in choosen receptionst !");

				} else
					alertLabel.setText("Please Choose a Receptionist");
			else
				alertLabel.setText("Subcscription ID already exists in this customer!");
		} catch (NumberFormatException e) {
			alertLabel.setText("Invalid ID!");
		}
	}

	// ========================== Display ==========================

	/**
	 * Changes buttons' style
	 */
	@FXML
	private void buttonOnMouseEntered(MouseEvent e) {
		Button butt = (Button) e.getSource();

		if (butt.equals(submitButton))
			submitButton.setStyle("-fx-background-color: radial-gradient(center 50% 0%, radius 100%, white, #DDDDDD);");
	}

	/**
	 * Changes buttons' style
	 */
	@FXML
	private void buttonOnMouseExited(MouseEvent e) {
		Button butt = (Button) e.getSource();

		if (butt.equals(submitButton))
			submitButton.setStyle("-fx-background-color: white; -fx-text-fill: black");
	}
}