package View;

import Controller.Validation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Class LoginController ~ controller of Login window
 * 
 * @author ID: 205791056
 * @author ID: 312181605
 */
public class LoginController extends AbstractController {

	// ------------------------------ Variables ------------------------------
	@FXML
	private Pane pain;
	@FXML
	private Button loginBut;
	@FXML
	private TextField userField;
	@FXML
	private PasswordField pwField;
	@FXML
	private ComboBox<String> languageComboBox;

	// ------------------------------ Methods ------------------------------
	// ========================== General Methods ==========================

	@Override
	public void initialize() {
		// userField.setText("BLAH");
		pain.setStyle("-fx-background-image: url(\"/resources/img-login-background.jpg\");"
				+ "-fx-background-repeat: no-repeat; -fx-background-size: stretch;");
	}

	@Override
	protected void closeWindow() {
		((Stage) userField.getScene().getWindow()).close();
	}

	// ========================== Action Listeners ==========================
	/**
	 * If the user exists in the system, this method allows him to log in when he
	 * presses on the login button
	 */
	@FXML
	private void loginOnAction() {
		String username = userField.getText();
		String pw = pwField.getText();

		ViewLogic.currentUserType = ViewLogic.sysData.validateUser(username, pw);

		if (ViewLogic.currentUserType == null) {
			System.out.println("User Doesn't exist!");

		

			Validation.alert("Login Error!", "Invalid Credentials!");
		}

		else {
			ViewLogic.currentUserID = username;
			System.out.println(ViewLogic.currentUserType + " ID: " + ViewLogic.currentUserID + " has logged in!");
			Sound.playLoginSound();
			
			switch (ViewLogic.currentUserType) {
			case ADMIN:
				closeWindow();
				ViewLogic.newAdminWindow();
				break;

			case COACH:
				closeWindow();
				ViewLogic.newCoachWindow();
				break;

			case RECEPTIONIST:
				closeWindow();
				ViewLogic.newRecepWindow();
				break;

			case CUSTOMER:
				closeWindow();
				ViewLogic.newCusWindow();
				break;
			}
		}
	}

	/**
	 * this method enables logging in pressing Enter
	 */
	@FXML
	private void onKeyReleased(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER)
			loginOnAction();
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

			if (butt.equals(loginBut)) {
				Image image = new Image("resources/icon-sign-in-white.png");
				ImageView imageView = new ImageView(image);
				imageView.setFitWidth(10);
				imageView.setFitHeight(10);
				loginBut.setGraphic(imageView);
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
			
			if (butt.equals(loginBut)) {
				Image image = new Image("resources/icon-sign-in.png");
				ImageView imageView = new ImageView(image);
				imageView.setFitWidth(10);
				imageView.setFitHeight(10);
				loginBut.setGraphic(imageView);
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
