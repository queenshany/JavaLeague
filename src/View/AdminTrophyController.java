package View;

import Controller.Validation;
import Model.Trophy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.E_Trophy;

@SuppressWarnings("rawtypes")
/**
 * Class AdminTrophyController ~ Admin Trophy window.
 * 
 * @author ID: 205791056
 * @author ID: 312181605
 */
public class AdminTrophyController extends AllWindowsController {
	// ------------------------------ Variables ------------------------------
	@FXML
	private Button backButton;

	@FXML
	private Button logoutButton;

	@FXML
	private Button addTrophyButton;

	@FXML
	private TextField mostTrophiesField;

	@FXML
	private Button removeTrophyButton;

	// ========================== Trophies DB Table ==========================
	@FXML
	private TableView<Trophy> trophiesTable;

	@FXML
	private TableColumn<Trophy, E_Trophy> trophyTypeColumn;

	@FXML
	private TableColumn<Trophy, ?> ownerColumn;

	@FXML
	private TableColumn<Trophy, String> dateColumn;

	private ObservableList<Trophy> trophies;

	// ------------------------------ Methods ------------------------------
	// ========================== General Methods ==========================
	@Override
	public void initialize() {
		ViewLogic.adminTrophyController = this;

		trophyTypeColumn.setCellValueFactory(new PropertyValueFactory<>("trophy"));
		ownerColumn.setCellValueFactory(new PropertyValueFactory<>("owner"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("trophyWinningDate"));

		setTrophyTable();
		setMostTrophy();

	}

	@Override
	protected void closeWindow() {
		Stage stage = (Stage) logoutButton.getScene().getWindow();
		stage.close();
		ViewLogic.adminTrophyController = null;
	}

	/**
	 * Setting the trophy table
	 */
	protected void setTrophyTable() {

		trophies = FXCollections.observableArrayList(); // Create new ObservableList (The only kind of list that can
		// populate a JavaFX TableView)
		trophies.setAll(ViewLogic.sysData.getTrophies());
		trophiesTable.setItems(trophies); // Tell the TableView to take it's data from the ObservableList
		// tableCoach.getItems().addAll(ViewLogic.sysData.getCoachs().values());
		trophiesTable.refresh();
	}

	/**
	 * Sets the most trophies textfield
	 */
	protected void setMostTrophy() {
		Object entity = ViewLogic.sysData.getEntityWithMostTrophies();
		if (entity != null) {
			mostTrophiesField.setStyle(null);
			mostTrophiesField.setText(entity.toString());
		}
		else {
			mostTrophiesField.setStyle("-fx-text-inner-color: gray;");
			mostTrophiesField.setText("Nobody holds the most trophies :(");
		}

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
	 * Opens Trophy Creation to add Trophy to the system when Add Trophy button is
	 * clicked.
	 */
	@FXML
	private void addTrophy() {

		// closeWindow();
		ViewLogic.newTrophyCreationWindow();
	}

	/**
	 * A method that makes the remove button available for use only after the table
	 * was clicked
	 */
	@FXML
	private void tableClick() {
		if (trophiesTable.getSelectionModel().getSelectedItem() != null)
			removeTrophyButton.setDisable(false);
	}

	/**
	 * Deletes a Trophy from the system
	 * 
	 */
	@FXML
	private void removeTrophy() {

		Trophy trophy = trophiesTable.getSelectionModel().getSelectedItem();

		if (trophy != null) {

			if (ViewLogic.sysData.removeTrophy(trophy)) {
				trophiesTable.getItems().remove(trophy);
				setMostTrophy();

			
				Validation.info("Removed Successfully", "The trophy was removed successfully");
				
			} else {
				Validation.alert("Trophy Error", "Error occurred");
			}

		} else
			Validation.alert("Trophy Error", "Please select a trophy to remove");

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
