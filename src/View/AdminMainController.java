package View;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
/**
 * Class AdminMainController ~ Controller of Admin-Main.
 * 
 * @author ID: 205791056
 * @author ID: 312181605
 */
public class AdminMainController extends AllWindowsController{

	// ------------------------------ Variables ------------------------------
	@FXML
	private Button coachBut;
	@FXML
	private Button playerBut;
	@FXML
	private Button matchBut;
	@FXML
	private Button recBut;
	@FXML
	private Button cusBut;
	@FXML
	private Button trophyBut;
	@FXML
	private Button stadBut;
	@FXML
	private Button teamBut;
	@FXML
	private Button logoutButton;
	@FXML
	private BorderPane pane;
	@FXML
	private Label welcomeLabel;

	// ------------------------------ Methods ------------------------------
	// ========================== General Methods ==========================
	@Override
	public void initialize() {
		pane.setStyle("-fx-background-image: url(\"/resources/img-welcomeBackground.jpg\");"
				+ "-fx-background-repeat: no-repeat; -fx-background-size: cover;");
	}

	@Override
	protected void closeWindow() {
		((Stage) pane.getScene().getWindow()).close();
	}

	// ========================== Action Listeners ==========================
	/**
	 * Opens Admin Coach window
	 */
	@FXML
	private void coachOnAction() {
		System.out.println("Open Coach Settings");
		closeWindow();
		ViewLogic.newAdminCoachWindow();
	}

	/**
	 * Opens Admin Player window
	 */
	@FXML
	private void playerOnAction() {
		System.out.println("Open Player Settings");
		closeWindow();
		ViewLogic.newAdminPlayerWindow();
	}

	/**
	 * Opens Admin Match window
	 */
	@FXML
	private void matchOnAction() {
		System.out.println("Open Match Settings");
		closeWindow();
		ViewLogic.newAdminMatchWindow();
	}

	/**
	 * Opens Admin Receptionist window
	 */
	@FXML
	private void recOnAction() {
		System.out.println("Open Receptionist Settings");
		closeWindow();
		ViewLogic.newAdminRecWindow();
	}

	/**
	 * Opens Admin Customer window
	 */
	@FXML
	private void cusOnAction() {
		System.out.println("Open Customer Settings");
		closeWindow();
		ViewLogic.newAdminCusWindow();
	}

	/**
	 * Opens Admin Stadium window
	 */
	@FXML
	private void stadOnAction() {
		System.out.println("Open Stadium Settings");
		closeWindow();
		ViewLogic.newAdminStadiumWindow();
	}

	/**
	 * Opens Admin Team window
	 */
	@FXML
	private void teamOnAction() {
		System.out.println("Open Team Settings");
		closeWindow();
		ViewLogic.newAdminTeamWindow();
	}

	/**
	 * Opens Admin Trophy window
	 */
	@FXML
	private void trophyOnAction() {
		System.out.println("Open Trophy Settings");
		closeWindow();
		ViewLogic.newAdminTrophyWindow();
	}

	// ========================== Display ==========================

