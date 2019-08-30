package View;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import Exceptions.SubException;
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
 * Class SubCreationController ~ add new sub to the system window
 * 
 * @author ID: 205791056
 * @author ID: 312181605
 */
public class SubCreationController extends AllWindowsController {
	// ------------------------------ Variables ------------------------------
	protected static String cusID;
	private String recID = ViewLogic.currentUserID;

	@FXML
	private VBox vboxPane;

	@FXML
	private TextField subIDField;

	@FXML
	private TextField recIDField;

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

	// ------------------------------ Methods ------------------------------
	// ========================== General Methods ==========================
	@Override
	public void initialize() {
		vboxPane.setStyle("-fx-background-image: url(\"/resources/img-newSubBackground.jpg\");"
				+ "-fx-background-repeat: no-repeat; -fx-background-size: cover; ");
		cusIDField.setText(cusID);
		recIDField.setText(recID);
		comboPeriods.getItems().addAll(E_Periods.values());

	}

	@Override
	protected void closeWindow() {
		Stage stage = (Stage) submitButton.getScene().getWindow();
		stage.close();
	}

	// ========================== Action Listeners ==========================
	/**
	 * A method which validates users input and adds the sub to the system
	 */
	@FXML
	private void submit() {

		// getting all the input values
		LocalDate localDate = datePicker.getValue();
		Date startDate = new Date();
		E_Periods period = comboPeriods.getSelectionModel().getSelectedItem();
		try {
			// Validate id
			Integer subID = Integer.parseInt(subIDField.getText());
			Integer receptionistId = Integer.parseInt(recID);
			if (!ViewLogic.sysData.getCustomers().get(cusID).getSubscriptions().contains(new Subscription(subID)))

				// Validate date
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

					if (startDate.after(today) || startDate.equals(today)) {

						// Validate period
						if (period != null) {

							try {
								if (ViewLogic.sysData.addSubscriptionToCustomer(subID, cusID, receptionistId, period,
										startDate)) {
									subIDField.clear();
									comboPeriods.getSelectionModel().clearSelection();
									datePicker.getEditor().clear();
									comboPeriods.getSelectionModel().clearSelection();

									alertLabel.setText("Added succesfully! Add another? if not, close the window");

									if (ViewLogic.recepMainController != null) {
										ViewLogic.recepMainController.tableOnClick();
										ViewLogic.recepMainController.setSubTable();
									}
								} else {
									alertLabel.setText("error occurred.");
								}
							}catch(SubException e) {
								alertLabel.setText(e.getMessage() + ". Contact the Admin and try again.");
							}
						} else
							alertLabel.setText("Please choose a Period");

					} else
						alertLabel.setText("Can't start a Subscription before today!");
				} else
					alertLabel.setText("Sucscription ID already exists!");
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