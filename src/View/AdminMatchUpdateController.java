package View;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;

import Model.Match;
import Model.Stadium;
import Model.Team;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Constants;

/**
 * Class AdminMatchUpdateController ~ updates match in the system window
 * 
 * @author ID: 205791056
 * @author ID: 312181605
 */
public class AdminMatchUpdateController extends AllWindowsController {
	// ------------------------------ Variables ------------------------------
	@FXML
	private VBox vboxPane;

	@FXML
	private TextField idText;

	@FXML
	private DatePicker matchdate;

	@FXML
	private TextField textHscore;

	@FXML
	private TextField textAscore;

	@FXML
	private TextField textHTeam;

	@FXML
	private TextField textATeam;

	@FXML
	private TextField textDuration;

	@FXML
	private TextField textExtra;

	@FXML
	private Label alertLabel;

	@FXML
	private Spinner<Integer> hrSpinner;

	@FXML
	private Spinner<Integer> minSpinner;

	@FXML
	private Button submitButton;

	// ------------------------------ Methods ------------------------------
	// ========================== General Methods ==========================
	@SuppressWarnings("deprecation")
	@Override
	public void initialize() {
		vboxPane.setStyle("-fx-background-image: url(\"/resources/img-addMatchBackground.jpg\");"
				+ "-fx-background-repeat: no-repeat; -fx-background-size: stretch;");
		Match match = AdminMatchController.matchSelected;

		textAscore.setText(Integer.toString(match.getAwayTeamScore()));
		textHscore.setText(Integer.toString(match.getHomeTeamScore()));
		textATeam.setText(match.getAwayTeam().getId() + " | " + match.getAwayTeam().getName());
		textHTeam.setText(match.getHomeTeam().getId() + " | " + match.getHomeTeam().getName());
		textExtra.setText(Integer.toString(match.getExtraTime()));
		idText.setText(Integer.toString(match.getId()));

		LocalDate loc = match.getStartDateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		matchdate.setValue(loc);

		// setting spinners
		hrSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23));
		minSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59));

		hrSpinner.getValueFactory().setValue(match.getStartDateTime().getHours());
		minSpinner.getValueFactory().setValue(match.getStartDateTime().getMinutes());
	}

	@Override
	protected void closeWindow() {
		Stage stage = (Stage) submitButton.getScene().getWindow();
		stage.close();
	}

	/**
	 * This method checks if two match overlap in the system
	 * 
	 * @param matches
	 * @param match
	 * @return true if dates don't overlap, false if they do
	 */
	private boolean isOverLapping(Set<Match> matches, Match match) {

		for (Match temp : matches)
			if (!match.equals(temp))
				if (temp.getStartDateTime().before(match.getFinishDateTime())
						&& match.getStartDateTime().before(temp.getFinishDateTime()))
					return false;
		return true;
	}

	// ========================== Action Listeners ==========================
	@SuppressWarnings("deprecation")
	/**
	 * When submit button is clicked, we check the data. If all is valid, we update
	 * the match to the system
	 */
	@FXML
	private void save() {
		// getting all the input values
		Match match = AdminMatchController.matchSelected;

		alertLabel.setText("");

		Team hTeam = match.getHomeTeam(), aTeam = match.getAwayTeam();

		Stadium stadium = match.getHomeTeam().getStadium();

		String aScoreStr = textAscore.getText(), hScoreStr = textHscore.getText(), extraStr = textExtra.getText();

		LocalDate locDate = matchdate.getValue();

		Date keeper = match.getStartDateTime();

		try {

			// Validates homeTeamScore
			int hScore = Integer.parseInt(hScoreStr);

			if (hScore >= 0) {
				try {
					// Validates awayTeamScore
					int aScore = Integer.parseInt(aScoreStr);
					if (aScore >= 0) {
						// Validates date
						if (locDate != null) {
							Date date = Date.from(locDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
							date.setHours(hrSpinner.getValue());
							date.setMinutes(minSpinner.getValue());

							System.out.println(date);

							try {
								// Validates extraTime
								int extraTime = Integer.parseInt(extraStr);

								if (extraTime >= 0) {
									// checks if dates overlap

									match.setStartDateTime(date);
									match.setExtraTime(extraTime);
									match.setDuration(Constants.DEFAULT_MATCH_DURATION + extraTime);

									if (isOverLapping(hTeam.getMatches(), match)) {
										if (isOverLapping(aTeam.getMatches(), match)) {
											if (isOverLapping(stadium.getMatches(), match)) {

												match.setAwayTeamScore(aScore);

												match.setHomeTeamScore(hScore);

												alertLabel.setText("Match updated successfully");
												ViewLogic.adminMatchController.setMatchTable();

											} else {
												alertLabel.setText(
														"The match overlaps with another match in the stadium");
												match.setStartDateTime(keeper);

											}

										} else {
											alertLabel.setText("Date doesn't fit to away team");
											match.setStartDateTime(keeper);

										}

									} else {
										alertLabel.setText("Date doesn't fit to home team");
										match.setStartDateTime(keeper);

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
				alertLabel.setText("Invalid home team score");

		} catch (NumberFormatException e) {

			if (hScoreStr.equals(""))
				alertLabel.setText("Please enter home team score");
			else
				alertLabel.setText("Invalid home team score");
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