	/**
	 * Changes buttons' style
	 */
	@FXML
	private void buttonOnMouseEntered(MouseEvent e) {
		try {
			Button butt = (Button) e.getSource(); 
			double dimension;

			if (butt.equals(logoutButton)) {
				dimension = 10;

				logoutButton.setStyle("-fx-background-color: black; -fx-text-fill: white");
				
				Image image = new Image("resources/icon-sign-out-white.png");
				ImageView imageView = new ImageView(image);
				imageView.setFitWidth(dimension);
				imageView.setFitHeight(dimension);
				logoutButton.setGraphic(imageView);
			}
			else {
				butt.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-content-display: top");
				dimension = 30;

				if (butt.equals(coachBut)) {
					Image image = new Image("resources/icon-coach-white.png");
					ImageView imageView = new ImageView(image);
					imageView.setFitWidth(dimension);
					imageView.setFitHeight(dimension);
					coachBut.setGraphic(imageView);
				}

				else if (butt.equals(playerBut)) {
					Image image = new Image("resources/icon-player-white.png");
					ImageView imageView = new ImageView(image);
					imageView.setFitWidth(dimension);
					imageView.setFitHeight(dimension);
					playerBut.setGraphic(imageView);
				}

				else if (butt.equals(matchBut)) {
					Image image = new Image("resources/icon-match-white.png");
					ImageView imageView = new ImageView(image);
					imageView.setFitWidth(dimension);
					imageView.setFitHeight(dimension);
					matchBut.setGraphic(imageView);
				}

				else if (butt.equals(teamBut)) {
					Image image = new Image("resources/icon-team-white.png");
					ImageView imageView = new ImageView(image);
					imageView.setFitWidth(dimension);
					imageView.setFitHeight(dimension);
					teamBut.setGraphic(imageView);
				}

				else if (butt.equals(trophyBut)) {
					Image image = new Image("resources/icon-trophy-white.png");
					ImageView imageView = new ImageView(image);
					imageView.setFitWidth(dimension);
					imageView.setFitHeight(dimension);
					trophyBut.setGraphic(imageView);
				}

				else if (butt.equals(stadBut)) {
					Image image = new Image("resources/icon-stadium-white.png");
					ImageView imageView = new ImageView(image);
					imageView.setFitWidth(dimension);
					imageView.setFitHeight(dimension);
					stadBut.setGraphic(imageView);
				}

				else if (butt.equals(recBut)) {
					Image image = new Image("resources/icon-receptionist-white.png");
					ImageView imageView = new ImageView(image);
					imageView.setFitWidth(dimension);
					imageView.setFitHeight(dimension);
					recBut.setGraphic(imageView);
				}

				else if (butt.equals(cusBut)) {
					Image image = new Image("resources/icon-customer-white.png");
					ImageView imageView = new ImageView(image);
					imageView.setFitWidth(dimension);
					imageView.setFitHeight(dimension);
					cusBut.setGraphic(imageView);
				}
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
	private void buttonOnMouseExited(MouseEvent e) {
		try {
			//((Button) e.getSource()).setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-content-display: top");
			
			Button butt = (Button) e.getSource(); 
			double dimension;

			if (butt.equals(logoutButton)) {
				dimension = 10;

				logoutButton.setStyle("-fx-background-color: white; -fx-text-fill: black");
				
				Image image = new Image("resources/icon-sign-out.png");
				ImageView imageView = new ImageView(image);
				imageView.setFitWidth(dimension);
				imageView.setFitHeight(dimension);
				logoutButton.setGraphic(imageView);
			}
			else {
				butt.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-content-display: top");
				dimension = 30;

				if (butt.equals(coachBut)) {
					Image image = new Image("resources/icon-coach.png");
					ImageView imageView = new ImageView(image);
					imageView.setFitWidth(dimension);
					imageView.setFitHeight(dimension);
					coachBut.setGraphic(imageView);
				}

				else if (butt.equals(playerBut)) {
					Image image = new Image("resources/icon-player.png");
					ImageView imageView = new ImageView(image);
					imageView.setFitWidth(dimension);
					imageView.setFitHeight(dimension);
					playerBut.setGraphic(imageView);
				}

				else if (butt.equals(matchBut)) {
					Image image = new Image("resources/icon-match.png");
					ImageView imageView = new ImageView(image);
					imageView.setFitWidth(dimension);
					imageView.setFitHeight(dimension);
					matchBut.setGraphic(imageView);
				}

				else if (butt.equals(teamBut)) {
					Image image = new Image("resources/icon-team1.png");
					ImageView imageView = new ImageView(image);
					imageView.setFitWidth(dimension);
					imageView.setFitHeight(dimension);
					teamBut.setGraphic(imageView);
				}

				else if (butt.equals(trophyBut)) {
					Image image = new Image("resources/icon-trophy.png");
					ImageView imageView = new ImageView(image);
					imageView.setFitWidth(dimension);
					imageView.setFitHeight(dimension);
					trophyBut.setGraphic(imageView);
				}

				else if (butt.equals(stadBut)) {
					Image image = new Image("resources/icon-stadium.png");
					ImageView imageView = new ImageView(image);
					imageView.setFitWidth(dimension);
					imageView.setFitHeight(dimension);
					stadBut.setGraphic(imageView);
				}

				else if (butt.equals(recBut)) {
					Image image = new Image("resources/icon-receptionist.png");
					ImageView imageView = new ImageView(image);
					imageView.setFitWidth(dimension);
					imageView.setFitHeight(dimension);
					recBut.setGraphic(imageView);
				}

				else if (butt.equals(cusBut)) {
					Image image = new Image("resources/icon-customer.png");
					ImageView imageView = new ImageView(image);
					imageView.setFitWidth(dimension);
					imageView.setFitHeight(dimension);
					cusBut.setGraphic(imageView);
				}
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
