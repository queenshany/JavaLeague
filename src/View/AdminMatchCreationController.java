package View;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import Exceptions.MatchException;

import Model.Team;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Class AdminMatchCreationController ~ add new match to the system window
 * 
 * @author ID: 205791056
 * @author ID: 312181605
 */
public class AdminMatchCreationController extends AllWindowsController {
	// ------------------------------ Variables ------------------------------
	@FXML
	private VBox vboxPane;

	@FXML
	private GridPane Grid;

	@FXML
	private Label alertLabel;

	@FXML
	private TextField idText;

	@FXML
	private DatePicker matchdate;

	@FXML
	private ComboBox<String> comboHteam;

	@FXML
	private ComboBox<String> comboAteam;

	@FXML
	private TextField textHscore;

	@FXML
	private TextField textAscore;

	@FXML
	private TextField textDuration;

	@FXML
	private TextField textExtra;

	@FXML
	private Spinner<Integer> hrSpinner;

	@FXML
	private Spinner<Integer> minSpinner;

	@FXML
	private Button submitButton;

	// ======================================= Class Variables
	// ==========================

	private ArrayList<String> teamNames;

	private ArrayList<Team> teams;

	// ------------------------------ Methods ------------------------------
	// ========================== General Methods ==========================

	@Override
	public void initialize() {
		vboxPane.setStyle("-fx-background-image: url(\"/resources/img-addMatchBackground.jpg\");"
				+ "-fx-background-repeat: no-repeat; -fx-background-size: stretch;");
		teams = new ArrayList<>(ViewLogic.sysData.getTeams().values());
		teamNames = new ArrayList<>();

		for (Team temp : teams)
			teamNames.add(
					Integer.toString(temp.getId()) + " | Name: " + temp.getName() + " | Level: " + temp.getLevel());

		comboAteam.getItems().setAll(teamNames);
		comboHteam.getItems().setAll(teamNames);

		textAscore.setText("0");
		textHscore.setText("0");
		textExtra.setText("0");

		// setting spinners
		hrSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23));
		minSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59));
	}

	@Override
	protected void closeWindow() {
		Stage stage = (Stage) submitButton.getScene().getWindow();
		stage.close();
	}

	// ========================== Action Listeners ==========================
	@SuppressWarnings("deprecation")
	/**
	 * When submit button is clicked, we check the data. If all is valid, we add the
	 * match to the system
	 */
	@FXML
	private void submit() {
		// getting all the input values
		alertLabel.setText("");

		String idStr = idText.getText(), extraStr = textExtra.getText(), aScoreStr = textAscore.getText(),
				hScoreStr = textHscore.getText();

		int indexAteam = comboAteam.getSelectionModel().getSelectedIndex(),
				indexHteam = comboHteam.getSelectionModel().getSelectedIndex();

		LocalDate locDate = matchdate.getValue();

		try {
			// Validates ID
			int id = Integer.parseInt(idStr);

			if (id > 0) {
				if (!ViewLogic.sysData.getMatchs().containsKey(id)) {
					// Validates awayTeamScore
					if (indexHteam >= 0) {
						try {
							// Validates homeTeamScore
							int hScore = Integer.parseInt(hScoreStr);

							if (hScore >= 0) {
								// Validates homeTeam
								if (indexAteam >= 0) {
									Team aTeam = teams.get(indexAteam), hTeam = teams.get(indexHteam);

									// Validates different teams
									if (!aTeam.equals(hTeam)) {

										try {
											// Validates awayTeamScore
											int aScore = Integer.parseInt(aScoreStr);
											if (aScore >= 0) {

												// Validates date
												if (locDate != null) {
													Date date = Date.from(
															locDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
													date.setHours(hrSpinner.getValue());
													date.setMinutes(minSpinner.getValue());

													try {
														// Validates extraTime
														int extraTime = Integer.parseInt(extraStr);

														if (extraTime >= 0) {
															// adds the match and cleans the system

															try {
																if (ViewLogic.sysData.addMatch(id, date, extraTime,
																		hTeam.getId(), aTeam.getId(), hScore, aScore)) {
																	textAscore.clear();
																	// textDuration.clear();
																	textExtra.clear();
																	textHscore.clear();
																	idText.clear();
																	comboHteam.getSelectionModel().clearSelection();
																	comboAteam.getSelectionModel().clearSelection();
																	matchdate.getEditor().clear();
																	alertLabel.setText("Match was added successfully");

																	if (ViewLogic.adminMatchController != null)
																		ViewLogic.adminMatchController.setMatchTable();
																} else
																	alertLabel.setText(
																			"match overlaps with another match");
															} catch (MatchException e) {
																alertLabel.setText(e.getMessage());
															}

														} else
															alertLabel.setText("Invalid extra time");

													} catch (NumberFormatException e) {
														if (extraStr.equals(""))
															alertLabel.setText("Please enter the extra time");
														else
															alertLabel.setText("Invalid extra time");
													}

												} else {
													alertLabel.setText("Please choose a date ");
												}

											} else
												alertLabel.setText("Invalid away team score");

										} catch (NumberFormatException e) {
											if (aScoreStr.equals(""))
												alertLabel.setText("Please enter away team score");
											else
												alertLabel.setText("Invalid away team score");
										}
									} else
										alertLabel.setText("A team can't play agianst itself");

								} else
									alertLabel.setText("Please choose an away team");

							} else
								alertLabel.setText("Invalid home team score");

						} catch (NumberFormatException e) {

							if (hScoreStr.equals(""))
								alertLabel.setText("Please enter home team score");
							else
								alertLabel.setText("Invalid home team score");
						}

					} else
						alertLabel.setText("Please choose a home team");

				} else
					alertLabel.setText("ID already exists");

			} else
				alertLabel.setText("Invalid ID");

		} catch (NumberFormatException e) {

			if (idStr.equals(""))
				alertLabel.setText("Please enter an ID");
			else
				alertLabel.setText("Invalid ID");
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
